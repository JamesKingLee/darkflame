package cn.darkflame.model.meta.exception;

import cn.darkflame.common.constant.KeywordConstant;
import cn.darkflame.common.util.ObjectUtils;
import com.google.gson.Gson;

import java.util.Map;

/**
 * @author james
 */
@SuppressWarnings("unused")
public class ExceptionClassDO {

    private String workPath;

    private String srcPath;

    private String resourcePath;

    private String className;

    private Boolean runtimeException = false;

    /**
     * key - 地区信息 eg. zh_CN
     * value - 提示具体信息
     * key - 提示信息编码
     * value - 提示具体信息
     */
    private Map<String, Map<String, String>> message;

    public ExceptionClassDO() {
    }

    public ExceptionClassDO(String workPath, String srcPath, String resourcePath, String className, Boolean runtimeException, Map<String, Map<String, String>> message) {
        this.workPath = workPath;
        this.srcPath = srcPath;
        this.resourcePath = resourcePath;
        this.className = className;
        this.runtimeException = runtimeException == null ? false : runtimeException;
        this.message = message;
    }

    public String getWorkPath() {
        return workPath;
    }

    public void setWorkPath(String workPath) {
        this.workPath = workPath;
    }

    public String getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(String srcPath) {
        ObjectUtils.notNull(srcPath, "resourcePath");
        srcPath = srcPath.replace(KeywordConstant.BACK_SLASH, KeywordConstant.FORWARD_SLASH);
        if (srcPath.endsWith(KeywordConstant.FORWARD_SLASH)) {
            srcPath = srcPath.substring(0, srcPath.length() - 1);
        }
        this.srcPath = srcPath;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        ObjectUtils.notNull(resourcePath, "resourcePath");
        resourcePath = resourcePath.replace(KeywordConstant.BACK_SLASH, KeywordConstant.FORWARD_SLASH);
        if (resourcePath.endsWith(KeywordConstant.FORWARD_SLASH)) {
            resourcePath = resourcePath.substring(0, resourcePath.length() - 1);
        }
        this.resourcePath = resourcePath;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Boolean isRuntimeException() {
        return runtimeException;
    }

    public void setRuntimeException(Boolean runtimeException) {
        this.runtimeException = runtimeException == null ? false : runtimeException;
    }

    public Map<String, Map<String, String>> getMessage() {
        return message;
    }

    public void setMessage(Map<String, Map<String, String>> message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
