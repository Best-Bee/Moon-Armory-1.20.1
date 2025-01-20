package dev.bee.moon_armory;

import dev.bee.moon_armory.client.render.item.EchoScytheDynamicItemRenderer;
import dev.bee.moon_armory.item.MoonArmoryItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;

public class MoonArmoryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        BuiltinItemRendererRegistry.INSTANCE.register(MoonArmoryItems.ECHO_SCYTHE, new EchoScytheDynamicItemRenderer());
        ModelLoadingPlugin.register((pluginContext) -> pluginContext.addModels(EchoScytheDynamicItemRenderer.MODELS_TO_REGISTER));
    }
}
