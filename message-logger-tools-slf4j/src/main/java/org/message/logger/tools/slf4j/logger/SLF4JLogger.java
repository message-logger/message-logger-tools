package org.message.logger.tools.slf4j.logger;

import java.util.Collections;
import java.util.Map;

import org.message.logger.tools.api.ExtendedBasicLogger;
import org.slf4j.Logger;
import org.slf4j.MDC;

public class SLF4JLogger implements ExtendedBasicLogger {

   private final Logger logger;

   public SLF4JLogger(Logger logger) {
      this.logger = logger;
   }

   @Override
   public void debug(String msg) {
      debug(msg, Collections.emptyMap());
   }

   @Override
   public void debug(String msg, Map<String, String> metadata) {
      if (metadata.isEmpty()) {
         logger.debug(msg);
      } else {
         withMetadata(metadata, () -> logger.debug(msg));
      }
   }

   @Override
   public void debug(String msg, Object... arguments) {
      debug(msg, Collections.emptyMap(), arguments);
   }

   @Override
   public void debug(String msg, Map<String, String> metadata, Object... arguments) {
      if (metadata.isEmpty()) {
         logger.debug(msg, arguments);
      } else {
         withMetadata(metadata, () -> logger.debug(msg, arguments));
      }
   }

   @Override
   public void debug(String msg, Throwable t) {
      debug(msg, Collections.emptyMap(), t);
   }

   @Override
   public void debug(String msg, Map<String, String> metadata, Throwable t) {
      if (metadata.isEmpty()) {
         logger.debug(msg, t);
      } else {
         withMetadata(metadata, () -> logger.debug(msg, t));
      }
   }

   @Override
   public void info(String msg) {
      info(msg, Collections.emptyMap());
   }

   @Override
   public void info(String msg, Map<String, String> metadata) {
      if (metadata.isEmpty()) {
         logger.info(msg);
      } else {
         withMetadata(metadata, () -> logger.info(msg));
      }
   }

   @Override
   public void info(String msg, Object... arguments) {
      info(msg, Collections.emptyMap(), arguments);
   }

   @Override
   public void info(String msg, Map<String, String> metadata, Object... arguments) {
      if (metadata.isEmpty()) {
         logger.info(msg, arguments);
      } else {
         withMetadata(metadata, () -> logger.info(msg, arguments));
      }
   }

   @Override
   public void info(String msg, Throwable t) {
      info(msg, Collections.emptyMap(), t);
   }

   @Override
   public void info(String msg, Map<String, String> metadata, Throwable t) {
      if (metadata.isEmpty()) {
         logger.info(msg, t);
      } else {
         withMetadata(metadata, () -> logger.info(msg, t));
      }
   }

   @Override
   public void warn(String msg) {
      warn(msg, Collections.emptyMap());
   }

   @Override
   public void warn(String msg, Map<String, String> metadata) {
      if (metadata.isEmpty()) {
         logger.warn(msg);
      } else {
         withMetadata(metadata, () -> logger.warn(msg));
      }
   }

   @Override
   public void warn(String msg, Object... arguments) {
      warn(msg, Collections.emptyMap(), arguments);
   }

   @Override
   public void warn(String msg, Map<String, String> metadata, Object... arguments) {
      if (metadata.isEmpty()) {
         logger.warn(msg, arguments);
      } else {
         withMetadata(metadata, () -> logger.warn(msg, arguments));
      }
   }

   @Override
   public void warn(String msg, Throwable t) {
      warn(msg, Collections.emptyMap(), t);
   }

   @Override
   public void warn(String msg, Map<String, String> metadata, Throwable t) {
      if (metadata.isEmpty()) {
         logger.warn(msg, t);
      } else {
         withMetadata(metadata, () -> logger.warn(msg, t));
      }
   }

   @Override
   public void error(String msg) {
      error(msg, Collections.emptyMap());
   }

   @Override
   public void error(String msg, Map<String, String> metadata) {
      if (metadata.isEmpty()) {
         logger.error(msg);
      } else {
         withMetadata(metadata, () -> logger.error(msg));
      }
   }

   @Override
   public void error(String msg, Object... arguments) {
      error(msg, Collections.emptyMap(), arguments);
   }

   @Override
   public void error(String msg, Map<String, String> metadata, Object... arguments) {
      if (metadata.isEmpty()) {
         logger.error(msg, arguments);
      } else {
         withMetadata(metadata, () -> logger.error(msg, arguments));
      }
   }

   @Override
   public void error(String msg, Throwable t) {
      error(msg, Collections.emptyMap(), t);
   }

   @Override
   public void error(String msg, Map<String, String> metadata, Throwable t) {
      if (metadata.isEmpty()) {
         logger.error(msg, t);
      } else {
         withMetadata(metadata, () -> logger.error(msg, t));
      }
   }

   @Override
   public <T> T unwrap(Class<T> clazz) {
      return (T) logger;
   }

   private void withMetadata(Map<String, String> metadata, Runnable loggerAction) {
      Map<String, String> contextMap = MDC.getCopyOfContextMap();

      for (Map.Entry<String, String> entry : metadata.entrySet()) {
         if (!contextMap.containsKey(entry.getKey())) {
            MDC.put(entry.getKey(), entry.getValue());
         }
      }

      loggerAction.run();

      MDC.clear();
      MDC.setContextMap(contextMap);
   }
}
