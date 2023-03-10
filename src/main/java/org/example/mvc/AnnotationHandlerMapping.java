package org.example.mvc;

import org.example.mvc.annotation.Controller;
import org.example.mvc.annotation.RequestMapping;
import org.example.mvc.controller.HandlerKey;
import org.example.mvc.controller.RequestMethod;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping implements HandlerMapping{

    private final Object[] basePackage; // 필요이유 :
    private Map<HandlerKey, AnnotationHandler> handlers = new HashMap<>();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initalize(){
        Reflections reflections = new Reflections(basePackage);

        //HomeController
        Set<Class<?>> clazzWithControllerAnnotation = reflections.getTypesAnnotatedWith(Controller.class); //Controller Annotation을 가지고 있는 Class 추출

        clazzWithControllerAnnotation.forEach(clazz ->
                        Arrays.stream(clazz.getDeclaredMethods()).forEach(declaredMethod -> {
                            RequestMapping requestMapping = declaredMethod.getDeclaredAnnotation(RequestMapping.class); //RequestMapping Annotation 추출

                            Arrays.stream(getRequestMethods(requestMapping))
                                    .forEach(requestMethod -> handlers.put(
                                            new HandlerKey(requestMethod, requestMapping.value()), new AnnotationHandler(clazz, declaredMethod)));
                        })
                );

    }

    private RequestMethod[] getRequestMethods(RequestMapping requestMapping) {
        return requestMapping.method();
    }

    @Override
    public Object findHandler(HandlerKey handlerKey) {
        return handlers.get(handlerKey);
    }
}
