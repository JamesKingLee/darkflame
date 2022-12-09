package cn.darkflame.common.util;

import java.util.Collection;

/**
 * 集合工具类
 *
 * @author james
 */
@SuppressWarnings("unused")
public class CollectionUtils {

    /**
     * 判断列表数组是否为空
     *
     * @param arr 待判断数组
     * @return <p><code>true</code> <code>arr</code>等于<code>null</code>或者是<code>{@link Null}</code>的实例或arr长度为0</p>
     * <p><code>false</code> <code>arr</code>是其他情况</p>
     */
    public static boolean isEmpty(Object[] arr) {
        return ObjectUtils.isNull(arr) || arr.length == 0;
    }

    /**
     * 判断序列对象是否为空
     *
     * @param coll 待判断序列
     * @return <p><code>true</code> <code>coll</code>等于<code>null</code>或者是<code>{@link Null}</code>的实例或coll大小为0</p>
     * <p><code>false</code> <code>list</code>是其他情况</p>
     */
    public static boolean isEmpty(Collection<?> coll) {
        return ObjectUtils.isNull(coll) || coll.size() == 0;
    }

}
