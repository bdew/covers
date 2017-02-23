/*
 * Copyright (c) bdew, 2016 - 2017
 *
 * This file is part of Simple Covers.
 *
 * Simple Covers is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Simple Covers is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Simple Covers.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.bdew.covers.recipes

import net.bdew.covers.block.ItemCover
import net.bdew.covers.items.ItemSaw
import net.bdew.lib.crafting.RecipeMatcher
import net.minecraft.item.ItemStack

object RecipeSplitPart extends MicroblockRecipe {
  def verifyAndCreateResult(inv: RecipeMatcher): Option[ItemStack] = {
    for {
      saw <- inv.matchItem(ItemSaw).first()
      part <- inv.matchItem(ItemCover).and(saw.matchBelow).first() if inv.allMatched
      data <- ItemCover.getData(part.stack) if data.size % 2 == 0 && data.shape.validSizes.contains(data.size / 2)
    } yield ItemCover.makeStack(data.material, data.shape, data.size / 2, 2)
  }
}
