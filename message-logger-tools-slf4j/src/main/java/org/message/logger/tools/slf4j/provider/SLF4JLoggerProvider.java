package org.message.logger.tools.slf4j.provider;

import org.message.logger.tools.api.LibraryLogger;
import org.message.logger.tools.api.provider.LoggerProvider;
import org.message.logger.tools.slf4j.logger.SLF4JLogger;
import org.slf4j.LoggerFactory;

public class SLF4JLoggerProvider implements LoggerProvider {

   @Override
   public LibraryLogger getLogger(String name) {
      return new SLF4JLogger(LoggerFactory.getLogger(name));
   }
}
