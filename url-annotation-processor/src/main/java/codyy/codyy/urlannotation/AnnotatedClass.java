package codyy.codyy.urlannotation;

import com.codyy.urlannotation.ClassBase;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by kmdai on 17-12-18.
 */

public class AnnotatedClass {
    private String mBaseUrl;
    private TypeElement mTypeElement;
    private List<AnnotationField> mAnnotationFields;
    private Elements mElement;

    public AnnotatedClass(TypeElement typeElement, Elements element) {
        mTypeElement = typeElement;
        mElement = element;
        mAnnotationFields = new ArrayList<>();
    }

    public void setBaseUrl(String baseUrl) {
        mBaseUrl = baseUrl;
    }

    public void addField(AnnotationField annotationField) {
        mAnnotationFields.add(annotationField);
    }

    public List<AnnotationField> getAnnotationFields() {
        return mAnnotationFields;
    }

    public JavaFile generateFile() {
        //generateMethod
        MethodSpec.Builder bindViewMethod = MethodSpec.methodBuilder(ClassBase.METHOD_RESET_URL)
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC)
                .addParameter(ClassName.get(String.class), "url");
        String[] importStr = new String[mAnnotationFields.size()];

        bindViewMethod.addStatement(mBaseUrl + " = url");
        for (int i = 0; i < mAnnotationFields.size(); i++) {
            AnnotationField annotationField = mAnnotationFields.get(i);
            importStr[i] = annotationField.getVariable();
            bindViewMethod.addStatement("$N = " + mBaseUrl + " + $S", annotationField.getVariable(), annotationField.getAnnotation());
        }

        //generaClass
        TypeSpec injectClass = TypeSpec.classBuilder(mTypeElement.getSimpleName() + ClassBase.CLASS_SUFFIX)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(bindViewMethod.build())
                .build();
        String packageName = mElement.getPackageOf(mTypeElement).toString();
        //添加import
        JavaFile.Builder builder = JavaFile.builder(packageName, injectClass)
                .addStaticImport(ClassName.get(mTypeElement), importStr)
                .addStaticImport(ClassName.get(mTypeElement), mBaseUrl);
        return builder.build();
    }

    private String getPackage(String packageStr) {
        return packageStr.substring(packageStr.lastIndexOf('.') - 1);
    }
}
