package me.sjalfsvig.zoneenchantments.enchantment;

public enum Rarity {

	LEGENDARY("Legendary"),
	EPIC("Epic"),
	RARE("Rare"),
	UNCOMMON("Uncommon"),
	COMMON("Common");
	
	private String name;
	
	Rarity(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
