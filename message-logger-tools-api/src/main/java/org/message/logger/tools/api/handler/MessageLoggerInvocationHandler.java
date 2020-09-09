package org.message.logger.tools.api.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.message.logger.tools.annotations.Message;
import org.message.logger.tools.annotations.Metadata;
import org.message.logger.tools.annotations.containers.MetadataContainer;
import org.message.logger.tools.api.BasicLogger;
import org.message.logger.tools.api.ExtendedBasicLogger;
import org.message.logger.tools.api.exceptions.DelegationMethodNotFound;

import static java.lang.String.format;

public class MessageLoggerInvocationHandler implements InvocationHandler {

   private ExtendedBasicLogger logger;

   public MessageLoggerInvocationHandler(ExtendedBasicLogger logger) {
      this.logger = logger;
   }

   @Override
   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      boolean isBasicMethod = BasicLogger.class.equals(method.getDeclaringClass());

      boolean successfulInvocation = false;

      if (isBasicMethod) {
         Method underlyingMethod = findMethod(method, args);
         try {
            underlyingMethod.invoke(logger, args);
         } catch (InvocationTargetException e) {
            throw e.getTargetException();
         }

         successfulInvocation = true;
      } else {
         Message message = method.getAnnotation(Message.class);
         Message.Level level = message.level();

         MetadataContainer metadataContainer = method.getAnnotation(MetadataContainer.class);

         Map<String, String> metadata = Collections.emptyMap();

         if (metadataContainer != null) {
            metadata = new HashMap<>();
            Metadata[] metadataAnnotations = metadataContainer.value();

            for (Metadata metadataAnnotation : metadataAnnotations) {
               metadata.put(metadataAnnotation.key(), metadataAnnotation.value());
            }
         }
         if (args == null || args.length == 0) {
            ExtendedBasicLoggerWrapper.of(logger).logMessage(level, message.value(), metadata);

            successfulInvocation = true;
         } else {
            Object lastParameter = args[args.length - 1];

            boolean moreThanOne = args.length > 1;
            boolean isException = Throwable.class.isAssignableFrom(lastParameter.getClass());

            if (moreThanOne) {
               ExtendedBasicLoggerWrapper.of(logger).logMessageAndParameters(level, message.value(), metadata, args);

               successfulInvocation = true;
            } else if (isException) {
               ExtendedBasicLoggerWrapper.of(logger).logMessageAndException(level, message.value(), metadata, (Throwable) args[0]);

               successfulInvocation = true;
            }
         }
      }

      if (successfulInvocation) {
         return null;
      }

      throw new DelegationMethodNotFound(format("Method not found for %s#%s", "", ""));
   }

   private Method findMethod(Method method, Object[] args) throws NoSuchMethodException {
      String methodName = method.getName();
      Class[] parameterTypes = Arrays.stream(args).map(Object::getClass).toArray(Class[]::new);

      return logger.getClass().getMethod(methodName, parameterTypes);
   }
}