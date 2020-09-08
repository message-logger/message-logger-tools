package org.message.logger.tools.api.nop;

import org.message.logger.tools.api.BasicLogger;

public class NOPBasicLogger implements BasicLogger {

   @Override
   public void debug(String msg) {
   }

   @Override
   public void debug(String msg, Object... arguments) {
   }

   @Override
   public void debug(String msg, Throwable t) {
   }

   @Override
   public void info(String msg) {
   }

   @Override
   public void info(String msg, Object... arguments) {
   }

   @Override
   public void info(String msg, Throwable t) {
   }

   @Override
   public void warn(String msg) {
   }

   @Override
   public void warn(String msg, Object... arguments) {
   }

   @Override
   public void warn(String msg, Throwable t) {
   }

   @Override
   public void error(String msg) {
   }

   @Override
   public void error(String msg, Object... arguments) {
   }

   @Override
   public void error(String msg, Throwable t) {
   }

   @Override
   public <T> T unwrap(Class<T> clazz) {
      throw new UnsupportedOperationException("NOP Logger cannot be unwrap");
   }
}