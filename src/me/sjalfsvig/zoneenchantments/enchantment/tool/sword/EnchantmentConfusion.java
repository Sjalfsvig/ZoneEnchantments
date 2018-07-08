package me.sjalfsvig.zoneenchantments.enchantment.tool.sword;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.sjalfsvig.zoneenchantments.enchantment.Rarity;
import me.sjalfsvig.zoneenchantments.enchantment.SBEnchantment;
import me.sjalfsvig.zoneenchantments.util.RomanNumeral;
import net.md_5.bungee.api.ChatColor;

public class EnchantmentConfusion extends SBEnchantment implements Listener {

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player && event.getEntity() instanceof Player ) {
			Player player = (Player) event.getDamager();
			Player target = (Player) event.getEntity();
			Set<UUID> hasEnchant = new HashSet<UUID>();
			String numeral = null;
			int enchantmentLevel;
			
			if (player.getInventory().getItemInMainHand() != null) {
				ItemStack heldItem = player.getInventory().getItemInMainHand();
				if (heldItem.hasItemMeta() && heldItem.getItemMeta().hasLore()) {
					for (String lore : heldItem.getItemMeta().getLore()) {
						if (lore.startsWith(ChatColor.GRAY + "Confusion")) {
							hasEnchant.add(player.getUniqueId());
							numeral = lore.replace(ChatColor.GRAY + "Confusion ", "");
							break;
						}
					}
					
					if (hasEnchant.contains(player.getUniqueId())) {
						enchantmentLevel = RomanNumeral.toInt(numeral.trim());
						target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 400, enchantmentLevel));
					}
				}
			}
		}
	}
	
	@Override
	public String getName() {
		return "Confusion";
	}

	@Override
	public Rarity getRarity() {
		return Rarity.COMMON;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public List<Material> getAllowedItems() {
		return new ArrayList<Material>(Arrays.asList(Material.DIAMOND_SWORD, Material.GOLD_SWORD, Material.IRON_SWORD, Material.STONE_SWORD, Material.WOOD_SWORD));
	}
}
