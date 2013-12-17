package de.st_ddt.crazyutil.reloadable;

import java.util.Map;

public interface ReloadableProvider
{

	public Map<String, ? extends Reloadable> getReloadables();
}
