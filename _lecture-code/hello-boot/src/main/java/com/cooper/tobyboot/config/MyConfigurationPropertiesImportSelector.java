package com.cooper.tobyboot.config;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.MultiValueMap;

/**
 * 실제 import 할 클래스의 이름을 반환해준다.
 */
public class MyConfigurationPropertiesImportSelector implements DeferredImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        MultiValueMap<String, Object> attributes
                = importingClassMetadata.getAllAnnotationAttributes(EnableMyConfigurationProperties.class.getName());

        Class propertyClass = (Class) attributes.getFirst("value");

        return new String[] {propertyClass.getName()};
    }

}
