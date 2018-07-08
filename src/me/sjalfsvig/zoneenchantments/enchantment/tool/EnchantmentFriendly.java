package me.sjalfsvig.zoneenchantments.enchantment.tool;

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
import org.bukkit.entity.Entity;
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
import org.bukkit.scheduler.BukkitRunnable;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

import me.sjalfsvig.zoneenchantments.ZoneEnchantments;
import me.sjalfsvig.zoneenchantments.enchantment.Rarity;
import me.sjalfsvig.zoneenchantments.enchantment.SBEnchantment;
import me.sjalfsvig.zoneenchantments.util.RomanNumeral;
import net.md_5.bungee.api.ChatColor;

public class EnchantmentFriendly extends SBEnchantment implements Listener {

	private ZoneEnchantments plugin = ZoneEnchantments.getInstance();
	private ASkyBlockAPI skyblock = plugin.getSkyblock();
	
	private Map<UUID, Integer> taskIDs = new HashMap<UUID, Integer>();
	private Map<UUID, Set<UUID>> hastePlayers = new HashMap<UUID, Set<UUID>>();
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		if (player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
			player.removePotionEffect(PotionEffectType.FAST_DIGGING);
		}
		
		if (skyblock.hasIsland(player.getUniqueId()) || skyblock.getTeamMembers(player.getUniqueId()) != null) {
			stopCheckItem(player);
		}
	}
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		Set<UUID> hasEnchant = new HashSet<UUID>();
		Item item = event.getItemDrop();
		ItemStack heldItem = item.getItemStack();
		if (heldItem.hasItemMeta() && heldItem.getItemMeta().hasLore()) {
			for (String lore : heldItem.getItemMeta().getLore()) {
				if (lore.startsWith(ChatColor.GRAY + "Friendly")) {
					hasEnchant.add(player.getUniqueId());
					break;
				}
			}
			
			if (hasEnchant.contains(player.getUniqueId())) {
				if (player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
					player.removePotionEffect(PotionEffectType.FAST_DIGGING);
				}
				
				if (skyblock.hasIsland(player.getUniqueId()) || skyblock.getTeamMembers(player.getUniqueId()) != null) {
					stopCheckItem(player);
				}
			}
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
					if (lore.startsWith(ChatColor.GRAY + "Friendly")) {
						hasEnchant.add(player.getUniqueId());
						numeral = lore.replace(ChatColor.GRAY + "Friendly ", "");
						break;
					}
				}
				
				if (hasEnchant.contains(player.getUniqueId())) {
					enchantmentLevel = RomanNumeral.toInt(numeral);
					player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 99999, enchantmentLevel-1));
					
					if (skyblock.hasIsland(player.getUniqueId()) || skyblock.getTeamMembers(player.getUniqueId()) != null) {
						Set<UUID> set = new HashSet<UUID>();
						hastePlayers.put(player.getUniqueId(), set);
						checkItem(player, enchantmentLevel);
					}
				}
			}
		}
		
		if (player.getInventory().getItem(event.getPreviousSlot()) != null) {
			ItemStack oldItem = player.getInventory().getItem(event.getPreviousSlot());
			if (oldItem.hasItemMeta() && oldItem.getItemMeta().hasLore()) {
				for (String lore : oldItem.getItemMeta().getLore()) {
					if (lore.startsWith(ChatColor.GRAY + "Friendly")) {
						hasEnchant.add(player.getUniqueId());
						numeral = lore.replace(ChatColor.GRAY + "Friendly ", "");
						break;
					}
				}
				
				if (hasEnchant.contains(player.getUniqueId())) {
					if (player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
						player.removePotionEffect(PotionEffectType.FAST_DIGGING);
					}
					
					if (skyblock.hasIsland(player.getUniqueId()) || skyblock.getTeamMembers(player.getUniqueId()) != null) {
						stopCheckItem(player);
					}
					
					hasEnchant.remove(player.getUniqueId());
				}
			}
		}
	}
	
	private void checkItem(Player player, int enchantmentLevel) {
		new BukkitRunnable() {
			@Override
			public void run() {
				taskIDs.put(player.getUniqueId(), this.getTaskId());
				boolean hasEnchant = false;
				
				if (player.getInventory().getItemInMainHand() != null) {
					ItemStack heldItem = player.getInventory().getItemInMainHand();
					
					if (heldItem.hasItemMeta() && heldItem.getItemMeta().hasLore()) {
						for (String lore : heldItem.getItemMeta().getLore()) {
							if (lore.startsWith(ChatColor.GRAY + "Friendly")) {
								hasEnchant = true;
								break;
							}
						}
						
						if (hasEnchant) {
							List<UUID> islandMembers = skyblock.getTeamMembers(player.getUniqueId());
							List<Entity> nearbyEntities = player.getNearbyEntities(32, 32, 32);
							Set<UUID> nearbyMembers = new HashSet<UUID>();
							
							if (hastePlayers != null && hastePlayers.containsKey(player.getUniqueId())) {
								for (UUID hastePlayer : hastePlayers.get(player.getUniqueId())) {
									if (!nearbyMembers.contains(hastePlayer)) {
										Player hasteTarget = Bukkit.getPlayer(hastePlayer);
										if (hasteTarget.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
											hasteTarget.removePotionEffect(PotionEffectType.FAST_DIGGING);
										}
										
										hastePlayers.get(player.getUniqueId()).remove(hasteTarget.getUniqueId());
									}
								}
							}
							
							for (Entity entity : nearbyEntities) {
								if (entity instanceof Player) {
									Player target = (Player) entity;
									if (islandMembers.contains(target.getUniqueId())) {
										if (hastePlayers.containsKey(player.getUniqueId())) {
											if (!hastePlayers.get(player.getUniqueId()).contains(target.getUniqueId())) {
												hastePlayers.get(player.getUniqueId()).add(target.getUniqueId());
												target.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 99999, enchantmentLevel-1));
												nearbyMembers.add(target.getUniqueId());
											}
										}
									}
								}
							}
						} else {
							this.cancel();
						}
					}
				}
			}
		}.runTaskTimer(plugin, 1L, 32L*20L);
	}
	
	private void stopCheckItem(Player player) {
		if (taskIDs.containsKey(player.getUniqueId())) {
			Bukkit.getServer().getScheduler().cancelTask(taskIDs.get(player.getUniqueId()));
			if (hastePlayers != null && hastePlayers.containsKey(player.getUniqueId())) {
				for (UUID hastePlayer : hastePlayers.get(player.getUniqueId())) {
					if (Bukkit.getPlayer(hastePlayer) != null) {
						Player target = Bukkit.getPlayer(hastePlayer);
						target.removePotionEffect(PotionEffectType.FAST_DIGGING);
					}
				}
				
				hastePlayers.remove(player.getUniqueId());
			}
		}
	}
	
	@Override
	public String getName() {
		return "Friendly";
	}

	@Override
	public Rarity getRarity() {
		return Rarity.LEGENDARY;
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
