package me.sjalfsvig.zoneenchantments.enchantment.armor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import me.sjalfsvig.zoneenchantments.enchantment.Rarity;
import me.sjalfsvig.zoneenchantments.enchantment.SBEnchantment;
import net.md_5.bungee.api.ChatColor;

public class EnchantmentSelfDestruct extends SBEnchantment implements Listener {

	private Map<UUID, Set<ItemStack>> armorContents = new HashMap<UUID, Set<ItemStack>>();
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		Set<UUID> hasEnchant = new HashSet<UUID>();
		if (player.getInventory().getHelmet() != null) {
			if (!armorContents.containsKey(player.getUniqueId())) {
				armorContents.put(player.getUniqueId(), new HashSet<ItemStack>());
			}
			armorContents.get(player.getUniqueId()).add(player.getInventory().getHelmet());
		}
		
		if (player.getInventory().getChestplate() != null) {
			if (!armorContents.containsKey(player.getUniqueId())) {
				armorContents.put(player.getUniqueId(), new HashSet<ItemStack>());
			}
			armorContents.get(player.getUniqueId()).add(player.getInventory().getChestplate());
		}
		
		if (player.getInventory().getLeggings() != null) {
			if (!armorContents.containsKey(player.getUniqueId())) {
				armorContents.put(player.getUniqueId(), new HashSet<ItemStack>());
			}
			armorContents.get(player.getUniqueId()).add(player.getInventory().getLeggings());
		}
		
		if (player.getInventory().getBoots() != null) {
			if (!armorContents.containsKey(player.getUniqueId())) {
				armorContents.put(player.getUniqueId(), new HashSet<ItemStack>());
			}
			armorContents.get(player.getUniqueId()).add(player.getInventory().getBoots());
		}
		
		if (armorContents.containsKey(player.getUniqueId()) && !armorContents.get(player.getUniqueId()).isEmpty()) {
			for (ItemStack item : armorContents.get(player.getUniqueId())) {
				if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
					for (String lore : item.getItemMeta().getLore()) {
						if (lore.startsWith(ChatColor.GRAY + "Self-Destruct")) {
							hasEnchant.add(player.getUniqueId());
							break;
						}
					}
				}
			}
			
			if (hasEnchant.contains(player.getUniqueId())) {
				player.getWorld().createExplosion(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 2.5f, false, false); 
				hasEnchant.remove(player.getUniqueId());
				armorContents.remove(player.getUniqueId());
			}
		}
	}
	
	@Override
	public String getName() {
		return "Self-Destruct";
	}

	@Override
	public Rarity getRarity() {
		return Rarity.RARE;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public List<Material> getAllowedItems() {
		return new ArrayList<Material>(Arrays.asList(Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS,
				Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS,
				Material.GOLD_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS, Material.GOLD_BOOTS,
				Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS));
	}
}
