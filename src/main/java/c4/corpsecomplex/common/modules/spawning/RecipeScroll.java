/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public
 * License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl
 * .text
 */

package c4.corpsecomplex.common.modules.spawning;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeScroll extends ShapelessOreRecipe {

  public RecipeScroll() {
    super(null, NonNullList
                    .from(Ingredient.EMPTY, Ingredient.fromItem(Items.PAPER),
                            Ingredient.fromItem(Items.ENDER_PEARL),
                            Ingredient.fromItem(Items.ROTTEN_FLESH)),
            new ItemStack(SpawningModule.scroll));
  }
}
