package org.message.logger.tools.api.nop;

import org.message.logger.tools.api.BasicLogger;
import org.message.logger.tools.api.provider.LoggerProvider;

public class NOPLoggerProvider implements LoggerProvider {

   private static BasicLogger NOP_BASIC_LOGGER = new NOPBasicLogger();

   @Override
   public BasicLogger getLogger(String name) {
      return NOP_BASIC_LOGGER;
   }
}