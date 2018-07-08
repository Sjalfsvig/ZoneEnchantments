package me.sjalfsvig.zoneenchantments.enchantment.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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

public class EnchantmentTreeFeller extends SBEnchantment implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		Set<UUID> hasEnchant = new HashSet<UUID>();
        if (block.getType() == Material.LOG || block.getType() == Material.LOG_2) {
            if (player.getInventory().getItemInMainHand() != null) {
            	ItemStack heldItem = player.getInventory().getItemInMainHand();
            	if (heldItem.hasItemMeta() && heldItem.getItemMeta().hasLore()) {
            		for (String lore : heldItem.getItemMeta().getLore()) {
            			if (lore.contains(ChatColor.GRAY + "Tree-Feller")) {
            					hasEnchant.add(player.getUniqueId());
            					break;
            			}
            		}
            		
            		if (hasEnchant.contains(player.getUniqueId())) {
            			event.setCancelled(true);
                    	List<Material> allowedMaterials = new ArrayList<Material>(Arrays.asList(Material.LOG, Material.LOG_2));
                    	Set<Block> treeBlocks = getTree(event.getBlock(), allowedMaterials);
                    	for (Block treeBlock : treeBlocks) {
                    		treeBlock.breakNaturally();
                    	}
            		}
            	}
            }
        }
	}

	public Set<Block> getTree(Block start, List<Material> allowedMaterials){
		return this.getNearbyBlocks(start, allowedMaterials, new HashSet<Block>());
	}
	
	private Set<Block> getNearbyBlocks(Block start, List<Material> allowedMaterials, HashSet<Block> blocks){
		for (int x = -1; x < 2; x++) {
			for (int y = -1; y < 2; y++) {
				for (int z = -1; z < 2; z++) {
					Block block = start.getLocation().clone().add(x, y, z).getBlock();
					if (block != null && !blocks.contains(block) && allowedMaterials.contains(block.getType())) {
						blocks.add(block);
						blocks.addAll(getNearbyBlocks(block, allowedMaterials, blocks));
					}
				}
			}
		}
		
		return blocks;
	}
	
	@Override
	public String getName() {
		return "Tree-Feller";
	}

	@Override
	public Rarity getRarity() {
		return Rarity.LEGENDARY;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public List<Material> getAllowedItems() {
		return new ArrayList<Material>(Arrays.asList(Material.DIAMOND_AXE, Material.GOLD_AXE, Material.IRON_AXE, Material.STONE_AXE, Material.WOOD_AXE));
	}
}
