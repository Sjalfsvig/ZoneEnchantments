package me.sjalfsvig.zoneenchantments.enchanting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.sjalfsvig.zoneenchantments.enchantment.Enchantment;
import me.sjalfsvig.zoneenchantments.enchantment.Rarity;
import me.sjalfsvig.zoneenchantments.enchantment.SBEnchantment;
import me.sjalfsvig.zoneenchantments.util.RomanNumeral;
import net.md_5.bungee.api.ChatColor;

public class EventPrepareItemCraft implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getCurrentItem() != null) {
			if (event.getClickedInventory() instanceof CraftingInventory) {
				CraftingInventory inventory = (CraftingInventory) event.getClickedInventory();
				if (inventory.getResult() != null) {
					if (inventory.getResult().isSimilar(ItemEnchantedDust.getItem(Rarity.COMMON)) || inventory.getResult().isSimilar(ItemEnchantedDust.getItem(Rarity.UNCOMMON)) || 
							inventory.getResult().isSimilar(ItemEnchantedDust.getItem(Rarity.RARE)) || inventory.getResult().isSimilar(ItemEnchantedDust.getItem(Rarity.LEGENDARY)) ||
									inventory.getResult().isSimilar(ItemEnchantedDust.getItem(Rarity.EPIC))){
						ItemStack result = inventory.getResult().clone();
						ItemMeta resultMeta = result.getItemMeta();
						List<String> lore = resultMeta.getLore();
						SBEnchantment randomEnch = null;
						
						if (inventory.getResult().isSimilar(ItemEnchantedDust.getItem(Rarity.COMMON))) {
							randomEnch = Enchantment.getRandomEnchantment(Rarity.COMMON);
						}
						
						if (inventory.getResult().isSimilar(ItemEnchantedDust.getItem(Rarity.UNCOMMON))) {
							randomEnch = Enchantment.getRandomEnchantment(Rarity.UNCOMMON);
						}
						
						if (inventory.getResult().isSimilar(ItemEnchantedDust.getItem(Rarity.RARE))) {
							randomEnch = Enchantment.getRandomEnchantment(Rarity.RARE);
						}
						
						if (inventory.getResult().isSimilar(ItemEnchantedDust.getItem(Rarity.EPIC))) {
							randomEnch = Enchantment.getRandomEnchantment(Rarity.EPIC);
						}
						
						if (inventory.getResult().isSimilar(ItemEnchantedDust.getItem(Rarity.LEGENDARY))) {
							randomEnch = Enchantment.getRandomEnchantment(Rarity.LEGENDARY);
						}
						
						if (randomEnch.getMaxLevel() == 1) {
							lore.add(ChatColor.GRAY + randomEnch.getName());
						} else {
							Random random = new Random();
							int randomLevel = random.nextInt(randomEnch.getMaxLevel() - 1 + 1) + 1;
							lore.add(ChatColor.GRAY + randomEnch.getName() + " " + RomanNumeral.fromInt(randomLevel + ""));
						}
						resultMeta.setLore(lore);
						result.setItemMeta(resultMeta);
						inventory.setResult(result);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPrepareItemCraft(PrepareItemCraftEvent event) {
		CraftingInventory inventory = event.getInventory();
		
		List<ItemStack> contents = new ArrayList<ItemStack>(Arrays.asList(inventory.getContents()));
		for (ItemStack content : new ArrayList<ItemStack>(contents)) {
			if (content.getType() == Material.AIR) {
				contents.remove(content);
			}
		}
		
		if (contents.size() == 2) {
			ItemStack armor = null;
			ItemStack dust = null;
			if (contents.get(0).hasItemMeta() && contents.get(0).getItemMeta().hasDisplayName() && contents.get(0).getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Enchantment Dust")) {
				armor = contents.get(1);
				dust = contents.get(0);
			}
			
			if (contents.get(1).hasItemMeta() && contents.get(1).getItemMeta().hasDisplayName() && contents.get(1).getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Enchantment Dust")) {
				armor = contents.get(0);
				dust = contents.get(1);
			}
			
			if (dust != null && dust.hasItemMeta() && dust.getItemMeta().hasDisplayName() && dust.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Enchantment Dust")) {
				String ench = dust.getItemMeta().getLore().get(1);
				String[] enchSplit = ench.split(" ");
						
				if (Enchantment.fromName(ChatColor.stripColor(enchSplit[0])).getAllowedItems().contains(armor.getType())) {
					ItemStack newArmor = armor.clone();
					newArmor.addEnchantment(Enchantment.GLOW, 1);
					if (newArmor.hasItemMeta() && newArmor.getItemMeta().hasLore()) {
						ItemMeta newMeta = newArmor.getItemMeta();
						List<String> lore = newMeta.getLore();
						lore.add(ench);
						newMeta.setLore(lore);
						newArmor.setItemMeta(newMeta);
					} else {
						ItemMeta newMeta = newArmor.getItemMeta();
						newMeta.setLore(Arrays.asList(ench));
						newArmor.setItemMeta(newMeta);
					}

					
					
					if (inventory != null && inventory.getResult() != null && inventory.getResult().hasItemMeta() && inventory.getResult().getItemMeta().hasLore() && inventory.getResult().getItemMeta().getLore().contains(ChatColor.GRAY + "Quickdraw")
							&& inventory.getResult().getItemMeta().getLore().contains(ChatColor.GRAY + "Extra-Shot")) {
						inventory.setResult(null);
					} else {
						inventory.setResult(newArmor);
					}
				} else {
					inventory.setResult(null);
				}
			}
		}
	}
}
