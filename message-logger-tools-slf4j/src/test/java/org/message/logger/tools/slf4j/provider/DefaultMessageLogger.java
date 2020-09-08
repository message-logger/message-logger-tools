package org.message.logger.tools.slf4j.provider;

import org.message.logger.tools.annotations.Message;
import org.message.logger.tools.api.MessageLogger;

public interface DefaultMessageLogger extends MessageLogger {

   @Message("Testing")
   void test();
}
