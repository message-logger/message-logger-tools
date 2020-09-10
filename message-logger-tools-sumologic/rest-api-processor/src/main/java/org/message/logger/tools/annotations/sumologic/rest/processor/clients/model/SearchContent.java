package org.message.logger.tools.annotations.sumologic.rest.processor.clients.model;

import java.util.List;

public class SearchContent {

   private String queryText;

   private String defaultTimeRange;

   private boolean byReceiptTime;

   private String viewName;

   private String viewStartTime;

   private List<String> queryParameters;

   public SearchContent(String queryText,
                        String defaultTimeRange,
                        boolean byReceiptTime,
                        String viewName,
                        String viewStartTime,
                        List<String> queryParameters) {
      this.queryText = queryText;
      this.defaultTimeRange = defaultTimeRange;
      this.byReceiptTime = byReceiptTime;
      this.viewName = viewName;
      this.viewStartTime = viewStartTime;
      this.queryParameters = queryParameters;
   }
}
