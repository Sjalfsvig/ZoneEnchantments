package me.sjalfsvig.zoneenchantments.enchanting;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import me.sjalfsvig.zoneenchantments.ZoneEnchantments;
import me.sjalfsvig.zoneenchantments.enchantment.Rarity;

public class Recipes {

	private static ZoneEnchantments plugin = ZoneEnchantments.getInstance();
	
	public Recipes() {
		Bukkit.getServer().addRecipe(getCommonDustRecipe());
		Bukkit.getServer().addRecipe(getUncommonDustRecipe());
		Bukkit.getServer().addRecipe(getRareDustRecipe());
		Bukkit.getServer().addRecipe(getEpicDustRecipe());
		Bukkit.getServer().addRecipe(getLegendaryDustRecipe());
	}
	
	public static ShapedRecipe getCommonDustRecipe() {
		ItemStack dustItem = ItemEnchantedDust.getItem(Rarity.COMMON);
		
		ShapedRecipe dust = new ShapedRecipe(new NamespacedKey(plugin, "enchantment_dust_common"), dustItem);
		
		dust.shape(" I ", "C C", " I ");
		dust.setIngredient('I', Material.GOLD_ORE);
		dust.setIngredient('C', Material.COAL_ORE);
		
		return dust;
	}
	
	public static ShapedRecipe getUncommonDustRecipe() {
		ItemStack dustItem = ItemEnchantedDust.getItem(Rarity.UNCOMMON);
		
		ShapedRecipe dust = new ShapedRecipe(new NamespacedKey(plugin, "enchantment_dust_uncommon"), dustItem);
		
		dust.shape("IC ", "G G", " CI");
		dust.setIngredient('I', Material.IRON_ORE);
		dust.setIngredient('C', Material.COAL_ORE);
		dust.setIngredient('G', Material.GOLD_ORE);
		
		return dust;
	}
	
	public static ShapedRecipe getRareDustRecipe() {
		ItemStack dustItem = ItemEnchantedDust.getItem(Rarity.RARE);
		
		ShapedRecipe dust = new ShapedRecipe(new NamespacedKey(plugin, "enchantment_dust_rare"), dustItem);
		
		dust.shape("ILI", "CLC", "ILI");
		dust.setIngredient('I', Material.IRON_ORE);
		dust.setIngredient('C', Material.COAL_ORE);
		dust.setIngredient('L', Material.LAPIS_ORE);
		
		return dust;
	}
	
	public static ShapedRecipe getEpicDustRecipe() {
		ItemStack dustItem = ItemEnchantedDust.getItem(Rarity.EPIC);
		
		ShapedRecipe dust = new ShapedRecipe(new NamespacedKey(plugin, "enchantment_dust_epic"), dustItem);
		
		dust.shape("LDL", "DED", "LDL");
		dust.setIngredient('L', Material.LAPIS_ORE);
		dust.setIngredient('D', Material.DIAMOND_ORE);
		dust.setIngredient('E', Material.EMERALD_ORE);
		
		return dust;
	}
	
	public static ShapedRecipe getLegendaryDustRecipe() {
		ItemStack dustItem = ItemEnchantedDust.getItem(Rarity.LEGENDARY);
		
		ShapedRecipe dust = new ShapedRecipe(new NamespacedKey(plugin, "enchantment_dust_legendary"), dustItem);
		
		dust.shape("EDE", "DED", "EDE");
		dust.setIngredient('D', Material.DIAMOND_ORE);
		dust.setIngredient('E', Material.EMERALD_ORE);
		
		return dust;
	}
}
