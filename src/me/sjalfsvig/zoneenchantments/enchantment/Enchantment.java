package me.sjalfsvig.zoneenchantments.enchantment;

import me.sjalfsvig.zoneenchantments.enchantment.armor.EnchantmentBliss;
import me.sjalfsvig.zoneenchantments.enchantment.armor.EnchantmentHealthBoost;
import me.sjalfsvig.zoneenchantments.enchantment.armor.EnchantmentSelfDestruct;
import me.sjalfsvig.zoneenchantments.enchantment.armor.boots.EnchantmentAntiGravity;
import me.sjalfsvig.zoneenchantments.enchantment.armor.boots.EnchantmentDoubleJump;
import me.sjalfsvig.zoneenchantments.enchantment.armor.boots.EnchantmentSwiftness;
import me.sjalfsvig.zoneenchantments.enchantment.armor.elytra.EnchantmentBoost;
import me.sjalfsvig.zoneenchantments.enchantment.armor.helmet.EnchantmentAquatic;
import me.sjalfsvig.zoneenchantments.enchantment.armor.helmet.EnchantmentGlowing;
import me.sjalfsvig.zoneenchantments.enchantment.bow.EnchantmentEnder;
import me.sjalfsvig.zoneenchantments.enchantment.bow.EnchantmentExtraShot;
import me.sjalfsvig.zoneenchantments.enchantment.bow.EnchantmentMedic;
import me.sjalfsvig.zoneenchantments.enchantment.bow.EnchantmentQuickdraw;

public class Enchantment {

	// Used to store all the enchantments.
	/*
	 * All: GLOW, 
	 * Armor: BLISS, HEALTH_BOOST, SELF_DESTRUCT. Helmet: AQUATIC, GLOWING. Boots: ANTI_GRAVITY, SWIFTNESS, DOUBLE_JUMP. Elytra: BOOST
	 * Bow: ENDER, QUICKDRAW, EXTRA_SHOT, MEDIC, SHOCK
	 * Sword: LIFE_STEAL, FROST_ASPECT, DISARM, CONFUSION, BLINDNESS, EXPERIENCE, HARVEST
	 * Pickaxe: EXPLOSIVE, EXPERIENCE, AUTO_SMELT, HASTE, MAGNET, FRIEND
	 * Axe: EXPLOSIVE, HASTE, MAGNET, FRIEND, TREE_FELLER
	 * Shovel: HASTE, MAGNET, FRIEND
	 * Hoe: HARVESTRY
	 * Fishing Rod: ELECTRICITY
	 */
	
	public static EnchantmentGlow GLOW = new EnchantmentGlow(100);
	public static EnchantmentBliss BLISS = new EnchantmentBliss();
	public static EnchantmentHealthBoost HEALTH_BOOST = new EnchantmentHealthBoost();
	public static EnchantmentSelfDestruct SELF_DESTRUCT = new EnchantmentSelfDestruct();
	public static EnchantmentAntiGravity ANTI_GRAVITY = new EnchantmentAntiGravity();
	public static EnchantmentDoubleJump DOUBLE_JUMP = new EnchantmentDoubleJump();
	public static EnchantmentSwiftness SWIFTNESS = new EnchantmentSwiftness();
	public static EnchantmentAquatic AQUATIC = new EnchantmentAquatic();
	public static EnchantmentGlowing GLOWING = new EnchantmentGlowing();
	public static EnchantmentBoost BOOST = new EnchantmentBoost();
	public static EnchantmentEnder ENDER = new EnchantmentEnder();
	public static EnchantmentQuickdraw QUICKDRAW = new EnchantmentQuickdraw();
	public static EnchantmentExtraShot EXTRA_SHOT = new EnchantmentExtraShot();
	public static EnchantmentMedic MEDIC = new EnchantmentMedic();
}
