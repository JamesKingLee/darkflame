package cn.darkflame.meta.code.generator;

import cn.darkflame.meta.code.model.Class;
import cn.darkflame.meta.code.model.Field;
import cn.darkflame.meta.code.model.Method;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("unused")
@Component("javaCodeGenerator")
public class JavaCodeGenerator implements IGenerator {

    private static final int ROOT_LEVEL = 0;

    @Override
    public String genCode(Class clazz, List<Field> fields, List<Method> methods) {
        clazz.setFields(fields);
        clazz.setMethods(methods);
        return clazz.getCode(ROOT_LEVEL);
    }
}
