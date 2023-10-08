package com.librebuy.vaadin.core;

import com.librebuy.core.i18n.I18nManager;
import com.vaadin.flow.i18n.I18NProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Slf4j
public class TranslationProvider implements I18NProvider {

    public static final String BUNDLE_PREFIX = "i18n/translate";

    @Autowired
    private I18nManager i18nManager;

    public static final Locale LOCALE_EN = Locale.of("en", "GB");
    public static final Locale LOCALE_PL = Locale.of("pl", "PL");
    public static final List<Locale> SUPPORTED_LOCALES = List.of(LOCALE_EN, LOCALE_PL);

    @Override
    public List<Locale> getProvidedLocales() {
        return SUPPORTED_LOCALES;
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {
        if (key == null) {
            log.warn("Got lang request for key with null value!");
            return "";
        }

        String translation = I18nManager.getTranslation(key, locale);

        final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_PREFIX, locale);

        String value;
        try {
            value = bundle.getString(key);
        } catch (final MissingResourceException e) {
            log.warn("Missing resource", e);
            return "!" + locale.getLanguage() + ": " + key;
        }
        if (params.length > 0) {
            value = MessageFormat.format(value, params);
        }
        return value;
    }

    //    @Override
//    public String getTranslation(Object key, Locale locale, Object... params) {
//        if (key instanceof I18nText) {
//            I18nText t1 = (I18nText) key;
//            return t1.getEnglish();
//        }
//
//        if (key instanceof I18nField) {
//            I18nField f1 = (I18nField) key;
//            return f1.en();
//        }
//
//        return I18NProvider.super.getTranslation(key, locale, params);
//    }
}