package cn.darkflame.common.exception;

/**
 * @author james
 */
@SuppressWarnings("unused")
public class NotNullException extends BaseRuntimeException {

    public static final String ARRAY_NULL_MSG = "ARRAY_NULL_MSG";

    public static final String OBJ_NULL_MSG = "OBJ_NULL_MSG";

    public NotNullException(String msg, String... params) {
        super(msg, params);
    }

    public NotNullException(String msg, Throwable rootCase, String... params) {
        super(msg, rootCase, params);
    }

}
