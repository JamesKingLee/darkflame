package cn.darkflame.common.resource;

/**
 * @author james
 */
@SuppressWarnings("unused")
public interface IResource<K, V> {

    /**
     * 获取国际化资源
     *
     * @param key 键
     * @return 资源
     */
    V getResource(K key);

    /**
     * 获取国际化资源
     *
     * @param key    键
     * @param params 格式化参数
     * @return 资源
     */
    V getResource(K key, Object... params);

}
