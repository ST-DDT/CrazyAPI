package de.st_ddt.crazyutil.databases;

import org.bukkit.OfflinePlayer;

import de.st_ddt.crazyplugin.data.PlayerDataInterface;

public interface PlayerDataDatabase<S extends PlayerDataInterface> extends Database<S>
{

	public boolean hasEntry(OfflinePlayer player);

	public S getEntry(OfflinePlayer player);

	public boolean deleteEntry(OfflinePlayer player);

	/**
	 * Tries to loads the data belonging to key from data source.
	 * 
	 * @param player
	 *            The player belonging to the data which should be loaded.
	 * @return The current data, either loaded or just the current one.
	 */
	public S loadEntry(OfflinePlayer player);
	
	public void unloadEntry(OfflinePlayer player);
}
