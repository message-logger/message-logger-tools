package org.message.logger.sumologic.tf;

import org.message.logger.tools.annotations.Message;
import org.message.logger.tools.annotations.sumologic.SumoFolder;
import org.message.logger.tools.annotations.sumologic.SumoMetadata;
import org.message.logger.tools.api.MessageLogger;

@SumoFolder("Terraform")
@SumoMetadata(_sourceCategory = "*service*")
public interface TerraformLogger extends MessageLogger {

   @Message("Simple message")
   void simpleMessage();

   @Message("Simple message {}")
   void simpleMessageWithOneParameter(String parameter1);

   @Message
   void requestTo$WithMethod$Took$ms(String url, String method, long ms);
}
