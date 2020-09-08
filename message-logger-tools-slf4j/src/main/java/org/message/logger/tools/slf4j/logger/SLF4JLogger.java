package org.message.logger.tools.slf4j.logger;

import org.message.logger.tools.api.BasicLogger;
import org.slf4j.Logger;

public class SLF4JLogger implements BasicLogger {

   private final Logger logger;

   public SLF4JLogger(Logger logger) {
      this.logger = logger;
   }

   @Override
   public void debug(String msg) {
      logger.debug(msg);
   }

   @Override
   public void debug(String msg, Object... arguments) {
      logger.debug(msg, arguments);
   }

   @Override
   public void debug(String msg, Throwable t) {
      logger.debug(msg, t);
   }

   @Override
   public void info(String msg) {
      logger.info(msg);
   }

   @Override
   public void info(String msg, Object... arguments) {
      logger.info(msg, arguments);
   }

   @Override
   public void info(String msg, Throwable t) {
      logger.info(msg, t);
   }

   @Override
   public void warn(String msg) {
      logger.warn(msg);
   }

   @Override
   public void warn(String msg, Object... arguments) {
      logger.warn(msg, arguments);
   }

   @Override
   public void warn(String msg, Throwable t) {
      logger.warn(msg, t);
   }

   @Override
   public void error(String msg) {
      logger.error(msg);
   }

   @Override
   public void error(String msg, Object... arguments) {
      logger.error(msg, arguments);
   }

   @Override
   public void error(String msg, Throwable t) {
      logger.error(msg, t);
   }

   @Override
   public <T> T unwrap(Class<T> clazz) {
      return (T) logger;
   }

}
