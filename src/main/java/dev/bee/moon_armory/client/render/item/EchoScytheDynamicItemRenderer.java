package dev.bee.moon_armory.client.render.item;

//import dev.bee.moon_armory.index.ArsenalCosmetics;
//import dev.bee.moon_armory.item.ScytheItem;
//import dev.bee.moon_armory.item.ScytheItem.Skin;
import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.bee.moon_armory.MoonArmory;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.NotNull;

public class EchoScytheDynamicItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    public static final List<ModelIdentifier> MODELS_TO_REGISTER = new ArrayList<>();
    public static final Pair<ModelIdentifier, ModelIdentifier> DEFAULT_MODEL_IDENTIFIER = registerVariantModelPair("");
//    public static final Pair<ModelIdentifier, ModelIdentifier> CLOWN_MODEL_IDENTIFIER;
//    public static final Pair<ModelIdentifier, ModelIdentifier> CARRION_MODEL_IDENTIFIER;
//    public static final Pair<ModelIdentifier, ModelIdentifier> GILDED_MODEL_IDENTIFIER;
//    public static final Pair<ModelIdentifier, ModelIdentifier> ROZE_MODEL_IDENTIFIER;
//    public static final Pair<ModelIdentifier, ModelIdentifier> FOLLY_MODEL_IDENTIFIER;
//    public static final Pair<ModelIdentifier, ModelIdentifier> SCISSORS_MODEL_IDENTIFIER;

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
//        ScytheItem.Skin skin = Skin.fromString(ArsenalCosmetics.getSkin(stack));
//        if (skin != null) {
//            switch (skin) {
//                case CLOWN -> modelIdentifierPair = CLOWN_MODEL_IDENTIFIER;
//                case CARRION -> modelIdentifierPair = CARRION_MODEL_IDENTIFIER;
//                case GILDED -> modelIdentifierPair = GILDED_MODEL_IDENTIFIER;
//                case ROZE -> modelIdentifierPair = ROZE_MODEL_IDENTIFIER;
//                case FOLLY -> modelIdentifierPair = FOLLY_MODEL_IDENTIFIER;
//                case SCISSORS -> modelIdentifierPair = SCISSORS_MODEL_IDENTIFIER;
//            }
//        }
        return modelIdentifierPair;
    }

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        boolean inHand = mode.isFirstPerson() || mode == ModelTransformationMode.THIRD_PERSON_RIGHT_HAND
                || mode == ModelTransformationMode.THIRD_PERSON_LEFT_HAND
                || mode == ModelTransformationMode.FIXED
                || mode == ModelTransformationMode.GROUND;
        boolean inInventory = mode == ModelTransformationMode.GUI;
        matrices.push();
        matrices.scale(1.0F, 1.0F, 1.0F);
        //matrices.translate(0.5f, 1.0f, 0.5f);

        Pair<ModelIdentifier, ModelIdentifier> modelIdentifierPair = getModelIdentifierPair(stack);
        BakedModel model = MinecraftClient.getInstance().getBakedModelManager().getModel(
                !inHand ? modelIdentifierPair.getLeft() : modelIdentifierPair.getRight()
        );

        if (inInventory) {
            RenderSystem.enableBlend();
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

    static {
//        CLOWN_MODEL_IDENTIFIER = registerVariantModelPair(Skin.CLOWN.getName());
//        CARRION_MODEL_IDENTIFIER = registerVariantModelPair(Skin.CARRION.getName());
//        GILDED_MODEL_IDENTIFIER = registerVariantModelPair(Skin.GILDED.getName());
//        ROZE_MODEL_IDENTIFIER = registerVariantModelPair(Skin.ROZE.getName());
//        FOLLY_MODEL_IDENTIFIER = registerVariantModelPair(Skin.FOLLY.getName());
//        SCISSORS_MODEL_IDENTIFIER = registerVariantModelPair(Skin.SCISSORS.getName());
    }
}