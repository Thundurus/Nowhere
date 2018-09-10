package model;

public enum Type
{
	//Blatant robbery of they-who-shall-not-be-named's types, with improvements.
	ACID,		//
	AIR,		//
	AURA,		//
	DARK,		//
	ELECTRIC,	//
	FIRE,		//
	GHOST,		//
	GROUND,		//
	ICE,		//
	LEGEND,		//
	LIGHT,		//
	NATURE,		//
	NORMAL,		//
	NULL,		//Not actually a type.
	POISON,		//
	PSYCHIC,	//
	STEEL,		//
	UNDEAD,		//
	WATER;		//
	
	private static double immune = 0, ineffective = 0.5, normal = 1.0, supereffective = 2.0;
			
	public static double determineEffectiveness(Type type1, Type type2)
	{
		switch(type1)
		{
			case ACID:		if(type2 == POISON || type2 == STEEL || type2 == UNDEAD)
							{
								return supereffective;
							}
							if(type2 == ACID || type2 == WATER)
							{
								return ineffective;
							}
							return normal;
			case AIR:		if(type2 == FIRE || type2 == NATURE)
							{
								return supereffective;
							}
							if(type2 == AIR || type2 == STEEL)
							{
								return ineffective;
							}
							return normal;
			case AURA:		if(type2 == DARK || type2 == ICE || type2 == NORMAL || type2 == STEEL || type2 == UNDEAD)
							{
								return supereffective;
							}
							if(type2 == AIR || type2 == NATURE || type2 == PSYCHIC)
							{
								return ineffective;
							}
							if(type2 == GHOST)
							{
								return immune;
							}
							return normal;							
			case DARK:		if(type2 == LEGEND || type2 == LIGHT || type2 == PSYCHIC)
							{
								return supereffective;
							}
							if(type2 == AURA || type2 == DARK ||type2 == GHOST || type2 == UNDEAD)
							{
								return ineffective;
							}
							return normal;
			case ELECTRIC:	if(type2 == AIR || type2 == WATER)
							{
								return supereffective;
							}
							if(type2 == ELECTRIC || type2 == LEGEND || type2 == NATURE) //Maybe
							{
								return ineffective;
							}
							if(type2 == GROUND)
							{
								return immune;
							}
							return normal;
			case FIRE:		if(type2 == ICE || type2 == NATURE || type2 == UNDEAD)
							{
								return supereffective;
							}
							if(type2 == FIRE || type2 == GROUND || type2 == LEGEND || type2 == WATER)
							{
								return ineffective;
							}
							return normal;
			case GHOST:		if(type2 == PSYCHIC)
							{
								return supereffective;
							}
							if(type2 == LIGHT || type2 == UNDEAD)
							{
								return ineffective;
							}
							if(type2 == NORMAL)
							{
								return immune;
							}
							return normal;
			case GROUND:	if(type2 == ELECTRIC || type2 == FIRE || type2 == STEEL)
							{
								return supereffective;
							}
							if(type2 == NATURE)
							{
								return ineffective;
							}
							if(type2 == AIR)
							{
								return immune;
							}
							return normal;
			case ICE:		if(type2 == GROUND || type2 == NATURE || type2 == WATER)
							{
								return supereffective;
							}
							if(type2 == FIRE || type2 == ICE || type2 == LEGEND || type2 == STEEL)
							{
								return ineffective;
							}
							return normal;
			case LEGEND:	if(type2 == STEEL)
							{
								return ineffective;
							}
							return normal;
			case LIGHT:		if(type2 == DARK || type2 == GHOST || type2 == UNDEAD)
							{
								return supereffective;
							}
							if(type2 == FIRE || type2 == LIGHT || type2 == NATURE || type2 == POISON || type2 == STEEL)
							{
								return ineffective;
							}
							return normal;
			case NATURE:	if(type2 == GROUND || type2 == LEGEND || type2 == WATER)
							{
								return supereffective;
							}
							if(type2 == AIR || type2 == FIRE || type2 == NATURE || type2 == STEEL || type2 == UNDEAD)
							{
								return ineffective;
							}
							return normal;
			case NORMAL:	if(type2 == GROUND || type2 == STEEL || type2 == UNDEAD)
							{
								return ineffective;
							}
							if(type2 == GHOST)
							{
								return immune;
							}
							return normal;
			case POISON:	if(type2 == AURA || type2 == LIGHT || type2 == NATURE)
							{
								return supereffective;
							}
							if(type2 == ACID || type2 == POISON || type2 == PSYCHIC)
							{
								return ineffective;
							}
							if(type2 == STEEL)
							{
								return immune;
							}
							return normal;
			case PSYCHIC:	if(type2 == AURA || type2 == GHOST)
							{
								return supereffective;
							}
							if(type2 == DARK || type2 == NATURE || type2 == PSYCHIC || type2 == UNDEAD)
							{
								return ineffective;
							}
							return normal;
			case STEEL:		if(type2 == STEEL || type2 == WATER)
							{
								return ineffective;
							}
							return normal;
			case UNDEAD:	if(type2 == LIGHT || type2 == UNDEAD)
							{
								return ineffective;
							}
							return normal;
			case WATER:		if(type2 == ACID || type2 == FIRE || type2 == GROUND || type2 == POISON)
							{
								return supereffective;
							}
							if(type2 == ICE || type2 == LEGEND || type2 == NATURE || type2 == WATER)
							{
								return ineffective;
							}
							return normal;
			default: return normal;
		}
	}
	
	public static double calculateEffectiveness(Skill skill, Character target)
	{
		double effectiveness = 1.0;
		for(Type t : target.getType())
		{
			if(effectiveness != 0)
			{
				effectiveness *= determineEffectiveness(skill.getType(), t);
			}
			else
			{
				break;
			}
		}
		return effectiveness;
	}
}
