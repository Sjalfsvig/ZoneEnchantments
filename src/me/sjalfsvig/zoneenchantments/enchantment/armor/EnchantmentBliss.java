package me.sjalfsvig.zoneenchantments.enchantment.armor;

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

public class EnchantmentBliss extends SBEnchantment implements Listener {

	private ZoneEnchantments plugin = ZoneEnchantments.getInstance();
	private ASkyBlockAPI skyblock = plugin.getSkyblock();
	
	private Map<UUID, Integer> taskIDs = new HashMap<UUID, Integer>();
	private Map<UUID, Set<UUID>> regenPlayers = new HashMap<UUID, Set<UUID>>();
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		
		Set<ItemStack> armorContents = new HashSet<ItemStack>();
		Set<UUID> hasEnchant = new HashSet<UUID>();
		
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
					if (lore.startsWith(ChatColor.GRAY + "Bliss")) {
						hasEnchant.add(player.getUniqueId());
						break;
					}
				}
				
				if (hasEnchant.contains(player.getUniqueId())) {
					if (player.hasPotionEffect(PotionEffectType.REGENERATION)) {
						player.removePotionEffect(PotionEffectType.REGENERATION);
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
					if (lore.startsWith(ChatColor.GRAY + "Bliss")) {
						hasEnchant.add(player.getUniqueId());
						numeral = lore.replace(ChatColor.GRAY + "Bliss ", "");
						break;
					}
				}
			}
		}
		
		if (hasEnchant.contains(player.getUniqueId())) {
			if (skyblock.hasIsland(player.getUniqueId()) || skyblock.getTeamMembers(player.getUniqueId()) != null) {
				Set<UUID> set = new HashSet<UUID>();
				regenPlayers.put(player.getUniqueId(), set);	
				checkArmor(player, RomanNumeral.toInt(numeral));
			}
		} else {
			if (player.hasPotionEffect(PotionEffectType.REGENERATION)) {
				player.removePotionEffect(PotionEffectType.REGENERATION);
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
					if (lore.startsWith(ChatColor.GRAY + "Bliss")) {
						hasEnchant.add(playerUUID);
						break;
					}
				}
				if (hasEnchant.contains(playerUUID)) {
					if (player.hasPotionEffect(PotionEffectType.REGENERATION)) {
						player.removePotionEffect(PotionEffectType.REGENERATION);
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
					if (lore.startsWith(ChatColor.GRAY + "Bliss")) {
						hasEnchant.add(playerUUID);
						numeral = lore.replace(ChatColor.GRAY + "Bliss ", "");
						break;
					}
				}
				
				if (hasEnchant.contains(playerUUID)) {
					int enchantmentLevel = RomanNumeral.toInt(numeral);
					player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 99999, enchantmentLevel-1));
					
					if (skyblock.hasIsland(playerUUID) || skyblock.getTeamMembers(player.getUniqueId()) != null) {
						Set<UUID> set = new HashSet<UUID>();
						regenPlayers.put(playerUUID, set);	
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
							if (lore.startsWith(ChatColor.GRAY + "Bliss")) {
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
					
					if (regenPlayers != null && regenPlayers.containsKey(player.getUniqueId())) {
						for (UUID regenPlayer : regenPlayers.get(player.getUniqueId())) {
							if (!nearbyMembers.contains(regenPlayer)) {
								Player regenTarget = Bukkit.getPlayer(regenPlayer);
								if (regenTarget.hasPotionEffect(PotionEffectType.REGENERATION)) {
									regenTarget.removePotionEffect(PotionEffectType.REGENERATION);
								}
								regenPlayers.get(player.getUniqueId()).remove(regenTarget.getUniqueId());
							}
						}
					}
					
					for (Entity entity : nearbyEntities) {
						if (entity instanceof Player) {
							Player target = (Player) entity;
							if (islandMembers.contains(target.getUniqueId())) {
								if (regenPlayers.containsKey(player.getUniqueId())) {
									if (!regenPlayers.get(player.getUniqueId()).contains(target.getUniqueId())) {
										regenPlayers.get(player.getUniqueId()).add(target.getUniqueId());
										target.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 99999, enchantmentLevel-1));
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
			if (regenPlayers != null && regenPlayers.containsKey(player.getUniqueId())) {
				for (UUID regenPlayer : regenPlayers.get(player.getUniqueId())) {
					if (Bukkit.getPlayer(regenPlayer) != null) {
						Player target = Bukkit.getPlayer(regenPlayer);
						target.removePotionEffect(PotionEffectType.REGENERATION);
					}
				}
				
				regenPlayers.remove(player.getUniqueId());
			}
		}
	}
	
	@Override
	public String getName() {
		return "Bliss";
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
		return new ArrayList<Material>(Arrays.asList(Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS,
				Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS,
				Material.GOLD_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS, Material.GOLD_BOOTS,
				Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS));
	}
}
