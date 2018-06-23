package me.sjalfsvig.zoneenchantments.enchantment;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class EnchantmentGlow extends Enchantment {

	// Used to handle the glowing effect on items.
	
	public EnchantmentGlow(int id) {
		super(id);
	}
	
	@Override
	public int getId() {
		return 100;
	}
	
	@Override
	public boolean canEnchantItem(ItemStack arg0) {
		return true;
	}
	
	@Override
	public boolean conflictsWith(Enchantment arg0) {
		return false;
	}
	
	@Override
	public EnchantmentTarget getItemTarget() {
		return null;
	}
	
	@Override
	public int getMaxLevel() {
		return 3;
	}
	
	@Override
	public String getName() {
		return "SkyblockZone";
	}
	
	public String getDescription() {
		return "Enchantment to apply glow.";
	}
	
	@Override
	public int getStartLevel() {
		return 1;
	}
	
	@Override
	public boolean isCursed() {
		return false;
	}
	
	@Override
	public boolean isTreasure() {
		return false;
	}
}
