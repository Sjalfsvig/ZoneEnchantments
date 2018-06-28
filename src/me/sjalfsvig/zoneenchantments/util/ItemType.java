package me.sjalfsvig.zoneenchantments.util;

import org.bukkit.inventory.ItemStack;

public enum ItemType {
	
	ARMOR,
	HELMET,
	BOOTS,
	ELYTRA,
	BOW,
	SWORD,
	PICKAXE,
	AXE,
	SHOVEL,
	HOE,
	FISHING_ROD;
	
	public final static ItemType matchType(final ItemStack itemStack) {
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
				return ARMOR;
			case DIAMOND_LEGGINGS:
			case GOLD_LEGGINGS:
			case IRON_LEGGINGS:
			case LEATHER_LEGGINGS:
				return ARMOR;
			case DIAMOND_BOOTS:
			case GOLD_BOOTS:
			case IRON_BOOTS:
			case LEATHER_BOOTS:
				return BOOTS;
			case ELYTRA:
				return ELYTRA;
			case BOW:
				return BOW;
			case DIAMOND_SWORD:
			case GOLD_SWORD:
			case IRON_SWORD:
			case STONE_SWORD:
			case WOOD_SWORD:
				return SWORD;
			case DIAMOND_PICKAXE:
			case GOLD_PICKAXE:
			case IRON_PICKAXE:
			case STONE_PICKAXE:
			case WOOD_PICKAXE:
				return PICKAXE;
			case DIAMOND_AXE:
			case GOLD_AXE:
			case IRON_AXE:
			case STONE_AXE:
			case WOOD_AXE:
				return AXE;
			case DIAMOND_SPADE:
			case GOLD_SPADE:
			case IRON_SPADE:
			case STONE_SPADE:
			case WOOD_SPADE:
				return SHOVEL;
			case DIAMOND_HOE:
			case GOLD_HOE:
			case IRON_HOE:
			case STONE_HOE:
			case WOOD_HOE:
				return HOE;
			case FISHING_ROD:
				return FISHING_ROD;
			default:
				return null;
			}
		} else {
			return null;
		}
	}
}
