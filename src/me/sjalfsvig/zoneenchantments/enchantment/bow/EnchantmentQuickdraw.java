package me.sjalfsvig.zoneenchantments.enchantment.bow;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.sjalfsvig.zoneenchantments.enchantment.SBEnchantment;
import me.sjalfsvig.zoneenchantments.util.ItemType;
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
						if (player.getInventory().contains(Material.ARROW)) {
							event.setCancelled(true);
							player.launchProjectile(Arrow.class, player.getLocation().getDirection().add(player.getLocation().getDirection()));
							if (player.getGameMode() == GameMode.SURVIVAL) {
								ItemStack arrow = new ItemStack(Material.ARROW);
								player.getInventory().removeItem(arrow);
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
	public String getDescription() {
		return "This enchantment will give you the ability to shoot arrows faster.";
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public ItemType getItemType() {
		return ItemType.BOW;
	}
}
