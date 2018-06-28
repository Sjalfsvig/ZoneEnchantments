package me.sjalfsvig.zoneenchantments.enchantment.bow;

import org.bukkit.event.Listener;

import me.sjalfsvig.zoneenchantments.enchantment.SBEnchantment;
import me.sjalfsvig.zoneenchantments.util.ItemType;

public class EnchantmentShock extends SBEnchantment implements Listener {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public ItemType getItemType() {
		return ItemType.BOW;
	}
}
