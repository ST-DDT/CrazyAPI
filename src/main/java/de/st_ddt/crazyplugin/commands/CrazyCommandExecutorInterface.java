package de.st_ddt.crazyplugin.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import de.st_ddt.crazyplugin.exceptions.CrazyException;
import de.st_ddt.crazyutil.RestrictedAccess;

/**
 * Represent a command.
 */
public interface CrazyCommandExecutorInterface extends TabExecutor, RestrictedAccess
{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args);

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args);

	/**
	 * Tries to execute this command.
	 * 
	 * @param sender
	 *            The {@link CommandSender} of this command.
	 * @param args
	 *            The arguments used to execute the command.
	 * @throws CrazyException
	 *             if the command execution fails for some reason.
	 */
	public void command(CommandSender sender, String[] args) throws CrazyException;

	/**
	 * Triggers the tab help for this command.
	 * 
	 * @param sender
	 *            The {@link CommandSender} who triggered the tab help.
	 * @param args
	 *            The arguments passed to this command.<br>
	 *            Never Null, but may be empty.<br>
	 *            Should not be altered only copied.<br>
	 *            Subcommands will not receive the original arguments<br>
	 *            (The path arguments to the actual sub command is skipped).
	 * @return A {@link List} of possible sub commands and parameters, that can be set.
	 */
	public List<String> tab(CommandSender sender, String[] args);

	@Override
	public boolean hasAccessPermission(CommandSender sender);

	/**
	 * Checks whether this command can be properly executed by the given {@link CommandSender}. <br>
	 * This method does <b>NOT</b> check whether the given {@link CommandSender} has the permission to do so.<br>
	 * It only checks whether all pre requirements like a selection or being a player are fulfilled, if any.<br>
	 * If this method returns false, this command should not be visible in command trees tab completion.
	 * 
	 * @param sender
	 *            The {@link CommandSender} who tries to access the command.
	 * @return True, if the command can be executed properly under the current circumstances.
	 * @see #hasAccessPermission(CommandSender)
	 * @see {@link #handleNotExecutable(CommandSender)}
	 */
	public boolean isExecutable(CommandSender sender);

	/**
	 * Handles the event, if this command is not executable for the given {@link CommandSender}.<br>
	 * Any implementation of this method should send some kind of information to the {@link CommandSender}<br>
	 * why it cannot be executed yet.
	 * 
	 * @param sender
	 *            The {@link CommandSender} that tried but could not execute this command.
	 */
	public void handleNotExecutable(CommandSender sender);
}
