package me.sjalfsvig.zoneenchantments.enchantment;

import me.sjalfsvig.zoneenchantments.util.ItemType;

public abstract class SBEnchantment {
	
	public abstract String getName();
	
	public abstract String getDescription();
	
	public abstract int getMaxLevel();
	
	public abstract ItemType getItemType();
}
