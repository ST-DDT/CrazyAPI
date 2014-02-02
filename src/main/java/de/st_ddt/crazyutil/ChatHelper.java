package de.st_ddt.crazyutil;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.st_ddt.crazyplugin.data.ParameterData;
import de.st_ddt.crazyutil.locales.PersonalizedMessage;

public class ChatHelper
{

	private static boolean showChatHeaders;

	protected ChatHelper()
	{
	}

	public static boolean isShowingChatHeadersEnabled()
	{
		return showChatHeaders;
	}

	public static void setShowChatHeaders(final boolean showChatHeaders)
	{
		ChatHelper.showChatHeaders = showChatHeaders;
	}

	/**
	 * Replace Colorcodes texts with the real colors
	 * 
	 * @param string
	 *            The string the colors should apply to
	 * @return The colorated String
	 */
	public static String colorise(final String string)
	{
		if (string == null)
			return null;
		else
			return ChatColor.translateAlternateColorCodes('&', string);
	}

	/**
	 * Replace real colors texts with the Colorcodes
	 * 
	 * @param string
	 *            The string the colors should apply to
	 * @return The decolorated String
	 */
	public static String decolorise(final String string)
	{
		if (string == null)
			return null;
		final char[] b = string.toCharArray();
		for (int i = 0; i < b.length - 1; i++)
			if (b[i] == ChatColor.COLOR_CHAR && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1)
			{
				b[i] = '&';
				b[i + 1] = Character.toUpperCase(b[i + 1]);
			}
		return new String(b);
	}

	public static void sendMessage(final CommandSender target, final Object message, final Object... args)
	{
		sendFinalMessage(target, putArgsExtended(target, message, args));
	}

	public static void sendMessage(final CommandSender target, final String chatHeader, final Object message, final Object... args)
	{
		sendFinalMessage(target, chatHeader, putArgsExtended(target, message, args));
	}

	public static void sendMessage(final CommandSender[] targets, final Object message, final Object... args)
	{
		for (final CommandSender target : targets)
			sendMessage(target, message, args);
	}

	public static void sendMessage(final CommandSender[] targets, final String chatHeader, final Object message, final Object... args)
	{
		for (final CommandSender target : targets)
			sendMessage(target, chatHeader, message, args);
	}

	public static void sendMessage(final Collection<? extends CommandSender> targets, final Object message, final Object... args)
	{
		for (final CommandSender target : targets)
			sendMessage(target, message, args);
	}

	public static void sendMessage(final Collection<? extends CommandSender> targets, final String chatHeader, final Object message, final Object... args)
	{
		for (final CommandSender target : targets)
			sendMessage(target, chatHeader, message, args);
	}

	public static void sendFinalMessage(final CommandSender target, final String message)
	{
		if (message.equals(""))
			return;
		for (final String part : StringUtils.splitByWholeSeparator(message, "\\n"))
			target.sendMessage(part);
	}

	public static void sendFinalMessage(final CommandSender target, final String chatHeader, final String message)
	{
		if (message.equals(""))
			return;
		if (!showChatHeaders && target instanceof Player)
			sendFinalMessage(target, message);
		else
			for (final String part : StringUtils.splitByWholeSeparator(message, "\\n"))
				target.sendMessage(chatHeader + part);
	}

	@SafeVarargs
	public static <S> String putArgs(final String message, final S... args)
	{
		return putArgs(0, message, args);
	}

	@SafeVarargs
	public static <S> String putArgs(final int start, final String message, final S... args)
	{
		return putArgsExtended(null, message, args);
	}

	public static String putArgsPara(final CommandSender target, final String message, final ParameterData data)
	{
		return putArgsExtended(target, message, toList(data));
	}

	public static String[] putArgsPara(final CommandSender target, final String[] message, final ParameterData data)
	{
		final int length = message.length;
		final String[] res = new String[length];
		final PersonalizedMessage[] args = toList(data);
		for (int i = 0; i < length; i++)
			res[i] = putArgsExtended(target, message, args);
		return res;
	}

	@SafeVarargs
	public static <S> String putArgsExtended(final CommandSender target, final Object message, final S... args)
	{
		return putArgsExtended(0, target, message, args);
	}

	@SafeVarargs
	public static <S> String putArgsExtended(final int start, final CommandSender target, final Object message, final S... args)
	{
		String res = message.toString();
		if (message instanceof PersonalizedMessage)
			res = ((PersonalizedMessage) message).getMessage(target);
		final int length = args.length;
		for (int i = 0; i < length; i++)
			res = StringUtils.replace(res, "{" + (i + start) + "}", getMessage(target, args[i]));
		return res;
	}

	private static String getMessage(final CommandSender sender, final Object object)
	{
		if (sender == null || !(object instanceof PersonalizedMessage))
			return String.valueOf(object.toString());
		else
			return ((PersonalizedMessage) object).getMessage(sender);
	}

	public static <S> String listingString(final S[] strings)
	{
		return listingString(", ", strings);
	}

	@SafeVarargs
	public static <S> String listingString(final String separator, final S... strings)
	{
		return StringUtils.join(strings, separator);
	}

	public static <S> String listingString(final Collection<S> strings)
	{
		return listingString(", ", strings);
	}

	public static <S> String listingString(final String seperator, final Collection<S> strings)
	{
		return StringUtils.join(strings, seperator);
	}

	public static String[] toList(final CommandSender sender, final ParameterData data)
	{
		final int count = data.getParameterCount();
		final String[] res = new String[count];
		for (int i = 0; i < count; i++)
			res[i] = data.getParameter(sender, i);
		return res;
	}

	public static PersonalizedMessage[] toList(final ParameterData data)
	{
		final int count = data.getParameterCount();
		final PersonalizedMessage[] res = new PersonalizedMessage[count];
		for (int i = 0; i < count; i++)
			res[i] = new ParameterDataContainer(data, i);
		return res;
	}

	private static class ParameterDataContainer implements PersonalizedMessage
	{

		private final ParameterData data;
		private final int index;

		public ParameterDataContainer(final ParameterData data, final int index)
		{
			super();
			this.data = data;
			this.index = index;
		}

		@Override
		public String getMessage(final CommandSender sender)
		{
			return data.getParameter(sender, index);
		}

		@Override
		public String toString()
		{
			return "PersonalizedMessage{from: " + data.toString() + ", index: " + index + "}";
		}
	}

	public static void shortPrintStackTrace(final Throwable main, final Throwable throwable, final Object cause)
	{
		final StackTraceElement[] causedTrace = main.getStackTrace();
		final StackTraceElement[] trace = throwable.getStackTrace();
		int m = trace.length - 1, n = causedTrace.length - 1;
		while (m >= 0 && n >= 0 && trace[m].equals(causedTrace[n]))
		{
			m--;
			n--;
		}
		final int framesInCommon = trace.length - 1 - m;
		System.err.println(throwable);
		for (int i = 0; i <= m; i++)
			System.err.println("\tat " + trace[i]);
		if (framesInCommon != 0)
			System.err.println("\t... " + framesInCommon + " more");
		// Recurse if we have a cause
		final Throwable ourCause = throwable.getCause();
		if (ourCause != null)
			shortPrintStackTrace2(ourCause, trace, cause);
	}

	private static void shortPrintStackTrace2(final Throwable throwable, final StackTraceElement[] causedTrace, final Object cause)
	{
		final StackTraceElement[] trace = throwable.getStackTrace();
		int m = trace.length - 1, n = causedTrace.length - 1;
		while (m >= 0 && n >= 0 && trace[m].equals(causedTrace[n]))
		{
			m--;
			n--;
		}
		final int framesInCommon = trace.length - 1 - m;
		System.err.println("Caused by: " + cause);
		for (int i = 0; i <= m; i++)
			System.err.println("\tat " + trace[i]);
		if (framesInCommon != 0)
			System.err.println("\t... " + framesInCommon + " more");
		// Recurse if we have a cause
		final Throwable ourCause = throwable.getCause();
		if (ourCause != null)
			shortPrintStackTrace2(ourCause, trace, cause);
	}
}
