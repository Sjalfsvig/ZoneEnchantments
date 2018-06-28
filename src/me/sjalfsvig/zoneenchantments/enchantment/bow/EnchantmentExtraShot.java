package me.sjalfsvig.zoneenchantments.enchantment.bow;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

import me.sjalfsvig.zoneenchantments.enchantment.SBEnchantment;
import me.sjalfsvig.zoneenchantments.util.ItemType;
import me.sjalfsvig.zoneenchantments.util.RomanNumeral;
import net.md_5.bungee.api.ChatColor;

public class EnchantmentExtraShot extends SBEnchantment implements Listener {
	
	@EventHandler
	public void onProjectileShoot(EntityShootBowEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			Set<UUID> hasEnchant = new HashSet<UUID>();
			String numeral = null;
			int enchantmentLevel;
				
			if (player.getInventory().getItemInMainHand() != null) {
				ItemStack heldItem = player.getInventory().getItemInMainHand();
				
				if (heldItem.hasItemMeta() && heldItem.getItemMeta().hasLore()) {
					for (String lore : heldItem.getItemMeta().getLore()) {
						if (lore.startsWith(ChatColor.GRAY + "Extra Shot")) {
							hasEnchant.add(player.getUniqueId());
							numeral = lore.replace(ChatColor.GRAY + "Extra Shot ", "");
							break;
						}
					}
					
					if (hasEnchant.contains(player.getUniqueId())) {
						if (player.getInventory().contains(Material.ARROW)) {
							enchantmentLevel = RomanNumeral.toInt(numeral.trim());
							event.setCancelled(true);
							Location playerLoc = player.getLocation();
							float playerYaw = playerLoc.getYaw();
							Location loc = new Location(playerLoc.getWorld(), playerLoc.getX(), playerLoc.getY(), playerLoc.getZ(), playerYaw-5f, playerLoc.getPitch());
							Location loc1 = new Location(playerLoc.getWorld(), playerLoc.getX(), playerLoc.getY(), playerLoc.getZ(), playerYaw+5f, playerLoc.getPitch());
							if (enchantmentLevel == 1) {
								player.launchProjectile(Arrow.class, loc.getDirection().add(playerLoc.getDirection()));
								player.launchProjectile(Arrow.class, loc1.getDirection().add(playerLoc.getDirection()));
							}
							
							if (enchantmentLevel == 2) {
								player.launchProjectile(Arrow.class, loc.getDirection().add(playerLoc.getDirection()));
								player.launchProjectile(Arrow.class, loc1.getDirection().add(playerLoc.getDirection()));
								player.launchProjectile(Arrow.class, player.getLocation().getDirection().add(player.getLocation().getDirection()));
							}
							
							if (player.getGameMode() == GameMode.SURVIVAL) {
								ItemStack arrow = new ItemStack(Material.ARROW, enchantmentLevel+1);
								player.getInventory().removeItem(arrow);
							}
							
							hasEnchant.remove(player.getUniqueId());
						}
					}
				}
			}
		}
	}
	
	@Override
	public String getName() {
		return "Extra Shot";
	}

	@Override
	public String getDescription() {
		return "This enchantment will give you the ability to use an extra arrow when shooting a bow.";
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}

	@Override
	public ItemType getItemType() {
		return ItemType.BOW;
	}
}
