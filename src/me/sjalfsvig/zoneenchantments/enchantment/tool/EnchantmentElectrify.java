package me.sjalfsvig.zoneenchantments.enchantment.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.inventory.ItemStack;

import me.sjalfsvig.zoneenchantments.enchantment.Rarity;
import me.sjalfsvig.zoneenchantments.enchantment.SBEnchantment;

public class EnchantmentElectrify extends SBEnchantment implements Listener {

	@EventHandler
	public void onPlayerFish(PlayerFishEvent event) {
		if (event.getState() == State.CAUGHT_FISH) {
			if (event.getCaught() instanceof Item) {
				Item caught = (Item) event.getCaught();
				ItemStack caughtStack = caught.getItemStack();
				if (caught.getItemStack().getType() == Material.RAW_FISH && caught.getItemStack().getDurability() == 0) {
					caughtStack.setType(Material.COOKED_FISH);
					event.getCaught().getLocation().getWorld().strikeLightningEffect(event.getCaught().getLocation());
					
				}
				
				if (caught.getItemStack().getType() == Material.RAW_FISH && caught.getItemStack().getDurability() == 1) {
					caughtStack.setType(Material.COOKED_FISH);
					caughtStack.setDurability((short) 1);
					event.getCaught().getLocation().getWorld().strikeLightningEffect(event.getCaught().getLocation());
				}
			}
		}
	}
	
	@Override
	public String getName() {
		return "Electrify";
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
		return new ArrayList<Material>(Arrays.asList(Material.FISHING_ROD));
	}
}
