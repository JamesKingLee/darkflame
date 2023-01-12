package cn.darkflame.common.util;

import cn.darkflame.common.constant.KeywordConstant;

import java.util.Arrays;
import java.util.Optional;

public class PathUtils {

    public static String getPath(String... pathList) {
        pathList = Optional.ofNullable(pathList).orElse(new String[0]);
        StringBuffer res = new StringBuffer();
        Arrays.stream(pathList).forEach(path -> {
            path = Optional.ofNullable(path).orElse(KeywordConstant.EMPTY);
            path = StringUtils.trim(path);
            if (path.contains(KeywordConstant.BACK_SLASH)) {
                path = path.replace(KeywordConstant.BACK_SLASH, KeywordConstant.FORWARD_SLASH);
            }
            path = StringUtils.replaceStart(path, KeywordConstant.FORWARD_SLASH_CHAR, KeywordConstant.EMPTY);
            path = StringUtils.replaceEnd(path, KeywordConstant.FORWARD_SLASH_CHAR, KeywordConstant.EMPTY);
            if (!path.contains(KeywordConstant.FULL_STOP)) {
                path += KeywordConstant.FORWARD_SLASH;
            }
            res.append(path);
        });
        return res.toString();
    }
}
