package de.st_ddt.crazyplugin;

import java.util.Collection;
import java.util.Map;

import org.bukkit.OfflinePlayer;

import de.st_ddt.crazyplugin.commands.CrazyCommandTreeExecutorInterface;
import de.st_ddt.crazyplugin.comparator.PlayerDataComparator;
import de.st_ddt.crazyplugin.data.PlayerDataFilterInterface;
import de.st_ddt.crazyplugin.data.PlayerDataInterface;
import de.st_ddt.crazyutil.ListFormat;
import de.st_ddt.crazyutil.ListOptionsModder;
import de.st_ddt.crazyutil.databases.PlayerDataDatabase;

public interface CrazyPlayerDataPluginInterface<T extends PlayerDataInterface, S extends T> extends CrazyPluginInterface, PlayerDataProvider
{

	public void loadDatabase();

	public void saveDatabase();

	public PlayerDataDatabase<S> getCrazyDatabase();

	public CrazyCommandTreeExecutorInterface getPlayerCommand();

	@Override
	public T getAvailablePlayerData(final String name);

	@Override
	public T getAvailablePlayerData(final OfflinePlayer player);

	public Collection<? extends PlayerDataFilterInterface<T>> getPlayerDataFilters();

	public Map<String, PlayerDataComparator<T>> getPlayerDataComparators();

	public PlayerDataComparator<T> getPlayerDataDefaultComparator();

	public ListFormat getPlayerDataListFormat();

	public ListOptionsModder<T> getPlayerDataListModder();
}
