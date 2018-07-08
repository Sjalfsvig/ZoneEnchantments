package me.sjalfsvig.zoneenchantments.enchantment.armor.elytra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.sjalfsvig.zoneenchantments.ZoneEnchantments;
import me.sjalfsvig.zoneenchantments.enchantment.Rarity;
import me.sjalfsvig.zoneenchantments.enchantment.SBEnchantment;
import net.md_5.bungee.api.ChatColor;

public class EnchantmentBoost extends SBEnchantment implements Listener {

	private ZoneEnchantments plugin = ZoneEnchantments.getInstance();
	private Set<UUID> isLaunching = new HashSet<UUID>();
	private Map<UUID, Integer> taskIDs = new HashMap<UUID, Integer>();
	private Map<UUID, Integer> soundTaskIDs = new HashMap<UUID, Integer>();
	
	@EventHandler
	public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();
		Set<UUID> hasEnchant = new HashSet<UUID>();
		
		if (!player.isSneaking()) {
			if (player.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR) {
				if (player.getInventory().getChestplate() != null && player.getInventory().getChestplate().getType() == Material.ELYTRA) {
					ItemStack elytra = player.getInventory().getChestplate();
					if (elytra.hasItemMeta() && elytra.getItemMeta().hasLore()) {
						for (String lore : elytra.getItemMeta().getLore()) {
							if (lore.startsWith(ChatColor.GRAY + "Boost")) {
								hasEnchant.add(player.getUniqueId());
								break;
							}
						}
					}
					
					if (hasEnchant.contains(player.getUniqueId())) {
						isLaunching.add(player.getUniqueId());
						doSound(player);
						startCountdown(player);
					}
				}
			}
		} else {
			if (taskIDs.containsKey(player.getUniqueId())) {
				isLaunching.remove(player.getUniqueId());
				stopCountdown(player);
			}
			
			if (soundTaskIDs.containsKey(player.getUniqueId())) {
				stopSound(player);
			}
		}
	}
	
	private void startCountdown(Player player) {
		new BukkitRunnable() {
			public void run() {
				if (isLaunching.contains(player.getUniqueId())) {
					stopSound(player);
					taskIDs.put(player.getUniqueId(), this.getTaskId());
					isLaunching.remove(player.getUniqueId());
					player.setVelocity(new Vector(0, 2, 0));
					stopCountdown(player);
				}
			}
		}.runTaskLater(plugin, 3L*20L);
	}
	
	private void stopCountdown(Player player) {
		Bukkit.getScheduler().cancelTask(taskIDs.get(player.getUniqueId()));
	}
	
	private void doSound(Player player) {
		new BukkitRunnable() {
			public void run() {
				soundTaskIDs.put(player.getUniqueId(), this.getTaskId());
				if (isLaunching.contains(player.getUniqueId())) {
					player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 20f, 0.1f);
				} else {
					this.cancel();
					stopCountdown(player);
				}
			}
		}.runTaskTimer(plugin, 0L, 25L);
	}
	
	private void stopSound(Player player) {
		Bukkit.getScheduler().cancelTask(soundTaskIDs.get(player.getUniqueId()));
	}
	
	@Override
	public String getName() {
		return "Boost";
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
		return new ArrayList<Material>(Arrays.asList(Material.ELYTRA));
	}
}
