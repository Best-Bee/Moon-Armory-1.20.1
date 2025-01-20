package dev.bee.moon_armory.item;

import dev.bee.moon_armory.MoonArmory;
import dev.bee.moon_armory.item.custom.EchoScytheItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MoonArmoryItems {

    //register an item internally without visuals, name, or access.
    public static final Item REFINED_ECHO = registerItem("refined_echo", new Item(new FabricItemSettings()));

    public static final Item ECHO_SCYTHE = registerItem("echo_scythe", new EchoScytheItem(
            MoonArmoryToolMaterial.ECHO,
            2,  // Default(1) + Material(11) + Modifier     = 14
            -2.75f,     // Default(4) + Modifier(0)                 = 1.25
            new FabricItemSettings()
    ));


    //method to add item to the ingredients tab of creative
    private static void addItemsToIngredientTabItemGroup(FabricItemGroupEntries entries){
        entries.add(REFINED_ECHO);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MoonArmory.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MoonArmory.LOGGER.info("Registering mod items for " + MoonArmory.MOD_ID);

        //calls method to add items to ingredients tab
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(MoonArmoryItems::addItemsToIngredientTabItemGroup);
    }
}
