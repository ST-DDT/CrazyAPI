package de.st_ddt.crazyutil;

import org.bukkit.configuration.ConfigurationSection;

public interface ConfigurationSaveable
{

	// public ConfigurationSaveable(ConfigurationSection config);
	/**
	 * Saves this object to config.
	 * 
	 * @param config
	 *            The config this object should be saved to.
	 * @param path
	 *            The path within this config where this object should be saved. (Should end with "." in most cases)
	 */
	public void save(ConfigurationSection config, String path);
}
