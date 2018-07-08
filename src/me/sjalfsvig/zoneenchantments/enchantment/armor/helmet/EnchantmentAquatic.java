package me.sjalfsvig.zoneenchantments.enchantment.armor.helmet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.sjalfsvig.zoneenchantments.enchantment.Rarity;
import me.sjalfsvig.zoneenchantments.enchantment.SBEnchantment;
import me.sjalfsvig.zoneenchantments.util.RomanNumeral;
import me.sjalfsvig.zoneenchantments.util.api.ArmorEquipEvent;
import net.md_5.bungee.api.ChatColor;

public class EnchantmentAquatic extends SBEnchantment implements Listener {

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		if (player.hasPotionEffect(PotionEffectType.WATER_BREATHING)) {
			player.removePotionEffect(PotionEffectType.WATER_BREATHING);
		}
	}
	
	@EventHandler
	public void onArmorEquip(ArmorEquipEvent event) {
		Player player = event.getPlayer();
		Set<UUID> hasEnchant = new HashSet<UUID>();
		String numeral = null;
		int enchantmentLevel;
		
		if (event.getOldArmorPiece() != null) {
			ItemStack oldArmor = event.getOldArmorPiece();
			if (oldArmor.hasItemMeta() && oldArmor.getItemMeta().hasLore()) {
				for (String lore : oldArmor.getItemMeta().getLore()) {
					if (lore.startsWith(ChatColor.GRAY + "Aquatic")) {
						hasEnchant.add(player.getUniqueId());
						numeral = lore.replace(ChatColor.GRAY + "Aquatic ", "");
						break;
					}
				}
			}
			
			if (hasEnchant.contains(player.getUniqueId())) {
				if (player.hasPotionEffect(PotionEffectType.WATER_BREATHING)) {
					player.removePotionEffect(PotionEffectType.WATER_BREATHING);
					hasEnchant.remove(player.getUniqueId());
				}
			}
		}
		
		if (event.getNewArmorPiece() != null) {
			ItemStack newArmor = event.getNewArmorPiece();
			if (newArmor.hasItemMeta() && newArmor.getItemMeta().hasLore()) {
				for (String lore : newArmor.getItemMeta().getLore()) {
					if (lore.startsWith(ChatColor.GRAY + "Aquatic")) {
						hasEnchant.add(player.getUniqueId());
						numeral = lore.replace(ChatColor.GRAY + "Aquatic ", "");
						break;
					}
				}
				
				if (hasEnchant.contains(player.getUniqueId())) {
					enchantmentLevel = RomanNumeral.toInt(numeral.trim());
					player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 99999, enchantmentLevel));
					hasEnchant.remove(player.getUniqueId());
				}
			}
		}
	}
	
	@Override
	public String getName() {
		return "Aquatic";
	}

	@Override
	public Rarity getRarity() {
		return Rarity.COMMON;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public List<Material> getAllowedItems() {
		return new ArrayList<Material>(Arrays.asList(Material.DIAMOND_HELMET, Material.GOLD_HELMET, Material.IRON_HELMET, Material.LEATHER_HELMET));
	}
}
