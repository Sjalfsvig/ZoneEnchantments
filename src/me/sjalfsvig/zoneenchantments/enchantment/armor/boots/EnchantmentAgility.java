package me.sjalfsvig.zoneenchantments.enchantment.armor.boots;

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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

import me.sjalfsvig.zoneenchantments.ZoneEnchantments;
import me.sjalfsvig.zoneenchantments.enchantment.Rarity;
import me.sjalfsvig.zoneenchantments.enchantment.SBEnchantment;
import me.sjalfsvig.zoneenchantments.util.RomanNumeral;
import me.sjalfsvig.zoneenchantments.util.api.ArmorEquipEvent;
import net.md_5.bungee.api.ChatColor;

public class EnchantmentAgility extends SBEnchantment implements Listener {

	private ZoneEnchantments plugin = ZoneEnchantments.getInstance();
	private ASkyBlockAPI skyblock = plugin.getSkyblock();
	
	private Map<UUID, Integer> taskIDs = new HashMap<UUID, Integer>();
	private Map<UUID, Set<UUID>> glowingPlayers = new HashMap<UUID, Set<UUID>>();
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		Set<UUID> hasEnchant = new HashSet<UUID>();
		if (player.getInventory().getHelmet() != null) {
			ItemStack armor = player.getInventory().getHelmet();
			if (armor.hasItemMeta() && armor.getItemMeta().hasLore()) {
				for (String lore : armor.getItemMeta().getLore()) {
					if (lore.startsWith(ChatColor.GRAY + "Agility")) {
						hasEnchant.add(player.getUniqueId());
						break;
					}
				}
				
				if (hasEnchant.contains(player.getUniqueId())) {
					if (player.hasPotionEffect(PotionEffectType.SPEED)) {
						player.removePotionEffect(PotionEffectType.SPEED);
					}
					
					if (skyblock.hasIsland(player.getUniqueId()) || skyblock.getTeamMembers(player.getUniqueId()) != null) {
						stopCheckArmor(player);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		
		Set<ItemStack> armorContents = new HashSet<ItemStack>();
		Set<UUID> hasEnchant = new HashSet<UUID>();
		String numeral = null;
		
		if (player.getInventory().getHelmet() != null) {
			armorContents.add(player.getInventory().getHelmet());
		}
		
		if (player.getInventory().getChestplate() != null) {
			armorContents.add(player.getInventory().getChestplate());
		}
		
		if (player.getInventory().getLeggings() != null) {
			armorContents.add(player.getInventory().getLeggings());
		}
		
		if (player.getInventory().getBoots() != null) {
			armorContents.add(player.getInventory().getBoots());
		}
		
		for (ItemStack armor : armorContents) {
			if (armor.hasItemMeta() && armor.getItemMeta().hasLore()) {
				for (String lore : armor.getItemMeta().getLore()) {
					if (lore.startsWith(ChatColor.GRAY + "Agility")) {
						hasEnchant.add(player.getUniqueId());
						numeral = lore.replace(ChatColor.GRAY + "Agility ", "");
						break;
					}
				}
			}
		}
		
		if (hasEnchant.contains(player.getUniqueId())) {
			if (skyblock.hasIsland(player.getUniqueId()) || skyblock.getTeamMembers(player.getUniqueId()) != null) {
				Set<UUID> set = new HashSet<UUID>();
				glowingPlayers.put(player.getUniqueId(), set);	
				checkArmor(player, RomanNumeral.toInt(numeral));
			}
		} else {
			if (player.hasPotionEffect(PotionEffectType.SPEED)) {
				player.removePotionEffect(PotionEffectType.SPEED);
			}
		}
	}
	
	@EventHandler
	public void onArmorEquip(ArmorEquipEvent event) {
		Player player = event.getPlayer();
		UUID playerUUID = player.getUniqueId();
		Set<UUID> hasEnchant = new HashSet<UUID>();
		
		if (event.getOldArmorPiece() != null) {
			ItemStack oldArmor = event.getOldArmorPiece();
			if (oldArmor.hasItemMeta() && oldArmor.getItemMeta().hasLore()) {
				for (String lore : oldArmor.getItemMeta().getLore()) {
					if (lore.startsWith(ChatColor.GRAY + "Agility")) {
						hasEnchant.add(playerUUID);
						break;
					}
				}
				if (hasEnchant.contains(playerUUID)) {
					if (player.hasPotionEffect(PotionEffectType.SPEED)) {
						player.removePotionEffect(PotionEffectType.SPEED);
					}
					
					if (skyblock.hasIsland(playerUUID) || skyblock.getTeamMembers(player.getUniqueId()) != null) {
						stopCheckArmor(player);
					}
					
					hasEnchant.remove(playerUUID);
				}
			}
		}
		
		if (event.getNewArmorPiece() != null) {
			ItemStack newArmor = event.getNewArmorPiece();
			String numeral = null;
			
			if (newArmor.hasItemMeta() && newArmor.getItemMeta().hasLore()) {
				for (String lore : newArmor.getItemMeta().getLore()) {
					if (lore.startsWith(ChatColor.GRAY + "Agility")) {
						hasEnchant.add(playerUUID);
						numeral = lore.replace(ChatColor.GRAY + "Agility ", "");
						break;
					}
				}
				
				if (hasEnchant.contains(playerUUID)) {
					int enchantmentLevel = RomanNumeral.toInt(numeral);
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, enchantmentLevel));
					
					if (skyblock.hasIsland(playerUUID) || skyblock.getTeamMembers(player.getUniqueId()) != null) {
						Set<UUID> set = new HashSet<UUID>();
						glowingPlayers.put(playerUUID, set);	
						checkArmor(player, enchantmentLevel);
					}
				}
			}
		}
	}
	
	private void checkArmor(Player player, int enchantmentLevel) {
		new BukkitRunnable() {
			@Override
			public void run() {
				taskIDs.put(player.getUniqueId(), this.getTaskId());
				boolean hasEnchant = false;
				Set<ItemStack> armorContents = new HashSet<ItemStack>();
				
				if (player.getInventory().getHelmet() != null) {
					armorContents.add(player.getInventory().getHelmet());
				}
				
				if (player.getInventory().getChestplate() != null) {
					armorContents.add(player.getInventory().getChestplate());
				}
				
				if (player.getInventory().getLeggings() != null) {
					armorContents.add(player.getInventory().getLeggings());
				}
				
				if (player.getInventory().getBoots() != null) {
					armorContents.add(player.getInventory().getBoots());
				}
				
				for (ItemStack armor : armorContents) {
					if (armor.hasItemMeta() && armor.getItemMeta().hasLore()) {
						for (String lore : armor.getItemMeta().getLore()) {
							if (lore.startsWith(ChatColor.GRAY + "Agility")) {
								hasEnchant = true;
								break;
							}
						}
					}
				}
				
				if (hasEnchant) {
					List<UUID> islandMembers = skyblock.getTeamMembers(player.getUniqueId());
					List<Entity> nearbyEntities = player.getNearbyEntities(32, 32, 32);
					Set<UUID> nearbyMembers = new HashSet<UUID>();
					
					if (glowingPlayers != null && glowingPlayers.containsKey(player.getUniqueId())) {
						for (UUID regenPlayer : glowingPlayers.get(player.getUniqueId())) {
							if (!nearbyMembers.contains(regenPlayer)) {
								Player regenTarget = Bukkit.getPlayer(regenPlayer);
								if (regenTarget.hasPotionEffect(PotionEffectType.SPEED)) {
									regenTarget.removePotionEffect(PotionEffectType.SPEED);
								}
								glowingPlayers.get(player.getUniqueId()).remove(regenTarget.getUniqueId());
							}
						}
					}
					
					for (Entity entity : nearbyEntities) {
						if (entity instanceof Player) {
							Player target = (Player) entity;
							if (islandMembers.contains(target.getUniqueId())) {
								if (glowingPlayers.containsKey(player.getUniqueId())) {
									if (!glowingPlayers.get(player.getUniqueId()).contains(target.getUniqueId())) {
										glowingPlayers.get(player.getUniqueId()).add(target.getUniqueId());
										target.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, enchantmentLevel));
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
		}.runTaskTimer(plugin, 1L, 32L*20L); // I think that's 32 seconds.
	}
	
	private void stopCheckArmor(Player player) {
		if (taskIDs.containsKey(player.getUniqueId())) {
			Bukkit.getServer().getScheduler().cancelTask(taskIDs.get(player.getUniqueId()));
			if (glowingPlayers != null && glowingPlayers.containsKey(player.getUniqueId())) {
				for (UUID regenPlayer : glowingPlayers.get(player.getUniqueId())) {
					if (Bukkit.getPlayer(regenPlayer) != null) {
						Player target = Bukkit.getPlayer(regenPlayer);
						target.removePotionEffect(PotionEffectType.SPEED);
					}
				}
				
				glowingPlayers.remove(player.getUniqueId());
			}
		}
	}
	
	@Override
	public String getName() {
		return "Agility";
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
		return new ArrayList<Material>(Arrays.asList(Material.DIAMOND_BOOTS, Material.GOLD_BOOTS, Material.IRON_BOOTS, Material.LEATHER_BOOTS));
	}
}
