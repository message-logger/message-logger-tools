resource "sumologic_content" "query" {
  parent_id = "${parentId}"
  config = jsonencode({
    "type": "SavedSearchWithScheduleSyncDefinition",
    "name": "${name}",
    "description": "${description}",
    "search": {
      "queryText": "${queryText}",
      "defaultTimeRange": "-15m",
      "byReceiptTime": false,
      "viewName": "",
      "viewStartTime": null,
      "queryParameters": []
    },
    "searchSchedule": null
  })
}
