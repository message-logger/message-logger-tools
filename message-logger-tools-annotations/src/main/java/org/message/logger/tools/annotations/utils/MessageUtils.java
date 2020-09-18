package org.message.logger.tools.annotations.utils;

import javax.lang.model.element.ExecutableElement;
import java.lang.reflect.Method;

import org.message.logger.tools.annotations.Message;
import org.message.logger.tools.annotations.Metadata;
import org.message.logger.tools.annotations.containers.MetadataContainer;

public class MessageUtils {

   private static final String PLACEHOLDER = "\\{}";
   private static final Metadata[] EMPTY_METADATA = new Metadata[0];

   public static int countPlaceholders(String messageTemplate) {
      return splitByPlaceholder(messageTemplate.concat(" ")).length - 1;
   }

   public static String replacePlaceholderWith(String message, String replacement) {
      return message.replaceAll(PLACEHOLDER, replacement);
   }

   public static String[] splitByPlaceholder(String message) {
      return message.split(PLACEHOLDER);
   }

   public static String resolveMessage(ExecutableElement executableElement) {
      Message messageAnnotation = executableElement.getAnnotation(Message.class);
      return resolveMessage(executableElement.getSimpleName().toString(), messageAnnotation);
   }

   public static String resolveMessage(Method method) {
      Message messageAnnotation = method.getAnnotation(Message.class);
      return resolveMessage(method.getName(), messageAnnotation);
   }

   public static Message.Level resolveLevel(Method method) {
      Message message = method.getAnnotation(Message.class);
      return message != null ? message.level() : Message.Level.INFO;
   }

   public static Metadata[] resolveMetadata(Method method) {
      Metadata[] metadataAnnotations = EMPTY_METADATA;

      Metadata metadata = method.getAnnotation(Metadata.class);
      MetadataContainer metadataContainer = method.getAnnotation(MetadataContainer.class);

      if (metadata != null) {
         metadataAnnotations = new Metadata[1];
         metadataAnnotations[0] = metadata;
      } else if (metadataContainer != null) {
         metadataAnnotations = metadataContainer.value();
      }

      return metadataAnnotations;
   }

   private static String resolveMessage(String methodName, Message messageAnnotation) {
      String message;

      if (messageAnnotation != null && !"".equals(messageAnnotation.value())) {
         message = messageAnnotation.value();
      } else {
         char previousChar = 0;
         StringBuilder sb = new StringBuilder();
         for (int i = 0; i < methodName.length(); i++) {
            Character current = methodName.charAt(i);
            if (Character.isUpperCase(current)) {
               sb.append(" ");
               sb.append(Character.toLowerCase(current));
            } else {
               if (current == '$' || previousChar == '$') {
                  sb.append(" ");
               }

               if (current == '$') {
                  sb.append("{}");
               } else {
                  sb.append(current);
               }
            }
            previousChar = current;
         }

         message = sb.toString();
      }
      return message.trim();
   }
}
