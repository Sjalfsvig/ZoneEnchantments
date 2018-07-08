package me.sjalfsvig.zoneenchantments.enchantment;

import java.util.List;

import org.bukkit.Material;

public abstract class SBEnchantment {

	public abstract String getName();
	
	public abstract Rarity getRarity();
	
	public abstract int getMaxLevel();
	
	public abstract List<Material> getAllowedItems();
}
