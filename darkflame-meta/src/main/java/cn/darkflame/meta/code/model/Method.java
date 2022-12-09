package cn.darkflame.meta.code.model;

import cn.darkflame.common.constant.KeywordConstant;
import cn.darkflame.common.util.CollectionUtils;
import cn.darkflame.common.util.ObjectUtils;
import cn.darkflame.common.util.StringUtils;

import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author james
 */
@SuppressWarnings("unused")
public class Method extends CommonMember {

    private final List<Param> params = new ArrayList<>();

    private String returnType;

    private final List<String> throwsType = new ArrayList<>();

    private String body;

    public List<Param> getParams() {
        return params;
    }

    public void addParam(String type, String paramName) {
        ObjectUtils.notNull(type, "type");
        Param param = new Param(type, paramName);
        boolean isDynArgs = param.isDynArgs();
        if (isDynArgs) {
            type = type.substring(0, type.length() - 3);
        }
        if (type.contains(KeywordConstant.FULL_STOP)) {
            addImport(type);
            type = type.substring(type.lastIndexOf(KeywordConstant.FULL_STOP) + 1);
        }
        if (isDynArgs) {
            String dynArgsKey = String.join(KeywordConstant.EMPTY, Collections.nCopies(3, KeywordConstant.FULL_STOP));
            type += dynArgsKey;
        }
        param = new Param(type, paramName);
        this.params.add(param);
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        if (returnType.contains(KeywordConstant.FULL_STOP)) {
            addImport(returnType);
            returnType = returnType.substring(returnType.lastIndexOf(KeywordConstant.FULL_STOP) + 1);
        }
        this.returnType = returnType;
    }

    public List<String> getThrowsType() {
        return throwsType;
    }

    public void addThrowType(String throwType) {
        if (throwType.contains(KeywordConstant.FULL_STOP)) {
            addImport(throwType);
            throwType = throwType.substring(throwType.lastIndexOf(KeywordConstant.FULL_STOP) + 1);
        }
        this.throwsType.add(throwType);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body == null ? "" : body;
    }

    public String getBodyCode(int level) {
        StringBuilder code = new StringBuilder();
        body = body.trim();
        if (!StringUtils.isEmpty(body)) {
            if (body.startsWith(KeywordConstant.LEFT_BRACKETS) && body.endsWith(KeywordConstant.RIGHT_BRACKETS)) {
                body = body.substring(1, body.length() - 1);
            }
            String indentation = String.join(KeywordConstant.EMPTY, Collections.nCopies(level, KeywordConstant.TAB));
            String[] split = body.split(KeywordConstant.REGEX_NEW_LINE);
            for (int i = 0, len = split.length; i < len; i++) {
                if (i != len - 1) {
                    code.append(indentation).append(split[i]).append(KeywordConstant.NEW_LINE);
                } else {
                    code.append(indentation).append(split[i]);
                }
            }
        }
        return code.toString();
    }

    public String getCode(int level) {
        StringBuilder code = new StringBuilder();
        String indentation = String.join(KeywordConstant.EMPTY, Collections.nCopies(level, KeywordConstant.TAB));
        code.append(getDocCode(level)).append(KeywordConstant.NEW_LINE);
        String annotationCode = getAnnotationCode(level);
        if (!StringUtils.isEmpty(annotationCode)) {
            code.append(annotationCode).append(KeywordConstant.NEW_LINE);
        }
        code.append(indentation).append(Modifier.toString(getModifiers())).append(KeywordConstant.BLANK_SPACE);
        if (!StringUtils.isEmpty(returnType)) {
            code.append(returnType).append(KeywordConstant.BLANK_SPACE);
        }
        code.append(getName());

        if (params.isEmpty()) {
            code.append(KeywordConstant.LEFT_RIGHT_PARENTHESES).append(KeywordConstant.BLANK_SPACE);
        } else {
            code.append(KeywordConstant.LEFT_PARENTHESES);
            int paramSize = params.size();
            int count = 0;
            for (Param param : params) {
                if (count != paramSize - 1) {
                    code.append(param.getType()).append(KeywordConstant.BLANK_SPACE).append(param.getName()).append(KeywordConstant.COMMA)
                            .append(KeywordConstant.BLANK_SPACE);
                } else {
                    code.append(param.getType()).append(KeywordConstant.BLANK_SPACE).append(param.getName());
                }
                count++;
            }
            code.append(KeywordConstant.RIGHT_PARENTHESES).append(KeywordConstant.BLANK_SPACE);
        }
        if (!CollectionUtils.isEmpty(throwsType)) {
            code.append(KeywordConstant.THROWS).append(KeywordConstant.BLANK_SPACE);
            int throwSize = throwsType.size();
            int count = 0;
            for (String throwType : throwsType) {
                if (count != throwSize - 1) {
                    code.append(throwType).append(KeywordConstant.COMMA).append(KeywordConstant.BLANK_SPACE);
                } else {
                    code.append(throwType).append(KeywordConstant.BLANK_SPACE);
                }
                count++;
            }
        }
        if (StringUtils.isEmpty(body)) {
            code.append(KeywordConstant.LEFT_RIGHT_BRACKETS);
        } else {
            code.append(KeywordConstant.LEFT_BRACKETS);
            String bodyCode = getBodyCode(level + 1);
            if (!StringUtils.isEmpty(bodyCode)) {
                code.append(KeywordConstant.NEW_LINE).append(bodyCode).append(KeywordConstant.NEW_LINE);
            }
            code.append(indentation).append(KeywordConstant.RIGHT_BRACKETS);
        }
        return code.toString();
    }
}
