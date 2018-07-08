package me.sjalfsvig.zoneenchantments.enchantment.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.sjalfsvig.zoneenchantments.enchantment.Rarity;
import me.sjalfsvig.zoneenchantments.enchantment.SBEnchantment;
import me.sjalfsvig.zoneenchantments.util.RomanNumeral;
import net.md_5.bungee.api.ChatColor;

public class EnchantmentHaste extends SBEnchantment implements Listener {

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		Set<UUID> hasEnchant = new HashSet<UUID>();
		Item item = event.getItemDrop();
		ItemStack heldItem = item.getItemStack();
		if (heldItem.hasItemMeta() && heldItem.getItemMeta().hasLore()) {
			for (String lore : heldItem.getItemMeta().getLore()) {
				if (lore.startsWith(ChatColor.GRAY + "Haste")) {
					hasEnchant.add(player.getUniqueId());
					break;
				}
			}
			
			if (hasEnchant.contains(player.getUniqueId())) {
				if (player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
					player.removePotionEffect(PotionEffectType.FAST_DIGGING);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		if (player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
			player.removePotionEffect(PotionEffectType.FAST_DIGGING);
		}
	}
	
	@EventHandler
	public void onItemHeld(PlayerItemHeldEvent event) {
		Player player = event.getPlayer();
		Set<UUID> hasEnchant = new HashSet<UUID>();
		String numeral = null;
		int enchantmentLevel;
		
		if (player.getInventory().getItem(event.getNewSlot()) != null) {
			ItemStack heldItem = player.getInventory().getItem(event.getNewSlot());
			if (heldItem.hasItemMeta() && heldItem.getItemMeta().hasLore()) {
				for (String lore : heldItem.getItemMeta().getLore()) {
					if (lore.startsWith(ChatColor.GRAY + "Haste")) {
						hasEnchant.add(player.getUniqueId());
						numeral = lore.replace(ChatColor.GRAY + "Haste ", "");
						break;
					}
				}
				
				if (hasEnchant.contains(player.getUniqueId())) {
					enchantmentLevel = RomanNumeral.toInt(numeral.trim());
					player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 99999, enchantmentLevel-1));
				}
			}
		}
		
		if (player.getInventory().getItem(event.getPreviousSlot()) != null) {
			ItemStack oldItem = player.getInventory().getItem(event.getPreviousSlot());
			if (oldItem.hasItemMeta() && oldItem.getItemMeta().hasLore()) {
				for (String lore : oldItem.getItemMeta().getLore()) {
					if (lore.startsWith(ChatColor.GRAY + "Haste")) {
						hasEnchant.add(player.getUniqueId());
						numeral = lore.replace(ChatColor.GRAY + "Haste ", "");
						break;
					}
				}
				
				if (hasEnchant.contains(player.getUniqueId())) {
					player.removePotionEffect(PotionEffectType.FAST_DIGGING);
				}
			}
		}
	}
	
	@Override
	public String getName() {
		return "Haste";
	}

	@Override
	public Rarity getRarity() {
		return Rarity.COMMON;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public List<Material> getAllowedItems() {
		return new ArrayList<Material>(Arrays.asList(Material.DIAMOND_PICKAXE, Material.GOLD_PICKAXE, Material.IRON_PICKAXE, Material.STONE_PICKAXE, Material.WOOD_PICKAXE,
				Material.DIAMOND_AXE, Material.GOLD_AXE, Material.IRON_AXE, Material.STONE_AXE, Material.WOOD_AXE,
				Material.DIAMOND_SPADE, Material.GOLD_SPADE, Material.IRON_SPADE, Material.STONE_SPADE, Material.WOOD_SPADE));
	}
}
