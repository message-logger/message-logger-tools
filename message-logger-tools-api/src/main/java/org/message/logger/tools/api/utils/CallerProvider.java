package org.message.logger.tools.api.utils;

public class CallerProvider extends SecurityManager {

   private static final int OFFSET = 1;

   public Class<?> getCallingClass() {
      return getClassContext()[OFFSET + 1];
   }
}