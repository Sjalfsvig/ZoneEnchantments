package me.sjalfsvig.zoneenchantments.enchantment.tool;

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
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import me.sjalfsvig.zoneenchantments.enchantment.Rarity;
import me.sjalfsvig.zoneenchantments.enchantment.SBEnchantment;
import me.sjalfsvig.zoneenchantments.util.RomanNumeral;
import net.md_5.bungee.api.ChatColor;

public class EnchantmentExperience extends SBEnchantment implements Listener {

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntity().getKiller() instanceof Player) {
			Player player = event.getEntity().getKiller();
			Set<UUID> hasEnchant = new HashSet<UUID>();
			String numeral = null;
			int enchantmentLevel;
			if (player.getInventory().getItemInMainHand() != null) {
				ItemStack heldItem = player.getInventory().getItemInMainHand();
				
				if (heldItem.hasItemMeta() && heldItem.getItemMeta().hasLore()) {
					for (String lore : heldItem.getItemMeta().getLore()) {
						if (lore.startsWith(ChatColor.GRAY + "Experience")) {
							hasEnchant.add(player.getUniqueId());
							numeral = lore.replace(ChatColor.GRAY + "Experience ", "");
							break;
						}
					}
					
					if (hasEnchant.contains(player.getUniqueId())) {
						enchantmentLevel = RomanNumeral.toInt(numeral.trim());
						event.setDroppedExp(event.getDroppedExp()*enchantmentLevel);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.getBlock() != null) {
			Player player = event.getPlayer();
			Set<UUID> hasEnchant = new HashSet<UUID>();
			String numeral = null;
			int enchantmentLevel;
			
			if (player.getInventory().getItemInMainHand() != null) {
				ItemStack heldItem = player.getInventory().getItemInMainHand();
				if (heldItem.hasItemMeta() && heldItem.getItemMeta().hasLore()) {
					for (String lore : heldItem.getItemMeta().getLore()) {
						if (lore.startsWith(ChatColor.GRAY + "Experience")) {
							hasEnchant.add(player.getUniqueId());
							numeral = lore.replace(ChatColor.GRAY + "Experience ", "");
							break;
						}
					}
					
					if (heldItem.getType().toString().contains("PICKAXE")) {
						if (hasEnchant.contains(player.getUniqueId())) {
							enchantmentLevel = RomanNumeral.toInt(numeral.trim());
							event.setExpToDrop(event.getExpToDrop()*enchantmentLevel);
						}
					}
				}
			}
		}
	}
	
	@Override
	public String getName() {
		return "Experience";
	}

	@Override
	public Rarity getRarity() {
		return Rarity.EPIC;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public List<Material> getAllowedItems() {
		return new ArrayList<Material>(Arrays.asList(Material.DIAMOND_PICKAXE, Material.GOLD_PICKAXE, Material.IRON_PICKAXE, Material.STONE_PICKAXE, Material.WOOD_PICKAXE,
				Material.DIAMOND_AXE, Material.GOLD_AXE, Material.IRON_AXE, Material.STONE_AXE, Material.WOOD_AXE,
				Material.DIAMOND_SWORD, Material.GOLD_SWORD, Material.IRON_SWORD, Material.STONE_SWORD, Material.WOOD_SWORD));
	}
}
