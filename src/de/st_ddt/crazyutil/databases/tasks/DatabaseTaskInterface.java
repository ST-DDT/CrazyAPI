package de.st_ddt.crazyutil.databases.tasks;

public interface DatabaseTaskInterface
{

	public boolean run();

	public String getPrimaryKey();

	public boolean isImportantTask();
}
