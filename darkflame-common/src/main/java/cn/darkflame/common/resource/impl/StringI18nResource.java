package cn.darkflame.common.resource.impl;

import cn.darkflame.common.util.CollectionUtils;
import cn.darkflame.common.resource.IResource;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author james
 */
@SuppressWarnings("unused")
public class StringI18nResource implements IResource<String, String> {

    private final ResourceBundle resourceBundle;

    public StringI18nResource(String baseName) {
        resourceBundle = ResourceBundle.getBundle(baseName, Locale.getDefault());
    }

    public StringI18nResource(String baseName, Locale locale) {
        resourceBundle = ResourceBundle.getBundle(baseName, locale);
    }

    @Override
    public String getResource(String key) {
        return resourceBundle.getString(key);
    }

    @Override
    public String getResource(String key, Object... params) {
        if (CollectionUtils.isEmpty(params)) {
            return getResource(key);
        }
        return getResource(key).formatted(params);
    }
}
