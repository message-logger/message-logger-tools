package org.message.logger.tools.api;

import org.message.logger.tools.annotations.BusinessException;
import org.message.logger.tools.annotations.Message;
import org.message.logger.tools.annotations.Metadata;
import org.message.logger.tools.api.exceptions.ResourceNotFound;
import org.message.logger.tools.api.exceptions.RuntimeBusinessException;

public interface ParameterLogger extends MessageLogger {

   @Message("Message without parameters should be compiled")
   void messageWithoutParameterShouldCompile();

   @Message("Message with one parameter should be compiled -> Parameter1: {}")
   void messageWithOneParameter(String parameter1);

   @Message("Message with two parameter should be compiled -> Parameter1: {} Parameter2: {}")
   void messageWithTwoParameters(String parameter1, String parameter2);

   @Message("Message with only exception should be compiled")
   void messageWithOnlyException(Exception e);

   @Message("Message with only throwable should be compiled")
   void messageWithOnlyThrowable(Throwable t);

   @Message("Message with one parameter and exception should be compiled -> Parameter1: {}")
   void messageWithOneParameterAndException(String parameter1, Exception e);

   @Message("Message with one parameter and throwable should be compiled -> Parameter1: {}")
   void messageWithOneParameterAndThrowable(String parameter1, Throwable t);

   @Message("Message with custom exception should be compiled - Exception auto discovery")
   void messageWithOnlyBusinessException(RuntimeBusinessException e);

   @Message("Message with custom exception should be compiled - Exception no following name convention")
   @BusinessException
   void messageWithOnlyBusinessException(ResourceNotFound e);

   @Message("Message with custom exception should be compiled - Exception auto discovery -> Parameter1: {}")
   void messageWithOneParameterAndBusinessException(String parameter1, RuntimeBusinessException e);

   @Message("Message with custom exception should be compiled - Exception no following name convention -> Parameter1: {}")
   @BusinessException
   void messageWithOneParameterAndBusinessException(String parameter1, ResourceNotFound e);

   @Message("Message without parameters and single metadata should be compiled")
   @Metadata(key = "MetadataKey", value = "MetadataValue")
   void messageWithoutParameterAndSingleMetadataShouldCompile();

   @Message("Message without parameters and multiples metadata should be compiled")
   @Metadata(key = "MetadataKey1", value = "MetadataValue")
   @Metadata(key = "MetadataKey2", value = "MetadataValue")
   void messageWithoutParameterAndMultipleMetadataShouldCompile();
}
