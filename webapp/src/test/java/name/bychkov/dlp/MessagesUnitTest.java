package name.bychkov.dlp;

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

public class MessagesUnitTest {
	@Test
	void testKeys() {
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
}