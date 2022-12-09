package cn.darkflame.meta.code.generator;

import cn.darkflame.meta.code.model.Class;
import cn.darkflame.meta.code.model.Field;
import cn.darkflame.meta.code.model.Method;

import java.util.List;

public interface IGenerator {

    public String genCode(Class clazz, List<Field> fields, List<Method> methods);

}
