package org.message.logger.tools.annotations.utils;

import org.message.logger.tools.annotations.Message;

public class MessageUtils {

   private static final String PLACEHOLDER = "\\{}";

   public static int countPlaceholders(Message message) {
      return splitByPlaceholder(message.value().concat(" ")).length - 1;
   }

   public static String replacePlaceholderWith(String message, String replacement) {
      return message.replaceAll(PLACEHOLDER, replacement);
   }

   public static String[] splitByPlaceholder(String message) {
      return message.split(PLACEHOLDER);
   }
}
