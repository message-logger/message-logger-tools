package org.message.logger.tools.annotations.sumologic.rest.processor.clients.model;

public class CreateFolder {

   private String name;

   private String description;

   private String parentId;

   public CreateFolder(String name, String description, String parentId) {
      this.name = name;
      this.description = description;
      this.parentId = parentId;
   }
}
