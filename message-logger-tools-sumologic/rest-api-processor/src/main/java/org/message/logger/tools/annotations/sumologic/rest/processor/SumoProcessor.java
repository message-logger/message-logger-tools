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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import feign.Feign;
import feign.FeignException;
import feign.auth.BasicAuthRequestInterceptor;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import org.message.logger.tools.annotations.Message;
import org.message.logger.tools.annotations.sumologic.SumoFolder;
import org.message.logger.tools.annotations.sumologic.rest.processor.clients.SumoClient;
import org.message.logger.tools.annotations.sumologic.rest.processor.clients.model.ContentInformation;
import org.message.logger.tools.annotations.sumologic.rest.processor.clients.model.ContentPath;
import org.message.logger.tools.annotations.sumologic.rest.processor.clients.model.CreateContent;
import org.message.logger.tools.annotations.sumologic.rest.processor.clients.model.CreateFolder;
import org.message.logger.tools.annotations.sumologic.rest.processor.clients.model.FolderResponse;
import org.message.logger.tools.annotations.sumologic.rest.processor.clients.model.PersonalFolder;
import org.message.logger.tools.annotations.sumologic.rest.processor.clients.model.SearchContent;
import org.message.logger.tools.annotations.sumologic.utils.SumoUtils;

import static java.lang.String.format;

@SupportedOptions({"url", "accessID", "accessKey"})
public class SumoProcessor extends AbstractProcessor {

   @Override
   public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
      String sumoUrl = processingEnv.getOptions().get("url");
      String accessID = processingEnv.getOptions().get("accessID");
      String accessKey = processingEnv.getOptions().get("accessKey");

      if (accessID != null && accessKey != null) {

         SumoClient sumoClient = Feign.builder().client(new OkHttpClient()).encoder(new GsonEncoder()).decoder(new GsonDecoder()).requestInterceptor(new BasicAuthRequestInterceptor(accessID, accessKey)).target(SumoClient.class, sumoUrl);

         Messager messager = processingEnv.getMessager();

         for (TypeElement typeElement : annotations) {

            PersonalFolder personal = sumoClient.getPersonalFolder();
            ContentPath personalPath = sumoClient.getContentPath(personal.getId());

            Set<String> folders = new HashSet<>();

            for (Element annotatedElement : env.getElementsAnnotatedWith(typeElement)) {
               SumoFolder folder = SumoUtils.resolveSumoFolder(annotatedElement);

               if (folder != null) {
                  folders.add(folder.value());
               }
            }

            for (String folder : folders) {
               try {
                  FolderResponse folderResponse = sumoClient.createFolder(new CreateFolder(folder, String.format("Folder for %s", folder), personal.getId()));
               } catch (FeignException.BadRequest e) {
               }
            }

            Map<String, String> folderIds = new HashMap<>();
            for (String folder : folders) {
               ContentInformation contentInformation = sumoClient.getContentByPath(personalPath.getPath() + "/" + folder);

               folderIds.put(folder, contentInformation.getId());
            }

            for (Element annotatedElement : env.getElementsAnnotatedWith(typeElement)) {
               SumoFolder folder = SumoUtils.resolveSumoFolder(annotatedElement);

               if (folder != null) {
                  String sumoQuery = SumoUtils.resolveQuery((ExecutableElement) annotatedElement);

                  String methodName = annotatedElement.getSimpleName().toString();

                  SearchContent searchContent = new SearchContent(sumoQuery, "-15m", false, "", null, Collections.emptyList());
                  CreateContent content = new CreateContent("SavedSearchWithScheduleSyncDefinition", methodName, format("Query for %s", methodName), searchContent);

                  String folderId = folderIds.get(folder.value());

                  sumoClient.createContent(folderId, content);
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
