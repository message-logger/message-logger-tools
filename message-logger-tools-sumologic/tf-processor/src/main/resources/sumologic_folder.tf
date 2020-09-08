resource "sumologic_folder" "${snakeCaseName}" {
  name        = "${name}"
  description = "${description}"
  parent_id   = "${parentId}"
}
