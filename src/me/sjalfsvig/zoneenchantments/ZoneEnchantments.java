package me.sjalfsvig.zoneenchantments;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

import me.sjalfsvig.zoneenchantments.enchantment.EnchantmentGlow;
import me.sjalfsvig.zoneenchantments.enchantment.Enchantments;
import me.sjalfsvig.zoneenchantments.util.ArmorEquipListener;

public class ZoneEnchantments extends JavaPlugin {

	private static ZoneEnchantments instance;
	private ASkyBlockAPI skyblock;
	
	@Override
	public void onEnable() {
		instance = this;
		this.skyblock = ASkyBlockAPI.getInstance();
		this.registerGlowEnchantment();
		this.registerEvents();
	}
	
	public static ZoneEnchantments getInstance() {
		return instance;
	}
	
	public ASkyBlockAPI getSkyblock() {
		return this.skyblock;
	}
	
	private void registerEvents() {
		PluginManager manager = Bukkit.getPluginManager();
		manager.registerEvents(new ArmorEquipListener(), this);
		manager.registerEvents(Enchantments.BLISS, this);
		manager.registerEvents(Enchantments.HEALTH_BOOST, this);
	}
	
	private void registerGlowEnchantment() {
		Field field;
		try {
			field = Enchantment.class.getDeclaredField("acceptingNew");
			field.setAccessible(true);
			try {
				field.set(null, true);
				Enchantment.registerEnchantment(new EnchantmentGlow(100));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
}
