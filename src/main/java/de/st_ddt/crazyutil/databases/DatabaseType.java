package de.st_ddt.crazyutil.databases;

public enum DatabaseType
{
	CONFIG(true),
	FLAT(true),
	MYSQL(false),
	SQLITE(false);

	private final boolean staticDatabase;

	private DatabaseType(final boolean staticDatabase)
	{
		this.staticDatabase = staticDatabase;
	}

	public boolean isStaticDatabase()
	{
		return staticDatabase;
	}
}
