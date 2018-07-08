package me.sjalfsvig.zoneenchantments.enchantment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import me.sjalfsvig.zoneenchantments.enchantment.armor.EnchantmentBliss;
import me.sjalfsvig.zoneenchantments.enchantment.armor.EnchantmentHealthBoost;
import me.sjalfsvig.zoneenchantments.enchantment.armor.EnchantmentSelfDestruct;
import me.sjalfsvig.zoneenchantments.enchantment.armor.boots.EnchantmentAgility;
import me.sjalfsvig.zoneenchantments.enchantment.armor.boots.EnchantmentAntiGravity;
import me.sjalfsvig.zoneenchantments.enchantment.armor.boots.EnchantmentDoubleJump;
import me.sjalfsvig.zoneenchantments.enchantment.armor.boots.EnchantmentSwiftness;
import me.sjalfsvig.zoneenchantments.enchantment.armor.elytra.EnchantmentBoost;
import me.sjalfsvig.zoneenchantments.enchantment.armor.helmet.EnchantmentAquatic;
import me.sjalfsvig.zoneenchantments.enchantment.armor.helmet.EnchantmentGlowing;
import me.sjalfsvig.zoneenchantments.enchantment.tool.EnchantmentElectrify;
import me.sjalfsvig.zoneenchantments.enchantment.tool.EnchantmentExperience;
import me.sjalfsvig.zoneenchantments.enchantment.tool.EnchantmentFriendly;
import me.sjalfsvig.zoneenchantments.enchantment.tool.EnchantmentHarvestry;
import me.sjalfsvig.zoneenchantments.enchantment.tool.EnchantmentHaste;
import me.sjalfsvig.zoneenchantments.enchantment.tool.EnchantmentTreeFeller;
import me.sjalfsvig.zoneenchantments.enchantment.tool.bow.EnchantmentEnder;
import me.sjalfsvig.zoneenchantments.enchantment.tool.bow.EnchantmentExtraShot;
import me.sjalfsvig.zoneenchantments.enchantment.tool.bow.EnchantmentMedic;
import me.sjalfsvig.zoneenchantments.enchantment.tool.bow.EnchantmentQuickdraw;
import me.sjalfsvig.zoneenchantments.enchantment.tool.bow.EnchantmentZeus;
import me.sjalfsvig.zoneenchantments.enchantment.tool.sword.EnchantmentBlindness;
import me.sjalfsvig.zoneenchantments.enchantment.tool.sword.EnchantmentConfusion;
import me.sjalfsvig.zoneenchantments.enchantment.tool.sword.EnchantmentDisarm;
import me.sjalfsvig.zoneenchantments.enchantment.tool.sword.EnchantmentFrostAspect;
import me.sjalfsvig.zoneenchantments.enchantment.tool.sword.EnchantmentLifeSteal;
import me.sjalfsvig.zoneenchantments.enchantment.tool.sword.EnchantmentNutrition;

public class Enchantment {
	
	public static EnchantmentGlow GLOW = new EnchantmentGlow(100);
	
	public static EnchantmentBliss BLISS = new EnchantmentBliss();
	public static EnchantmentHealthBoost HEALTH_BOOST = new EnchantmentHealthBoost();
	public static EnchantmentSelfDestruct SELF_DESTRUCT = new EnchantmentSelfDestruct();
	
	public static EnchantmentAntiGravity ANTI_GRAVITY = new EnchantmentAntiGravity();
	public static EnchantmentDoubleJump DOUBLE_JUMP = new EnchantmentDoubleJump();
	public static EnchantmentSwiftness SWIFTNESS = new EnchantmentSwiftness();
	public static EnchantmentAgility AGILITY = new EnchantmentAgility();
	
	public static EnchantmentAquatic AQUATIC = new EnchantmentAquatic();
	public static EnchantmentGlowing GLOWING = new EnchantmentGlowing();
	
	public static EnchantmentBoost BOOST = new EnchantmentBoost();
	
	public static EnchantmentEnder ENDER = new EnchantmentEnder();
	public static EnchantmentQuickdraw QUICKDRAW = new EnchantmentQuickdraw();
	public static EnchantmentExtraShot EXTRA_SHOT = new EnchantmentExtraShot();
	public static EnchantmentMedic MEDIC = new EnchantmentMedic();
	public static EnchantmentZeus ZEUS = new EnchantmentZeus();
	
	public static EnchantmentLifeSteal LIFE_STEAL = new EnchantmentLifeSteal();
	public static EnchantmentFrostAspect FROST_ASPECT = new EnchantmentFrostAspect();
	public static EnchantmentDisarm DISARM = new EnchantmentDisarm();
	public static EnchantmentConfusion CONFUSION = new EnchantmentConfusion();
	public static EnchantmentBlindness BLINDNESS = new EnchantmentBlindness();
	public static EnchantmentExperience EXPERIENCE = new EnchantmentExperience();
	public static EnchantmentNutrition NUTRITION = new EnchantmentNutrition();
	
	public static EnchantmentHaste HASTE = new EnchantmentHaste();
	public static EnchantmentFriendly FRIENDLY = new EnchantmentFriendly();
	public static EnchantmentTreeFeller TREE_FELLER = new EnchantmentTreeFeller();
	public static EnchantmentHarvestry HARVESTRY = new EnchantmentHarvestry();
	public static EnchantmentElectrify ELECTRIFY = new EnchantmentElectrify();
	
	private static List<SBEnchantment> enchantments = new ArrayList<SBEnchantment>(Arrays.asList(BLISS, HEALTH_BOOST, SELF_DESTRUCT, ANTI_GRAVITY, DOUBLE_JUMP, SWIFTNESS, AGILITY, AQUATIC, GLOWING, BOOST, ENDER,
			QUICKDRAW, EXTRA_SHOT, MEDIC, ZEUS, LIFE_STEAL, FROST_ASPECT, DISARM, CONFUSION, BLINDNESS, EXPERIENCE, NUTRITION, HASTE, FRIENDLY, TREE_FELLER, HARVESTRY, ELECTRIFY));
	
	
	public static SBEnchantment fromName(String enchantment) {
		switch (enchantment.toLowerCase()) {
		case "bliss":
			return Enchantment.BLISS;
		case "health-boost":
			return Enchantment.HEALTH_BOOST;
		case "self-destruct":
			return Enchantment.SELF_DESTRUCT;
		case "anti-gravity":
			return Enchantment.ANTI_GRAVITY;
		case "double-jump":
			return Enchantment.DOUBLE_JUMP;
		case "swiftness":
			return Enchantment.SWIFTNESS;
		case "agility":
			return Enchantment.AGILITY;
		case "aquatic":
			return Enchantment.AQUATIC;
		case "glowing":
			return Enchantment.GLOWING;
		case "boost":
			return Enchantment.BOOST;
		case "ender":
			return Enchantment.ENDER;
		case "quickdraw":
			return Enchantment.QUICKDRAW;
		case "extra-shot":
			return Enchantment.EXTRA_SHOT;
		case "medic":
			return Enchantment.MEDIC;
		case "zeus":
			return Enchantment.ZEUS;
		case "life-steal":
			return Enchantment.LIFE_STEAL;
		case "frost-aspect":
			return Enchantment.FROST_ASPECT;
		case "disarm":
			return Enchantment.DISARM;
		case "confusion":
			return Enchantment.CONFUSION;
		case "blindness":
			return Enchantment.BLINDNESS;
		case "nutrition":
			return Enchantment.NUTRITION;
		case "experience":
			return Enchantment.EXPERIENCE;
		case "haste":
			return Enchantment.HASTE;
		case "friendly":
			return Enchantment.FRIENDLY;
		case "tree-feller":
			return Enchantment.TREE_FELLER;
		case "harvestry":
			return Enchantment.HARVESTRY;
		case "electrify":
			return Enchantment.ELECTRIFY;
		default:
			return null;
		}
	}
	
	public static SBEnchantment fromString(String enchantment) {
		switch (enchantment.toLowerCase()) {
		case "bliss":
			return Enchantment.BLISS;
		case "healthboost":
			return Enchantment.HEALTH_BOOST;
		case "selfdestruct":
			return Enchantment.SELF_DESTRUCT;
		case "antigravity":
			return Enchantment.ANTI_GRAVITY;
		case "doublejump":
			return Enchantment.DOUBLE_JUMP;
		case "swiftness":
			return Enchantment.SWIFTNESS;
		case "agility":
			return Enchantment.AGILITY;
		case "aquatic":
			return Enchantment.AQUATIC;
		case "glowing":
			return Enchantment.GLOWING;
		case "boost":
			return Enchantment.BOOST;
		case "ender":
			return Enchantment.ENDER;
		case "quickdraw":
			return Enchantment.QUICKDRAW;
		case "extrashot":
			return Enchantment.EXTRA_SHOT;
		case "medic":
			return Enchantment.MEDIC;
		case "zeus":
			return Enchantment.ZEUS;
		case "lifesteal":
			return Enchantment.LIFE_STEAL;
		case "frostaspect":
			return Enchantment.FROST_ASPECT;
		case "disarm":
			return Enchantment.DISARM;
		case "confusion":
			return Enchantment.CONFUSION;
		case "blindness":
			return Enchantment.BLINDNESS;
		case "nutrition":
			return Enchantment.NUTRITION;
		case "experience":
			return Enchantment.EXPERIENCE;
		case "haste":
			return Enchantment.HASTE;
		case "friendly":
			return Enchantment.FRIENDLY;
		case "treefeller":
			return Enchantment.TREE_FELLER;
		case "harvestry":
			return Enchantment.HARVESTRY;
		case "electrify":
			return Enchantment.ELECTRIFY;
		default:
			return null;
		}
	}
	
	public static SBEnchantment getRandomEnchantment(Rarity rarity) {
		List<SBEnchantment> rarityEnch = new ArrayList<SBEnchantment>();
		for (SBEnchantment sbench : enchantments) {
			if (sbench.getRarity() == rarity) {
				rarityEnch.add(sbench);
			}
		}
		
		Random random = new Random();
		int randEnch = random.nextInt(rarityEnch.size() - 1 + 1) + 1;
		
		return rarityEnch.get(randEnch-1);
	}
}
