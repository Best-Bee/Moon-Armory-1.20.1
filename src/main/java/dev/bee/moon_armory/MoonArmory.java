package dev.bee.moon_armory;

import dev.bee.moon_armory.item.MoonArmoryItemGroups;
import dev.bee.moon_armory.item.MoonArmoryItems;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoonArmory implements ModInitializer {
	public static final String MOD_ID = "moon_armory";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	public static Identifier id(String path) {
		return new Identifier("moon_armory", path);
	}

	@Override
	public void onInitialize() {

		MoonArmoryItemGroups.registerItemGroups();
		MoonArmoryItems.registerModItems();
	}
}