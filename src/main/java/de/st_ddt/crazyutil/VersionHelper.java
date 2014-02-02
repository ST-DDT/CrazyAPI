package de.st_ddt.crazyutil;

import org.bukkit.Bukkit;

import de.st_ddt.crazyutil.comparators.VersionComparator;

public class VersionHelper
{

	private static String minecraftVersion = Bukkit.getBukkitVersion().split("-", 2)[0];
	private static String bukkitVersion = Bukkit.getBukkitVersion().replace("-R", ".").split("-", 2)[0];

	public static String getMinecraftVersion()
	{
		return minecraftVersion;
	}

	public static String getBukkitVersion()
	{
		return bukkitVersion;
	}

	public static boolean hasRequiredVersion(final String version)
	{
		return VersionComparator.compareVersions(bukkitVersion, version) >= 0;
	}
}
