package org.message.logger.tools.api.nop;

import org.message.logger.tools.api.LibraryLogger;
import org.message.logger.tools.api.provider.LoggerProvider;

public class NOPLoggerProvider implements LoggerProvider {

   private static LibraryLogger NOP_LIBRARY_LOGGER = new NOPLibraryLogger();

   @Override
   public LibraryLogger getLogger(String name) {
      return NOP_LIBRARY_LOGGER;
   }
}