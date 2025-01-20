package dev.bee.moon_armory.datagen;

import dev.bee.moon_armory.item.MoonArmoryItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
    //private static final List<ItemConvertible> RUBY_SMELTABLES = List.of();

    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {


        ShapedRecipeJsonBuilder.create(
                RecipeCategory.MISC,
                MoonArmoryItems.REFINED_ECHO)
                .pattern("EEE")
                .pattern("ENE")
                .pattern("EEE")
                .input('E', Items.ECHO_SHARD)
                .input('N', Items.NETHERITE_INGOT)
                .criterion(hasItem(Items.ECHO_SHARD), conditionsFromItem(Items.ECHO_SHARD))
                .offerTo(exporter, new Identifier(getRecipeName(MoonArmoryItems.REFINED_ECHO)));

        ShapedRecipeJsonBuilder.create(
                RecipeCategory.MISC,
                MoonArmoryItems.ECHO_SCYTHE)
                .pattern("QNQ")
                .pattern(" DC")
                .pattern("  E")
                .input('N', Items.NETHER_STAR)
                .input('E', MoonArmoryItems.REFINED_ECHO)
                .input('C', Items.CRYING_OBSIDIAN)
                .input('Q', Items.QUARTZ)
                .input('D', Items.DRAGON_BREATH)
                .criterion(hasItem(MoonArmoryItems.REFINED_ECHO), conditionsFromItem(MoonArmoryItems.REFINED_ECHO))
                .offerTo(exporter, new Identifier(getRecipeName(MoonArmoryItems.ECHO_SCYTHE)));
    }
}
