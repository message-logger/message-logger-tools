package org.message.logger.tools.annotations.sumologic.utils;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.util.Types;
import java.util.Optional;
import java.util.StringJoiner;

import org.message.logger.tools.annotations.Message;
import org.message.logger.tools.annotations.sumologic.SumoFolder;
import org.message.logger.tools.annotations.sumologic.SumoMetadata;
import org.message.logger.tools.annotations.utils.MessageUtils;

public class SumoUtils {

   public static String resolveQuery(ExecutableElement annotatedElement) {
      StringBuilder query = new StringBuilder();
      Message message = annotatedElement.getAnnotation(Message.class);
      Optional<SumoMetadata> maybeMetadata = resolveSumoMetadata(annotatedElement);

      if (maybeMetadata.isPresent()) {
         SumoMetadata metadata = maybeMetadata.get();

         if (!"".equals(metadata._sourceHost())) {
            query.append("_sourceHost=");
            query.append(metadata._sourceHost());
            query.append(" AND ");
         }

         if (!"".equals(metadata._sourceCategory())) {
            query.append("_sourceCategory=");
            query.append(metadata._sourceCategory());
            query.append(" AND ");
         }
      }

      int placeholders = MessageUtils.countPlaceholders(message);

      if (placeholders > 0) {
         StringJoiner joiner = new StringJoiner(" AND ");
         String[] messageParts = MessageUtils.splitByPlaceholder(message.value());
         String messageWithoutPlaceholders = MessageUtils.replacePlaceholderWith(message.value(), "*");

         for (String messagePart: messageParts) {
            joiner.add("'" + messagePart.trim() + "'");
         }

         query.append(joiner.toString());

         query.append(" | parse ");
         query.append("'" + messageWithoutPlaceholders + "'");
         query.append(" as ");

         StringJoiner parameters = new StringJoiner(", ");

         for (VariableElement parameter: annotatedElement.getParameters()) {
            parameters.add(parameter.getSimpleName());
         }

         query.append(parameters.toString());
      } else {
         query.append(message.value());
      }

      return query.toString();
   }

   public static SumoFolder resolveSumoFolder(Element annotatedElement) {
      SumoFolder annotation = annotatedElement.getAnnotation(SumoFolder.class);

      if (annotation == null) {
         Element parentElement = annotatedElement.getEnclosingElement();
         annotation = parentElement.getAnnotation(SumoFolder.class);
      }

      return annotation;
   }

   public static Optional<SumoMetadata> resolveSumoMetadata(Element annotatedElement) {
      SumoMetadata annotation = annotatedElement.getAnnotation(SumoMetadata.class);

      if (annotation == null) {
         Element parentElement = annotatedElement.getEnclosingElement();
         annotation = parentElement.getAnnotation(SumoMetadata.class);
      }

      return Optional.ofNullable(annotation);
   }
}
