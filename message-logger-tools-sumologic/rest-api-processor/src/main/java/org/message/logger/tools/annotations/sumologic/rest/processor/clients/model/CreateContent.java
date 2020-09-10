package org.message.logger.tools.annotations.sumologic.rest.processor.clients.model;

public class CreateContent {

   private String type;

   private String name;

   private String description;

   private SearchContent search;

   public CreateContent(String type, String name, String description, SearchContent search) {
      this.type = type;
      this.name = name;
      this.description = description;
      this.search = search;
   }
}
