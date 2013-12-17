package de.st_ddt.crazyutil;

import java.util.Comparator;

/**
 * This object has a name.<br>
 * In most cases this name is unique for the given type.
 */
public interface Named
{

	public final static Comparator<? extends Named> COMPARATOR = new Comparator<Named>()
	{

		@Override
		public int compare(final Named o1, final Named o2)
		{
			final int res = o1.getName().compareTo(o2.getName());
			if (res == 0 && !o1.equals(o2))
				return Integer.valueOf(o1.hashCode()).compareTo(o2.hashCode());
			else
				return res;
		}
	};
	public final static Comparator<? extends Named> IGNORECASECOMPARATOR = new Comparator<Named>()
	{

		@Override
		public int compare(final Named o1, final Named o2)
		{
			final int res = o1.getName().compareToIgnoreCase(o2.getName());
			if (res == 0 && !o1.equals(o2))
				return Integer.valueOf(o1.hashCode()).compareTo(o2.hashCode());
			else
				return res;
		}
	};

	/**
	 * @return The Name of this object.
	 */
	public String getName();
}
