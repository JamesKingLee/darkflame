package cn.darkflame.common.exception;

/**
 * @author james
 */
@SuppressWarnings("unused")
public class ClassException extends BaseRuntimeException {

    public static final String NO_CLASS = "NO_CLASS";

    public static final String NO_CONSTRUCTOR = "NO_CONSTRUCTOR";

    public static final String NO_CONSTRUCTOR_PARAM = "NO_CONSTRUCTOR_PARAM";

    public static final String NO_FIELD = "NO_FIELD";

    public static final String NO_METHOD = "NO_METHOD";

    public static final String NO_NEW = "NO_NEW";

    public static final String CALL_METHOD = "CALL_METHOD";

    public static final String METHOD_NUM_NEQ_PARAM_NUM = "METHOD_NUM_NEQ_PARAM_NUM";

    public static final String INSTANCE_NUM_NEQ_PARAM_NUM = "INSTANCE_NUM_NEQ_PARAM_NUM";

    public static final String CANNOT_COMPILE = "CANNOT_COMPILE";

    public ClassException(String msg, String... params) {
        super(msg, params);
    }

    public ClassException(String msg, Throwable rootCase, String... params) {
        super(msg, rootCase, params);
    }

}
