package me.sjalfsvig.zoneenchantments.enchantment;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class EnchantmentGlow extends Enchantment {

	// Used to give the glowing effect to items, easier to give enchantment than edit NBT.
	
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
		return 1;
	}
	
	@Override
	public String getName() {
		return "SkyblockZone";
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
