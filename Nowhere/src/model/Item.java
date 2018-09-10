package model;

import java.util.ArrayList;

public abstract class Item
{
	public String name,
	pName,
	nName,
	description;
	
	public int physicalAttack,
	magicAttack,
	accuracyMod,
	criticalChanceMod,
	criticalDamageMod,
	evasionMod,
	healthMod,
	manaMod,
	magicDefense,
	physicalDefense,
	staminaMod,
	handlingMod;
	
	public ArrayList<String> flags = new ArrayList<String>();
	
	public abstract void onEquip();
	public abstract void onRemove();
}