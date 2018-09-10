package model.Weapons;

import model.Weapon;

public class Axe extends Weapon
{
	public Axe()
	{
		name = "axe";
		pName = "Giant Axe";
		nName = "a large axe";
		description = "A giant axe, capable of powerful attacks.";
		physicalAttack = 5;
		magicAttack = 0;
		accuracyMod = 0;
		criticalChanceMod = 0;
		criticalDamageMod = 0;
		evasionMod = 0;
		healthMod = 5;
		manaMod = 0;
		magicDefense = 0;
		physicalDefense = 0;
		staminaMod = 0;
		handlingMod = 0;
	}

	@Override
	public void onEquip()
	{
	}

	@Override
	public void onRemove()
	{
	}
}
