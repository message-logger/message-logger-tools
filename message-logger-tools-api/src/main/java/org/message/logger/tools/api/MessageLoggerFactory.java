package org.message.logger.tools.api;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;

import org.message.logger.tools.api.handler.MessageLoggerInvocationHandler;
import org.message.logger.tools.api.nop.NOPLoggerProvider;
import org.message.logger.tools.api.provider.LoggerProvider;
import org.message.logger.tools.api.utils.CallerDetector;

public final class MessageLoggerFactory {

   private static LoggerProvider NOP_LOGGER_PROVIDER = new NOPLoggerProvider();

   private static LoggerProvider LOGGER_PROVIDER = getLoggerProvider();

   public static <T extends MessageLogger> T getLogger(Class<T> clazz) {
      Objects.requireNonNull(clazz, "Message logger class cannot be null");

      Class<?> caller = CallerDetector.$.getCallingClass();
      ExtendedBasicLogger logger = LOGGER_PROVIDER.getLogger(caller.getName());

      return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new MessageLoggerInvocationHandler(logger));
   }

   private static LoggerProvider getLoggerProvider() {
      ServiceLoader<LoggerProvider> serviceLoader = ServiceLoader.load(LoggerProvider.class);
      List<LoggerProvider> providers = new ArrayList<>();
      for (LoggerProvider provider : serviceLoader) {
         providers.add(provider);
      }

      if (providers.isEmpty()) {
         return NOP_LOGGER_PROVIDER;
      }
      return providers.get(0);
   }
}
