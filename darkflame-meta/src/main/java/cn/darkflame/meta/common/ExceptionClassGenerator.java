package cn.darkflame.meta.common;

import cn.darkflame.common.constant.ClassNameConstant;
import cn.darkflame.common.constant.KeywordConstant;
import cn.darkflame.common.util.Counter;
import cn.darkflame.common.util.PathUtils;
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
import java.util.*;

/**
 * @author james
 */
@Component("exceptionClassGenerator")
public class ExceptionClassGenerator {

    @Resource
    private IGenerator javaCodeGenerator;

    public void generateExceptionClass(ExceptionClassDO exceptionClass) throws IOException {
        // 处理异常类继承信息 与 工作路径
        Boolean runtimeException = Optional.ofNullable(exceptionClass.isRuntimeException()).orElse(false);
        String workPath = PathUtils.getPath(exceptionClass.getWorkPath());

        // 处理类信息
        Class cls = new Class();
        cls.setModifiers(Modifier.PUBLIC);
        cls.setName(exceptionClass.getClassName());
        cls.setExtendsName(runtimeException ? ClassNameConstant.BASE_RUNTIME_EXCEPTION : ClassNameConstant.BASE_EXCEPTION);

        // 处理提示信息
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
                    field.setInitVal(KeywordConstant.DOUBLE_QUOTATION + key + KeywordConstant.DOUBLE_QUOTATION);
                    fields.add(field);
                }
                resource.append(key).append(KeywordConstant.EQUALS).append(KeywordConstant.DOUBLE_QUOTATION)
                        .append(messageInfo).append(KeywordConstant.DOUBLE_QUOTATION).append(KeywordConstant.NEW_LINE);
            });
            resources.put(loc, resource);
            counter.countDown();
        });

        // 处理构造器
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

        // 写入文件 java 与 properties
        String javaFileContent = javaCodeGenerator.genCode(cls, fields, methods);
        String srcPath = PathUtils.getPath(workPath, exceptionClass.getSrcPath(), cls.getClassPath());
        File javaFile = new File(srcPath);
        FileUtils.writeStringToFile(javaFile, javaFileContent, StandardCharsets.UTF_8);
        for (String loc : resources.keySet()) {
            StringBuffer msg = resources.get(loc);
            String resourcePath = PathUtils.getPath(workPath, exceptionClass.getResourcePath(), cls.getResourcePath(loc));
            File resourceFile = new File(resourcePath);
            FileUtils.writeStringToFile(resourceFile, msg.toString(), StandardCharsets.UTF_8);
        }
    }
}
