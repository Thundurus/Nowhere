
package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.scene.layout.VBox;
import model.Items.Nothing;
import model.Skills.*;
import model.Weapons.Axe;
import util.TurnData;
import view.TextManager;
import view.TextStyle;

public class Character
{
	protected String name;
	protected Item[] inventory = new Item[10];
	protected Item[] equipped = new Item[4];
	protected ArrayList<Character> nearby = new ArrayList<Character>();
	protected Form[] forms = new Form[3];
	protected Form currentForm;
	protected Node location = Master.getBaseNode();
	protected Skill queuedSkill = new Wait();
	protected Optional<Character> target = Optional.empty();
	/**
	 * Fighting status consists of five integers:
	 * <p>[0] is 0 or 1, where 1 means they are in a fight currently.
	 * <p>[1] is 0 or 1, where 1 means they have not been K.O.'d yet.
	 * <p>[2] is 1~{@link Combat#MAX_SIZE}, and represents their fighter id. It is generally 0 outside of combat, but not necessarily. Intruders also have a value of 0 until they get into the fight.
	 * <p>[3] is 1~{@link Integer#MAX_SIZE}, and represents the combat instance id. It is generally 0 outside of combat, but not necessarily.
	 * <p>[4] is 1~{@link Combat#MAX_SIZE}, and represents the side they are fighting on. Characters with the same [4] value are considered to be allies, and do not need to defeat each other to end battle.
	 */
	protected int[] fightingStats = {0,0,0,0,0};
	/**
	 *The stat display is the {@link VBox} containing the HP, MP, and SP displays for this {@link Character}. 
	 */
	protected VBox statDisplay;
	protected LinkedHashMap<String, Double> displayed = new LinkedHashMap<String, Double>();
	
	protected int id;
	
	
	public Character(String name, int characterID)
	{
		this.name = name;
		id = characterID;
		forms[0] = new Form("Base", this, 0);
		currentForm = forms[0];
		equipped[0] = new Axe();
		equipped[1] = new Nothing();
		equipped[2] = new Nothing();
		equipped[3] = new Nothing();
		//This is allegedly poor form.
		//This is definitely poor form!
		recalculate();
		currentForm.hp = currentForm.maxHP;
		currentForm.mp = currentForm.maxMP;
		currentForm.sp = currentForm.maxSP;
		displayed.put("hp", 100.0);
		displayed.put("mp", 100.0);
		displayed.put("sp", 100.0);
		statDisplay = Master.createStatDisplay(this);
	}
	
	public int getID()
	{
		return id;
	}
	public String getName()
	{
		return name;
	}
	
	public void calculateMaxHP(Form form)
	{
		form.calculateMaxHP();
	}
	public void calculateMaxMP(Form form)
	{
		form.calculateMaxMP();
	}
	public void calculateMaxSP(Form form)
	{
		form.calculateMaxSP();
	}
	public void calculateMaxHP()
	{
		currentForm.calculateMaxHP();
	}
	public void calculateMaxMP()
	{
		currentForm.calculateMaxMP();
	}
	public void calculateMaxSP()
	{
		currentForm.calculateMaxSP();
	}
	public void recalculate(Form form)
	{
		//I do not recall off-hand whether or not this works in this language. Needs testing.
		//I could also just not do it, but where is the learning in that???
		for (Effect i : form.effects)
		{
			if(i.remainingTime <= 0)
			{
				form.effects.remove(i);
			}
		}
		
		//Derived stats currently check all equipped items to determine stat totals.
		//One faster option would be to have the onEquip() method for items add their bonuses to tempStats, and remove them when the item is removed
		
		int temp = 0;
		
		//physical attack
		temp =  form.level * 5;
		temp += getStat("eStrength").intValue() * 5;
		temp += getWeapon().physicalAttack;
		temp *= getWeapon().physicalAttackModifier;
		//physicalAttack added by equipment (other than the weapon) provide flat increases
		for (int i = 1; i < equipped.length;i++)
		{
			temp += equipped[i].physicalAttack;
		}
		form.baseStats[11] = temp;
		
		
		//magic attack
		temp = form.level * 5;
		temp += getStat("eIntelligence").intValue() * 5;
		temp += getWeapon().magicAttack;
		temp *= getWeapon().magicAttackModifier;
		//magicAttack added by equipment (other than the weapon) provide flat increases
		for (int i = 1; i < equipped.length;i++)
		{
			temp += equipped[i].magicAttack;
		}
		form.baseStats[12] = temp;
		
//		//accuracy
//		temp = 0;
//		temp += ((getStat("ePerception").intValue()) * 3) + getStat("ePsychology").intValue();
//		for (int i = 0; i < equipped.length;i++)
//		{
//			temp += equipped[i].accuracyMod;
//		}
//		form.baseStats[13] = temp;
		
		//critical chance
		temp = (getStat("eSkill").intValue() / 2);
		for (int i = 0; i < equipped.length;i++)
		{
			temp += equipped[i].criticalChanceMod;
		}
		form.baseStats[14] = temp;
		
		//critical damage
		temp = 0;
		for (int i = 0; i < equipped.length;i++)
		{
			temp += equipped[i].criticalDamageMod;
		}
		form.baseStats[15] = temp;
		
//		//evasion
//		temp = 0;
//		temp += ((getStat("eAgility").intValue()) * 3) + getStat("eSpeed").intValue();
//		for (int i = 0; i < equipped.length;i++)
//		{
//			temp += equipped[i].evasionMod;
//		}
//		form.baseStats[16] = temp;
		
		//physical defense
		temp = getStat("eEndurance").intValue() * 5;
		for (int i = 0; i < equipped.length;i++)
		{
			temp += equipped[i].physicalDefense;
		}
		form.baseStats[17] = temp;
		
		//magic defense
		temp = getStat("eWillpower").intValue() * 5;
		for (int i = 0; i < equipped.length;i++)
		{
			temp += equipped[i].magicDefense;
		}
		form.baseStats[18] = temp;
		
		//weapon handling
		temp = ((getStat("eSkill").intValue()) * 5);
		for (int i = 0; i < equipped.length;i++)
		{
			temp += equipped[i].handlingMod;
		}
		form.baseStats[19] = temp;
		
		calculateMaxHP(form);
		calculateMaxMP(form);
		calculateMaxSP(form);
	}
	/**
	 * Calculates the stats that are derived from base stats.
	 * TODO: Maybes: Accuracy + Evasion, Psychic defense stat
	 */
	public void recalculate()
	{
		recalculate(currentForm);
	}
	public void statValidator()
	{
		//Not sure if a heavy method like this should be here.
		recalculate();
		
		for (int i = 0; i < 10;i++)
		{
			if (currentForm.baseStats[i] < 1) currentForm.baseStats[i] = 1;
		}
		
		if (currentForm.hp < 0) currentForm.hp = 0;
		if (currentForm.hp > currentForm.maxHP) currentForm.hp = currentForm.maxHP;
		if (currentForm.mp < 0) currentForm.mp = 0;
		if (currentForm.mp > currentForm.maxMP) currentForm.mp = currentForm.maxMP;
		if (currentForm.sp < 0) currentForm.sp = 0;
		if (currentForm.sp > currentForm.maxSP) currentForm.sp = currentForm.maxSP;
	}
	/**
	 * Returns a {@link Number} containing the value for the specified statistic. Utilizes the <code>getStat(String)</code> method in {@link Form}.
	 * 
	 * @param stat {@link String} representation of a {@link Character} stat
	 * @return {@link Number} representing the specified stat
	 * @see Form#getStat(String) Form.getStat()
	 */
	public Number getStat (String stat)
	{
		return currentForm.getStat(stat);
	}
	/**
	 * Adds permanent stats to this {@link Character}.
	 * 
	 * @param stat {@link String} representation of a {@link Character} stat
	 * @param amount amount to increase stat by
	 * @see #modStat(String, int)
	 */
	public void changeStat(String stat, int amount)
	{
		switch(stat)
		{
			case "strength":
				currentForm.baseStats[0] += amount;
				break;
			
			case "arcane":
				currentForm.baseStats[1] += amount;
				break;
			
			case "intelligence":
				currentForm.baseStats[2] += amount;
				break;
			
			case "endurance":
				currentForm.baseStats[3] += amount;
				break;
			
			case "willpower":
				currentForm.baseStats[4] += amount;
				break;
			
			case "resilience":
				currentForm.baseStats[5] += amount;
				break;
			
			case "speed":
				currentForm.baseStats[6] += amount;
				break;
			
			case "skill":
				currentForm.baseStats[7] += amount;
				break;
			
			case "aura":
				currentForm.baseStats[8] += amount;
				break;
				
			case "baseAttack":
				currentForm.baseStats[10] += amount;
				break;
			
			case "physicalAttack":
				currentForm.baseStats[11] += amount;
				break;
				
			case "magicAttack":
				currentForm.baseStats[12] += amount;
				break;

			case "accuracy":
				currentForm.baseStats[13] += amount;
				break;

			case "criticalChance":
				currentForm.baseStats[14] += amount;
				break;

			case "criticalDamage":
				currentForm.baseStats[15] += amount;
				break;

			case "evasion":
				currentForm.baseStats[16] += amount;
				break;

			case "magicDefense":
				currentForm.baseStats[17] += amount;
				break;

			case "physicalDefense":
				currentForm.baseStats[18] += amount;
				break;

			case "weaponHandling":
				currentForm.baseStats[19] += amount;
				break;
			default: throw new IllegalArgumentException("The stat \"" + stat + "\" does not exist.");
		}
		
		statValidator();
	}
	/**
	 * Adds temporary stats to this {@link Character}.
	 * 
	 * <p>Temporary stats are not automatically purged, and are to be modified only by {@link #addEffect(Effect)} and {@link #removeEffect()}.
	 * 
	 * @param stat {@link String} representation of a {@link Character} stat
	 * @param amount amount to increase stat by
	 * @see #changeStat(String, int)
	 */
	public void modStat(String stat, int amount)
	{
		switch(stat)
		{
			case "strength":
				currentForm.tempStats[0] += amount;
				break;
			
			case "arcane":
				currentForm.tempStats[1] += amount;
				break;
			
			case "intelligence":
				currentForm.tempStats[2] += amount;
				break;
			
			case "endurance":
				currentForm.tempStats[3] += amount;
				break;
			
			case "willpower":
				currentForm.tempStats[4] += amount;
				break;
			
			case "resilience":
				currentForm.tempStats[5] += amount;
				break;
			
			case "speed":
				currentForm.tempStats[6] += amount;
				break;
			
			case "skill":
				currentForm.tempStats[7] += amount;
				break;
			
			case "aura":
				currentForm.tempStats[8] += amount;
				break;
				
			case "baseAttack":
				currentForm.tempStats[10] += amount;
				break;
				
			case "physicalAttack":
				currentForm.tempStats[11] += amount;
				break;
				
			case "magicAttack":
				currentForm.tempStats[12] += amount;
				break;

			case "accuracy":
				currentForm.tempStats[13] += amount;
				break;

			case "criticalChance":
				currentForm.tempStats[14] += amount;
				break;

			case "criticalDamage":
				currentForm.tempStats[15] += amount;
				break;

			case "evasion":
				currentForm.tempStats[16] += amount;
				break;

			case "magicDefense":
				currentForm.tempStats[17] += amount;
				break;

			case "physicalDefense":
				currentForm.tempStats[18] += amount;
				break;

			case "weaponHandling":
				currentForm.tempStats[19] += amount;
				break;
			default: throw new IllegalArgumentException("The stat \"" + stat + "\" does not exist.");
		}
		
		statValidator();
	}
	/**
	 * Deals damage to recoverable stats (i.e. <tt>"hp"</tt>, <tt>"mp"</tt>, and <tt>"sp"</tt>).
	 * <p>Accepts positive and negative values.
	 * 
	 * @param stat {@link String} representation of a {@link Character} stat
	 * @param amount amount to damage stat by
	 * @param display whether or not {@link #statDisplay} is changed. <strong>We usually want this.</strong>
	 * @see #restoreStat(String, double, boolean)
	 */
	public void damageStat(String stat, double amount, boolean display)
	{
		switch(stat)
		{
			case "hp": 
				currentForm.hp -= amount;
				statValidator();
				if(display)
				{
					Master.changeStatDisplay(this, "hp", currentForm.hp, (currentForm.hp/currentForm.maxHP) * 100);
					displayed.put("hp", (currentForm.hp/currentForm.maxHP) * 100);
				}
				break;
				
			case "mp":
				currentForm.mp -= amount;
				statValidator();
				if(display)
				{
					Master.changeStatDisplay(this, "mp", currentForm.mp, (currentForm.mp/currentForm.maxMP) * 100);
					displayed.put("mp", (currentForm.mp/currentForm.maxMP) * 100);
				}
				break;
				
			case "sp":
				currentForm.sp -= amount;
				statValidator();
				if(display)
				{
					Master.changeStatDisplay(this, "sp", currentForm.sp, (currentForm.sp/currentForm.maxSP) * 100);
					displayed.put("sp", (currentForm.sp/currentForm.maxSP) * 100);
				}
				break;
			default: throw new IllegalArgumentException("The stat \"" + stat + "\" does not exist.");
		}
	}
	/**
	 *Recovers recoverable stats (i.e. <tt>"hp"</tt>, <tt>"mp"</tt>, and <tt>"sp"</tt>).
	 *<p>This method calls the {@link #damageStat(String, double, boolean)} method with a reversed sign.
	 * 
	 * @param stat {@link String} representation of a {@link Character} stat
	 * @param amount amount to recover stat by
	 * @param display whether or not {@link #statDisplay} is changed. <strong>We usually want this.</strong>
	 * @see #damageStat(String, double, boolean)
	 */
	public void restoreStat(String stat, double amount, boolean display)
	{
		damageStat(stat, amount * -1, display);
	}

	public Weapon getWeapon()
	{
		return (Weapon) equipped[0];
	}
	//TODO: Fix this.
	public String listEquipped()
	{
		StringBuffer items = new StringBuffer();
		if (equipped[1].name == "nothing" && equipped[2].name == "nothing" && equipped[3].name == "nothing" && equipped[4].name == "nothing")
			items.append("You are not wearing anything." );
		else
		{
			//TODO
		}
		if (equipped[0].name != "nothing")
			items.append("In your weapon slot, you have " + equipped[0].nName + " equipped. ");
		else
			items.append("You do not have a weapon equipped. ");
		return items.toString();
	}
	public String listInventory()
	{
		StringBuffer items = new StringBuffer();
		items.append("You currently possess: ");
		for(Item i : inventory)
		{
			items.append("\n" + i.nName);
		}
		return items.toString();
	}
	public String listPerks()
	{
		StringBuffer perkz = new StringBuffer();
		perkz.append("You currently possess:\n");
		for(String i : currentForm.perks)
		{
			perkz.append("\n" + i.toString());
		}
		perkz.append("\n\n");
		return perkz.toString();
	}
	public String listSkills()
	{
		StringBuilder skillz = new StringBuilder();
		skillz.append("You currently possess:\n");
		for(Skill i : currentForm.skills)
		{
			skillz.append(i.pName + "\n");
		}
		return skillz.toString();
	}
	
	/**
	 * @return Returns an unmodifiable copy of the types from this character.
	 */
	public ArrayList<Type> getType()
	{
		final ArrayList<Type> copy = new ArrayList<Type>(currentForm.type);
		return copy;
	}
	//TODO: This is not how types work.
	public void addType(Type type)
	{
		currentForm.type.add(type);
	}
	/**
	 *Returns the <code>index</code> of <code>currentForm</code> in {@link forms}.
	 * 
	 * @return index of the current form
	 */
	public int getFormIndex()
	{
		return currentForm.index;
	}
	/**
	 * Returns the <code>name</code> of the specified {@link Form}.
	 * 
	 * @param index the index of {@link forms} containing the desired {@link Form}
	 * @return name of the current form
	 * @throws IllegalArgumentException if the index is negative
	 */
	public String getFormName(int index)
	{
		if(index < 0)
		{
			throw new IllegalArgumentException("The index must be non-negative.");
		}
		if(forms[index] == null)
		{
			TextManager.appendText(("Attempted to access nonexistant form on " + name + "."), TextStyle.ERROR);
			return "ERROR";
		}
		return forms[index].name;
	}
	/**
	 * Returns the indices in <code>forms</code> that contain valid (non-null) forms.  
	 * <p>The reason why this method does not simply return the quantity of forms is because we are not yet sure if <code>forms</code> is allowed to have noncontiguous placement of Forms.
	 * 
	 * @return int[] containing the indices in <code>forms</code> that contain a {@link Form}.
	 */
	public int[] getForms()
	{
		//There is probably a way to do this in a single pass without using an additional structure.
		ArrayList<Integer> indicies = new ArrayList<Integer>(forms.length);
		for(int i = 0; i < forms.length; i++)
		{
			if(forms[i] != null)
				indicies.add(i);
			else
				break;
		}
		return indicies.stream().mapToInt(i -> Integer.valueOf(i)).toArray();		
	}
	public void addForm(String name, ArrayList<Type> types, boolean change)
	{
		int empty = -1;
		for(int i = 0; i < forms.length; i++)
		{
			if(forms[i] == null)
			{
				empty = i;
				break;
			}
		}
		if(empty != -1)
		{
			forms[empty] = new Form(name, this, empty);
			for(Type t : types)
			{
				forms[empty].type.add(t);
			}
			recalculate(forms[empty]);
			forms[empty].hp = forms[empty].maxHP;
			forms[empty].mp = forms[empty].maxMP;
			forms[empty].sp = forms[empty].maxSP;
			if(change)
			{
				changeForm(empty);
			}
		}
		else
		{
			//TODO: "Replace Form" dialogue 
		}
	}
	/**
	 * Changes the used form for this Character. If this Character is currently in an active {@link Combat}, also updates their available actions.
	 * 
	 * @param index the index of {@link forms} containing the desired {@link Form}
	 * @throws IllegalArgumentException if the index is negative
	 */
	public void changeForm(int index)
	{
		if(index < 0)
		{
			throw new IllegalArgumentException("The index must be non-negative.");
		}
		if(forms[index] == null)
		{
			TextManager.appendText(("Attempted to set " + name + "'s form to nothing."), TextStyle.ERROR);
			return;
		}
		currentForm = forms[index];
		TextManager.appendText((forms[index].name + " form set for " + name +  "."), TextStyle.DEBUG);
		if(fightingStats[0] == 1)
		{
			Master.setAttackSkills();
			Master.changeStatDisplay(this, "hp", currentForm.hp, (currentForm.hp/currentForm.maxHP) * 100);
			displayed.put("hp", (currentForm.hp/currentForm.maxHP) * 100);
			Master.changeStatDisplay(this, "mp", currentForm.mp, (currentForm.mp/currentForm.maxMP) * 100);
			displayed.put("mp", (currentForm.mp/currentForm.maxMP) * 100);
			Master.changeStatDisplay(this, "sp", currentForm.sp, (currentForm.sp/currentForm.maxSP) * 100);
			displayed.put("sp", (currentForm.sp/currentForm.maxSP) * 100);
		}
	}
	
	
 	public void react(Character attacker, Skill skill)
	{
 		if(skill.baseCost > 0)
 		{
 			//Might be skills that cost HP, or some combination of resources.
 			if(!skill.getFlags().contains(SkillFlags.SPELL))
 				attacker.damageStat("sp", skill.baseCost, true);
 			if(skill.getFlags().contains(SkillFlags.SPELL))
 				attacker.damageStat("mp", skill.baseCost, true);
 		}
 		int totalDamage = 0;
 		ArrayList<Character> targets = new ArrayList<Character>(List.of(this));
 		//TODO: All kinds of shit.
		//TODO: Add a way for skills to check all other actions used that turn to have access to more complex interactions.  
		if(skill.conflictsWith(queuedSkill))
		{
			//Either conflictsWith() resolves the conflict, or a more generic method which does so is called here.
			TextManager.appendText(("Skill conflict between " + skill.name + " and " + queuedSkill.name + "."), TextStyle.DEBUG);
			//TODO: Skill conflicts
			return;
		}
		if(skill.flags.contains(SkillFlags.BUFF))
		{
			//just accept positive effects like healing
			((Buff) skill).applyEffect(this);
			Master.addAction(Master.getCombatInstance(getCombatInstance()).getInstance(), new TurnData(attacker, targets, skill));
			return;
		}
		if(!skill.flags.contains(SkillFlags.UNREFLECTABLE))
		{
			//determine whether or not the attack is deflected or reflected
		}
		if(!skill.flags.contains(SkillFlags.UNABSORBABLE))
		{
			//determine whether or not the attack is negated or absorbed
		}
		if(!skill.flags.contains(SkillFlags.UNCOUNTERABLE))
		{
			//determine whether or not the attack is countered
		}
		//TODO: Needs updating for multihits.
		if(!skill.flags.contains(SkillFlags.UNAVOIDABLE))
		{
			//determine whether or not the attack is dodged
		}
		if(!skill.flags.contains(SkillFlags.UNBLOCKABLE))
		{
			//determine whether or not the attack damage is reduced (by a blocking action), and by how much
		}
		if(skill.baseArchetype == SkillArchetype.ATTACK)
		{
			TextManager.appendText(("\n" + name + " was hit by " + attacker.name + " with " + skill.pName + ".\n"), TextStyle.REGULAR);
			
			//modify damage from the attack using the user's stats
			TextManager.appendText(("Calculating damage."), TextStyle.DEBUG);
			//TODO: Text processing for damage.
			
			
			//modify damage based on type
			final DamageArray trueDamage = ((model.Skills.Attack) skill).baseDamage(this); 
			
			@SuppressWarnings("unused")
			boolean immune = false;
			
			for(int multi = 0; multi < skill.multiplier; multi++)
			{
				DamageArray damage = new DamageArray(trueDamage);
				
				for(Map.Entry<Damage, Double> i : damage.getDamage().entrySet())
				{
					if(i.getValue() > 0)
					{
						double mod = 1;
						for(int j = 0; j < currentForm.type.size(); j++)
						{
							if(mod > 0)
							{
								mod *= Type.determineEffectiveness(i.getKey().type, this.currentForm.type.get(j));
								if(mod <= 0)
								{
									immune = true;
								}
							}
							else
							{
								immune = true;
							}
						}
						TextManager.appendText("Damage value = " + (damage.addDamage(i.getKey(), i.getValue() * mod)), TextStyle.DEBUG);
					}
				}
				
				LinkedHashMap<Damage, Double> finalDamage = new LinkedHashMap<Damage, Double>();
				finalDamage.putAll(damage.getDamage()); 
				for(int i = 0; i < finalDamage.size(); i++)
				{
					double debug = currentForm.hp;
					double dmg =finalDamage.get(finalDamage.keySet().toArray()[i]);
					if(dmg > 0)
					{
						TextManager.appendText((" (" + dmg + ")"), TextStyle.DAMAGE);
						currentForm.hp -= dmg;
						totalDamage += dmg;
						TextManager.appendText((name + " HP reduced from " + debug + " to " + currentForm.hp + "."), TextStyle.DEBUG);
					}
					else
					{
						TextManager.appendText((" (IMMUNE)"), TextStyle.REGULAR);	
					}
					if(currentForm.hp <= 0)
					{
						break;
					}
				}
				if(currentForm.hp <= 0)
				{
					setFightingStatus(-1, 0, -1, -1, -1);
					TextManager.appendText(("\n" + name + " was defeated."), TextStyle.REGULAR);
					break;
				}
			}
			statValidator();
			Master.changeStatDisplay(this, "hp", currentForm.hp, (currentForm.hp/currentForm.maxHP) * 100);
		}
		//TODO: Create a way for skills to determine when and in what order they will execute their effects on an individual basis.
		skill.executeOnTarget(this);
		statValidator();
		Master.addAction(Master.getCombatInstance(getCombatInstance()).getInstance(), new TurnData(attacker, targets, skill, totalDamage));
	}
	
	
	public boolean is(StatusEffect statusEffect)
	{
		return currentForm.statusEffects.contains(statusEffect);
	}
	/**
	 * Returns an unmodifiable copy of the effects for this {@link Character}.
	 * 
	 * @return unmodifiable copy of the <code>effects</code> {@link ArrayList}<{@link Effect}> from the current {@link Form} used by this {@link Character}
	 * @see #getEffectsModifiable()
	 */
	public ArrayList<Effect> getEffects()
	{
		final ArrayList<Effect> copy = new ArrayList<Effect>(currentForm.effects); 
		return copy;
	}
	/**
	 * Returns <code>effects</code> for this {@link Character}.
	 * 
	 * @return <code>effects</code> {@link ArrayList}<{@link Effect}> from the current {@link Form} used by this {@link Character}
	 */
	public ArrayList<Effect> getEffectsModifiable()
	{
		return currentForm.effects;
	}
	public void addEffect(Effect effect)
	{
		if (!currentForm.effects.contains(effect))
		{
			currentForm.effects.add(effect);
			for (Map.Entry<String, Integer> i : effect.effects.entrySet())
			{
				modStat((i.getKey()), i.getValue());
			}
			effect.multipliers.entrySet().forEach(e ->
			{
				if(currentForm.multipliers.containsKey(e.getKey()))
				{
					currentForm.multipliers.put(e.getKey(), (((1 + currentForm.multipliers.get(e.getKey())) * (1 + e.getValue())) - 1));
				}
				else
				{
					currentForm.multipliers.put(e.getKey(), e.getValue());
				}
			});
			effect.statusEffects.forEach(e ->
			{				
				currentForm.statusEffects.add(e);
				TextManager.appendText((this.name + " was " + e.name + "."), TextStyle.DEBUG);
			});
		}
		else
		{
			//TODO: Increase effect magnitude or duration.
		}
		statValidator();
	}
	public void removeEffect(Effect effect)
	{
		effect.statusEffects.forEach(e -> currentForm.statusEffects.remove(e));
		effect.multipliers.entrySet().forEach(e ->
		{
			if(currentForm.multipliers.containsKey(e.getKey()))
			{
				currentForm.multipliers.put(e.getKey(), (((1 + currentForm.multipliers.get(e.getKey())) / (1 + e.getValue())) - 1));
			}
			else
			{
				//This should not happen.
				assert(false);
				currentForm.multipliers.put(e.getKey(), 0.0);
			}
		});
		for (Map.Entry<String, Integer> i : effect.effects.entrySet())
		{
			modStat(i.getKey(), (i.getValue() * -1));
		}
		TextManager.appendText((this.name + " is no longer " + effect.name + "."), TextStyle.DEBUG);
		currentForm.effects.remove(effect);
		statValidator();
	}
	public void decrementEffects()
	{
		currentForm.effects.forEach(e ->
		{
			e.remainingTime--;
		});
		
		//There are undoubtedly more reliable ways to do this.
		ArrayList<Effect> copy = new ArrayList<Effect>(currentForm.effects);
		for(Effect e : copy)
		{
			if(e.remainingTime <= 0)
			{
				removeEffect(e);
			}
		}
	}
	/**
	 * Returns an unmodifiable copy of the status effects for this {@link Character}.
	 * 
	 * @return unmodifiable copy of the <code>effects</code> {@link ArrayList}<{@link StatusEffect}> from the current {@link Form} used by this {@link Character}
	 * @see #geStatustEffectsModifiable()
	 */
	public ArrayList<StatusEffect> getStatusEffects()
	{
		return new ArrayList<StatusEffect>(currentForm.statusEffects);
	}
	/**
	 * Returns <code>statusEffects</code> for this {@link Character}.
	 * 
	 * @return <code>statusEffects</code> {@link ArrayList}<{@link StatusEffect}> from the current {@link Form} used by this {@link Character}
	 */
	public ArrayList<StatusEffect> getStatusEffectsModifiable()
	{
		return currentForm.statusEffects;
	}
	
	public void gainXP(int experience)
	{
		currentForm.xp += experience;
	}
	
	public Node getLocation()
	{
		return location;
	}
	public void setLocation(Node location)
	{
		this.location = location;
	}
	
	
	/**
	 * Sets the fighting state in {@link #fightingStats} with booleans. -1 can be passed as a value for any argument to avoid changing it
	 * 
	 * @param isFighting whether or not this {@link Character} is currently part of a {@link Combat} instance
	 * @param isActive whether or not this character has been K.O.'d yet
	 * @param characterID unique ID within the {@link Combat} instance
	 * @param combatInstance ID of the {@link Combat} instance
	 * @param side team identifier (value is shared with allies)
	 * @throws IllegalArgumentException if <code>isActive</code>, <code>characterID</code>, or <code>side</code> are negative numbers other than -1
	 * @see #fightingStats
	 */
	public void setFightingStatus(boolean isFighting, boolean isActive, int characterID, int combatInstance, int side)
	{
		if(characterID < -1)
		{
			throw new IllegalArgumentException("The \"characterID\" argument must be -1 at minimum.");
		}
		if(combatInstance < -1)
		{
			throw new IllegalArgumentException("The \"combatInstance\" argument must be -1 at minimum.");
		}
		if(side < -1)
		{
			throw new IllegalArgumentException("The \"side\" argument must be -1 at minimum.");
		}
		
		fightingStats[0] = isFighting ? 1 : 0;
		fightingStats[1] = isActive ? 1 : 0;
		if (characterID != -1) fightingStats[2] = characterID;
		if (combatInstance != -1) fightingStats[3] = combatInstance;
		if (side != -1) fightingStats[4] = side;
	}
	/**
	 * Sets the fighting state in {@link #fightingStats}. -1 can be passed as a value for any argument to avoid changing it
	 * 
	 * @param isFighting whether or not this {@link Character} is currently part of a {@link Combat} instance
	 * @param isActive whether or not this character has been K.O.'d yet
	 * @param characterID unique ID within the {@link Combat} instance
	 * @param combatInstance ID of the {@link Combat} instance
	 * @param side team identifier (value is shared with allies)
	 * @throws IllegalArgumentException if <code>isFighting</code> or <code>isActive</code> are not between -1 and 1, or if <code>isActive</code>, <code>characterID</code>, or <code>side</code> are negative numbers other than -1
	 * @see #fightingStats
	 */
	public void setFightingStatus(int isFighting, int isActive, int characterID, int combatInstance, int side)
	{
		if(isFighting < -1 || isFighting > 1)
		{
			throw new IllegalArgumentException("The \"isFighting\" argument must be between -1 and 1.");
		}
		if(isActive < -1 || isActive > 1)
		{
			throw new IllegalArgumentException("The \"isActive\" argument must be between -1 and 1.");
		}
		if(characterID < -1)
		{
			throw new IllegalArgumentException("The \"characterID\" argument must be -1 at minimum.");
		}
		if(combatInstance < -1)
		{
			throw new IllegalArgumentException("The \"combatInstance\" argument must be -1 at minimum.");
		}
		if(side < -1)
		{
			throw new IllegalArgumentException("The \"side\" argument must be -1 at minimum.");
		}
		
		if (isFighting != -1) fightingStats[0] = isFighting;
		if (isActive != -1) fightingStats[1] = isActive;
		if (characterID != -1) fightingStats[2] = characterID;
		if (combatInstance != -1) fightingStats[3] = combatInstance;
		if (side != -1) fightingStats[4] = side;
	}
	public int getCombatInstance()
	{
		return fightingStats[3];
	}
	public int getSide()
	{
		return fightingStats[4];
	}
	/**
	 * Returns an unmodifiable copy of the status effects for this {@link Character}.
	 * 
	 * @return unmodifiable copy of <code>fightingStats</code> from this {@link Character}
	 */
	public int[] getFightingStatus()
	{
		final int[] copy = {fightingStats[0], fightingStats[1], fightingStats[2], fightingStats[3], fightingStats[4]};
		return copy;
	}
	/**
	 * Returns whether or not the character is considered to be fighting. More specifically, it returns <code>true</code> when <code>{@link #fightingStats}[0] == 1</code>.
	 * 
	 * @return <code>true</code> if this character is currently in an active {@link Combat} instance.
	 */
	public boolean isFighting()
	{
		return (fightingStats[0] == 1) ? true : false;
	}
	public boolean isActive()
	{
		return fightingStats[1] == 1 ? true : false;
	}
	/**
	 * Returns <code>true</code> when this {@link Character} has either been defeated in the current {@link Combat}, or is currently affected with {@link Incapacitated}, a {@link StatusEffect}. 
	 * 
	 * @return true if this {@link Character} cannot choose an action
	 */
	public boolean isIncapacitated()
	{
		return (!isActive() || is(new model.StatusEffects.Incapacitated()));
	}
	//TODO: This is stupid and should go.
	public Optional<ArrayList<Character>> getEnemies()
	{
		if(isFighting())
		{
			ArrayList<Character> characters = (Master.getCombatInstance(fightingStats[3])).getCharacters();
			ArrayList<Character> enemies = new ArrayList<Character>();
			for(int i = 0; i < characters.size(); i++)
			{
				if (characters.get(i).fightingStats[4] != fightingStats[4])
				{
					enemies.add(characters.get(i));
				}
			}
			return Optional.of(enemies);
		}
		return Optional.empty();
	}
	
	public void resetNearby()
	{
		nearby.clear();
		nearby = location.getCharacters();
		nearby.remove(this);
	}
	public Optional<ArrayList<Character>> getNearby()
	{
		if(!isFighting())
		{
			ArrayList<Character> characters = location.getCharacters();
			characters.remove(this);
			if(characters.size() > 0)
			{
				return Optional.of(characters);
			}
			return Optional.empty();
		}
		ArrayList<Character> characters = Master.getCombatInstance(fightingStats[3]).getCharacters();
		characters.remove(this);
		return Optional.of(characters);
	}
	
	public void addSkill(Skill skill)
	{
		currentForm.skills.add(skill);
	}
	public ArrayList<Skill> getSkills()
	{
		return currentForm.skills;
	}
	public Skill getQueuedSkill()
	{
		return queuedSkill;
	}
	public void setQueuedSkill(Skill skill)
	{
		queuedSkill = skill;
	}
	
	public void setTarget(Character character)
	{
		target = Optional.of(character);
	}
	public Optional<Character> getTarget()
	{
		return target;
	}

	
	public VBox getStatDisplay()
	{
		return statDisplay;
	}
	public LinkedHashMap<String, Double> getDisplayed()
	{
		return displayed;
	}
	
	
	public void debugInfo()
	{
		//location info
		if(!location.equals(null))
		{
			TextManager.appendText(("Character: " + name + " with ID #" + id + " is currently located in " + location.getLocation()), TextStyle.DEBUG);
		}
		else
		{
			TextManager.appendText(("Character: " + name + " with ID #" + id + " is currently nowhere."), TextStyle.DEBUG);
		}
	}
	@Override
	public boolean equals(Object obj)
	{
		if(!obj.getClass().equals(this.getClass()))
			return false;
		if(name == ((model.Character) obj).name)
			return true;
		return false;
	}
	
	//Completely secure and cromulent hashing algorithm #497!
	//Let's hope that names never get too long.
	//Or that character IDs ever get too large.
	@Override
	public int hashCode()
	{
		int i = 0, j = 0;
		for(char c : name.toCharArray())
		{
			j += (int) Math.pow(java.lang.Character.getNumericValue(c) + i, 2);
			j += id;
			i++;
		}
		return j;
	}
	@Override
	public String toString()
	{
		return name;
	}
	
	public boolean isNPC()
	{
		return false;
	}
}
