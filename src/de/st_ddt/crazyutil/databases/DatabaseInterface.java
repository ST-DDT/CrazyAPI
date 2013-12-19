package de.st_ddt.crazyutil.databases;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import de.st_ddt.crazyutil.databases.datas.DatabaseDataInterface;
import de.st_ddt.crazyutil.databases.tasks.DatabaseTaskInterface;

public interface DatabaseInterface<D extends DatabaseDataInterface>
{

	public void addData(D data);

	public D createData(String key);

	public boolean hasRawKey(String key);

	public Collection<String> getAllRawKeys();

	public void loadData(String key);

	public void loadAllData();

	public D getData(String key);

	public D getOrLoadData(String key);

	public D getOrCreateData(String key);

	public D getOrLoadOrCreateData(String key);

	public Map<String, D> getAllData();

	public Set<String> getAllKeys();

	public int size();

	public void unloadData(String key);

	public void unloadAllData();

	public D removeData(String key);

	public void removeAllData(String key);

	public void flush();

	public boolean isActive();

	public boolean isShutingDown();

	public void shutdown();

	public void queueTask(DatabaseTaskInterface task);

	public void processAllTasks();

	public DatabaseTaskInterface loadTask(D data);

	public DatabaseTaskInterface loadTask(D data, int index);

	public DatabaseTaskInterface saveTask(D data);

	public DatabaseTaskInterface saveTask(D data, int index);
}
