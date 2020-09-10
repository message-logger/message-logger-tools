package org.message.logger.tools.annotations.sumologic.rest.processor.clients;

import feign.Headers;
import feign.RequestLine;
import org.message.logger.tools.annotations.sumologic.rest.processor.clients.model.CreateFolder;
import org.message.logger.tools.annotations.sumologic.rest.processor.clients.model.PersonalFolder;

public interface SumoClient {

   @RequestLine("GET /content/folders/personal")
   PersonalFolder getPersonalFolder();

   @RequestLine("POST /content/folders")
   @Headers("Content-Type: application/json")
   void createFolder(CreateFolder folder);
}
