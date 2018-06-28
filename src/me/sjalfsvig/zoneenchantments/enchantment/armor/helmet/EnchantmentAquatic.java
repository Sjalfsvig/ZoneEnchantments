package me.sjalfsvig.zoneenchantments.enchantment.armor.helmet;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.sjalfsvig.zoneenchantments.enchantment.SBEnchantment;
import me.sjalfsvig.zoneenchantments.util.ItemType;
import me.sjalfsvig.zoneenchantments.util.RomanNumeral;
import me.sjalfsvig.zoneenchantments.util.api.ArmorEquipEvent;
import net.md_5.bungee.api.ChatColor;

public class EnchantmentAquatic extends SBEnchantment implements Listener {
	
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
					player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 99999, enchantmentLevel-1));
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
	public String getDescription() {
		return "This enchantment gives you the ability to breathe underwater.";
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public ItemType getItemType() {
		return ItemType.HELMET;
	}
}
