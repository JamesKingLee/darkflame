package cn.darkflame.meta.code.model;

import cn.darkflame.common.constant.KeywordConstant;
import cn.darkflame.common.util.CollectionUtils;
import cn.darkflame.common.util.ObjectUtils;
import cn.darkflame.common.util.StringUtils;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author james
 */
@SuppressWarnings("unused")
public class Class extends CommonMember {

    private String packageName;

    private final List<Field> fields = new ArrayList<>();

    private final List<Method> methods = new ArrayList<>();

    private String extendsName;

    private List<String> implementsList;

    /**
     * <p>默认值 {@link Class#TYPE_CLASS}</p>
     * <p>{@link Class#TYPE_CLASS} - class</p>
     * <p>{@link Class#TYPE_INTERFACE} - interface</p>
     * <p>{@link Class#TYPE_ENUM} - enum</p>
     * <p>{@link Class#TYPE_RECORD} - record</p>
     */
    private byte type = TYPE_CLASS;

    public static final byte TYPE_CLASS = 0;

    public static final byte TYPE_INTERFACE = 1;

    public static final byte TYPE_ENUM = 2;

    public static final byte TYPE_RECORD = 3;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        fields.forEach(this::addField);
    }

    @Override
    public void setName(String name) {
        if (name.contains(KeywordConstant.FULL_STOP)) {
            String packageName = name.substring(0, name.lastIndexOf(KeywordConstant.FULL_STOP));
            setPackageName(packageName);
            name = name.substring(name.lastIndexOf(KeywordConstant.FULL_STOP) + 1);
        }
        super.setName(name);
    }

    public void addField(Field field) {
        if (!ObjectUtils.isNull(field)) {
            this.fields.add(field);
            getImports().addAll(field.getImports());
        }
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        if (!CollectionUtils.isEmpty(methods)) {
            methods.forEach(this::addMethod);
        }
    }

    public void addMethod(Method method) {
        if (!ObjectUtils.isNull(method)) {
            this.methods.add(method);
            getImports().addAll(method.getImports());
        }
    }

    public String getExtendsName() {
        return extendsName;
    }

    public void setExtendsName(String extendsName) {
        if (extendsName.contains(KeywordConstant.FULL_STOP)) {
            addImport(extendsName);
            extendsName = extendsName.substring(extendsName.lastIndexOf(KeywordConstant.FULL_STOP) + 1);
        }
        this.extendsName = extendsName;
    }

    public List<String> getImplementsList() {
        return implementsList;
    }

    public void setImplementsList(List<String> implementsList) {
        this.implementsList = implementsList;
        implementsList.forEach(implementsItem -> {
            if (implementsItem.contains(KeywordConstant.FULL_STOP)) {
                addImport(implementsItem);
            }
        });
    }

    public void setType(byte type) {
        if (type >= 0 && type < 4) {
            this.type = type;
        } else {
            throw new IllegalArgumentException("type range in 0 -> 3");
        }
    }

    public String getPackageCode() {
        if (StringUtils.isEmpty(packageName)) {
            return KeywordConstant.EMPTY;
        }
        return KeywordConstant.PACKAGE + KeywordConstant.BLANK_SPACE + packageName + KeywordConstant.SEMICOLON;
    }

    public String getTypeCode() {
        return switch (type) {
            case TYPE_CLASS -> KeywordConstant.CLASS;
            case TYPE_INTERFACE -> KeywordConstant.INTERFACE;
            case TYPE_ENUM -> KeywordConstant.ENUM;
            case TYPE_RECORD -> KeywordConstant.RECORD;
            default -> KeywordConstant.EMPTY;
        };
    }

    public String getImportsCode() {
        StringBuilder importCode = new StringBuilder();
        Set<String> imports = getImports();
        int count = 0;
        int size = imports.size();
        for (String importItem : imports) {
            if (count != size - 1) {
                importCode.append(KeywordConstant.IMPORT).append(KeywordConstant.BLANK_SPACE).append(importItem)
                        .append(KeywordConstant.SEMICOLON).append(KeywordConstant.NEW_LINE);
            } else {
                importCode.append(KeywordConstant.IMPORT).append(KeywordConstant.BLANK_SPACE).append(importItem)
                        .append(KeywordConstant.SEMICOLON);
            }
            count++;
        }
        return importCode.toString();
    }

    public String getExtendsCode() {
        if (!StringUtils.isEmpty(extendsName)) {
            return KeywordConstant.EXTENDS + KeywordConstant.BLANK_SPACE + extendsName;
        } else {
            return KeywordConstant.EMPTY;
        }
    }

    public String getImplementsCode() {
        if (!CollectionUtils.isEmpty(implementsList)) {
            StringBuilder implementsCode = new StringBuilder(KeywordConstant.IMPLEMENTS).append(KeywordConstant.BLANK_SPACE);
            for (int i = 0, size = implementsList.size(); i < size; i++) {
                if (i != size - 1) {
                    implementsCode.append(implementsList.get(i)).append(KeywordConstant.COMMA).append(KeywordConstant.BLANK_SPACE);
                } else {
                    implementsCode.append(implementsList.get(i));
                }
            }
            return implementsCode.toString();
        } else {
            return KeywordConstant.EMPTY;
        }
    }

    public String getFieldsCode(int level) {
        return getMemberCode(fields, level);
    }

    public String getMethodsCode(int level) {
        return getMemberCode(methods, level);
    }

    private String getMemberCode(List<? extends CommonMember> members, int level) {
        StringBuilder code = new StringBuilder();
        for (int i = 0, size = members.size(); i < size; i++) {
            if (i != size - 1) {
                code.append(members.get(i).getCode(level)).append(KeywordConstant.NEW_LINE).append(KeywordConstant.NEW_LINE);
            } else {
                code.append(members.get(i).getCode(level));
            }
        }
        return code.toString();
    }

    @Override
    public String getCode(int level) {
        StringBuilder code = new StringBuilder();
        Set<String> imports = getImports();

        String packageCode = getPackageCode();
        if (!StringUtils.isEmpty(packageCode)) {
            code.append(packageCode).append(KeywordConstant.NEW_LINE).append(KeywordConstant.NEW_LINE);
        }
        String importsCode = getImportsCode();
        if (!StringUtils.isEmpty(importsCode)) {
            code.append(importsCode).append(KeywordConstant.NEW_LINE).append(KeywordConstant.NEW_LINE);
        }
        code.append(getDocCode(level)).append(KeywordConstant.NEW_LINE);
        String annotationCode = getAnnotationCode(level);
        if (!StringUtils.isEmpty(annotationCode)) {
            code.append(annotationCode).append(KeywordConstant.NEW_LINE);
        }
        String modifierCode = Modifier.toString(getModifiers());
        code.append(modifierCode).append(KeywordConstant.BLANK_SPACE);
        if (!modifierCode.contains(KeywordConstant.INTERFACE)) {
            code.append(getTypeCode()).append(KeywordConstant.BLANK_SPACE);
        }
        code.append(getName()).append(KeywordConstant.BLANK_SPACE);

        String extendsCode = getExtendsCode();
        if (!StringUtils.isEmpty(extendsCode)) {
            code.append(extendsCode).append(KeywordConstant.BLANK_SPACE);
        }
        String implementsCode = getImplementsCode();
        if (!StringUtils.isEmpty(implementsCode)) {
            code.append(implementsCode).append(KeywordConstant.BLANK_SPACE);
        }

        code.append(KeywordConstant.LEFT_BRACKETS).append(KeywordConstant.NEW_LINE).append(KeywordConstant.NEW_LINE);
        String fieldsCode = getFieldsCode(level + 1);
        if (!StringUtils.isEmpty(fieldsCode)) {
            code.append(fieldsCode).append(KeywordConstant.NEW_LINE).append(KeywordConstant.NEW_LINE);
        }
        String methodsCode = getMethodsCode(level + 1);
        if (!StringUtils.isEmpty(methodsCode)) {
            code.append(methodsCode).append(KeywordConstant.NEW_LINE).append(KeywordConstant.NEW_LINE);
        }
        code.append(KeywordConstant.RIGHT_BRACKETS);

        return code.toString();
    }

    public String getClassPath() {
        return packageName.replace(KeywordConstant.FULL_STOP, KeywordConstant.FORWARD_SLASH)
                + KeywordConstant.FORWARD_SLASH + getName() + KeywordConstant.FULL_STOP + KeywordConstant.JAVA;
    }

    public String getResourcePath(String loc) {
        return packageName.replace(KeywordConstant.FULL_STOP, KeywordConstant.FORWARD_SLASH)
                + KeywordConstant.FORWARD_SLASH + getName() + KeywordConstant.UNDER_LINE + loc
                + KeywordConstant.FULL_STOP + KeywordConstant.PROPERTIES;
    }
}
