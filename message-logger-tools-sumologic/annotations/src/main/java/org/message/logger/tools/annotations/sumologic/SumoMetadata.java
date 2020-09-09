package org.message.logger.tools.annotations.sumologic;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

@Target({TYPE, METHOD})
@Retention(CLASS)
@Documented
public @interface SumoMetadata {

   String _sourceHost() default "";

   String _sourceCategory() default "";
}
