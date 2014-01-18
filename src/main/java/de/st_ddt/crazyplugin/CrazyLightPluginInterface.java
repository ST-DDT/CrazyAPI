package de.st_ddt.crazyplugin;

import org.bukkit.plugin.Plugin;

import de.st_ddt.crazyplugin.data.ParameterData;
import de.st_ddt.crazyutil.ChatHeaderProvider;
import de.st_ddt.crazyutil.Named;

/**
 * This object represents a lightweighted CrazyPlugin.
 */
public interface CrazyLightPluginInterface extends Named, ChatHeaderProvider, ParameterData, Plugin, Comparable<CrazyLightPluginInterface>
{

	/**
	 * @return The current version number of this plugin.
	 */
	public String getVersion();
}
