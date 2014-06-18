package de.st_ddt.crazyplugin.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.st_ddt.crazyplugin.exceptions.CrazyException;

/**
 * Represent a command only accessible by {@link Player}s.
 */
public interface CrazyPlayerCommandExecutorInterface extends CrazyCommandExecutorInterface
{

	/**
	 * Tries to execute this command.
	 * 
	 * @param sender
	 *            The {@link Player} of this command.
	 * @param args
	 *            The arguments used to execute the command.
	 * @throws CrazyException
	 *             if the command execution fails for some reason.
	 * @see #command(CommandSender, String[])
	 */
	public void command(Player player, String[] args) throws CrazyException;

	/**
	 * Triggers the tab help for this command.
	 * 
	 * @param sender
	 *            The {@link Player} who triggered the tab help.
	 * @param args
	 *            The arguments passed to this command.<br>
	 *            Never Null, but may be empty.<br>
	 *            Should not be altered only copied.<br>
	 *            Subcommands will not receive the original arguments<br>
	 *            (The path arguments to the actual sub command is skipped).
	 * @return A {@link List} of possible sub commands and parameters, that can be set.
	 * @see #tab(CommandSender, String[])
	 */
	public List<String> tab(Player player, String[] args);

	/**
	 * Checks whether the given {@link Player} has access to this object.
	 * 
	 * @param sender
	 *            The {@link Player} that should be checked.
	 * @return True, if the {@link Player} has access to this object. False otherwise.
	 * @see #hasAccessPermission(CommandSender)
	 */
	public boolean hasAccessPermission(Player player);

	/**
	 * Checks whether this command can be properly executed by the given {@link Player}. <br>
	 * This method does <b>NOT</b> check whether the given {@link Player} has the permission to do so.<br>
	 * It only checks whether all pre requirements like a selection are fulfilled, if any.<br>
	 * If this method returns false, this command should not be visible in command trees tab completion.
	 * 
	 * @param sender
	 *            The {@link Player} who tries to access the command.
	 * @return True, if the command can be executed properly under the current circumstances.
	 * @see #hasAccessPermission(Player)
	 * @see #isExecutable(CommandSender)
	 * @see #handleNotExecutable(Player)
	 */
	public boolean isExecutable(Player sender);

	/**
	 * Handles the event, if this command is not executable for the given {@link Player}.<br>
	 * Any implementation of this method should send some kind of information to the {@link Player}<br>
	 * why it cannot be executed yet.
	 * 
	 * @param sender
	 *            The {@link Player} that tried but could not execute this command.
	 * @see #isExecutable(Player)
	 * @see #handleNotExecutable(CommandSender)
	 */
	public void handleNotExecutable(Player sender);
}
