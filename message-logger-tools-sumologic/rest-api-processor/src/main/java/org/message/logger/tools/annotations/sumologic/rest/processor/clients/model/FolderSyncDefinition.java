package org.message.logger.tools.annotations.sumologic.rest.processor.clients.model;

import java.util.List;

public class FolderSyncDefinition {

   private String type = "FolderSyncDefinition";

   private String name;

   private String description;

   private List<SavedSearchWithScheduleSyncDefinition> children;

   public FolderSyncDefinition(String name,
                               String description,
                               List<SavedSearchWithScheduleSyncDefinition> children) {
      this.name = name;
      this.description = description;
      this.children = children;
   }
}
