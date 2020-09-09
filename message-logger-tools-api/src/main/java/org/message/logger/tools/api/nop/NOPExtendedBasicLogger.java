package org.message.logger.tools.api.nop;

import java.util.Map;

import org.message.logger.tools.api.ExtendedBasicLogger;

public class NOPExtendedBasicLogger implements ExtendedBasicLogger {

   @Override
   public void debug(String msg) {
   }

   @Override
   public void debug(String msg, Map<String, String> metadata) {
   }

   @Override
   public void debug(String msg, Object... arguments) {
   }

   @Override
   public void debug(String msg, Map<String, String> metadata, Object... arguments) {
   }

   @Override
   public void debug(String msg, Throwable t) {
   }

   @Override
   public void debug(String msg, Map<String, String> metadata, Throwable t) {
   }

   @Override
   public void info(String msg) {
   }

   @Override
   public void info(String msg, Map<String, String> metadata) {
   }

   @Override
   public void info(String msg, Object... arguments) {
   }

   @Override
   public void info(String msg, Map<String, String> metadata, Object... arguments) {
   }

   @Override
   public void info(String msg, Throwable t) {
   }

   @Override
   public void info(String msg, Map<String, String> metadata, Throwable t) {
   }

   @Override
   public void warn(String msg) {
   }

   @Override
   public void warn(String msg, Map<String, String> metadata) {
   }

   @Override
   public void warn(String msg, Object... arguments) {
   }

   @Override
   public void warn(String msg, Map<String, String> metadata, Object... arguments) {
   }

   @Override
   public void warn(String msg, Throwable t) {
   }

   @Override
   public void warn(String msg, Map<String, String> metadata, Throwable t) {
   }

   @Override
   public void error(String msg) {
   }

   @Override
   public void error(String msg, Map<String, String> metadata) {
   }

   @Override
   public void error(String msg, Object... arguments) {
   }

   @Override
   public void error(String msg, Map<String, String> metadata, Object... arguments) {
   }

   @Override
   public void error(String msg, Throwable t) {
   }

   @Override
   public void error(String msg, Map<String, String> metadata, Throwable t) {
   }

   @Override
   public <T> T unwrap(Class<T> clazz) {
      throw new UnsupportedOperationException("NOP Logger cannot be unwrap");
   }
}