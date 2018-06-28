package me.sjalfsvig.zoneenchantments.enchantment.bow;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.sjalfsvig.zoneenchantments.ZoneEnchantments;
import me.sjalfsvig.zoneenchantments.enchantment.Enchantment;
import me.sjalfsvig.zoneenchantments.enchantment.SBEnchantment;
import me.sjalfsvig.zoneenchantments.util.ItemType;
import net.md_5.bungee.api.ChatColor;

public class EnchantmentMedic extends SBEnchantment implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		event.getPlayer().getInventory().clear();
		ItemStack a = new ItemStack(Material.BOW);
		a.addEnchantment(Enchantment.GLOW, 1);
		ItemMeta aMeta = a.getItemMeta();
		aMeta.setLore(Arrays.asList(ChatColor.GRAY + "Medic"));
		a.setItemMeta(aMeta);
		
		event.getPlayer().getInventory().addItem(a);
	}
	
	@EventHandler
	public void onEntityShootBow(EntityShootBowEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			ItemStack bow = event.getBow();
			Set<UUID> hasEnchant = new HashSet<UUID>();
			
			if (bow != null && bow.hasItemMeta() && bow.getItemMeta().hasLore()) {
				for (String lore : bow.getItemMeta().getLore()) {
					if (lore.startsWith(ChatColor.GRAY + "Medic")) {
						hasEnchant.add(player.getUniqueId());
						break;
					}
				}
				
				if (hasEnchant.contains(player.getUniqueId())) {
					if (event.getProjectile() instanceof Arrow) {
						Arrow arrow = (Arrow) event.getProjectile();
						arrow.setMetadata("Medic", new FixedMetadataValue(ZoneEnchantments.getInstance(), true));
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		if (event.getHitEntity() != null && event.getEntity().getShooter() != null) {
			if (event.getHitEntity() instanceof Player && event.getEntity().getShooter() instanceof Player && event.getEntity() instanceof Arrow) {
				Player target = (Player) event.getHitEntity();
				Arrow arrow = (Arrow) event.getEntity();
				List<MetadataValue> arrowMetadata = arrow.getMetadata("Medic");
				if (!arrowMetadata.isEmpty() && arrowMetadata.get(0).value().equals(true)) {
					target.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, 3));
				}
			}
		}
	}
	
	@Override
	public String getName() {
		return "Medic";
	}

	@Override
	public String getDescription() {
		return "This enchantment gives you the ability to heal players shot with this bow.";
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public ItemType getItemType() {
		return ItemType.BOW;
	}
}
