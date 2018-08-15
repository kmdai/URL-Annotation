package codyy.codyy.urlannotation;

/**
 * Created by kmdai on 17-12-18.
 */

public class AnnotationField {
    private String mAnnotation;
    private String mVariable;

    public AnnotationField(String annotation, String variable) {
        mAnnotation = annotation;
        mVariable = variable;
    }

    public String getAnnotation() {
        return mAnnotation;
    }

    public void setAnnotation(String annotation) {
        mAnnotation = annotation;
    }

    public String getVariable() {
        return mVariable;
    }

    public void setVariable(String variable) {
        mVariable = variable;
    }
}
