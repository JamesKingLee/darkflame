package cn.darkflame.meta.code.model;

import cn.darkflame.common.constant.KeywordConstant;
import cn.darkflame.common.util.CollectionUtils;
import cn.darkflame.common.util.StringUtils;

import java.util.*;

public abstract class CommonMember implements IMember {

    private String name;

    private int modifiers;

    private final Set<String> annotations = new HashSet<>(3);

    private String doc = "";

    private final Set<String> imports = new HashSet<>();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        if (name.contains(KeywordConstant.FULL_STOP)) {
            String packageName = name.substring(0, name.lastIndexOf(KeywordConstant.FULL_STOP));
        }
        this.name = name;
    }

    public int getModifiers() {
        return modifiers;
    }

    public void setModifiers(int mod) {
        this.modifiers = mod;
    }

    @Override
    public Set<String> getAnnotations() {
        return annotations;
    }

    @Override
    public void addAnnotation(String annotation) {
        if (annotation.startsWith(KeywordConstant.AT)) {
            annotation = annotation.substring(1);
        }
        if (annotation.contains(KeywordConstant.FULL_STOP)) {
            if (annotation.contains(KeywordConstant.LEFT_PARENTHESES)) {
                addImport(annotation.substring(0, annotation.indexOf(KeywordConstant.LEFT_PARENTHESES)));
            } else {
                addImport(annotation);
            }
        }
        annotations.add(annotation.substring(annotation.lastIndexOf(KeywordConstant.FULL_STOP) + 1));
    }

    @Override
    public void removeAnnotation(String annotation) {
        annotations.remove(annotation);
    }

    @Override
    public void removeAnnotations(Set<String> annotations) {
        annotationsFormat(annotations).forEach(this.annotations::remove);
    }

    @Override
    public String getDoc() {
        return doc;
    }

    @Override
    public void setDoc(String doc) {
        this.doc = doc == null ? "" : doc;
    }

    public Set<String> getImports() {
        return imports;
    }

    public void addImport(String importItem) {
        this.imports.add(importItem);
    }


    public List<String> annotationsFormat(Set<String> annotations) {
        List<String> result = new ArrayList<>();
        annotations.forEach(annotation -> {
            if (annotation.startsWith(KeywordConstant.AT)) {
                result.add(annotation.substring(1));
            } else {
                result.add(annotation);
            }
        });
        return result;
    }

    public String getAnnotationCode(int level) {
        StringBuilder code = new StringBuilder();
        String indentation = String.join(KeywordConstant.EMPTY, Collections.nCopies(level, KeywordConstant.TAB));
        if (!CollectionUtils.isEmpty(annotations)) {
            int count = 0;
            int size = annotations.size();
            for (String annotation : annotations) {
                if (count != size - 1) {
                    code.append(indentation).append(KeywordConstant.AT).append(annotation).append(KeywordConstant.NEW_LINE);
                } else {
                    code.append(indentation).append(KeywordConstant.AT).append(annotation);
                }
                count++;
            }
        }
        return code.toString();
    }

    public String getDocCode(int level) {
        StringBuilder code = new StringBuilder();
        String indentation = String.join(KeywordConstant.EMPTY, Collections.nCopies(level, KeywordConstant.TAB));
        code.append(indentation).append(KeywordConstant.DOC_START).append(KeywordConstant.NEW_LINE);
        if (StringUtils.isEmpty(doc)) {
            code.append(indentation).append(KeywordConstant.BLANK_SPACE).append(KeywordConstant.DOC_ROW)
                    .append(KeywordConstant.BLANK_SPACE).append(KeywordConstant.NEW_LINE);
        } else {
            String[] docSplit = doc.split(KeywordConstant.REGEX_NEW_LINE);
            for (String docRow : docSplit) {
                code.append(indentation).append(KeywordConstant.BLANK_SPACE).append(KeywordConstant.DOC_ROW)
                        .append(KeywordConstant.BLANK_SPACE).append(docRow).append(KeywordConstant.NEW_LINE);
            }
        }
        code.append(indentation).append(KeywordConstant.BLANK_SPACE).append(KeywordConstant.DOC_END);

        return code.toString();
    }

    public abstract String getCode(int level);
}
