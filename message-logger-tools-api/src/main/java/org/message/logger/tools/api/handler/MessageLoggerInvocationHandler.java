package org.message.logger.tools.api.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.message.logger.tools.annotations.Message;
import org.message.logger.tools.api.BasicLogger;
import org.message.logger.tools.api.exceptions.DelegationMethodNotFound;

import static java.lang.String.format;

public class MessageLoggerInvocationHandler implements InvocationHandler {

   private BasicLogger logger;

   public MessageLoggerInvocationHandler(BasicLogger logger) {
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

         if (args == null || args.length == 0) {
            BasicLoggerWrapper.of(logger).logMessage(level, message.value());

            successfulInvocation = true;
         } else {
            Object lastParameter = args[args.length - 1];

            boolean moreThanOne = args.length > 1;
            boolean isException = Throwable.class.isAssignableFrom(lastParameter.getClass());

            if (moreThanOne) {
               BasicLoggerWrapper.of(logger).logMessageAndParameters(level, message.value(), args);

               successfulInvocation = true;
            } else if (isException) {
               BasicLoggerWrapper.of(logger).logMessageAndException(level, message.value(), (Throwable) args[0]);

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