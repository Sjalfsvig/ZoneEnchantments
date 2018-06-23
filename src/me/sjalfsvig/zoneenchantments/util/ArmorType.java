package me.sjalfsvig.zoneenchantments.util;

import org.bukkit.inventory.ItemStack;

/**
 * @Author Borlea
 * @Github https://github.com/borlea/
 * @Website http://codingforcookies.com/
 * @Since Jul 30, 2015
 */

public enum ArmorType {
	HELMET(5),
	CHESTPLATE(6),
	LEGGINGS(7),
	BOOTS(8);
	
	private final int slot;
	
	ArmorType(int slot) {
		this.slot = slot;
	}
	
	public final static ArmorType matchType(final ItemStack itemStack) {
		if (itemStack != null) {
			switch (itemStack.getType()) {
				case DIAMOND_HELMET:
				case GOLD_HELMET:
				case IRON_HELMET:
				case LEATHER_HELMET:
					return HELMET;
				case DIAMOND_CHESTPLATE:
				case GOLD_CHESTPLATE:
				case IRON_CHESTPLATE:
				case LEATHER_CHESTPLATE:
					return CHESTPLATE;
				case DIAMOND_LEGGINGS:
				case GOLD_LEGGINGS:
				case IRON_LEGGINGS:
				case LEATHER_LEGGINGS:
					return LEGGINGS;
				case DIAMOND_BOOTS:
				case GOLD_BOOTS:
				case IRON_BOOTS:
				case LEATHER_BOOTS:
					return BOOTS;
				default:
					return null;
			}
		} else {
			return null;
		}
	}
	
	public int getSlot() {
		return slot;
	}
}
