package me.sjalfsvig.zoneenchantments.enchantment.armor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import me.sjalfsvig.zoneenchantments.enchantment.SBEnchantment;
import me.sjalfsvig.zoneenchantments.util.ItemType;
import me.sjalfsvig.zoneenchantments.util.RomanNumeral;
import me.sjalfsvig.zoneenchantments.util.api.ArmorEquipEvent;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_12_R1.EntityPlayer;


public class EnchantmentHealthBoost extends SBEnchantment implements Listener {
	
	@EventHandler
	public void onArmorEquip(ArmorEquipEvent event) {
		Player player = event.getPlayer();
		Set<UUID> hasEnchant = new HashSet<UUID>();
		String numeral = null;
		int enchantmentLevel;
		EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
		
		if (event.getOldArmorPiece() != null) {
			ItemStack oldArmor = event.getOldArmorPiece();
			if (oldArmor.hasItemMeta() && oldArmor.getItemMeta().hasLore()) {
				for (String lore : oldArmor.getItemMeta().getLore()) {
					if (lore.startsWith(ChatColor.GRAY + "Health Boost")) {
						hasEnchant.add(player.getUniqueId());
						numeral = lore.replace(ChatColor.GRAY + "Health Boost ", "");
						break;
					}
				}
			}
			
			if (hasEnchant.contains(player.getUniqueId())) {
				enchantmentLevel = RomanNumeral.toInt(numeral.trim());
				entityPlayer.setAbsorptionHearts(entityPlayer.getAbsorptionHearts()-(enchantmentLevel*2));
				hasEnchant.remove(player.getUniqueId());
			}
		}
		
		if (event.getNewArmorPiece() != null) {
			ItemStack newArmor = event.getNewArmorPiece();
			if (newArmor.hasItemMeta() && newArmor.getItemMeta().hasLore()) {
				for (String lore : newArmor.getItemMeta().getLore()) {
					if (lore.startsWith(ChatColor.GRAY + "Health Boost")) {
						hasEnchant.add(player.getUniqueId());
						numeral = lore.replace(ChatColor.GRAY + "Health Boost ", "");
						break;
					}
				}
			}
			
			if (hasEnchant.contains(player.getUniqueId())) {
				enchantmentLevel = RomanNumeral.toInt(numeral.trim());
				entityPlayer.setAbsorptionHearts(entityPlayer.getAbsorptionHearts()+(enchantmentLevel*2));
				hasEnchant.remove(player.getUniqueId());
			}
		}
	}
	
	@Override
	public String getName() {
		return "Health Boost";
	}

	@Override
	public String getDescription() {
		return "This enchantment will give you absorption hearts per piece of armor.";
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}

	@Override
	public ItemType getItemType() {
		return ItemType.ARMOR;
	}
}
