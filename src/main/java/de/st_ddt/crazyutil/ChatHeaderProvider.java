package de.st_ddt.crazyutil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * This object provides a chatheader.<br>
 * These can be used to add a prefix to messages that this object sends to chat.
 */
public interface ChatHeaderProvider
{

	/**
	 * Used to format Dates to a readable String <b>yyyyMMddHHmmss</b>
	 */
	public static DateFormat SHORTDATETIMEFORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 * Used to format Dates to a readable String <b>yyyy-MM-dd HH:mm:ss</b>
	 */
	public static DateFormat DATETIMEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * Used to format Dates to a readable String <b>yyyy-MM-dd</b>
	 */
	public static DateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * Used to format Dates to a readable String <b>HH:mm:ss</b>
	 */
	public static DateFormat TIMEFORMAT = new SimpleDateFormat("HH:mm:ss");

	/**
	 * @return The chatHeader used to send a message to chat.
	 */
	public String getChatHeader();
}
