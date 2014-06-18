package de.st_ddt.crazyplugin.commands;

import java.util.Collection;
import java.util.SortedMap;

import org.bukkit.command.CommandSender;

import de.st_ddt.crazyplugin.exceptions.CrazyException;

public interface CrazyCommandTreeExecutorInterface extends CrazyCommandExecutorInterface
{

	public void addSubCommand(CrazyCommandExecutorInterface executor, String... subCommandLabels);

	public void addSubCommand(CrazyCommandExecutorInterface executor, Collection<String> subCommandLabels);

	public void removeSubCommand(String... subCommandLabels);

	public void removeSubCommand(Collection<String> subCommandLabels);

	public CrazyCommandExecutorInterface getDefaultExecutor();

	public void setDefaultExecutor(CrazyCommandExecutorInterface defaultExecutor);

	public SortedMap<String, CrazyCommandExecutorInterface> getSubExecutors();

	@Override
	public void command(CommandSender sender, String[] args) throws CrazyException;
}
