package model.Skills;

import java.util.function.Function;

public interface Counter
{
	//By default, counters may not target multiple opponents. Should this change, the logic may require amending.
	public void counter(model.Character counter, model.Character target, model.Skill skill);
	//Everything about the preparation text seems off. This will likely be heavily modified in the future.
	public String preparation(model.Character character);
	public Function<model.Skill, Boolean> counterable();
}
