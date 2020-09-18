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
import org.message.logger.tools.annotations.utils.MessageUtils;
import org.message.logger.tools.api.BasicLogger;
import org.message.logger.tools.api.LibraryLogger;
import org.message.logger.tools.api.exceptions.DelegationMethodNotFound;

import static java.lang.String.format;

public class MessageLoggerInvocationHandler implements InvocationHandler {

   private LibraryLogger logger;

   public MessageLoggerInvocationHandler(LibraryLogger logger) {
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
         Message.Level level = MessageUtils.resolveLevel(method);
         Metadata[] metadataAnnotations = MessageUtils.resolveMetadata(method);

         Map<String, String> metadata = Collections.emptyMap();

         if (metadataAnnotations.length != 0) {
            metadata = new HashMap<>();
            for (Metadata metadataAnnotation : metadataAnnotations) {
               metadata.put(metadataAnnotation.key(), metadataAnnotation.value());
            }
         }

         String message = MessageUtils.resolveMessage(method);
         if (args == null || args.length == 0) {
            LibraryLoggerWrapper.of(logger).logMessage(level, message, metadata);

            successfulInvocation = true;
         } else {
            Object lastParameter = args[args.length - 1];

            boolean moreThanOne = args.length > 1;
            boolean isException = Throwable.class.isAssignableFrom(lastParameter.getClass());

            if (moreThanOne) {
               LibraryLoggerWrapper.of(logger).logMessageAndParameters(level, message, metadata, args);

               successfulInvocation = true;
            } else if (isException) {
               LibraryLoggerWrapper.of(logger).logMessageAndException(level, message, metadata, (Throwable) args[0]);

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