package de.st_ddt.crazyutil.databases;

import de.st_ddt.crazyutil.Named;

public interface DatabaseEntry extends Named
{

	public boolean reload();

	public void flush();

	public void delete();
}
