package cn.darkflame.common.util;


import cn.darkflame.common.constant.KeywordConstant;

import java.util.Collections;
import java.util.Optional;

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

    /**
     * 去掉首位空格
     *
     * @param s 目标字符串
     * @return 操作后字符串
     */
    public static String trim(String s) {
        return s.trim();
    }

    /**
     * 替代开始的字符，可替换多个字符
     *
     * @param s       目标字符串
     * @param oldChar 旧字符
     * @param newStr  新字符串
     * @return 替换完成字符串
     */
    public static String replaceStart(String s, char oldChar, String newStr) {
        s = Optional.ofNullable(s).orElse(KeywordConstant.EMPTY);
        if (isEmpty(s)) {
            return s;
        }
        int i = 0;
        while (oldChar == s.charAt(i)) {
            i++;
        }
        String startStr = times(newStr, i);
        s = s.substring(i);
        return startStr + s;

    }

    /**
     * 替代结束的字符，可替换多个字符
     *
     * @param s       目标字符串
     * @param oldChar 旧字符
     * @param newStr  新字符串
     * @return 替换完成字符串
     */
    public static String replaceEnd(String s, char oldChar, String newStr) {
        s = Optional.ofNullable(s).orElse(KeywordConstant.EMPTY);
        if (isEmpty(s)) {
            return s;
        }
        int i = s.length() - 1;
        while (oldChar == s.charAt(i)) {
            i--;
        }
        String endStr = times(newStr, i);
        s = s.substring(0, i + 1);
        return s + endStr;

    }

    /**
     * 生成重复time次的s的字符串
     *
     * @param s    源字符串
     * @param time 重复次数
     * @return 重复字符串
     */
    public static String times(String s, int time) {
        s = Optional.ofNullable(s).orElse(KeywordConstant.EMPTY);
        time = Math.max(time, 0);
        if (KeywordConstant.EMPTY.equals(s) || time == 0) {
            return KeywordConstant.EMPTY;
        }
        return String.join(KeywordConstant.EMPTY, Collections.nCopies(time, s));
    }

}
