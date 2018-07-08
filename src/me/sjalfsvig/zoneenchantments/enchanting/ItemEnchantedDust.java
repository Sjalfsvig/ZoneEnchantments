package me.sjalfsvig.zoneenchantments.enchanting;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.sjalfsvig.zoneenchantments.enchantment.Rarity;
import net.md_5.bungee.api.ChatColor;

public class ItemEnchantedDust {

	public static ItemStack getItem(Rarity rarity) {
		ItemStack enchantmentDust = null;
		
		if (rarity == Rarity.EPIC || rarity == Rarity.LEGENDARY) {
			enchantmentDust = new ItemStack(Material.GLOWSTONE_DUST);
		} else {
			enchantmentDust = new ItemStack(Material.SULPHUR);
		}
		
		ItemMeta enchantmentDustMeta = enchantmentDust.getItemMeta();
		enchantmentDustMeta.setDisplayName(ChatColor.AQUA + "Enchantment Dust");
		enchantmentDustMeta.setLore(Arrays.asList(ChatColor.GRAY + rarity.getName()));
		enchantmentDust.setItemMeta(enchantmentDustMeta);
		
		return enchantmentDust;
	}
}
