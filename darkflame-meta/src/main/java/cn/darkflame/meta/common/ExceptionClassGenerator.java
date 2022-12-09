package cn.darkflame.meta.common;

import cn.darkflame.common.constant.ClassNameConstant;
import cn.darkflame.common.constant.KeywordConstant;
import cn.darkflame.common.util.Counter;
import cn.darkflame.common.util.ObjectUtils;
import cn.darkflame.meta.code.generator.IGenerator;
import cn.darkflame.meta.code.model.Class;
import cn.darkflame.meta.code.model.Field;
import cn.darkflame.meta.code.model.Method;
import cn.darkflame.model.meta.exception.ExceptionClassDO;
import jakarta.annotation.Resource;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author james
 */
@Component("exceptionClassGenerator")
public class ExceptionClassGenerator {

    @Resource
    private IGenerator javaCodeGenerator;

    public void generateExceptionClass(ExceptionClassDO exceptionClass) throws IOException {
        ObjectUtils.notNull(exceptionClass, "exceptionClass");
        ObjectUtils.notNull(exceptionClass.getClassName(), "className");
        boolean runtimeException = exceptionClass.isRuntimeException();
        String workPath = exceptionClass.getWorkPath().replace(KeywordConstant.BACK_SLASH, KeywordConstant.FORWARD_SLASH);
        workPath = workPath.endsWith(KeywordConstant.FORWARD_SLASH) ? workPath : workPath + KeywordConstant.FORWARD_SLASH;

        Class cls = new Class();
        cls.setModifiers(Modifier.PUBLIC);
        cls.setName(exceptionClass.getClassName());
        cls.setExtendsName(runtimeException ? ClassNameConstant.BASE_RUNTIME_EXCEPTION : ClassNameConstant.BASE_EXCEPTION);
        Map<String, Map<String, String>> message = exceptionClass.getMessage();
        List<Field> fields = new ArrayList<>();
        int mod = Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL;
        Counter counter = new Counter(1);
        Map<String, StringBuffer> resources = new HashMap<>();
        message.forEach((loc, messageMap) -> {
            StringBuffer resource = new StringBuffer();
            messageMap.forEach((key, messageInfo) -> {
                if (!counter.isZero()) {
                    Field field = new Field();
                    field.setModifiers(mod);
                    field.setName(key);
                    field.setType(ClassNameConstant.STRING);
                    field.setInitVal("\"" + key + "\"");
                    cls.addField(field);
                }
                resource.append(key).append(KeywordConstant.EQUALS).append(KeywordConstant.DOUBLE_QUOTATION)
                        .append(messageInfo).append(KeywordConstant.DOUBLE_QUOTATION).append(KeywordConstant.NEW_LINE);
            });
            resources.put(loc, resource);
            counter.countDown();
        });
        cls.setFields(fields);

        List<Method> methods = new ArrayList<>();
        Method twoArgsConstructor = new Method();
        twoArgsConstructor.setModifiers(Modifier.PUBLIC);
        twoArgsConstructor.setName(cls.getName());
        twoArgsConstructor.addParam(KeywordConstant.STRING, "message");
        twoArgsConstructor.addParam(KeywordConstant.DYN_ARGS_STRING, "params");
        twoArgsConstructor.setBody("super(message, params);");
        Method threeArgsConstructor = new Method();
        threeArgsConstructor.setModifiers(Modifier.PUBLIC);
        threeArgsConstructor.setName(cls.getName());
        threeArgsConstructor.addParam(KeywordConstant.STRING, "message");
        threeArgsConstructor.addParam(KeywordConstant.THROWABLE, "t");
        threeArgsConstructor.addParam(KeywordConstant.DYN_ARGS_STRING, "params");
        threeArgsConstructor.setBody("super(message, t, params);");
        methods.add(twoArgsConstructor);
        methods.add(threeArgsConstructor);

        String javaCode = javaCodeGenerator.genCode(cls, fields, methods);
        System.out.println(javaCode);
        String srcPath = workPath + exceptionClass.getSrcPath() + KeywordConstant.FORWARD_SLASH + cls.getClassPath();
        File javaFile = new File(srcPath);
        FileUtils.writeStringToFile(javaFile, javaCode, StandardCharsets.UTF_8);
        for (String loc : resources.keySet()) {
            StringBuffer msg = resources.get(loc);
            System.out.println(msg);
            String resourcePath = workPath + exceptionClass.getResourcePath() + KeywordConstant.FORWARD_SLASH + cls.getResourcePath(loc);
            File resourceFile = new File(resourcePath);
            FileUtils.writeStringToFile(resourceFile, msg.toString(), StandardCharsets.UTF_8);
        }
    }
}
