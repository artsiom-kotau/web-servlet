package by.korona.demo_web.reflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // The annotation is available at runtime
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface WebService {
    String path();
}
