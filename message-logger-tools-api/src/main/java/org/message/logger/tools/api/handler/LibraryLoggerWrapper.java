package org.message.logger.tools.api.handler;

import java.util.Map;

import org.message.logger.tools.annotations.Message;
import org.message.logger.tools.api.LibraryLogger;

final class LibraryLoggerWrapper {

   private LibraryLogger logger;

   private LibraryLoggerWrapper(LibraryLogger logger) {
      this.logger = logger;
   }

   static LibraryLoggerWrapper of(LibraryLogger logger) {
      return new LibraryLoggerWrapper(logger);
   }

   void logMessage(Message.Level level, String msg, Map<String, String> metadata) {
      switch (level) {
         case DEBUG:
            logger.debug(msg, metadata);
            break;
         case INFO:
            logger.info(msg, metadata);
            break;
         case WARN:
            logger.warn(msg, metadata);
            break;
         case ERROR:
            logger.error(msg, metadata);
            break;
         default:
            throw new RuntimeException("Invalid Log Level");
      }
   }

   void logMessageAndParameters(Message.Level level, String msg, Map<String, String> metadata, Object... arguments) {
      switch (level) {
         case DEBUG:
            logger.debug(msg, metadata, arguments);
            break;
         case INFO:
            logger.info(msg, metadata, arguments);
            break;
         case WARN:
            logger.warn(msg, metadata, arguments);
            break;
         case ERROR:
            logger.error(msg, metadata, arguments);
            break;
         default:
            throw new RuntimeException("Invalid Log Level");
      }
   }

   void logMessageAndException(Message.Level level, String msg, Map<String, String> metadata, Throwable t) {
      switch (level) {
         case DEBUG:
            logger.debug(msg, metadata, t);
            break;
         case INFO:
            logger.info(msg, metadata, t);
            break;
         case WARN:
            logger.warn(msg, metadata, t);
            break;
         case ERROR:
            logger.error(msg, metadata, t);
            break;
         default:
            throw new RuntimeException("Invalid Log Level");
      }
   }
}
