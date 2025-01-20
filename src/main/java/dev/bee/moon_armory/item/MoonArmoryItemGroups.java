package dev.bee.moon_armory.item;

import dev.bee.moon_armory.MoonArmory;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MoonArmoryItemGroups {

    public static final ItemGroup MOON_ARMORY_Group = Registry.register(Registries.ITEM_GROUP,
            new Identifier(MoonArmory.MOD_ID, "refined_echo"),
            FabricItemGroup.builder().displayName(Text.translatable("Moon Armory"))
                    .icon(() -> new ItemStack(MoonArmoryItems.REFINED_ECHO)).entries((displayContext, entries) -> {

                        entries.add(MoonArmoryItems.REFINED_ECHO);

                        entries.add(MoonArmoryItems.ECHO_SCYTHE);


                    }).build());

    public static void registerItemGroups() {
        MoonArmory.LOGGER.info("Registering Item Groups for " + MoonArmory.MOD_ID);
    }
}
