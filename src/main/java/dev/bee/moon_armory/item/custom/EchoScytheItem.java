package dev.bee.moon_armory.item.custom;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.UUID;

public class EchoScytheItem extends SwordItem implements MoonArmoryWeaponItem {
    private static final EntityAttributeModifier MELEE_RANGE_MODIFIER;

    public EchoScytheItem(ToolMaterial material, int damage, float speed, Item.Settings settings) {
        super(material, damage, speed, settings);
    }

    // public

    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        // Create a new Multimap based on the superclass's attribute modifiers for the given equipment slot
        Multimap<EntityAttribute, EntityAttributeModifier> map = LinkedHashMultimap.create(super.getAttributeModifiers(slot));

        // Check if the equipment slot is the main hand
        if (slot == EquipmentSlot.MAINHAND) {
            // Add a custom attribute modifier for attack range
            map.put(ReachEntityAttributes.ATTACK_RANGE, MELEE_RANGE_MODIFIER);
        }

        // Return the modified map of attribute modifiers
        return map;
    }

    static {
        MELEE_RANGE_MODIFIER = new EntityAttributeModifier(
                UUID.fromString("fdae6866-e42c-433c-a54a-3a84111f973c"),
                "Weapon modifier",
                (double)0.5F,
                EntityAttributeModifier.Operation.ADDITION);
    }
}
