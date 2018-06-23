package me.sjalfsvig.zoneenchantments.enchantment.armor;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.sjalfsvig.zoneenchantments.enchantment.Enchantments;
import me.sjalfsvig.zoneenchantments.enchantment.SBEnchantment;
import me.sjalfsvig.zoneenchantments.util.ArmorEquipEvent;
import me.sjalfsvig.zoneenchantments.util.ArmorType;
import me.sjalfsvig.zoneenchantments.util.RomanNumeralHandler;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_12_R1.EntityPlayer;

public class EnchantmentHealthBoost extends SBEnchantment implements Listener {

	// Used for testing purposes.
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		ItemStack a = new ItemStack(Material.DIAMOND_CHESTPLATE);
		a.addEnchantment(Enchantments.GLOW, 1);
		ItemMeta meta = a.getItemMeta();
		
		if (meta.hasLore()) {
			List<String> lore = meta.getLore();
			lore.add(ChatColor.GRAY + "Health Boost I");
		} else {
			meta.setLore(Arrays.asList(ChatColor.GRAY + "Health Boost I"));
		}
		
		a.setItemMeta(meta);
		
		event.getPlayer().getInventory().addItem(a);
	}
	
	@EventHandler
	public void onArmorEquip(ArmorEquipEvent event) {
		Player player = event.getPlayer();
		ItemStack oldArmor = event.getOldArmorPiece();
		ItemStack newArmor = event.getNewArmorPiece();
		boolean hasHealthBoost = false;
		String numeral = null;
		int enchantmentLevel;
		EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
		
		if (oldArmor != null && oldArmor.hasItemMeta() && oldArmor.getItemMeta().hasLore()) {
			for (String lore : oldArmor.getItemMeta().getLore()) {
				if (lore.startsWith(ChatColor.GRAY + "Health Boost")) {
					hasHealthBoost = true;
					numeral = lore.replace("Health Boost ", "");
					break;
				}
			}
			
			if (hasHealthBoost) {
				enchantmentLevel = RomanNumeralHandler.toInt(numeral);
				entityPlayer.setAbsorptionHearts(entityPlayer.getAbsorptionHearts()-enchantmentLevel);
			}
		}
		
		Bukkit.broadcastMessage("Ugh");
		
		if (newArmor.hasItemMeta() && newArmor.getItemMeta().hasLore()) {
			Bukkit.broadcastMessage("has lore n shit");
			for (String lore : newArmor.getItemMeta().getLore()) {
				if (lore.startsWith(ChatColor.GRAY + "Health Boost")) {
					hasHealthBoost = true;
					numeral = lore.replace("Health Boost ", "");
				}
			}
			
			enchantmentLevel = RomanNumeralHandler.toInt(numeral);
			entityPlayer.setAbsorptionHearts(entityPlayer.getAbsorptionHearts()+enchantmentLevel);
			Bukkit.broadcastMessage("should be giving hearts");
		}
	}
	
	@Override
	public String getName() {
		return "Health Boost";
	}

	@Override
	public String getDescription() {
		return "This enchantment gives you one absorption heart per piece of armor";
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}

	@Override
	public List<SBEnchantment> getConflictsWith() {
		return null;
	}

	@Override
	public ArmorType getItemTarget() {
		return null;
	}
}
