package model;

public enum SkillFlags
{
	/*
	 * Skill flags can apply to any non-item-based actions, not just attacks.
	 * They describe some of the features of a skill to the system so that it may intelligently handle interactions between various factors.
	 * This allows for skills that, for instance, freeze anyone using an electricity-based attack, but only if its also a spell.
	 * Some/all of these could use better names.
	 */
	
	
	//Delivery Methods
	MELEE,					//Applies to attacks that are launched from a short range, including some projectile weapons and magic.
	DISJOINTED,				//Applies to attacks where the user does not make physical contact with the target.
	PROJECTILE,				//Applies to any move that is or creates a projectile.
	MISSILE, 				//Applies to projectiles from traditional ranged weapons (e.g. rocks, bullets, arrows).
	HITSCAN,				//Applies to moves that affect their target(s) without regard to space or positioning.
	BEAM,					//Applies to any move that takes the form of a jet or beam.
	RAIN,					//Applies to moves that are delivered via a large number of (usually small) projectiles from overhead.
	SPELL,					//Applies to traditional spells.
	
	
	/*
	 * Countering, Deflection and Reflection, Blocking, Dodging, Negation and Absorption
	 * Deflection and Reflection are the same effect, with the only difference being effectiveness (the same goes for Negation and Absorption). 
	 * Melee attacks are implicitly counterable, blockable, and dodgeable unless otherwise specified.
	 * Magic is usually subject to most of these defensive measures besides countering, but non-projectile magic is often unreflectable.
	 * Projectiles are implicitly deflectable, blockable, and dodgeable. Rarely, some skills can negate them too.
	 * Chi is usually reflectable and dodgeable.
	 */	
	UNCOUNTERABLE,			//Applies to attacks which cannot be countered by any means.
	UNREFLECTABLE,			//Applies to attacks which cannot be deflected or reflected by any means.
	UNBLOCKABLE,			//Applies to attacks which cannot be guarded against by traditional means.
	UNAVOIDABLE,			//Applies to attacks which cannot be dodged by any means.
	UNABSORBABLE,			//Applies to moves which cannot be negated or absorbed by any means. Definitely a word.
	PENETRATING,			//Applies to attacks which are able to bypass barrier abilities.
	MULTIHIT,				//Applies to attacks which innately hit more than once.
	
	
	/*
	 * Move Mechanics and Methods
	 * Listed here are the means utilized by moves to achieve their effects, such as dealing damage.
	 * These are not directly related to Types.
	 */
	BLADE,					//Applies to moves that achieve their purposes via slicing or stabbing. These are generally attacks.
	BLUNT,					//Applies to moves that achieve their purposes via percussive force. These are generally attacks.
	CHI,					//Applies to moves that achieve their purposes via the user's fighting spirit energy. Yes, I know.
	MAGICAL,				//Applies to any move that uses magic in any form.
	PSYCHIC,				//Applies to moves that utilize psychic power.  
	ARCANE,					//Applies to non-elemental magic.
	FLAMING,				//Applies to fire-based moves. "Burning" and "Infernal" seemed misleading.
	FREEZING,				//Applies to ice-based moves.
	ELECTRICAL,				//Applies to electricity-based moves.
	TERRA,					//Applies to ground-based moves.
	SAND,					//Applies to sand-based moves.
	HYDRIC,					//Applies to water-based moves. Definitely needs another name.
	CORROSIVE,				//Applies to moves that achieve their purposes via corrosive substances. These are generally attacks.
	POISONOUS,				//It is difficult to determine exactly what sorts of moves would be called "Poisonous", but they use poison.
	HYDRAULIC,				//Applies to moves leveraging liquid under pressure.
	PNEUMATIC,				//Applies to moves leveraging gas under pressure.
	DARK,					//Applies to moves that utilize dark magic.
	LIGHT,					//Applies to moves that utilize light.
	
	
	//Position Modification and Interaction
	EVADING,				//Applies to moves which involve the user actively dodging.
	RETREATING,				//Applies to moves which involve the user traveling away from the target while being performed, increasing the distance.
	ADVANCING,				//Applies to moves which involve the user traveling towards the target while being performed, closing the distance.
	KNOCKBACK,				//Applies to moves which involve the target of the move being blown away from the user.
	ACCURATE,				//Applies to attacks that mostly ignore the accuracy of the user.
	UNREACTABLE,			//Applies to attacks that mostly ignore the evasion of the target(s).
	
	
	//Skill Archetypes
	ATTACK,					//Applies to moves which involve the user harming their target(s).
	GUARD,					//Applies to moves which involve the user defending themselves or their target(s).
	BARRIER,				//Applies to moves which involve the construction of a barrier.
	STRUCTURE,				//Applies to moves which involve the construction of a persistent structure.
	BUFF,					//Applies to moves that apply a beneficial effect of some kind to the user and/or the target.
	TRANSFORM,				//Applies to moves which involve the user transforming themselves.
	WAIT;					//Applies to actions which are characterized by their lack of action.
}
