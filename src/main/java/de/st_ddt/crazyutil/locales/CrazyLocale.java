package de.st_ddt.crazyutil.locales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import de.st_ddt.crazyplugin.CrazyPluginInterface;
import de.st_ddt.crazyutil.ChatHelper;

public class CrazyLocale extends HashMap<String, CrazyLocale> implements PersonalizedMessage
{

	private static final long serialVersionUID = 7789788937594284997L;
	public final static String FALLBACKLANGUAGE = "en_GB";
	private final static Pattern PATTERN_LANGUAGE = Pattern.compile("([a-z]{2})_([A-Z]{2})");
	private final static Pattern PATTERN_LANGUAGE_CASE_INSENSITIVE = Pattern.compile(PATTERN_LANGUAGE.pattern(), Pattern.CASE_INSENSITIVE);
	private final static Pattern PATTERN_DOT = Pattern.compile("\\.");
	private final static Pattern PATTERN_UNDERSCORE = Pattern.compile("_");
	private final static Pattern PATTERN_EQUALSIGN = Pattern.compile("=");
	private final static Pattern PATTERN_OLD = Pattern.compile("\\$([0-9]+)\\$");
	private final static CrazyLocale locale = getCrazyLocaleHead();
	private final static CrazyLocale missing = getCrazyLocaleMissing();
	private final static Map<String, String> userLanguages = new HashMap<String, String>();
	private final static Set<String> languages = new HashSet<String>();
	private final static Map<String, HashSet<String>> languageAlternatives = new HashMap<String, HashSet<String>>();
	private static String defaultLanguage;
	private final String name;
	private final Map<String, String> localeTexts = new HashMap<String, String>();
	private final CrazyLocale parent;
	private CrazyLocale alternative = null;

	public final static CrazyLocale getLocaleHead()
	{
		return locale;
	}

	public final static CrazyLocale getLocaleMissing()
	{
		return missing;
	}

	static
	{
		getLocaleHead().addLanguageEntry(FALLBACKLANGUAGE, "CRAZYPLUGIN", "CRAZYPLUGIN");
		getLocaleHead().addLanguageEntry(FALLBACKLANGUAGE, "CRAZYPLUGIN.UPDATED", "{0} has been updated. Updating language files.");
		getLocaleHead().addLanguageEntry(FALLBACKLANGUAGE, "CRAZYPLUGIN.LANGUAGE.ERROR.READ", "Failed reading {0} language files!");
		getLocaleHead().addLanguageEntry(FALLBACKLANGUAGE, "CRAZYPLUGIN.LANGUAGE.ERROR.EXTRACT", "Failed exporting {0} language files!");
		getLocaleHead().addLanguageEntry(FALLBACKLANGUAGE, "CRAZYPLUGIN.LANGUAGE.ERROR.AVAILABLE", "{0} language files not available!");
		getLocaleHead().addLanguageEntry(FALLBACKLANGUAGE, "CRAZYPLUGIN.DATABASE.LOADED", "Loaded {0} entries from database!");
		getLocaleHead().addLanguageEntry(FALLBACKLANGUAGE, "CRAZYPLUGIN.DATABASE.ACCESSWARN", "&CWARNING! Cannot access Database!");
		getLocaleHead().addLanguageEntry(FALLBACKLANGUAGE, "LANGUAGE.NAME", "English");
	}

	public static String fixLanguage(final String language)
	{
		return fixLanguage(language, null);
	}

	public static String fixLanguage(String language, final String defaultLanguage)
	{
		if (language == null)
			return defaultLanguage;
		if (language.startsWith("custom_"))
			language = defaultLanguage.substring(7);
		if (language.endsWith(".lang"))
			language = language.substring(0, language.length() - 5);
		final Matcher matcher = PATTERN_LANGUAGE_CASE_INSENSITIVE.matcher(language);
		if (!matcher.matches())
			return defaultLanguage;
		else
			return matcher.group(1).toLowerCase() + "_" + matcher.group(2).toUpperCase();
	}

	public static String getDefaultLanguage()
	{
		return defaultLanguage;
	}

	public static void setDefaultLanguage(final String language)
	{
		CrazyLocale.defaultLanguage = fixLanguage(language, FALLBACKLANGUAGE);
	}

	public final static CrazyLocale getPluginHead(final CrazyPluginInterface plugin)
	{
		return getLocaleHead().getSecureLanguageEntry(plugin.getName());
	}

	public final static CrazyLocale getUnit(final String unit)
	{
		return getLocaleHead().getLanguageEntry("UNIT." + unit.toUpperCase());
	}

	public final static String getUnitText(final String unit, final CommandSender sender)
	{
		return getUnit(unit).getLanguageText(sender);
	}

	public final static String getUnitText(final String unit, final String language)
	{
		return getUnit(unit).getLanguageText(language);
	}

	public static CrazyLocale getLanguageName()
	{
		return getLocaleHead().getSecureLanguageEntry("LANGUAGE.NAME");
	}

	public static String getLanguageName(final String language)
	{
		return getLanguageName().getExactLanguageText(language);
	}

	public static String getSaveLanguageName(final String language)
	{
		final String name = getLanguageName().getExactLanguageText(language);
		return (name == null ? "UNKNOWN" : name);
	}

	public static String getSaveLanguageName(final String language, final boolean appendLanguage)
	{
		final String res = getSaveLanguageName(language);
		if (appendLanguage)
			return res + " (" + language + ")";
		else
			return res;
	}

	public static String getSaveShortLanguageName(final String language, final boolean appendLanguage)
	{
		final String res = getSaveLanguageName(language);
		if (appendLanguage)
			return res.split(" - ")[0] + " (" + language + ")";
		else
			return res.split(" - ")[0];
	}

	public static boolean isActiveLanguage(final String language)
	{
		return getLanguageName(language) != null;
	}

	public static Set<String> getActiveLanguages()
	{
		return getLanguageName().getLanguageTextMap().keySet();
	}

	public static List<String> getActiveLanguagesNames(final boolean appendLanguage)
	{
		final List<String> res = new ArrayList<String>();
		for (final String language : getActiveLanguages())
		{
			final String text = getSaveLanguageName(language, appendLanguage);
			if (text == null)
				continue;
			res.add(text);
		}
		return res;
	}

	public static List<String> getActiveShortLanguagesNames(final boolean appendLanguage)
	{
		final List<String> res = new ArrayList<String>();
		for (final String language : getActiveLanguages())
		{
			final String text = getSaveShortLanguageName(language, appendLanguage);
			if (text == null)
				continue;
			res.add(text);
		}
		return res;
	}

	public static Set<String> getLanguageAlternatives(final String language)
	{
		final String[] split = PATTERN_UNDERSCORE.split(language.toLowerCase());
		final Set<String> res = new HashSet<String>();
		final Collection<String> temp = languageAlternatives.get(split[0]);
		if (temp != null)
			res.addAll(temp);
		if (split.length > 1)
		{
			final Collection<String> temp2 = languageAlternatives.get(split[1]);
			if (temp != null)
				res.addAll(temp2);
		}
		return res;
	}

	public static boolean isValid(final CrazyLocale locale)
	{
		return locale != null && locale != getLocaleHead() && locale != getLocaleMissing();
	}

	public boolean isValid()
	{
		return this != getLocaleHead() && this != getLocaleMissing();
	}

	public CrazyLocale(final CrazyLocale parent, final String name)
	{
		super();
		this.name = name;
		this.parent = parent;
	}

	private static CrazyLocale getCrazyLocaleHead()
	{
		final CrazyLocale head = new CrazyLocale(null, "_HEAD_");
		head.setLanguageText(FALLBACKLANGUAGE, "This Entry is the root!");
		return head;
	}

	private static CrazyLocale getCrazyLocaleMissing()
	{
		final CrazyLocale missing = new CrazyLocale(null, "_MISSING_");
		missing.setLanguageText(FALLBACKLANGUAGE, "This Language-Entry is missing!");
		return missing;
	}

	public String getName()
	{
		return name;
	}

	public String getPath()
	{
		if (parent == null)
			return name;
		return parent.getPath() + "." + name;
	}

	public CrazyLocale getAlternative()
	{
		return alternative;
	}

	public void setAlternative(final CrazyLocale alternative)
	{
		this.alternative = alternative;
	}

	public String getLocaleMessage(final CommandSender target, final String localePath, final Object... args)
	{
		return ChatHelper.putArgsExtended(target, getLanguageEntry(localePath), args);
	}

	/**
	 * Helper method for kick messages and other messages, that have to be fully formated.
	 * 
	 * @param target
	 *            The CommandSender who will recieve this message.
	 * @param localePath
	 *            The path to the given message.
	 * @param args
	 *            Message args to be inserted in this message.
	 * @return Returns a formated locale message.
	 */
	public String getFormatedLocaleMessage(final CommandSender target, final String localePath, final Object... args)
	{
		return StringUtils.replace(getLocaleMessage(target, localePath, args), "\\n", "\n");
	}

	public String getDefaultLocaleMessage(final String localePath, final Object... args)
	{
		return ChatHelper.putArgs(getLanguageEntry(localePath).getDefaultLanguageText(), args);
	}

	public Map<String, String> getLanguageTextMap()
	{
		return localeTexts;
	}

	@Override
	public String getMessage(final CommandSender sender)
	{
		return getLanguageText(sender);
	}

	public String getLanguageText(final CommandSender sender)
	{
		return getLanguageText(getUserLanguage(sender));
	}

	public String getFormatedLanguageText(final CommandSender sender)
	{
		return getFormatedLanguageText(getUserLanguage(sender));
	}

	public String getLanguageText(final String language)
	{
		String res = localeTexts.get(language);
		if (res == null)
		{
			final Iterator<String> it = getLanguageAlternatives(language).iterator();
			while (res == null && it.hasNext())
				res = localeTexts.get(it.next());
			if (res == null)
				res = getDefaultLanguageText();
			if (res == null)
			{
				res = "LOCALE IS MISSING!";
				if (this != missing)
					System.err.println("[CrazyLocale] " + getPath() + " is missing!");
			}
		}
		return res;
	}

	public String getFormatedLanguageText(final String language)
	{
		return StringUtils.replace(getLanguageText(language), "\\n", "\n");
	}

	public String getExactLanguageText(final String language)
	{
		return localeTexts.get(language);
	}

	public String getDefaultLanguageText()
	{
		String res = localeTexts.get(defaultLanguage);
		if (res == null)
		{
			res = localeTexts.get(FALLBACKLANGUAGE);
			if (res == null)
				if (localeTexts.isEmpty())
					return null;
				else
					return localeTexts.values().iterator().next();
		}
		return res;
	}

	public void setLanguageText(final String language, final String text)
	{
		this.localeTexts.put(language, text);
	}

	public void sendMessage(final CommandSender target)
	{
		target.sendMessage(getLanguageText(target));
	}

	public void sendMessage(final CommandSender... targets)
	{
		for (final CommandSender target : targets)
			sendMessage(target);
	}

	public void sendMessage(final CommandSender target, final Object... args)
	{
		ChatHelper.sendMessage(target, this, args);
	}

	public void sendMessage(final CommandSender[] targets, final Object... args)
	{
		for (final CommandSender target : targets)
			sendMessage(target, args);
	}

	public CrazyLocale getLanguageEntry(final String path)
	{
		final CrazyLocale locale = getEntry(path.toUpperCase());
		if (locale == missing)
			System.err.println("[CrazyLocale] " + getPath() + "." + path + " is missing!");
		return locale;
	}

	protected CrazyLocale getEntry(final String path)
	{
		final String[] split = PATTERN_DOT.split(path, 2);
		CrazyLocale locale = get(split[0]);
		if (locale == null)
			locale = missing;
		else if (split.length == 2)
			locale = locale.getEntry(split[1]);
		if (locale == missing)
			locale = getAlternativeEntry(path);
		if (locale == missing)
			locale = getParentialAlternativeEntry(path);
		return locale;
	}

	protected CrazyLocale getAlternativeEntry(final String path)
	{
		final String[] split = PATTERN_DOT.split(path, 2);
		CrazyLocale locale = missing;
		if (alternative != null)
		{
			locale = alternative.get(split[0]);
			if (locale == null)
				return missing;
			else if (split.length == 2)
				locale = locale.getEntry(split[1]);
		}
		return locale;
	}

	protected CrazyLocale getParentialAlternativeEntry(String path)
	{
		if (parent == null)
			return missing;
		path = name + "." + path;
		CrazyLocale locale = parent.getAlternativeEntry(path);
		if (locale == missing)
			locale = parent.getParentialAlternativeEntry(path);
		return locale;
	}

	public CrazyLocale getSecureLanguageEntry(String path)
	{
		path = path.toUpperCase();
		final String[] split = PATTERN_DOT.split(path, 2);
		CrazyLocale locale = get(split[0]);
		if (locale == null)
		{
			locale = new CrazyLocale(this, split[0]);
			put(split[0], locale);
		}
		if (split.length == 2)
			locale = locale.getSecureLanguageEntry(split[1]);
		return locale;
	}

	public CrazyLocale addLanguageEntry(final String language, final String path, final String entry)
	{
		final CrazyLocale locale = getSecureLanguageEntry(path);
		locale.setLanguageText(language, PATTERN_OLD.matcher(ChatHelper.colorise(entry)).replaceAll("\\{$1\\}"));
		return locale;
	}

	public static void readFile(final String language, final File file) throws IOException
	{
		try (InputStream stream = new FileInputStream(file);
				InputStreamReader reader = new InputStreamReader(stream, "UTF-8"))
		{
			readFile(language, reader);
		}
	}

	public static void readFile(final String language, final Reader in) throws IOException
	{
		final String lang = fixLanguage(language);
		if (lang == null)
			throw new IllegalArgumentException("Language " + language + " is not valid!");
		try (BufferedReader reader = new BufferedReader(in))
		{
			String zeile = reader.readLine();
			if (zeile == null)
				return;
			// Remove UTF-8 BOM (Windows, Linux)
			final byte[] bytes = zeile.getBytes();
			if (bytes.length > 1)
				if (bytes[0] == (byte) 63)
					zeile = zeile.substring(1);
				else if (bytes.length > 3)
					if (bytes[0] == (byte) 239 && bytes[1] == (byte) 187 && bytes[2] == (byte) 191)
						zeile = zeile.substring(3);
			String[] split = null;
			do
			{
				if (zeile.length() <= 1 || zeile.startsWith("#"))
					continue;
				split = PATTERN_EQUALSIGN.split(zeile, 2);
				try
				{
					locale.addLanguageEntry(lang, split[0], split[1]);
				}
				catch (final ArrayIndexOutOfBoundsException e)
				{
					System.err.println("Invalid language entry/line \"" + zeile + "\" for language: " + lang);
				}
			}
			while ((zeile = reader.readLine()) != null);
		}
	}

	public static void printAll(final String language, final CommandSender sender)
	{
		sender.sendMessage("Complete Language Print!");
		getLocaleHead().print(language, sender);
	}

	public static void printAll(final String language)
	{
		printAll(language, Bukkit.getConsoleSender());
	}

	public static void printAll(final CommandSender sender)
	{
		printAll(getUserLanguage(sender), sender);
	}

	public void print(final String language, final CommandSender sender)
	{
		sender.sendMessage(getPath() + ":" + getLanguageText(language));
		for (final CrazyLocale locale : this.values())
			locale.print(language, sender);
	}

	public void print(final CommandSender sender)
	{
		print(getUserLanguage(sender), sender);
	}

	public static String getUserLanguage(final CommandSender sender)
	{
		if (sender == null)
			return defaultLanguage;
		return getUserLanguage(sender.getName());
	}

	public static String getUserLanguage(final String name)
	{
		String res = userLanguages.get(name.toLowerCase());
		if (res == null)
			res = defaultLanguage;
		return res;
	}

	public static String getUserLanguageName(final CommandSender sender, final boolean appendLanguage)
	{
		return getSaveLanguageName(getUserLanguage(sender), appendLanguage);
	}

	public static String getUserLanguageName(final String name, final boolean appendLanguage)
	{
		return getSaveLanguageName(getUserLanguage(name), appendLanguage);
	}

	public static void setUserLanguage(final CommandSender sender, final String language)
	{
		setUserLanguage(sender.getName(), language);
	}

	public static void setUserLanguage(final String name, final String language)
	{
		userLanguages.put(name.toLowerCase(), language);
	}

	public static boolean removeUserLanguage(final String name)
	{
		return userLanguages.remove(name.toLowerCase()) != null;
	}

	public static void loadLanguage(final String language)
	{
		if (languages.add(language))
		{
			if (!isActiveLanguage(language))
				return;
			try
			{
				for (final String part : PATTERN_UNDERSCORE.split(language.toLowerCase()))
				{
					if (!languageAlternatives.containsKey(part))
						languageAlternatives.put(part, new HashSet<String>());
					languageAlternatives.get(part).add(language);
				}
			}
			catch (final Exception e)
			{}
		}
	}

	public static Set<String> getLoadedLanguages()
	{
		return languages;
	}

	public static String getSystemLanguage()
	{
		final String systemLanguage = System.getProperty("user.language").toLowerCase();
		final String systemCountry = System.getProperty("user.country", systemLanguage).toUpperCase();
		return systemLanguage + "_" + systemCountry;
	}

	public static void saveUserLanguages(final ConfigurationSection config, final String path)
	{
		for (final java.util.Map.Entry<String, String> user : userLanguages.entrySet())
			config.set(path + user.getKey(), user.getValue());
	}

	public static Set<String> loadUserLanguages(final ConfigurationSection config)
	{
		final Set<String> languages = new HashSet<String>();
		if (config == null)
			return languages;
		for (final String name : config.getKeys(false))
		{
			final String language = fixLanguage(config.getString(name));
			setUserLanguage(name, language);
			languages.add(language);
		}
		return languages;
	}

	@Override
	public String toString()
	{
		return getDefaultLanguageText();
	}
}
