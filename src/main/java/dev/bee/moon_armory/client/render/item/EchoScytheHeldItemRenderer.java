package dev.bee.moon_armory.client.render.item;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class EchoScytheHeldItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    private static final Identifier ECHO_SCYTHE_MODEL = new Identifier("moon_armory", "models/item/echo_scythe");

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        // Example: Apply custom transformations for animation
        long time = System.currentTimeMillis();
        float idleRotation = (time % 3600) / 10.0F; // Smooth rotation for idle animation
        matrices.translate(0.5F, 0.5F, 0.0F);
        matrices.push();

        //matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(idleRotation)); // Rotate around Y-axis

        // Render the item model
        net.minecraft.client.render.item.ItemRenderer renderer = net.minecraft.client.MinecraftClient.getInstance().getItemRenderer();
        renderer.renderItem(stack, mode, false, matrices, vertexConsumers, light, overlay, renderer.getModel(stack, null, null, 0));

        matrices.pop();
    }
}