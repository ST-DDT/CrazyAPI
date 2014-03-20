package de.st_ddt.crazyutil.databases;

import java.util.Collection;

import de.st_ddt.crazyutil.ConfigurationSaveable;

public interface Database<S extends DatabaseEntry> extends ConfigurationSaveable, Iterable<S>
{

	/**
	 * @return The type of the database.
	 */
	public String getDatabaseType();

	public Class<S> getEntryClazz();

	/**
	 * This methods connects to the database, checks the table and loads data if nessesary.
	 */
	public void initialize() throws Exception;

	public S createEntry(String entry);

	/**
	 * Checks whether data exists for key. If database isn't static, this method must load data if they exist.
	 * 
	 * @param key
	 *            The key used to search data.
	 * @return Return true if data exist.
	 */
	public boolean hasEntry(String key);

	/**
	 * Returns the data belonging to key, returns null if no data exists or aren't loaded.
	 * 
	 * @param key
	 *            The key used to get the data.
	 * @return The data belonging to key.
	 */
	public S getEntry(String key);

	/**
	 * @return The Lock making this database thread safe!
	 */
	public Object getDatabaseLock();

	/**
	 * Returns a collection containing all datas. (It contains only loaded datas), use synchronized(getDatabaseLock())!!!
	 * 
	 * @return The collection containing all loaded data.
	 */
	public Collection<? extends S> getAllEntries();

	/**
	 * Get the amount of stored entries. (It contains only loaded datas)
	 * 
	 * @return The amount of stored entries.
	 */
	public int size();

	/**
	 * Loads the data belonging to key from data source. This will overwrite existing data objects already stored in the cache. You have to update all references to data belonging to this key. (Otherwise you risk data inconsistency)
	 * 
	 * @param key
	 *            The key belonging to the data which should be loaded.
	 * @return The newly created/loaded data.
	 */
	public S loadEntry(String key);

	/**
	 * Loads all data from data source. This will overwrite existing data objects already stored in the cache. You have to update all references to data previously contained in this database, which has been updated. (Otherwise you risk data inconsistency)
	 */
	public void loadAllEntries();

	/**
	 * Unloads the data belonging to key from cache. Entry will be saved before been unloaded.
	 * 
	 * @param key
	 *            The key belonging to the data which should be unloaded.
	 * @return Returns true if the data was loaded.
	 */
	public boolean unloadEntry(String key);

	/**
	 * Unloads the data belonging to key from cache. Entry will be saved before been unloaded.<br>
	 * Do not modify entry after that.
	 * 
	 * @param entry
	 *            The entry which should be unloaded.
	 */
	public void unloadEntry(S entry);

	/**
	 * Unloads all data from cache. Entries will be saved before been unloaded.
	 */
	public void unloadAllEntries();

	/**
	 * Saves the selected entry to database. It will create an new entry if it does not exist.
	 * 
	 * @param entry
	 *            The entry which should be saved.
	 */
	public S insert(S entry);

	/**
	 * Save all elements of this collection to database. All entries will be created, if they do not exist.
	 * 
	 * @param entries
	 */
	public Collection<? extends S> insertAll(Collection<? extends S> entries);

	/**
	 * Deletes an entry from database and from cache. All references to the deleted data are still valid. This also deletes the data for never loaded entries. This method may save the database.
	 * 
	 * @param key
	 *            The key used to delete data.
	 * @return Returns true if datas has been attached to key.
	 */
	public boolean deleteEntry(String key);

	/**
	 * Deletes all entries from database. Loaded and unloaded ones.
	 */
	public void purgeDatabase();
	
	public void shutdown();
}
