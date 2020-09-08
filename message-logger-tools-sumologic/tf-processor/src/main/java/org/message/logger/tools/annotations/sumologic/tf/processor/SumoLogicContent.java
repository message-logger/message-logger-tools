package org.message.logger.tools.annotations.sumologic.tf.processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.message.logger.tools.annotations.Message;
import org.message.logger.tools.annotations.sumologic.SumoFolder;

import static java.lang.String.format;

public class SumoLogicContent extends AbstractProcessor {

   @Override
   public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
      Messager messager = processingEnv.getMessager();

      for (TypeElement typeElement : annotations) {

         File target = Utils.lookupTarget(processingEnv.getFiler());
         File terraformDirectory = Utils.lookupTerraformDirectory(target);

         try (FileWriter writer = new FileWriter(new File(terraformDirectory, "sumo-content.tf"), true)) {
            String sumologicContentTemplate = Utils.getContentAsString("sumologic_content.tf");

            for (Element annotatedElement : env.getElementsAnnotatedWith(typeElement)) {
               SumoFolder folder = annotatedElement.getEnclosingElement().getAnnotation(SumoFolder.class);

               if (folder != null) {
                  Message annotation = annotatedElement.getAnnotation(Message.class);

                  String folderName = Utils.folderName(folder.value());
                  String methodName = annotatedElement.getSimpleName().toString();

                  String sumologicContent = sumologicContentTemplate.replaceAll("\\$\\{parentId}", format("\\$\\{sumologic_folder.%s.id}", folderName));
                  sumologicContent = sumologicContent.replaceAll("\\$\\{name}", methodName);
                  sumologicContent = sumologicContent.replaceAll("\\$\\{description}", format("Query for %s", methodName));
                  sumologicContent = sumologicContent.replaceAll("\\$\\{queryText}", annotation.value());

                  writer.write(sumologicContent);
                  writer.write("\n");
               }
            }
         } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, format("Exception: %s", Utils.getStackTrace(e)));
         }
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
