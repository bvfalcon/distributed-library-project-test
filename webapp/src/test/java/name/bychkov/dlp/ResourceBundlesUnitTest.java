package name.bychkov.dlp;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.omnifaces.facesconfigparser.FacesConfigParser;
import org.omnifaces.facesconfigparser.digester.beans.FacesConfigBean;
import org.omnifaces.facesconfigparser.digester.beans.LocaleConfigBean;

public class ResourceBundlesUnitTest {
	@Test
	void testMessagesKeys() {
		FacesConfigBean facesConfigBean = FacesConfigParser.parseFacesConfig("target/classes/WEB-INF/faces-config.xml",
				null);

		List<Locale> locales = new ArrayList<>();
		LocaleConfigBean lcb = facesConfigBean.getApplication().getLocaleConfig();
		locales.add(Locale.forLanguageTag(lcb.getDefaultLocale()));
		Arrays.asList(lcb.getSupportedLocales())
				.forEach(o -> locales.add(Locale.forLanguageTag(o)));

		String baseName = this.getClass().getPackageName() + ".Messages";

		var allKeys = new HashSet<String>();
		var localeKeys = new HashMap<Locale, Set<String>>();
		for (Locale locale : locales) {
			ResourceBundle rb = ResourceBundle.getBundle(baseName, locale);
			Enumeration<String> keys = rb.getKeys();
			var localeSet = new HashSet<String>();
			keys.asIterator().forEachRemaining(o -> localeSet.add(o));
			localeKeys.put(locale, localeSet);
			allKeys.addAll(localeSet);
		}

		boolean error = false;
		for (Locale locale : locales) {
			Set<String> localeSet = localeKeys.get(locale);
			if (localeSet.size() < allKeys.size()) {
				Set<String> allKeysCopy = new HashSet<String>(allKeys);
				allKeysCopy.removeAll(localeSet);
				for (String key : allKeysCopy) {
					System.err.println("Not found key '" + key + "' for baseName=" + baseName + ", locale=" + locale);
				}
				error = true;
			}
		}
		Assertions.assertFalse(error);
	}
	
	@Test
	void testFiles() {
		FacesConfigBean facesConfigBean = FacesConfigParser.parseFacesConfig("target/classes/WEB-INF/faces-config.xml",
				null);

		List<Locale> locales = new ArrayList<>();
		LocaleConfigBean lcb = facesConfigBean.getApplication().getLocaleConfig();
		locales.add(Locale.forLanguageTag(lcb.getDefaultLocale()));
		Arrays.asList(lcb.getSupportedLocales())
				.forEach(o -> locales.add(Locale.forLanguageTag(o)));

		final String ext = ".txt";
		System.out.println();
		File folder = new File(System.getProperty("user.dir") + "/target/classes");
		System.out.println(folder.getAbsolutePath());
		List<String> fileNames = Arrays.asList(folder.list((File dir, String name) -> name.endsWith(ext)));
		Set<String> baseNames = new HashSet<>();
		for (String fileName : fileNames) {
			int lastIndex = fileName.lastIndexOf("_");
			if (lastIndex == -1) {
				continue;
			}
			for (Locale locale : locales) {
				String suffix = "_" + locale.toString() + ext;
				if (fileName.endsWith(suffix)) {
					lastIndex = fileName.indexOf(suffix);
					String baseName = fileName.substring(0, lastIndex);
					baseNames.add(baseName);
				}
			}
		}
		
		boolean error = false;
		for (String baseName : baseNames) {
			for (Locale locale : locales) {
				if (!(new File(folder, baseName + "_" + locale.toString() + ext)).exists()) {
					System.err.println("Not found file '" + baseName + "_" + locale + "'");
					error = true;
				}
			}
		}
		Assertions.assertFalse(error);
	}
}