package me.sjalfsvig.zoneenchantments.enchantment;

import java.util.List;

import me.sjalfsvig.zoneenchantments.util.ArmorType;

public abstract class SBEnchantment {
	
	public abstract String getName();
	
	public abstract String getDescription();
	
	public abstract int getMaxLevel();
	
	public abstract List<SBEnchantment> getConflictsWith();
	
	public abstract ArmorType getItemTarget();
}
