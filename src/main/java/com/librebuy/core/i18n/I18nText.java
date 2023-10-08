package com.librebuy.core.i18n;

import lombok.Getter;

@Getter
public class I18nText {

    private final String key;
    private final String english;
    private final String polish;

    public I18nText(String key, String english, String polish) {
        this.key = key;
        this.english = english;
        this.polish = polish;

        I18nManager.putTranslation(key, english, polish);
    }
}
