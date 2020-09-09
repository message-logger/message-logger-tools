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

import org.message.logger.tools.annotations.sumologic.SumoFolder;
import org.message.logger.tools.annotations.sumologic.utils.SumoUtils;

import static java.lang.String.format;

public class SumoLogicFolder extends AbstractProcessor {

   @Override
   public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
      Messager messager = processingEnv.getMessager();

      for (TypeElement typeElement : annotations) {

         File target = Utils.lookupTarget(processingEnv.getFiler());
         File terraformDirectory = Utils.lookupTerraformDirectory(target);

         try (FileWriter writer = new FileWriter(new File(terraformDirectory, "sumo-folders.tf"), true)) {
            String sumologicFolderTemplate = Utils.getContentAsString("sumologic_folder.tf");
            String sumologicPersonalFolderTemplate = Utils.getContentAsString("sumologic_personal_folder.tf");

            for (Element annotatedElement : env.getElementsAnnotatedWith(typeElement)) {
               SumoFolder folder = SumoUtils.resolveSumoFolder(annotatedElement);

               String folderName = Utils.folderName(folder.value());

               String sumologicPersonalFolder = sumologicPersonalFolderTemplate.replaceAll("\\$\\{name}", folderName);
               writer.write(sumologicPersonalFolder);
               writer.write("\n");
            }

            for (Element annotatedElement : env.getElementsAnnotatedWith(typeElement)) {
               SumoFolder folder = SumoUtils.resolveSumoFolder(annotatedElement);

               String folderName = Utils.folderName(folder.value());

               String sumologicFolder = sumologicFolderTemplate.replaceAll("\\$\\{snakeCaseName}", folderName);
               sumologicFolder = sumologicFolder.replaceAll("\\$\\{name}", folder.value());
               sumologicFolder = sumologicFolder.replaceAll("\\$\\{description}", format("Folder for %s", folder.value()));
               sumologicFolder = sumologicFolder.replaceAll("\\$\\{parentId}", format("\\$\\{data.sumologic_personal_folder.%s.id}", folderName));

               writer.write(sumologicFolder);
               writer.write("\n");
            }
         } catch (Throwable t) {
            messager.printMessage(Diagnostic.Kind.ERROR, format("Exception: %s", Utils.getStackTrace(t)));
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
      return new HashSet<>(Arrays.asList(SumoFolder.class.getName()));
   }
}
