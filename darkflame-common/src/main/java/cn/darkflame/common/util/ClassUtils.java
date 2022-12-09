package cn.darkflame.common.util;

import cn.darkflame.common.exception.ClassException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * 反射调用工具类
 * 提供实例、成员变量、成员方法、类方法相关操作方法
 *
 * @author james
 */
@SuppressWarnings("unused")
public class ClassUtils {

    public static final Class<Null> NULL_CLASS = Null.class;

    /**
     * 根据{@link Class}获取对应的类实例
     *
     * @param clazz      需要创建实例的{@link Class} - <b>不可为空</b>
     * @param paramTypes 有参构造时的参数类型数组，排序按照构造器定义顺序
     * @param params     有参构造时的参数数组
     * @param <T>        实例泛型
     * @return 创建好的实例
     */
    public static <T> T getInstance(Class<T> clazz, Class<?>[] paramTypes, Object... params) {
        ObjectUtils.notNull(clazz, "class");
        T result;
        Constructor<T> constructor = getConstructor(clazz, paramTypes);
        try {
            result = constructor.newInstance(params);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new ClassException(ClassException.NO_NEW, e, clazz.getName());
        }
        return result;
    }

    /**
     * 根据{@link Class}获取构造器
     *
     * @param clazz      {@link Class}实例 - <b>不可为空</b>
     * @param paramTypes 有参构造时，参数类型数组，排序按照构造器定义顺序
     * @param <T>        实例泛型
     * @return 构造器实例
     */
    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>[] paramTypes) {
        ObjectUtils.notNull(clazz, "class");
        try {
            return ObjectUtils.isNull(paramTypes) ? clazz.getDeclaredConstructor() : clazz.getDeclaredConstructor(paramTypes);
        } catch (NoSuchMethodException e) {
            if (ObjectUtils.isNull(paramTypes)) {
                throw new ClassException(ClassException.NO_CONSTRUCTOR, e, clazz.getName());
            } else {
                throw new ClassException(ClassException.NO_CONSTRUCTOR_PARAM, e, clazz.getName(), Arrays.toString(paramTypes));
            }
        }
    }

    /**
     * 根据{@link Class}获取构造器实例集合
     *
     * @param clazz {@link Class}实例 - <b>不可为空</b>
     * @param <T>   实例泛型
     * @return 构造器实例集合
     */
    public static <T> Constructor<?>[] getConstructors(Class<T> clazz) {
        ObjectUtils.notNull(clazz, "class");
        return clazz.getConstructors();
    }

    /**
     * 根据{@link Class}与方法名与方法参数类型集合，获取相应方法实例
     *
     * @param clazz      {@link Class}实例 - <b>不可为空</b>
     * @param methodName 方法名称
     * @param paramTypes 方法参数类型
     * @return 方法实例
     */
    public static Method getMethod(Class<?> clazz, String methodName, Class<?>[] paramTypes) {
        ObjectUtils.notNull(clazz, "class");
        try {
            Method result;
            if (paramTypes == null || ObjectUtils.isNull(paramTypes)) {
                result = clazz.getDeclaredMethod(methodName);
            } else {
                result = clazz.getDeclaredMethod(methodName, paramTypes);
            }
            return result;
        } catch (NoSuchMethodException e) {
            throw new ClassException(ClassException.NO_METHOD, e, clazz.getSimpleName(), methodName);
        }
    }

    /**
     * 根据{@link Class}与方法名，获取相应方法实例集合
     *
     * @param clazz      {@link Class}实例 - <b>不可为空</b>
     * @param methodName 方法名称
     * @return 方法实例集合
     */
    public static Method[] getMethods(Class<?> clazz, String methodName) {
        ObjectUtils.notNull(clazz, "class");
        List<Method> methodList = Arrays
                .stream(clazz.getDeclaredMethods())
                .filter(method -> methodName.equals(method.getName()))
                .toList();
        return methodList.toArray(new Method[0]);
    }

    /**
     * 根据实例与方法实例与方法参数值集合，执行对应方法
     *
     * @param instance 实例
     * @param method   方法实例
     * @param params   方法参数值集合
     * @return 方法返回值或方法抛出的异常
     */
    public static Object invokeMethod(Object instance, Method method, Object... params) {
        return invokeMethod(true, instance, method, params);
    }

    /**
     * 根据实例与方法实例与方法参数值集合，执行对应方法
     *
     * @param isThrow  抛出或返回异常 <p><code>true</code> 抛出异常</p> <p><code>false</code> 返回异常</p>
     * @param instance 实例
     * @param method   方法实例
     * @param params   方法参数值集合
     * @return 方法返回值或方法抛出的异常
     */
    public static Object invokeMethod(boolean isThrow, Object instance, Method method, Object... params) {
        ObjectUtils.notNull(instance, "instance");
        ObjectUtils.notNull(method, "method");
        boolean accessFlag = method.canAccess(instance);
        try {
            if (!accessFlag) {
                method.setAccessible(true);
            }
            return method.invoke(instance, params);
        } catch (IllegalAccessException | InvocationTargetException e) {
            if (isThrow) {
                throw new ClassException(ClassException.CALL_METHOD, e, method.getName(), e.getMessage());
            }
            return e;
        } finally {
            method.setAccessible(accessFlag);
        }
    }

    /**
     * 根据实例与方法实例集合与方法参数值集合的集合，按顺序执行对应方法集合中每个方法
     *
     * @param instance       实例
     * @param methods        方法实例集合
     * @param paramObjsArray 方法参数值集合的集合
     * @return 按顺序执行对应方法的返回值或抛出的异常的集合
     */
    public static Object[] invokeMethods(Object instance, Method[] methods, Object[]... paramObjsArray) {
        ObjectUtils.notNull(instance, "instance");
        ObjectUtils.notNull(methods, "methods");
        if (paramObjsArray != null && methods.length != paramObjsArray.length) {
            throw new ClassException(ClassException.METHOD_NUM_NEQ_PARAM_NUM);
        }
        Object[] res = new Object[methods.length];
        if (paramObjsArray == null) {
            for (int i = 0; i < methods.length; i++) {
                res[i] = invokeMethod(false, instance, methods[i]);
            }
        } else {
            for (int i = 0; i < methods.length; i++) {
                res[i] = invokeMethod(false, instance, methods[i], paramObjsArray[i]);
            }
        }
        return res;
    }

    /**
     * 根据实例集合与方法实例集合与方法参数值集合的集合，按顺序执行对应方法集合中每个方法
     *
     * @param instances      实例集合
     * @param methods        方法实例集合
     * @param paramObjsArray 方法参数值集合的集合
     * @return 按顺序执行对应方法的返回值或抛出的异常的集合
     */
    public static Object[] invokeMethods(Object[] instances, Method[] methods, Object[]... paramObjsArray) {
        ObjectUtils.notNull(instances, "instances");
        ObjectUtils.notNull(methods, "methods");
        if (paramObjsArray != null && methods.length != paramObjsArray.length) {
            throw new ClassException(ClassException.METHOD_NUM_NEQ_PARAM_NUM);
        }
        Object[] res = new Object[methods.length];
        if (paramObjsArray == null) {
            for (int i = 0; i < methods.length; i++) {
                res[i] = invokeMethod(false, instances[i], methods[i]);
            }
        } else {
            for (int i = 0; i < methods.length; i++) {
                res[i] = invokeMethod(false, instances[i], methods[i], paramObjsArray[i]);
            }
        }
        return res;
    }

    /**
     * 根据实例与方法名称集合与方法参数值集合的集合，按顺序执行对应方法集合中每个方法
     *
     * @param instance       实例
     * @param methodNames    方法名称集合
     * @param paramObjsArray 方法参数值集合的集合
     * @return 按顺序执行对应方法的返回值或抛出的异常的集合
     */
    public static Object[] invokeMethods(Object instance, String[] methodNames, Object[]... paramObjsArray) {
        ObjectUtils.notNull(instance, "instance");
        ObjectUtils.notNull(methodNames, "methodNames");
        if (paramObjsArray != null && methodNames.length != paramObjsArray.length) {
            throw new ClassException(ClassException.METHOD_NUM_NEQ_PARAM_NUM);
        }
        Object[] res = new Object[methodNames.length];
        Class<?> instanceClass = instance.getClass();
        Method method;
        if (paramObjsArray == null) {
            for (int i = 0; i < methodNames.length; i++) {
                method = getMethod(instanceClass, methodNames[i], null);
                res[i] = invokeMethod(false, instance, method);
            }
        } else {
            for (int i = 0; i < methodNames.length; i++) {
                method = getMethod(instanceClass, methodNames[i], getObjectsClasses(paramObjsArray[i]));
                res[i] = invokeMethod(false, instance, method, paramObjsArray[i]);
            }
        }
        return res;
    }

    /**
     * 根据实例集合与方法名称与方法参数值集合的集合，按顺序执行对应实例集合中方法
     *
     * @param instances      实例集合
     * @param methodName     方法名称
     * @param paramObjsArray 方法参数值集合的集合
     * @return 按顺序执行对应实例的方法的返回值或抛出的异常的集合
     */
    public static Object[] invokeMethods(Object[] instances, String methodName, Object[]... paramObjsArray) {
        ObjectUtils.notNull(instances, "instances");
        ObjectUtils.notNull(methodName, "methodName");
        if (paramObjsArray != null && instances.length != paramObjsArray.length) {
            throw new ClassException(ClassException.INSTANCE_NUM_NEQ_PARAM_NUM);
        }
        Object[] res = new Object[instances.length];
        Class<?> instanceClass;
        Method method;
        if (paramObjsArray == null) {
            for (int i = 0; i < instances.length; i++) {
                instanceClass = instances[i].getClass();
                method = getMethod(instanceClass, methodName, null);
                res[i] = invokeMethod(false, instances[i], method);
            }
        } else {
            for (int i = 0; i < instances.length; i++) {
                instanceClass = instances[i].getClass();
                method = getMethod(instanceClass, methodName, getObjectsClasses(paramObjsArray[i]));
                res[i] = invokeMethod(false, instances[i], method, paramObjsArray[i]);
            }
        }
        return res;
    }

    /**
     * 根据实例集合与方法名称集合与方法参数值集合的集合，按顺序执行对应方法集合中每个方法
     *
     * @param instances      实例集合
     * @param methodNames    方法名称集合
     * @param paramObjsArray 方法参数值集合的集合
     * @return 按顺序执行对应方法的返回值或抛出的异常
     */
    public static Object[] invokeMethods(Object[] instances, String[] methodNames, Object[]... paramObjsArray) {
        ObjectUtils.notNull(instances, "instances");
        ObjectUtils.notNull(methodNames, "methodNames");
        if (paramObjsArray != null && methodNames.length != paramObjsArray.length) {
            throw new ClassException(ClassException.METHOD_NUM_NEQ_PARAM_NUM);
        }
        Object[] res = new Object[methodNames.length];
        Class<?> instanceClass;
        Method method;
        if (paramObjsArray == null) {
            for (int i = 0; i < methodNames.length; i++) {
                instanceClass = instances[i].getClass();
                method = getMethod(instanceClass, methodNames[i], null);
                res[i] = invokeMethod(false, instances[i], method);
            }
        } else {
            for (int i = 0; i < methodNames.length; i++) {
                instanceClass = instances[i].getClass();
                method = getMethod(instanceClass, methodNames[i], getObjectsClasses(paramObjsArray[i]));
                res[i] = invokeMethod(false, instances[i], method, paramObjsArray[i]);
            }
        }
        return res;
    }

    /**
     * 根据{@link Class}与成员变量名称获取成员变量实例
     *
     * @param clazz     {@link Class}实例
     * @param fieldName 成员变量名称
     * @return clazz 的 名称为 fieldName 成员变量实例
     */
    public static Field getField(Class<?> clazz, String fieldName) {
        ObjectUtils.notNull(clazz, "Class");
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new ClassException(ClassException.NO_FIELD, e, clazz.getSimpleName(), fieldName);
        }
    }

    /**
     * 根据{@link Class}获取所有成员变量实例
     *
     * @param clazz {@link Class}实例
     * @return clazz 的所有成员变量实例集合
     */
    public static Field[] getFields(Class<?> clazz) {
        ObjectUtils.notNull(clazz, "class");
        return clazz.getDeclaredFields();
    }

    /**
     * 根据实例与成员变量获取成员变量值
     *
     * @param instance 实例
     * @param field    成员变量
     * @return 成员变量值集合，获取中产生异常将会返回异常
     */
    public static Object getFieldValue(Object instance, Field field) {
        ObjectUtils.notNull(instance, "instance");
        ObjectUtils.notNull(field, "field");
        boolean accessFlag = field.canAccess(instance);
        if (!accessFlag) {
            field.setAccessible(true);
        }
        try {
            return field.get(instance);
        } catch (IllegalAccessException e) {
            return e;
        } finally {
            field.setAccessible(accessFlag);
        }
    }

    /**
     * 根据实例与成员变量获取成员变量值
     *
     * @param instance  实例
     * @param fieldName 成员变量名称
     * @return 成员变量值集合，获取中产生异常将会返回异常
     */
    public static Object getFieldValue(Object instance, String fieldName) {
        ObjectUtils.notNull(instance, "instance");
        ObjectUtils.notNull(fieldName, "fieldName");
        Field field = getField(instance.getClass(), fieldName);
        return getFieldValue(instance, field);
    }

    /**
     * 根据实例与成员变量集合获取成员变量值集合，适合单实例，同一实例中不同成员变量获取值操作
     *
     * @param instance 实例
     * @param fields   成员变量集合
     * @return 成员变量值集合，获取中产生异常将会返回异常，排序按照成员变量顺序排列
     */
    public static Object[] getFieldValues(Object instance, Field[] fields) {
        ObjectUtils.notNull(instance, "instance");
        ObjectUtils.notNull(fields, "fields");
        Object[] fieldValues = new Object[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldValues[i] = getFieldValue(instance, fields[i]);
        }
        return fieldValues;
    }

    /**
     * 根据实例与成员变量名称集合获取成员变量值集合，适合单实例，同一实例不同成员变量获取值操作
     *
     * @param instance   实例
     * @param fieldNames 成员变量名称集合
     * @return 成员变量值集合，获取中产生异常将会返回异常，排序按照成员变量名称顺序排列
     */
    public static Object[] getFieldValues(Object instance, String[] fieldNames) {
        ObjectUtils.notNull(instance, "instance");
        ObjectUtils.notNull(fieldNames, "fieldNames");
        Object[] fieldValues = new Object[fieldNames.length];
        Class<?> clazz = instance.getClass();
        for (int i = 0; i < fieldNames.length; i++) {
            fieldValues[i] = getFieldValue(instance, getField(clazz, fieldNames[i]));
        }
        return fieldValues;
    }

    /**
     * 根据实例集合与成员变量名称获取成员变量值，适合多实例，不同实例相同成员变量名称获取值操作
     *
     * @param instances 实例集合
     * @param fieldName 成员变量名称
     * @return 成员变量值集合，获取中产生异常将会返回异常，排序按照实例顺序排列
     */
    public static Object[] getFieldValues(Object[] instances, String fieldName) {
        ObjectUtils.notNull(instances, "instances");
        ObjectUtils.notNull(fieldName, "fieldName");
        Object[] fieldValues = new Object[instances.length];
        Class<?> clazz = instances.getClass();
        for (int i = 0; i < instances.length; i++) {
            fieldValues[i] = getFieldValue(instances[i], fieldName);
        }
        return fieldValues;
    }

    /**
     * 根据实例集合与成员变量集合获取成员变量值集合，适合多实例，不同实例不同成员变量获取值操作
     *
     * @param instances 实例集合
     * @param fields    成员变量集合
     * @return 成员变量值集合，获取中产生异常将会返回异常，排序按照实例顺序排列
     */
    public static Object[] getFieldValues(Object[] instances, Field[] fields) {
        ObjectUtils.notNull(instances, "instances");
        ObjectUtils.notNull(fields, "fields");
        Object[] fieldValues = new Object[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldValues[i] = getFieldValue(instances[i], fields[i]);
        }
        return fieldValues;
    }

    /**
     * 根据实例集合与成员变量名称集合获取成员变量值集合，适合多实例，不同实例不同成员变量获取值操作
     *
     * @param instances  实例集合
     * @param fieldNames 成员变量名称集合
     * @return 成员变量值集合，获取中产生异常将会返回异常，排序按照实例顺序排列
     */
    public static Object[] getFieldValues(Object[] instances, String[] fieldNames) {
        ObjectUtils.notNull(instances, "instances");
        ObjectUtils.notNull(fieldNames, "fieldNames");
        Object[] fieldValues = new Object[fieldNames.length];
        for (int i = 0; i < fieldNames.length; i++) {
            fieldValues[i] = getFieldValue(instances[i], fieldNames[i]);
        }
        return fieldValues;
    }

    /**
     * 根据实例集合，获取实例对应的{@link Class}对象集合，元素为空时对应{@link Class}的值为<code>{@link ClassUtils#NULL_CLASS}</code>
     *
     * @param objects 实例对象
     * @return {@link Class}对象集合
     */
    public static Class<?>[] getObjectsClasses(Object... objects) {
        ObjectUtils.notNull(objects, "objects");
        return Arrays
                .stream(objects)
                .map(o -> ObjectUtils.isNull(o) ? NULL_CLASS : o.getClass())
                .toList()
                .toArray(new Class<?>[0]);
    }

    /**
     * 对象是否为异常对象
     *
     * @param o 待判断对象
     * @return <p><code>true</code> <code>o</code>是异常对象</p>
     * <p><code>false</code> <code>o</code>不是异常对象</p>
     */
    public static boolean isException(Object o) {
        return o instanceof Exception;
    }

}
