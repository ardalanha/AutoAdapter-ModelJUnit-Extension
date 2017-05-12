package hh.mbt.extension;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.METHOD })
public @interface Converter {
	String label() default "default";
	Class<?> type() default Object.class;
	Class<?> targetType() default Object.class;
	boolean multiple() default false;

}
