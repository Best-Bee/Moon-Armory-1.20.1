package dev.bee.moon_armory;

import dev.bee.moon_armory.client.render.item.EchoScytheDynamicItemRenderer;
import dev.bee.moon_armory.client.render.item.EchoScytheHeldItemRenderer;
import dev.bee.moon_armory.item.MoonArmoryItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

import static dev.bee.moon_armory.item.MoonArmoryItems.ECHO_SCYTHE;

public class MoonArmoryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        BuiltinItemRendererRegistry.INSTANCE.register(ECHO_SCYTHE, new EchoScytheDynamicItemRenderer());
        ModelLoadingPlugin.register((pluginContext) -> pluginContext.addModels(EchoScytheDynamicItemRenderer.MODELS_TO_REGISTER));

        registerModelPredicateProviders();
    }

    public static void registerModelPredicateProviders() {
        // For versions before 1.21, replace 'Identifier.ofVanilla' with 'new Identifier'.
        ModelPredicateProviderRegistry.register(ECHO_SCYTHE, new Identifier("pull"), (itemStack, clientWorld, livingEntity, seed) -> {
            if (livingEntity == null) {
                return 0.0F;
            }
            return livingEntity.getActiveItem() != itemStack ? 0.0F : (itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / 20.0F;
        });

        ModelPredicateProviderRegistry.register(ECHO_SCYTHE, new Identifier("pulling"), (itemStack, clientWorld, livingEntity, seed) -> {
            if (livingEntity == null) {
                return 0.0F;
            }
            return livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
        });
    }
}
