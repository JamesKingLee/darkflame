package cn.darkflame.meta.code.model;

import cn.darkflame.common.constant.KeywordConstant;
import cn.darkflame.common.util.StringUtils;

import java.lang.reflect.Modifier;
import java.util.Collections;

@SuppressWarnings("unused")
public class Field extends CommonMember {

    private String type;
    private String initVal;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type.contains(KeywordConstant.FULL_STOP)) {
            addImport(type);
            type = type.substring(type.lastIndexOf(KeywordConstant.FULL_STOP) + 1);
        }
        this.type = type;
    }

    public String getInitVal() {
        return initVal;
    }

    public void setInitVal(String initVal) {
        this.initVal = initVal == null ? "" : initVal;
    }

    public String getCode(int level) {
        StringBuilder code = new StringBuilder();
        code.append(getDocCode(level)).append(KeywordConstant.NEW_LINE);
        String annotationCode = getAnnotationCode(level);
        if (!StringUtils.isEmpty(annotationCode)) {
            code.append(annotationCode).append(KeywordConstant.NEW_LINE);
        }
        String indentation = String.join(KeywordConstant.EMPTY, Collections.nCopies(level, KeywordConstant.TAB));
        String modifiesCode = Modifier.toString(getModifiers());
        code.append(indentation);
        if (!StringUtils.isEmpty(modifiesCode)) {
            code.append(modifiesCode).append(KeywordConstant.BLANK_SPACE);
        }
        code.append(type).append(KeywordConstant.BLANK_SPACE).append(getName());
        if (!StringUtils.isEmpty(initVal)) {
            code.append(KeywordConstant.BLANK_SPACE).append(KeywordConstant.EQUALS).append(KeywordConstant.BLANK_SPACE)
                    .append(initVal);
        }
        if (StringUtils.isEmpty(initVal) || !initVal.endsWith(KeywordConstant.SEMICOLON)) {
            code.append(KeywordConstant.SEMICOLON);
        }
        return code.toString();
    }
}
