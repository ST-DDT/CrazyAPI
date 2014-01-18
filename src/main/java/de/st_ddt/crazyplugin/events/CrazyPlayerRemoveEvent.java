package de.st_ddt.crazyplugin.events;

import java.util.Set;
import java.util.TreeSet;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

import de.st_ddt.crazyutil.Named;

public class CrazyPlayerRemoveEvent extends CrazyEvent
{

	private static final HandlerList handlers = new HandlerList();
	private final Set<String> deletions = new TreeSet<String>();
	protected final String player;

	public CrazyPlayerRemoveEvent(final OfflinePlayer player)
	{
		this(player.getName());
	}

	public CrazyPlayerRemoveEvent(final String player)
	{
		super();
		if (player == null)
			throw new IllegalArgumentException("Playername cannot be null!");
		if (player.length() == 0)
			throw new IllegalArgumentException("Playername cannot be empty!");
		this.player = player;
	}

	public String getPlayer()
	{
		return player;
	}

	public void markDeletion(final Plugin dataKeeper)
	{
		markDeletion(dataKeeper.getName());
	}

	public void markDeletion(final Named dataKeeper)
	{
		markDeletion(dataKeeper.getName());
	}

	public void markDeletion(final String dataKeeper)
	{
		deletions.add(dataKeeper);
	}

	public Set<String> getDeletions()
	{
		return deletions;
	}

	public int getDeletionsCount()
	{
		return deletions.size();
	}

	@Override
	public HandlerList getHandlers()
	{
		return handlers;
	}

	public static HandlerList getHandlerList()
	{
		return handlers;
	}

	public void checkAndCallEvent()
	{
		final CrazyPlayerPreRemoveEvent event = new CrazyPlayerPreRemoveEvent(player);
		event.callEvent();
		if (event.isCancelled())
			return;
		callEvent();
	}
}
