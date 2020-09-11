package org.message.logger.tools.annotations.sumologic.rest.processor.clients.model;

public class SavedSearchWithScheduleSyncDefinition {

   private String type = "SavedSearchWithScheduleSyncDefinition";

   private String name;

   private String description;

   private SearchContent search;

   public SavedSearchWithScheduleSyncDefinition(String name, String description, SearchContent search) {
      this.name = name;
      this.description = description;
      this.search = search;
   }
}
