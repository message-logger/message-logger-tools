package org.message.logger.tools.annotations.sumologic.rest.processor.clients;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.message.logger.tools.annotations.sumologic.rest.processor.clients.model.FolderSyncDefinition;
import org.message.logger.tools.annotations.sumologic.rest.processor.clients.model.PersonalFolder;

public interface SumoClient {

   @RequestLine("GET /content/folders/personal")
   PersonalFolder getPersonalFolder();

   @RequestLine("POST /content/folders/{folderId}/import?overwrite=true")
   @Headers("Content-Type: application/json")
   void importFolder(@Param("folderId") String folderId, FolderSyncDefinition content);

}
