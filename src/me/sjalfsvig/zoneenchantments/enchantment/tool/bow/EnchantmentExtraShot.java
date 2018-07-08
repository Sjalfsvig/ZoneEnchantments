package me.sjalfsvig.zoneenchantments.enchantment.tool.bow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import me.sjalfsvig.zoneenchantments.ZoneEnchantments;
import me.sjalfsvig.zoneenchantments.enchantment.Rarity;
import me.sjalfsvig.zoneenchantments.enchantment.SBEnchantment;
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
						if (lore.startsWith(ChatColor.GRAY + "Extra-Shot")) {
							hasEnchant.add(player.getUniqueId());
							numeral = lore.replace(ChatColor.GRAY + "Extra-Shot ", "");
							break;
						}
					}
					
					if (hasEnchant.contains(player.getUniqueId())) {
						if (player.getInventory().contains(Material.ARROW) || heldItem.containsEnchantment(Enchantment.ARROW_INFINITE)) {
							enchantmentLevel = RomanNumeral.toInt(numeral.trim());
							event.setCancelled(true);
							Location playerLoc = player.getLocation();
							float playerYaw = playerLoc.getYaw();
							Location loc = new Location(playerLoc.getWorld(), playerLoc.getX(), playerLoc.getY(), playerLoc.getZ(), playerYaw-5f, playerLoc.getPitch());
							Location loc1 = new Location(playerLoc.getWorld(), playerLoc.getX(), playerLoc.getY(), playerLoc.getZ(), playerYaw+5f, playerLoc.getPitch());
							Arrow arrow = null;
							Arrow arrow1 = null;
							Arrow arrow2 = null;
							if (enchantmentLevel == 1) {
								arrow = player.launchProjectile(Arrow.class, loc.getDirection().add(playerLoc.getDirection()));
								arrow1 = player.launchProjectile(Arrow.class, loc1.getDirection().add(playerLoc.getDirection()));
							}
							
							if (enchantmentLevel == 2) {
								arrow = player.launchProjectile(Arrow.class, loc.getDirection().add(playerLoc.getDirection()));
								arrow1 = player.launchProjectile(Arrow.class, loc1.getDirection().add(playerLoc.getDirection()));
								arrow2 = player.launchProjectile(Arrow.class, player.getLocation().getDirection().add(player.getLocation().getDirection()));
							}
							
							if (player.getGameMode() == GameMode.SURVIVAL && !heldItem.containsEnchantment(Enchantment.ARROW_INFINITE)) {
								ItemStack arrowItem = new ItemStack(Material.ARROW, enchantmentLevel+1);
								player.getInventory().removeItem(arrowItem);
							}
							
							hasEnchant.remove(player.getUniqueId());
							
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
									arrow1.setMetadata("Medic", new FixedMetadataValue(ZoneEnchantments.getInstance(), true));
									arrow2.setMetadata("Medic", new FixedMetadataValue(ZoneEnchantments.getInstance(), true));
									break;
								}
								
								if (lore.startsWith(ChatColor.GRAY + "Zeus")) {
									arrow.setMetadata("Zeus", new FixedMetadataValue(ZoneEnchantments.getInstance(), true));
									arrow1.setMetadata("Zeus", new FixedMetadataValue(ZoneEnchantments.getInstance(), true));
									arrow2.setMetadata("Zeus", new FixedMetadataValue(ZoneEnchantments.getInstance(), true));
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
		return "Extra-Shot";
	}

	@Override
	public Rarity getRarity() {
		return Rarity.LEGENDARY;
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}

	@Override
	public List<Material> getAllowedItems() {
		return new ArrayList<Material>(Arrays.asList(Material.BOW));
	}
}
