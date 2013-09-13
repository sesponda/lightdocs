
description = """
Retrieves the list of titles available in the catalog.
"""

responseBody.example = """
{
  "total": 591,
  "movies": [
    {
        "title": "Turbo",
        "year": 2013,
        "runtimeMins": "91"
    },{
        "title": "Inception",
        "year": 2010,
        "runtimeMins": "122"
    },{
        "title": "Rise of The Guardians",
        "year": 2012,
        "runtimeMins": "94"
    }
    ]
}
"""



queryParam("start_idx") {
    validationRules = "Integer >=0"
    description = "The start index of the results."
}
queryParam("max_results") { description = "The maximun number of records to return." }

queryParam("search_term") { description = "If set, only titles containing this character sequence will be returned (case insensitive)." }


