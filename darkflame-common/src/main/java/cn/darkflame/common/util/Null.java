package cn.darkflame.common.util;

import java.io.Serializable;

/**
 * 空类型
 *
 * @author james
 */
@SuppressWarnings("unused")
public class Null implements Serializable {

    private static final Null INSTANCE = new Null();

    private Null() {
    }

    public static Null getInstance() {
        return INSTANCE;
    }
}
