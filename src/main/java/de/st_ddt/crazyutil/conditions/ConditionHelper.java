package de.st_ddt.crazyutil.conditions;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;

public class ConditionHelper
{

	public static Condition load(final ConfigurationSection config, final Map<String, Integer> parameterIndexes) throws Exception
	{
		if (config == null)
			throw new IllegalArgumentException("ConfigurationSection cannot be NULL!");
		final String type = config.getString("type");
		if (type == null)
			throw new IllegalArgumentException("ConditionType cannot be NULL!");
		Class<?> clazz;
		try
		{
			clazz = Class.forName(type);
		}
		catch (final ClassNotFoundException e1)
		{
			throw new IllegalArgumentException("The Condition's Class " + type + " was not found! Please fix your configuration!");
		}
		if (!Condition.class.isAssignableFrom(clazz))
			throw new IllegalArgumentException("The Condition's Class " + clazz.getSimpleName() + " is no supported Condition! Please fix your configuration!");
		try
		{
			final Constructor<? extends Condition> constructor = clazz.asSubclass(Condition.class).getConstructor(ConfigurationSection.class, Map.class);
			return constructor.newInstance(config, parameterIndexes);
		}
		catch (final Exception e)
		{
			System.err.println("WARNING: Serious bug detected, please report this issue!");
			throw e;
		}
	}

	public static Condition simpleLoad(final ConfigurationSection config, final String... parameterNames) throws Exception
	{
		return load(config, simpleParameterIndexes(parameterNames));
	}

	public static Map<String, Integer> simpleParameterIndexes(final String... parameterNames)
	{
		final Map<String, Integer> res = new HashMap<>(parameterNames.length);
		for (int i = 0; i < parameterNames.length; i++)
			res.put(parameterNames[i], i);
		return res;
	}

	public static void simpleSave(final Condition condition, final ConfigurationSection config, final String path, final String... parameterNames)
	{
		if (condition != null)
			condition.save(config, path, simpleParameterNames(parameterNames));
	}

	public static Map<Integer, String> simpleParameterNames(final String... parameterNames)
	{
		final Map<Integer, String> res = new HashMap<>(parameterNames.length);
		for (int i = 0; i < parameterNames.length; i++)
			res.put(i, parameterNames[i]);
		return res;
	}

	public static Map<Integer, Set<Class<?>>> simpleParameterClasses(final Class<?>... classes)
	{
		final Map<Integer, Set<Class<?>>> res = new HashMap<>(classes.length);
		for (int i = 0; i < classes.length; i++)
		{
			final Set<Class<?>> set = new HashSet<>(1);
			set.add(classes[i]);
			res.put(i, set);
		}
		return res;
	}

	public static boolean simpleCheck(final Condition condition, final Object... objects)
	{
		return condition.check(simpleParameters(objects));
	}

	public static Map<Integer, Object> simpleParameters(final Object... objects)
	{
		final Map<Integer, Object> res = new HashMap<>(objects.length);
		for (int i = 0; i < objects.length; i++)
			res.put(i, objects[i]);
		return res;
	}

	protected ConditionHelper()
	{
	}
}
