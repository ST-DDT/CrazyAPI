package de.st_ddt.crazyplugin;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.OfflinePlayer;

import de.st_ddt.crazyutil.ChatHeaderProvider;
import de.st_ddt.crazyplugin.data.PlayerDataInterface;

public interface PlayerDataProvider extends ChatHeaderProvider
{

	public static final Set<PlayerDataProvider> PROVIDERS = new HashSet<>();

	public PlayerDataInterface getAvailablePlayerData(OfflinePlayer player);

	public PlayerDataInterface getAvailablePlayerData(String player);
}
