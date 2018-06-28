package me.sjalfsvig.zoneenchantments;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

import me.sjalfsvig.zoneenchantments.enchantment.Enchantment;
import me.sjalfsvig.zoneenchantments.enchantment.EnchantmentGlow;
import me.sjalfsvig.zoneenchantments.util.api.ArmorEquipListener;

public class ZoneEnchantments extends JavaPlugin {

	private static ZoneEnchantments instance;
	private ASkyBlockAPI skyblock;
	
	@Override
	public void onEnable() {
		instance = this;
		this.skyblock = ASkyBlockAPI.getInstance();
		this.registerEvents();
		this.registerGlowEnchantment();
	}
	
	public static ZoneEnchantments getInstance() {
		return instance;
	}
	
	public ASkyBlockAPI getSkyblock() {
		return this.skyblock;
	}
	
	public void registerEvents() {
		PluginManager manager = Bukkit.getPluginManager();
		manager.registerEvents(new ArmorEquipListener(), this);
		manager.registerEvents(Enchantment.BLISS, this);
		manager.registerEvents(Enchantment.HEALTH_BOOST, this);
		manager.registerEvents(Enchantment.SELF_DESTRUCT, this);
		manager.registerEvents(Enchantment.ANTI_GRAVITY, this);
		manager.registerEvents(Enchantment.DOUBLE_JUMP, this);
		manager.registerEvents(Enchantment.SWIFTNESS, this);
		manager.registerEvents(Enchantment.AQUATIC, this);
		manager.registerEvents(Enchantment.GLOWING, this);
		manager.registerEvents(Enchantment.BOOST, this);
		manager.registerEvents(Enchantment.ENDER, this);
		manager.registerEvents(Enchantment.QUICKDRAW, this);
		manager.registerEvents(Enchantment.EXTRA_SHOT, this);
		manager.registerEvents(Enchantment.MEDIC, this);
	}
	
	private void registerGlowEnchantment() {
		Field field;
		try {
			field = org.bukkit.enchantments.Enchantment.class.getDeclaredField("acceptingNew");
			field.setAccessible(true);
			try {
				field.set(null, true);
				org.bukkit.enchantments.Enchantment.registerEnchantment(new EnchantmentGlow(100));
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
