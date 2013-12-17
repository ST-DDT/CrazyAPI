package de.st_ddt.crazyutil.modules.permissiongroups;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;

import de.st_ddt.crazyutil.Named;

/**
 * The permission helper instance provides the fuctionality,<br>
 * which is required for {@link PermissionHelper}.<br>
 * All implementing classes should have an default constructor.<br>
 * It has to throw an exception to cancel the instanciation,<br>
 * if it is not supported by current system/plugin setup.<br>
 * All available {@link PermissionSystem}s should be added to<br>
 * {@link #PERMISSIONSYSTEMCLASSES} in order to be checked on startup.
 */
public interface PermissionSystem extends Named
{

	/**
	 * An ordered list of Classes that implements this interface<br>
	 * and that can be initiated at runtime using the default constructor.
	 */
	public final static List<Class<? extends PermissionSystem>> PERMISSIONSYSTEMCLASSES = new ArrayList<Class<? extends PermissionSystem>>();

	/**
	 * Checks whether the player has this group or not.
	 * 
	 * @param player
	 *            The player whose group should be checked.<br>
	 *            Must not be NULL.
	 * @param group
	 *            The group the player should have.<br>
	 *            Must not be NULL.
	 * @return True, if the player is in the specified group, False otherwise.
	 */
	public boolean hasGroup(Player player, String group);

	/**
	 * Returns all groups a player has.<br>
	 * Returns NULL, if this feature is not supported by this PermissionHelperInstance.
	 * 
	 * @param player
	 *            The player whose primary group should ve returned.<br>
	 *            Must not be NULL.
	 * @return The primary group this player belongs too.
	 */
	public String getGroup(Player player);

	/**
	 * Returns all groups a player has.<br>
	 * Returns NULL, if this feature is not supported by this PermissionHelperInstance.
	 * 
	 * @param player
	 *            The players whose groups should be returned.<br>
	 *            Must not be NULL.
	 * @return A set of all groups this player has.
	 */
	public Set<String> getGroups(Player player);

	/**
	 * Returns the group prefix this player has.
	 * 
	 * @param player
	 *            The player whose group prefix should be returned.<br>
	 *            Must not be NULL.
	 * @return The group prefix this player has.
	 */
	public String getGroupPrefix(final Player player);

	/**
	 * Returns the group suffix this player has.
	 * 
	 * @param player
	 *            The player whose group suffix should be returned.<br>
	 *            Must not be NULL.
	 * @return The group suffix this player has.
	 */
	public String getGroupSuffix(final Player player);
}
