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

package net.bdew.covers.compat.jei

import mcmultipart.api.microblock.MicroMaterial
import mezz.jei.api.ingredients.IIngredients
import net.bdew.covers.block.ItemCover
import net.bdew.covers.items.ItemSaw
import net.bdew.covers.microblock.MicroMaterialHelper
import net.bdew.covers.microblock.shape.{FaceShape, MicroblockShape}
import net.minecraft.item.ItemStack

import scala.collection.JavaConversions._

// 2 slabs => block
class RecipeCombineBlock(material: MicroMaterial) extends MicroblockRecipe {
  override def getIngredients(ingredients: IIngredients): Unit = {
    val part = ItemCover.makeStack(material, FaceShape, 4)
    ingredients.setInputs(classOf[ItemStack], List(part, part))
    ingredients.setOutput(classOf[ItemStack], MicroMaterialHelper.getItemStack(material))
  }
}

// 2 parts => bigger part
class RecipeCombinePart(material: MicroMaterial, shape: MicroblockShape, size: Int) extends MicroblockRecipe {
  override def getIngredients(ingredients: IIngredients): Unit = {
    val input = ItemCover.makeStack(material, shape, size)
    ingredients.setInputs(classOf[ItemStack], List(input, input))
    ingredients.setOutput(classOf[ItemStack], ItemCover.makeStack(material, shape, size * 2, 1))
  }
}

// saw + block => 2 slabs
class RecipeCutBlock(material: MicroMaterial) extends MicroblockRecipe {
  override def getIngredients(ingredients: IIngredients): Unit = {
    ingredients.setInputs(classOf[ItemStack], List(new ItemStack(ItemSaw), null, null, MicroMaterialHelper.getItemStack(material)))
    ingredients.setOutput(classOf[ItemStack], ItemCover.makeStack(material, FaceShape, 4, 2))
  }
}

// saw + part => smaller part
class RecipeCutPart(material: MicroMaterial, shape: MicroblockShape, size: Int) extends MicroblockRecipe {
  override def getIngredients(ingredients: IIngredients): Unit = {
    ingredients.setInputs(classOf[ItemStack], List[ItemStack](
      new ItemStack(ItemSaw), null, null, ItemCover.makeStack(material, shape, size)
    ))
    ingredients.setOutput(classOf[ItemStack], ItemCover.makeStack(material, shape, size / 2, 2))
  }
}

// 8 parts in hollow pattern => hollow version
class RecipeHollowPart(material: MicroMaterial, shape: MicroblockShape, size: Int) extends MicroblockRecipe {
  override def getIngredients(ingredients: IIngredients): Unit = {
    val (newShape, newSize) = shape.hollow(size).getOrElse(sys.error("Invalid recipe (%s,%s)".format(shape, size)))
    val input = ItemCover.makeStack(material, shape, size)

    ingredients.setInputs(classOf[ItemStack], List(
      input, input, input,
      input, null, input,
      input, input, input
    ))

    ingredients.setOutput(classOf[ItemStack], ItemCover.makeStack(material, newShape, newSize, 8))
  }
}

// 4 parts in diamond pattern => ghost version
class RecipeGhostPart(material: MicroMaterial, shape: MicroblockShape, size: Int) extends MicroblockRecipe {
  override def getIngredients(ingredients: IIngredients): Unit = {
    val (newShape, newSize) = shape.ghost(size).getOrElse(sys.error("Invalid recipe (%s,%s)".format(shape, size)))
    val input = ItemCover.makeStack(material, shape, size)

    ingredients.setInputs(classOf[ItemStack], List(
      null, input, null,
      input, null, input,
      null, input, null
    ))

    ingredients.setOutput(classOf[ItemStack], ItemCover.makeStack(material, newShape, newSize, 8))
  }
}

// saw above part => smaller shape
class RecipeReducePart(material: MicroMaterial, shape: MicroblockShape, size: Int) extends MicroblockRecipe {
  override def getIngredients(ingredients: IIngredients): Unit = {
    val (newShape, newSize) = shape.reduce(size).getOrElse(sys.error("Invalid recipe (%s,%s)".format(shape, size)))
    ingredients.setInputs(classOf[ItemStack], List(
      new ItemStack(ItemSaw), ItemCover.makeStack(material, shape, size)
    ))
    ingredients.setOutput(classOf[ItemStack], ItemCover.makeStack(material, newShape, newSize, 2))
  }
}

// part above part => bigger shape
class RecipeUnreducePart(material: MicroMaterial, shape: MicroblockShape, size: Int) extends MicroblockRecipe {
  override def getIngredients(ingredients: IIngredients): Unit = {
    val (newShape, newSize) = shape.combine(size).getOrElse(sys.error("Invalid recipe (%s,%s)".format(shape, size)))
    val input = ItemCover.makeStack(material, shape, size)
    ingredients.setInputs(classOf[ItemStack], List(input, null, null, input))
    ingredients.setOutput(classOf[ItemStack], ItemCover.makeStack(material, newShape, newSize, 1))
  }
}

// Part alone => different shape
class RecipeTransform(material: MicroMaterial, shape: MicroblockShape, size: Int) extends MicroblockRecipe {
  override def getIngredients(ingredients: IIngredients): Unit = {
    val (newShape, newSize) = shape.transform(size).getOrElse(sys.error("Invalid recipe (%s,%s)".format(shape, size)))
    ingredients.setInput(classOf[ItemStack], ItemCover.makeStack(material, shape, size))
    ingredients.setOutput(classOf[ItemStack], ItemCover.makeStack(material, newShape, newSize))
  }
}
