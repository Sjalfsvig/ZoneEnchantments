package me.sjalfsvig.zoneenchantments.enchantment.tool.sword;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import me.sjalfsvig.zoneenchantments.enchantment.Rarity;
import me.sjalfsvig.zoneenchantments.enchantment.SBEnchantment;
import me.sjalfsvig.zoneenchantments.util.RomanNumeral;
import net.md_5.bungee.api.ChatColor;

public class EnchantmentDisarm extends SBEnchantment implements Listener {

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
						if (lore.startsWith(ChatColor.GRAY + "Disarm")) {
							hasEnchant.add(player.getUniqueId());
							numeral = lore.replace(ChatColor.GRAY + "Disarm ", "");
							break;
						}
					}
					
					if (hasEnchant.contains(player.getUniqueId())) {
						enchantmentLevel = RomanNumeral.toInt(numeral.trim());
						Random random = new Random();
						int chance = random.nextInt(50) + 1;
						
						if (enchantmentLevel == 1) {
							if (chance < 5) {
								if (target.getInventory().getHeldItemSlot() > 5) {
									target.getInventory().setHeldItemSlot(0);
								} else if (target.getInventory().getHeldItemSlot() < 5) {
									target.getInventory().setHeldItemSlot(8);
								}
							}
						}
						
						if (enchantmentLevel == 2) {
							if (chance < 15) {
								if (target.getInventory().getHeldItemSlot() > 5) {
									target.getInventory().setHeldItemSlot(0);
								} else if (target.getInventory().getHeldItemSlot() < 5) {
									target.getInventory().setHeldItemSlot(8);
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
		return "Disarm";
	}

	@Override
	public Rarity getRarity() {
		return Rarity.UNCOMMON;
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}

	@Override
	public List<Material> getAllowedItems() {
		return new ArrayList<Material>(Arrays.asList(Material.DIAMOND_SWORD, Material.GOLD_SWORD, Material.IRON_SWORD, Material.STONE_SWORD, Material.WOOD_SWORD));
	}
}
