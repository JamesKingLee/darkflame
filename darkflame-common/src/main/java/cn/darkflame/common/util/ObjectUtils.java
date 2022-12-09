package cn.darkflame.common.util;

import cn.darkflame.common.exception.NotNullException;

/**
 * 对象工具类
 *
 * @author james
 */
public class ObjectUtils {

    /**
     * 判断对象是否为空，是否空数组与数组内元素是否均为空
     *
     * @param obj 待判断对象
     * @return <p><code>true</code> <code>obj</code>等于<code>null</code>或者是<code>{@link Null}</code>的实例</p>
     * <p><code>false</code> <code>obj</code>是其他情况</p>
     */
    public static boolean isNull(Object obj) {
        return obj == null || obj instanceof Null;
    }


    /**
     * <p>不能为空校验，内部调用<code>{@link ObjectUtils#isNull(Object)}</code>与<code>{@link CollectionUtils#isEmpty(Object[])}</code>进行判断</p>
     * <p>如果为空则抛出异常<code>{@link NotNullException}</code></p>
     *
     * @param o   待判断对象
     * @param msg 为空时异常信息
     */
    public static void notNull(Object o, String msg) {
        if (isNull(o)) {
            throw new NotNullException(NotNullException.OBJ_NULL_MSG, msg);
        } else if (o.getClass().isArray() && CollectionUtils.isEmpty((Object[]) o)) {
            throw new NotNullException(NotNullException.ARRAY_NULL_MSG, msg, msg);
        }
    }

}
