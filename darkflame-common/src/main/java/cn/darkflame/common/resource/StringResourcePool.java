package cn.darkflame.common.resource;

import cn.darkflame.common.util.ObjectUtils;
import cn.darkflame.common.resource.impl.StringI18nResource;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author james
 */
@SuppressWarnings("unused")
public class StringResourcePool {

    private static final StringResourcePool INSTANCE = new StringResourcePool();

    private final Map<String, IResource<String, String>> pool = new ConcurrentHashMap<>();

    private StringResourcePool() {
    }

    public static StringResourcePool getInstance() {
        return INSTANCE;
    }

    public IResource<String, String> getResourceBundle(String baseName) {
        return getResourceBundle(baseName, Locale.getDefault());
    }

    public IResource<String, String> getResourceBundle(String baseName, Locale locale) {
        ObjectUtils.notNull(baseName, "baseName");
        String key = baseName + "_" + locale;
        if (pool.containsKey(key)) {
            return pool.get(key);
        }
        synchronized (this) {
            if (pool.containsKey(key)) {
                return pool.get(key);
            }
            IResource<String, String> bundle = new StringI18nResource(baseName, locale);
            pool.put(key, bundle);
            return bundle;
        }
    }

    public void clearPool() {
        pool.clear();
    }
}
