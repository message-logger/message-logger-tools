package org.message.logger.tools.api.handler;

import org.message.logger.tools.annotations.Message;
import org.message.logger.tools.api.BasicLogger;

final class BasicLoggerWrapper {

   private BasicLogger logger;

   private BasicLoggerWrapper(BasicLogger logger) {
      this.logger = logger;
   }

   static BasicLoggerWrapper of(BasicLogger logger) {
      return new BasicLoggerWrapper(logger);
   }

   void logMessage(Message.Level level, String msg) {
      switch (level) {
         case DEBUG:
            logger.debug(msg);
            break;
         case INFO:
            logger.info(msg);
            break;
         case WARN:
            logger.warn(msg);
            break;
         case ERROR:
            logger.error(msg);
            break;
         default:
            throw new RuntimeException("Invalid Log Level");
      }
   }

   void logMessageAndParameters(Message.Level level, String msg, Object... arguments) {
      switch (level) {
         case DEBUG:
            logger.debug(msg, arguments);
            break;
         case INFO:
            logger.info(msg, arguments);
            break;
         case WARN:
            logger.warn(msg, arguments);
            break;
         case ERROR:
            logger.error(msg, arguments);
            break;
         default:
            throw new RuntimeException("Invalid Log Level");
      }
   }

   void logMessageAndException(Message.Level level, String msg, Throwable t) {
      switch (level) {
         case DEBUG:
            logger.debug(msg, t);
            break;
         case INFO:
            logger.info(msg, t);
            break;
         case WARN:
            logger.warn(msg, t);
            break;
         case ERROR:
            logger.error(msg, t);
            break;
         default:
            throw new RuntimeException("Invalid Log Level");
      }
   }
}
