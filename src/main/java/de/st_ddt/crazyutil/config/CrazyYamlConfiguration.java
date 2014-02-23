package de.st_ddt.crazyutil.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class CrazyYamlConfiguration extends YamlConfiguration
{

	@Override
	public void load(final File file) throws FileNotFoundException, IOException, InvalidConfigurationException
	{
		try
		{
			super.load(file);
		}
		catch (final IllegalArgumentException e)
		{
			throw e;
		}
		catch (final FileNotFoundException e)
		{
			throw e;
		}
		catch (final IOException e)
		{
			throw e;
		}
		catch (final InvalidConfigurationException e)
		{
			createBackup(file, e);
			throw e;
		}
	}

	private void createBackup(final File file, final Throwable e)
	{
		final String orgName = file.getName();
		final File parentFile = file.getParentFile();
		final long time = System.currentTimeMillis();
		// Backup File
		final String bakName = orgName.replace(".yml", "_" + time + ".yml.bak");
		final File bakFile;
		if (bakName.equals(orgName))
			bakFile = new File(parentFile, orgName + "_" + time + ".yml.bak");
		else
			bakFile = new File(parentFile, bakName);
		try
		{
			file.renameTo(bakFile);
		}
		catch (final Throwable t)
		{
			System.err.println("Could not create backup-file " + bakFile.getName() + " for " + file.getPath());
			System.err.println("Caused by " + t.getClass().getName() + ": " + t.getMessage());
		}
		// Error File
		final String errName = orgName.replace(".yml", "_" + time + ".yml.err");
		final File errFile;
		if (errName.equals(orgName))
			errFile = new File(parentFile, orgName + "_" + time + ".yml.err");
		else
			errFile = new File(parentFile, errName);
		try (PrintWriter writer = new PrintWriter(errFile))
		{
			e.printStackTrace(writer);
		}
		catch (final Throwable t)
		{
			System.err.println("Could not create error-file " + errFile.getName() + " for " + file.getPath());
			System.err.println("Caused by " + t.getClass().getName() + ": " + t.getMessage());
		}
	}
}
