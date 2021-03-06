/*
 * Copyright (c) bdew 2016.
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

package net.bdew.covers.config

import net.bdew.covers.Creative
import net.bdew.covers.items.{ItemMicroblock, ItemSaw}
import net.bdew.covers.transition.OldItemMicroblock
import net.bdew.lib.config.ItemManager

object Items extends ItemManager(Creative.main) {
  regItem(ItemSaw)
  regItem(ItemMicroblock)
  regItem(OldItemMicroblock)
}
