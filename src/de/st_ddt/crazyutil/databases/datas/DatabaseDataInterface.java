package de.st_ddt.crazyutil.databases.datas;

public interface DatabaseDataInterface
{

	public String getPrimaryKey();

	public Object getField(int index);

	public void setField(int index, Object value);
}
