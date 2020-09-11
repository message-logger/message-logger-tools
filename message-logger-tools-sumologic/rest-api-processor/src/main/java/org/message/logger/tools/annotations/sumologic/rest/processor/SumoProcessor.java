package org.message.logger.tools.annotations.sumologic.rest.processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import org.message.logger.tools.annotations.Message;
import org.message.logger.tools.annotations.sumologic.SumoFolder;
import org.message.logger.tools.annotations.sumologic.rest.processor.clients.SumoClient;
import org.message.logger.tools.annotations.sumologic.rest.processor.clients.model.FolderSyncDefinition;
import org.message.logger.tools.annotations.sumologic.rest.processor.clients.model.PersonalFolder;
import org.message.logger.tools.annotations.sumologic.rest.processor.clients.model.SavedSearchWithScheduleSyncDefinition;
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

         for (TypeElement typeElement : annotations) {
            PersonalFolder personal = sumoClient.getPersonalFolder();

            Map<String, List<SavedSearchWithScheduleSyncDefinition>> queriesByFolder = new HashMap<>();

            for (Element annotatedElement : env.getElementsAnnotatedWith(typeElement)) {
               SumoFolder folder = SumoUtils.resolveSumoFolder(annotatedElement);

               if (folder != null) {
                  String sumoQuery = SumoUtils.resolveQuery((ExecutableElement) annotatedElement);

                  String methodName = annotatedElement.getSimpleName().toString();

                  SearchContent searchContent = new SearchContent(sumoQuery, "-15m", false, "", null, Collections.emptyList());
                  SavedSearchWithScheduleSyncDefinition savedSearchWithScheduleSyncDefinition = new SavedSearchWithScheduleSyncDefinition(methodName, format("Query for %s", methodName), searchContent);

                  List<SavedSearchWithScheduleSyncDefinition> queries = queriesByFolder.computeIfAbsent(folder.value(), (k) -> new ArrayList<>());

                  queries.add(savedSearchWithScheduleSyncDefinition);
               }
            }

            Set<Map.Entry<String, List<SavedSearchWithScheduleSyncDefinition>>> entries = queriesByFolder.entrySet();

            for (Map.Entry<String, List<SavedSearchWithScheduleSyncDefinition>> entry : entries) {
               String folderName = entry.getKey();
               List<SavedSearchWithScheduleSyncDefinition> queries = entry.getValue();
               FolderSyncDefinition folderSyncDefinition = new FolderSyncDefinition(folderName, format("Folder for %s", folderName), queries);

               sumoClient.importFolder(personal.getId(), folderSyncDefinition);
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
