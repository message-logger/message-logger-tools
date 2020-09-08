package org.message.logger.sumologic.tf;

import org.message.logger.tools.annotations.Message;
import org.message.logger.tools.annotations.sumologic.SumoFolder;
import org.message.logger.tools.api.MessageLogger;

@SumoFolder("Terraform")
public interface TerraformLogger extends MessageLogger {

   @Message("Simple message")
   void simpleMessage();
}
