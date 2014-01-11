package de.st_ddt.crazyutil.conditions;

import java.util.Collection;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;

public interface Condition
{

	public Condition secure(Map<Integer, ? extends Collection<Class<?>>> classes);

	public boolean check(Map<Integer, ? extends Object> parameters);

	/**
	 * Saves this object to config.
	 * 
	 * @param config
	 *            The config this object should be saved too.
	 * @param path
	 *            The path within this config where this object should be saved. (Should end with "." in most cases)
	 */
	public void save(ConfigurationSection config, String path, Map<Integer, String> parameterNames);
}
