package cn.darkflame.meta.code.model;

import java.util.List;
import java.util.Set;

/**
 * @author james
 */
public interface IMember {

    Set<String> getAnnotations();

    void addAnnotation(String annotation);

    void removeAnnotation(String annotation);

    void removeAnnotations(Set<String> annotations);

    int getModifiers();

    void setModifiers(int mod);

    String getName();

    void setName(String name);

    String getDoc();

    void setDoc(String doc);

}
