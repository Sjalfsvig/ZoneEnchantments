package me.sjalfsvig.zoneenchantments.enchantment.armor.boots;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;

import me.sjalfsvig.zoneenchantments.enchantment.SBEnchantment;
import me.sjalfsvig.zoneenchantments.util.ItemType;
import me.sjalfsvig.zoneenchantments.util.api.ArmorEquipEvent;
import net.md_5.bungee.api.ChatColor;

public class EnchantmentDoubleJump extends SBEnchantment implements Listener {

	private Set<UUID> jumpingPlayers = new HashSet<UUID>();
	
	@EventHandler
	public void onArmorEquip(ArmorEquipEvent event) {
		Player player = event.getPlayer();
		
		if (event.getOldArmorPiece() != null) {
			ItemStack oldArmor = event.getOldArmorPiece();
			if (oldArmor.hasItemMeta() && oldArmor.getItemMeta().hasLore()) {
				for (String lore : oldArmor.getItemMeta().getLore()) {
					if (lore.startsWith(ChatColor.GRAY + "Double Jump")) {
						if (!player.hasPermission("essentials.fly")) {
							player.setAllowFlight(false);
						}
					}
				}
			}
		}
		
		if (event.getNewArmorPiece() != null) {
			ItemStack newArmor = event.getNewArmorPiece();
			if (newArmor.hasItemMeta() && newArmor.getItemMeta().hasLore()) {
				for (String lore : newArmor.getItemMeta().getLore()) {
					if (lore.startsWith(ChatColor.GRAY + "Double Jump")) {
						player.setAllowFlight(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();
		Set<UUID> hasEnchant = new HashSet<UUID>();
		
		if (player.getInventory().getBoots() != null) {
			ItemStack boots = player.getInventory().getBoots();
			if (boots.hasItemMeta() && boots.getItemMeta().hasLore()) {
				for (String lore : boots.getItemMeta().getLore()) {
					if (lore.startsWith(ChatColor.GRAY + "Double Jump")) {
						hasEnchant.add(player.getUniqueId());
						break;
					}
				}
			}
			
			if (hasEnchant.contains(player.getUniqueId())) {
				jumpingPlayers.add(player.getUniqueId());
				event.setCancelled(true);
				player.setAllowFlight(false);
				player.setFlying(false);
				player.setVelocity(player.getLocation().getDirection().multiply(1.5D).setY(1D));
				player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 15, 1);
				player.setFallDistance(100);
				hasEnchant.remove(player.getUniqueId());
			}
		}
	}
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player && event.getCause() == DamageCause.FALL) {
			Player player = (Player) event.getEntity();
			
			if (jumpingPlayers.contains(player.getUniqueId())) {
				event.setCancelled(true);
				jumpingPlayers.remove(player.getUniqueId());
				player.setAllowFlight(true);
			}
		}
	}
	
	@Override
	public String getName() {
		return "Double Jump";
	}

	@Override
	public String getDescription() {
		return "This enchantment gives you the ability to jump twice.";
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public ItemType getItemType() {
		return ItemType.BOOTS;
	}
	
}
