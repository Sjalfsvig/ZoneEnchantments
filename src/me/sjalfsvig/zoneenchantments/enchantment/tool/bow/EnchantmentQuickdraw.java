package me.sjalfsvig.zoneenchantments.enchantment.tool.bow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import me.sjalfsvig.zoneenchantments.ZoneEnchantments;
import me.sjalfsvig.zoneenchantments.enchantment.Rarity;
import me.sjalfsvig.zoneenchantments.enchantment.SBEnchantment;
import net.md_5.bungee.api.ChatColor;

public class EnchantmentQuickdraw extends SBEnchantment implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Set<UUID> hasEnchant = new HashSet<UUID>();
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (player.getInventory().getItemInMainHand() != null) {
				ItemStack heldItem = player.getInventory().getItemInMainHand();
				
				if (heldItem.getType() == Material.BOW && heldItem.hasItemMeta() && heldItem.getItemMeta().hasLore()) {
					for (String lore : heldItem.getItemMeta().getLore()) {
						if (lore.startsWith(ChatColor.GRAY + "Quickdraw")) {
							hasEnchant.add(player.getUniqueId());
							break;
						}
					}
					
					if (hasEnchant.contains(player.getUniqueId())) {
						if (player.getInventory().contains(Material.ARROW) || heldItem.containsEnchantment(Enchantment.ARROW_INFINITE)) {
							event.setCancelled(true);
							Arrow arrow = player.launchProjectile(Arrow.class, player.getLocation().getDirection().add(player.getLocation().getDirection()));
							if (player.getGameMode() == GameMode.SURVIVAL && !heldItem.containsEnchantment(Enchantment.ARROW_INFINITE)) {
								ItemStack arrowItem = new ItemStack(Material.ARROW);
								player.getInventory().removeItem(arrowItem);
							}
							
							for (String lore : heldItem.getItemMeta().getLore()) {
								if (lore.startsWith(ChatColor.GRAY + "Ender")) {
									Random rand = new Random();
									int chance = rand.nextInt(50) + 1;
									if (chance > 10) {
									arrow.setMetadata("Ender", new FixedMetadataValue(ZoneEnchantments.getInstance(), true));
									break;
									}
								}
								
								if (lore.startsWith(ChatColor.GRAY + "Medic")) {
									arrow.setMetadata("Medic", new FixedMetadataValue(ZoneEnchantments.getInstance(), true));
									break;
								}
								
								if (lore.startsWith(ChatColor.GRAY + "Zeus")) {
									arrow.setMetadata("Zeus", new FixedMetadataValue(ZoneEnchantments.getInstance(), true));
									break;
								}
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public String getName() {
		return "Quickdraw";
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
		return new ArrayList<Material>(Arrays.asList(Material.BOW));
	}
}
