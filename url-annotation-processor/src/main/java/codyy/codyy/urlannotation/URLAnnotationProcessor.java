package codyy.codyy.urlannotation;


import com.codyy.urlannotation.URL;
import com.codyy.urlannotation.URLBase;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by kmdai on 17-12-18.
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.codyy.urlannotation.URL", "com.codyy.urlannotation.URLBase"})
public class URLAnnotationProcessor extends AbstractProcessor {
    private Filer mFiler; //文件相关的辅助类
    private Elements mElements; //元素相关的辅助类
    private Map<String, AnnotatedClass> mAnnotatedClass;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        mElements = processingEnvironment.getElementUtils();
        mAnnotatedClass = new TreeMap<>();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mAnnotatedClass.clear();
        for (Element element : roundEnvironment.getElementsAnnotatedWith(URL.class)) {
            if (checkURLRule(element)) {
                TypeElement typeElement = (TypeElement) element.getEnclosingElement();
                AnnotatedClass annotatedClass = getAnnotation(typeElement);
                AnnotationField annotationField = new AnnotationField(element.getAnnotation(URL.class).value(), element.getSimpleName().toString());
                annotatedClass.addField(annotationField);
            }
        }
        for (Element element : roundEnvironment.getElementsAnnotatedWith(URLBase.class)) {
            if (checkURLRule(element)) {
                TypeElement typeElement = (TypeElement) element.getEnclosingElement();
                AnnotatedClass annotatedClass = getAnnotation(typeElement);
                annotatedClass.setBaseUrl(element.getSimpleName().toString());
            }
        }
        for (AnnotatedClass annotatedClass : mAnnotatedClass.values()) {
            try {
                annotatedClass.generateFile().writeTo(mFiler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private AnnotatedClass getAnnotation(TypeElement typeElement) {
        String key = typeElement.getQualifiedName().toString();
        AnnotatedClass annotatedClass = mAnnotatedClass.get(key);
        if (annotatedClass == null) {
            annotatedClass = new AnnotatedClass(typeElement, mElements);
            mAnnotatedClass.put(key, annotatedClass);
        }
        return annotatedClass;
    }

    /**
     * 检查
     *
     * @param element
     * @return
     */
    private boolean checkURLRule(Element element) {
        Set<Modifier> modifiers = element.getModifiers();
        if (modifiers.contains(Modifier.PRIVATE)
                || !modifiers.contains(Modifier.STATIC) ||
                modifiers.contains(Modifier.FINAL)) {
            return false;
        }
        return true;
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }
}
