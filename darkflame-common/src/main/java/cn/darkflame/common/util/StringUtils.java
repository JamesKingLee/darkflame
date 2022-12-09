package cn.darkflame.common.util;


/**
 * 字符串工具类
 *
 * @author james
 */
@SuppressWarnings("unused")
public class StringUtils {

    /**
     * 判断字符串是否为空
     *
     * @param s 待判断字符串
     * @return <p><code>true</code> 空串或<code>null</code></p> <p><code>false</code> 其他情况</p>
     */
    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    /**
     * 判断字符串是否为空格串
     *
     * @param s 待判断字符串
     * @return <p><code>true</code> 空串或者<code>null</code>或者只有空格的字符串</p> <p><code>false</code> 其他情况</p>
     */
    public static boolean isBlank(String s) {
        return isEmpty(s) || s.isBlank();
    }

    public static String trim(String s) {
        return s.trim();
    }

}
