package me.sjalfsvig.zoneenchantments.enchantment.armor.boots;

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

public class EnchantmentAntiGravity extends SBEnchantment implements Listener {

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		
		if (player.hasPotionEffect(PotionEffectType.JUMP)) {
			player.removePotionEffect(PotionEffectType.JUMP);
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
					if (lore.startsWith(ChatColor.GRAY + "Anti-Gravity")) {
						hasEnchant.add(player.getUniqueId());
						numeral = lore.replace(ChatColor.GRAY + "Anti-Gravity ", "");
						break;
					}
				}
			}
			
			if (hasEnchant.contains(player.getUniqueId())) {
				if (player.hasPotionEffect(PotionEffectType.JUMP)) {
					player.removePotionEffect(PotionEffectType.JUMP);
					hasEnchant.remove(player.getUniqueId());
				}
			}
		}
		
		if (event.getNewArmorPiece() != null) {
			ItemStack newArmor = event.getNewArmorPiece();
			if (newArmor.hasItemMeta() && newArmor.getItemMeta().hasLore()) {
				for (String lore : newArmor.getItemMeta().getLore()) {
					if (lore.startsWith(ChatColor.GRAY + "Anti-Gravity")) {
						hasEnchant.add(player.getUniqueId());
						numeral = lore.replace(ChatColor.GRAY + "Anti-Gravity ", "");
						break;
					}
				}
				
				if (hasEnchant.contains(player.getUniqueId())) {
					enchantmentLevel = RomanNumeral.toInt(numeral.trim());
					player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, enchantmentLevel-1));
					hasEnchant.remove(player.getUniqueId());
				}
			}
		}
	}
	
	@Override
	public String getName() {
		return "Anti-Gravity";
	}

	@Override
	public Rarity getRarity() {
		return Rarity.RARE;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public List<Material> getAllowedItems() {
		return new ArrayList<Material>(Arrays.asList(Material.DIAMOND_BOOTS, Material.GOLD_BOOTS, Material.IRON_BOOTS, Material.LEATHER_BOOTS));
	}
}
