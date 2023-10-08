package com.librebuy.core.i18n;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.librebuy.vaadin.core.TranslationProvider;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class I18nManager {

    private static final Table<Locale, String, String> TRANSLATIONS = HashBasedTable.create();
    private static final Set<String> KEYS = new HashSet<>();

//    public static final Locale LOCALE_EN = Locale.of("en", "GB");
//    public static final Locale LOCALE_PL = Locale.of("pl", "PL");
//    public static final List<Locale> SUPPORTED_LOCALES = List.of(LOCALE_EN, LOCALE_PL);

    static {
        System.out.println("INICJALIZACJA!!!");
    }


    //------------------------------------------------------------------------------------------------------------------

    public static void putTranslation(String key, Locale locale, String value) {
        TRANSLATIONS.put(locale, key, value);
    }

    public static void putTranslation(String key, String english, String polish) {
        TRANSLATIONS.put(TranslationProvider.LOCALE_EN, key, english);
        TRANSLATIONS.put(TranslationProvider.LOCALE_PL, key, polish);
        KEYS.add(key);
    }

    public static String getTranslation(String key, Locale locale) {
        return TRANSLATIONS.get(key, locale);
    }


}
