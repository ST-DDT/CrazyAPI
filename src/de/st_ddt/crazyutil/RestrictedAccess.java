package de.st_ddt.crazyutil;

import org.bukkit.command.CommandSender;

public interface RestrictedAccess
{

	/**
	 * Checks whether the given {@link CommandSender} has access to this object.
	 * 
	 * @param sender
	 *            The {@link CommandSender} that should be checked.
	 * @return True, if the {@link CommandSender} has access to this object. False otherwise.
	 */
	public boolean hasAccessPermission(CommandSender sender);
}
