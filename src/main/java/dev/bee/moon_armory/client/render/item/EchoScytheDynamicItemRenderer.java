package dev.bee.moon_armory.client.render.item;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.bee.moon_armory.MoonArmory;
import dev.bee.moon_armory.client.render.entity.model.EchoScythePosing;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.util.Pair;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.NotNull;

public class EchoScytheDynamicItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    public static final List<ModelIdentifier> MODELS_TO_REGISTER = new ArrayList<>();
    public static final Pair<ModelIdentifier, ModelIdentifier> DEFAULT_MODEL_IDENTIFIER = registerVariantModelPair("");

    public EchoScytheDynamicItemRenderer() {}

    private static @NotNull Pair<ModelIdentifier, ModelIdentifier> registerVariantModelPair(String name) {
        String suffix = name.isEmpty() ? "" : "_";
        String modelName = "echo_scythe" + suffix + name;
        ModelIdentifier inventoryModel = new ModelIdentifier(MoonArmory.id(modelName + "_gui"), "inventory");
        ModelIdentifier inHandModel = new ModelIdentifier(MoonArmory.id(modelName + "_hand"), "inventory");
        MODELS_TO_REGISTER.add(inventoryModel);
        MODELS_TO_REGISTER.add(inHandModel);
        return new Pair<>(inventoryModel, inHandModel);
    }

    private static @NotNull Pair<ModelIdentifier, ModelIdentifier> getModelIdentifierPair(ItemStack stack) {
        Pair<ModelIdentifier, ModelIdentifier> modelIdentifierPair = DEFAULT_MODEL_IDENTIFIER;
        return modelIdentifierPair;
    }

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        MinecraftClient client = MinecraftClient.getInstance();
        LivingEntity player = client.player;

        boolean inHand = mode.isFirstPerson() || mode == ModelTransformationMode.THIRD_PERSON_RIGHT_HAND
                || mode == ModelTransformationMode.THIRD_PERSON_LEFT_HAND
                || mode == ModelTransformationMode.FIXED
                || mode == ModelTransformationMode.GROUND;
        boolean inInventory = mode == ModelTransformationMode.GUI;

        matrices.push();

        if (inHand && player != null) {
            matrices.scale(1.0F, 1.5F, 1.5F);
            matrices.translate(-0.05F, -0.65F, 0.06F);
            matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(10.0F));
            float swingProgress = player.getHandSwingProgress(client.getTickDelta());
            if (swingProgress > 0.0F) {
                // Swing animation logic
//                float weaponMovement = swingProgress;
//                float swingAngle = -40.0F * swingProgress; // Rotate based on swing progress
//                matrices.translate(0.0F, weaponMovement, 0.0F);
//                matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(swingAngle)); // Swing the weapon
            }
        }

        Pair<ModelIdentifier, ModelIdentifier> modelIdentifierPair = getModelIdentifierPair(stack);
        BakedModel model = MinecraftClient.getInstance().getBakedModelManager().getModel(
                !inHand ? modelIdentifierPair.getLeft() : modelIdentifierPair.getRight()
        );

        if (inInventory) {
            RenderSystem.enableBlend();
            matrices.translate(0.5F, 0.5F, 0.0F);
        }

        MinecraftClient.getInstance().getItemRenderer().renderItem(
                stack, mode, false, matrices, vertexConsumers, light, overlay, model
        );

        if (vertexConsumers instanceof VertexConsumerProvider.Immediate immediate) {
            immediate.draw();
        }

        if (inInventory) {
            RenderSystem.disableBlend();
        }

        matrices.pop();
    }
}