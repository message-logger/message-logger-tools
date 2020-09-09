package org.message.logger.tools.api.nop;

import org.message.logger.tools.api.ExtendedBasicLogger;
import org.message.logger.tools.api.provider.LoggerProvider;

public class NOPLoggerProvider implements LoggerProvider {

   private static ExtendedBasicLogger NOP_EXTENDED_BASIC_LOGGER = new NOPExtendedBasicLogger();

   @Override
   public ExtendedBasicLogger getLogger(String name) {
      return NOP_EXTENDED_BASIC_LOGGER;
   }
}