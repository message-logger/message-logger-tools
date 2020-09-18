package org.message.logger.tools.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(METHOD)
@Retention(RUNTIME)
@Documented
public @interface Message {

   String value() default "";

   Message.Level level() default Message.Level.INFO;

   enum Level {
      ERROR, WARN, INFO, DEBUG
   }
}
