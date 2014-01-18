package de.st_ddt.crazyutil.comparators;

import java.util.Comparator;

import de.st_ddt.crazyutil.Named;

public class NamedComparator<N extends Named> implements Comparator<N>
{

	private final boolean ignoreCase;

	public NamedComparator(final boolean ignoreCase)
	{
		super();
		this.ignoreCase = ignoreCase;
	}

	@Override
	public int compare(final N o1, final N o2)
	{
		if (ignoreCase)
			return o1.getName().compareToIgnoreCase(o2.getName());
		else
			return o1.getName().compareTo(o2.getName());
	}
}
