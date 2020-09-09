package org.message.logger.tools.annotations.sumologic.rest.processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.message.logger.tools.annotations.Message;
import org.message.logger.tools.annotations.sumologic.SumoFolder;
import org.message.logger.tools.annotations.sumologic.utils.SumoUtils;

@SupportedOptions({"accessID", "accessKey"})
public class SumoLogicContent extends AbstractProcessor {

   @Override
   public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
      String accessID = processingEnv.getOptions().get("accessID");
      String accessKey = processingEnv.getOptions().get("accessKey");

      if (accessID != null && accessKey != null) {
         Messager messager = processingEnv.getMessager();

         for (TypeElement typeElement : annotations) {
            for (Element annotatedElement : env.getElementsAnnotatedWith(typeElement)) {
               SumoFolder folder = SumoUtils.resolveSumoFolder(annotatedElement);

               if (folder != null) {
                  System.out.println("******************");
                  System.out.println("Sumo Query: " + SumoUtils.resolveQuery((ExecutableElement) annotatedElement));
                  System.out.println("******************");
               }
            }
         }

         return false;
      }

      return false;
   }

   @Override
   public SourceVersion getSupportedSourceVersion() {
      return SourceVersion.latestSupported();
   }

   @Override
   public Set<String> getSupportedAnnotationTypes() {
      return new HashSet<>(Arrays.asList(Message.class.getName()));
   }
}
