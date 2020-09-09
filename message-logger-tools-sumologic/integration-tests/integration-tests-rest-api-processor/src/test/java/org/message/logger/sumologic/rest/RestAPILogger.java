package org.message.logger.sumologic.rest;

import org.message.logger.tools.annotations.Message;
import org.message.logger.tools.annotations.sumologic.SumoFolder;
import org.message.logger.tools.annotations.sumologic.SumoMetadata;
import org.message.logger.tools.api.MessageLogger;

@SumoFolder("REST")
@SumoMetadata(_sourceCategory = "*service*")
public interface RestAPILogger extends MessageLogger {

   @Message("Simple message")
   void simpleMessage();

   @Message("Simple message {}")
   void simpleMessageWithOneParameter(String parameter1);
}
