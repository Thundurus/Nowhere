package model.Items;

import model.Item;

public class Nothing extends Item
{
	public Nothing()
	{
		name = "nothing";
		pName = "nothing";
		nName = "nothing";
		description = "nothing";
		physicalAttack = 0;
		magicAttack = 0;
		accuracyMod = 0;
		criticalChanceMod = 0;
		criticalDamageMod = 0;
		evasionMod = 0;
		healthMod = 0;
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
