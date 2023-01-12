package cn.darkflame.model.meta.exception;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Map;

/**
 * @author james
 */
@SuppressWarnings("unused")
public class ExceptionClassDO implements Serializable {

    private String workPath;

    private String srcPath;

    private String resourcePath;

    private String className;

    private Boolean runtimeException = false;

    /**
     * <p>key - 地区信息 eg. zh_CN</p>
     * <p>value - 提示具体信息</p>
     * <p>  --key - 提示信息编码</p>
     * <p>  --value - 提示具体信息</p>
     */
    private Map<String, Map<String, String>> message;

    public ExceptionClassDO() {
    }

    public ExceptionClassDO(String workPath, String srcPath, String resourcePath, String className,
                            Boolean runtimeException, Map<String, Map<String, String>> message) {
        this.workPath = workPath;
        this.srcPath = srcPath;
        this.resourcePath = resourcePath;
        this.className = className;
        this.runtimeException = runtimeException;
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
        this.srcPath = srcPath;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
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
        this.runtimeException = runtimeException;
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
