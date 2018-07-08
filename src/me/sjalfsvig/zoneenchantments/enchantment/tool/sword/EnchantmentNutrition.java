package me.sjalfsvig.zoneenchantments.enchantment.tool.sword;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import me.sjalfsvig.zoneenchantments.enchantment.Rarity;
import me.sjalfsvig.zoneenchantments.enchantment.SBEnchantment;
import net.md_5.bungee.api.ChatColor;

public class EnchantmentNutrition extends SBEnchantment implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.getBlock() != null) {
			Player player = event.getPlayer();
			Block block = event.getBlock();
			Set<UUID> hasEnchant = new HashSet<UUID>();
			
			if (block.getType().toString().contains("LEAVES")) {
				if (player.getInventory().getItemInMainHand() != null) {
					ItemStack heldItem = player.getInventory().getItemInMainHand();
					
					if (heldItem.hasItemMeta() && heldItem.getItemMeta().hasLore()) {
						for (String lore : heldItem.getItemMeta().getLore()) {
							if (lore.contains(ChatColor.GRAY + "Nutrition")) {
								hasEnchant.add(player.getUniqueId());
								break;
							}
						}
						
						if (hasEnchant.contains(player.getUniqueId())) {
							Random random = new Random();
							double chance = 0 + (100 - 0) * random.nextDouble();
							if (chance < 25 && chance > 10) {
								block.getLocation().getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.APPLE));
							}
							
							if (chance < 10 && chance > 0.1) {
								block.getLocation().getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.APPLE));
								block.getLocation().getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.APPLE));
							}
							
							if (chance < 0.05) {
								block.getLocation().getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.GOLDEN_APPLE));
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public String getName() {
		return "Nutrition";
	}

	@Override
	public Rarity getRarity() {
		return Rarity.UNCOMMON;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public List<Material> getAllowedItems() {
		return new ArrayList<Material>(Arrays.asList(Material.DIAMOND_SWORD, Material.GOLD_SWORD, Material.IRON_SWORD, Material.STONE_SWORD, Material.WOOD_SWORD));
	}
}
