package de.st_ddt.crazyutil.reloadable;

import org.bukkit.command.CommandSender;

public interface Reloadable
{

	public void reload(CommandSender sender);

	public boolean hasReloadPermission(CommandSender sender);

	public void save(CommandSender sender);

	public boolean hasSavePermission(CommandSender sender);
}
