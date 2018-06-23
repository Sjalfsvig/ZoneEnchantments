package me.sjalfsvig.zoneenchantments.enchantment.armor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

import me.sjalfsvig.zoneenchantments.ZoneEnchantments;
import me.sjalfsvig.zoneenchantments.enchantment.SBEnchantment;
import me.sjalfsvig.zoneenchantments.util.ArmorEquipEvent;
import me.sjalfsvig.zoneenchantments.util.ArmorType;
import me.sjalfsvig.zoneenchantments.util.RomanNumeralHandler;
import net.md_5.bungee.api.ChatColor;

public class EnchantmentBliss extends SBEnchantment implements Listener {

	private ZoneEnchantments plugin = ZoneEnchantments.getInstance();
	private ASkyBlockAPI skyblock = plugin.getSkyblock();
	
	// idk if this works properly yet
	
	private Map<UUID, Integer> taskID = new HashMap<UUID, Integer>();
	
	@EventHandler
	public void onArmorEquip(ArmorEquipEvent event) {
		Player player = event.getPlayer();
		UUID playerUUID = player.getUniqueId();
		ItemStack oldArmor = event.getOldArmorPiece();
		ItemStack newArmor = event.getNewArmorPiece();
		
		if (oldArmor.hasItemMeta() && oldArmor.getItemMeta().hasLore()) {
			boolean hasBliss = false;
			for (String lore : oldArmor.getItemMeta().getLore()) {
				if (lore.startsWith(ChatColor.GRAY + "Bliss")) {
					hasBliss = true;
					break;
				}
			}
			
			if (hasBliss) {
				if (skyblock.hasIsland(playerUUID)) {
					player.removePotionEffect(PotionEffectType.REGENERATION);
					stopCheckArmor(player);
				} else {
					player.removePotionEffect(PotionEffectType.REGENERATION);
				}
			}
		}
		
		if (newArmor.hasItemMeta() && newArmor.getItemMeta().hasLore()) {
			boolean hasBliss = false;
			String numeral = "";
			for (String lore : newArmor.getItemMeta().getLore()) {
				if (lore.startsWith(ChatColor.GRAY + "Bliss")) {
					hasBliss = true;
					numeral = lore;
					break;
				}
			}
			
			if (hasBliss) {
				numeral = numeral.replace("Bliss ", "");
				int enchantmentLevel = RomanNumeralHandler.toInt(numeral);
				
				if (skyblock.hasIsland(playerUUID)) {
					List<UUID> islandMembers = skyblock.getTeamMembers(playerUUID);
					List<Entity> nearbyEntities = player.getNearbyEntities(50, 50, 50);
					ArrayList<UUID> nearbyMembers = new ArrayList<UUID>();
					nearbyMembers.add(playerUUID);
					
					for (Entity entity : nearbyEntities) {
						if (entity instanceof Player) {
							Player target = (Player) entity;
							if (islandMembers.contains(target.getUniqueId())) {
								nearbyMembers.add(target.getUniqueId());
								break;
							}
						}
					}
					
					checkArmor(player, nearbyMembers, enchantmentLevel, newArmor);
				} else {
					player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 99999, enchantmentLevel));
				}
			}
		}
	}
	
	private void checkArmor(Player player, ArrayList<UUID> nearbyMembers, int enchantmentLevel, ItemStack armor) {
		new BukkitRunnable() {
			public void run() {
				if (armor.hasItemMeta() && armor.getItemMeta().hasLore()) {
					boolean hasBliss = false;
					
					for (String lore : armor.getItemMeta().getLore()) {
						if (lore.startsWith(ChatColor.GRAY + "Bliss")) {
							hasBliss = true;
							break;
						}
					}
					
					if (hasBliss) {
						for (UUID uuid : nearbyMembers) {
							if (Bukkit.getPlayer(uuid) != null) {
								Player target = Bukkit.getPlayer(uuid);
								target.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 99999, enchantmentLevel));
								taskID.put(player.getUniqueId(), this.getTaskId());
							}
						}
					}
				} else {
					this.cancel();
				}
			}
		}.runTaskTimer(plugin, 5L, 2L*20L*60L);
	}
	
	private void stopCheckArmor(Player player) {
		int task = taskID.get(player.getUniqueId());
		Bukkit.getServer().getScheduler().cancelTask(task);
	}
	
	
	@Override
	public String getName() {
		return "Bliss";
	}

	@Override
	public String getDescription() {
		return "This enchantment gives nearby island members regeneration.";
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public List<SBEnchantment> getConflictsWith() {
		return null;
	}

	@Override
	public ArmorType getItemTarget() {
		return null;
	}
}
