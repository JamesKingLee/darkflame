package cn.darkflame.common.exception;


import cn.darkflame.common.resource.IResource;
import cn.darkflame.common.resource.StringResourcePool;

/**
 * @author james
 */
public class BaseRuntimeException extends RuntimeException {

    private final String[] params;

    public BaseRuntimeException(String msg, String... params) {
        super(msg);
        this.params = params;
    }

    public BaseRuntimeException(String msg, Throwable t, String... params) {
        super(msg, t);
        this.params = params;
    }

    @Override
    public String getMessage() {
        IResource<String, String> res = StringResourcePool.getInstance().getResourceBundle(this.getClass().getName());
        String key = super.getMessage();
        return res.getResource(key, (Object[]) params);
    }
}
