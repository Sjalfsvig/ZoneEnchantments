package me.sjalfsvig.zoneenchantments.enchantment.tool.bow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import me.sjalfsvig.zoneenchantments.ZoneEnchantments;
import me.sjalfsvig.zoneenchantments.enchantment.Rarity;
import me.sjalfsvig.zoneenchantments.enchantment.SBEnchantment;
import net.md_5.bungee.api.ChatColor;

public class EnchantmentEnder extends SBEnchantment implements Listener  {

	@EventHandler
	public void onEntityShootBow(EntityShootBowEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			ItemStack bow = event.getBow();
			Set<UUID> hasEnchant = new HashSet<UUID>();
			
			if (bow != null && bow.hasItemMeta() && bow.getItemMeta().hasLore()) {
				for (String lore : bow.getItemMeta().getLore()) {
					if (lore.startsWith(ChatColor.GRAY + "Ender")) {
						hasEnchant.add(player.getUniqueId());
						break;
					}
				}
			}
			
			if (hasEnchant.contains(player.getUniqueId())) {
				if (event.getProjectile() instanceof Arrow) {
					Random rand = new Random();
					int chance = rand.nextInt(50) + 1;
					if (chance > 10) {
						Arrow arrow = (Arrow) event.getProjectile();
						arrow.setMetadata("Ender", new FixedMetadataValue(ZoneEnchantments.getInstance(), true));
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		if (event.getEntity() instanceof Arrow) {
			Arrow arrow = (Arrow) event.getEntity();
			List<MetadataValue> arrowMetadata = arrow.getMetadata("Ender");
			if (!arrowMetadata.isEmpty() && arrowMetadata.get(0).value().equals(true)) {
				if (arrow.getShooter() instanceof Player) {
					Player player = (Player) arrow.getShooter();
					player.teleport(arrow);
				}
			}
		}
	}
	
	@Override
	public String getName() {
		return "Ender";
	}

	@Override
	public Rarity getRarity() {
		return Rarity.UNCOMMON;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public List<Material> getAllowedItems() {
		return new ArrayList<Material>(Arrays.asList(Material.BOW));
	}
}
