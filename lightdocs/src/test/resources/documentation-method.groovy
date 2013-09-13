// single-line comment

/*
Multi line comment
*/


description = "Operation description, example 1"

requestBody.description = """
  This is a sample description, written in a multi line string (delimited with triple line quotes).
  Multi line strings can contain code and variables, and do not need to escape <html/> or \n &nsbs;"
  This string was evaluated at ${ new Date()}
"""
requestBody.example = "RequestBody Example."

headerParam("X-Auth") {
  description = "This is a sample documentation for header parameter X-Auth"
  required = true
  defaultValue = "def-auth"
  validationRules = "validation rules A"
}

queryParam("q1") {
  validationRules = "must be regexp x"
}

pathParam("id1") {
  description = "This is a sample documentation for a path parameter."
}