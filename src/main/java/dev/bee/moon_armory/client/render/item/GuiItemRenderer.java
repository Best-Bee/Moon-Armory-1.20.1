package dev.bee.moon_armory.client.render.item;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceReloadListenerKeys;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class GuiItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer, SimpleSynchronousResourceReloadListener {
    private static final Set<ModelTransformationMode> inventoryModes;
    private final Identifier id;
    private final Identifier weaponId;
    private ItemRenderer itemRenderer;
    private BakedModel inventoryItemModel;
    private BakedModel worldItemModel;

    public GuiItemRenderer(Identifier weaponId) {
        this.id = new Identifier(weaponId.getNamespace(), weaponId.getPath() + "_renderer");
        this.weaponId = weaponId;

    }

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.pop();
        matrices.push();
        if (inventoryModes.contains(mode)) {
            matrices.translate(0.0F, 10.5F, 0.0F);
            this.itemRenderer.renderItem(stack, mode, false, matrices, vertexConsumers, light, overlay, this.inventoryItemModel);
        } else {
            boolean leftHanded;
            switch (mode) {
                case FIRST_PERSON_LEFT_HAND:
                case THIRD_PERSON_LEFT_HAND:
                    leftHanded = true;
                    break;
                default:
                    leftHanded = false;
            }
            this.itemRenderer.renderItem(stack, mode, leftHanded, matrices, vertexConsumers, light, overlay, this.worldItemModel);
        }
    }

    @Override
    public Identifier getFabricId() {
        return this.id;
    }

    @Override
    public Collection<Identifier> getFabricDependencies() {
        return Collections.singletonList(ResourceReloadListenerKeys.MODELS);
    }

    @Override
    public void reload(ResourceManager manager) {
        final MinecraftClient client = MinecraftClient.getInstance();
        this.itemRenderer = client.getItemRenderer();
        this.inventoryItemModel = client.getBakedModelManager().getModel(new ModelIdentifier(this.weaponId.getNamespace(), this.weaponId + "_gui", "inventory"));
        this.worldItemModel = client.getBakedModelManager().getModel(new ModelIdentifier(this.weaponId.getNamespace(), this.weaponId + "_handheld", "inventory"));
    }

    static {
            inventoryModes = Set.of(ModelTransformationMode.GUI, ModelTransformationMode.GROUND);
    }

}
