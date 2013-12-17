package de.st_ddt.crazyutil.modules.economy;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import de.st_ddt.crazyutil.Named;

public interface EconomySystem extends Named
{

	public final static List<Class<? extends EconomySystem>> ECONOMYSYSTEMCLASSES = new ArrayList<>();

	public double getMoney(Player player);

	public void changeMoney(Player player, double money);
}
