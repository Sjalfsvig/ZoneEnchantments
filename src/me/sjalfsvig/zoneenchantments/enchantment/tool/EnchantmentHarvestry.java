package me.sjalfsvig.zoneenchantments.enchantment.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Crops;

import me.sjalfsvig.zoneenchantments.enchantment.Rarity;
import me.sjalfsvig.zoneenchantments.enchantment.SBEnchantment;
import me.sjalfsvig.zoneenchantments.util.RomanNumeral;
import net.md_5.bungee.api.ChatColor;

public class EnchantmentHarvestry extends SBEnchantment implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		Set<UUID> hasEnchant = new HashSet<UUID>();
		String numeral = null;
		int enchantmentLevel;
		if (player.getInventory().getItemInMainHand() != null) {
			ItemStack heldItem = player.getInventory().getItemInMainHand();
			if (heldItem.hasItemMeta() && heldItem.getItemMeta().hasLore()) {
				for (String lore : heldItem.getItemMeta().getLore()) {
					if (lore.contains(ChatColor.GRAY + "Harvestry")) {
						hasEnchant.add(player.getUniqueId());
						numeral = lore.replace(ChatColor.GRAY + "Harvestry ", "");
						break;
					}
				}
				
				if (hasEnchant.contains(player.getUniqueId())) {
					if (block.getType() == Material.CROPS || block.getType() == Material.CARROT || block.getType() == Material.POTATO || block.getType() == Material.BEETROOT_BLOCK) {
						Crops crops = (Crops) block.getState().getData();
						
						if (crops.getState() == CropState.RIPE) {
							Material drop = Material.STONE;
							
							if (block.getType() == Material.CROPS) {
								drop = Material.WHEAT;
							}
							
							if (block.getType() == Material.CARROT) {
								drop = Material.CARROT_ITEM;
							}
							
							if (block.getType() == Material.POTATO) {
								drop = Material.POTATO_ITEM;
							}
							
							if (block.getType() == Material.BEETROOT_BLOCK) {
								drop = Material.BEETROOT;
							}
							
							enchantmentLevel = RomanNumeral.toInt(numeral);
							ItemStack dropItem = new ItemStack(drop, enchantmentLevel);
							block.getWorld().dropItemNaturally(block.getLocation(), dropItem);
						}
					}
				}
			}
		}
	}
	
	@Override
	public String getName() {
		return "Harvestry";
	}

	@Override
	public Rarity getRarity() {
		return Rarity.UNCOMMON;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public List<Material> getAllowedItems() {
		return new ArrayList<Material>(Arrays.asList(Material.DIAMOND_HOE, Material.GOLD_HOE, Material.IRON_HOE, Material.STONE_HOE, Material.WOOD_HOE));
	}
}
