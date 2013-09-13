
description = """
Retrieves information on an individual title.
"""

responseBody.example = """
{
  "id": 827362,
  "title": "Rise of the Guardians",
  "year": 2012,
  "genres": [
    "Animation",
    "Kids & Family",
    "Science Fiction & Fantasy"
  ],
  "mpaa_rating": "G",
  "runtime": 93,
  "releaseDates": "2012-11-21",
  "synopsis": "Rise of the Guardians is a 2012 American 3D computer-animated fantasy film based on William Joyce's The Guardians of Childhood book series and The Man in the Moon short film by Joyce and Reel FX",
  "studio": "DreamWorks Animation"
}
"""



queryParam("start_idx") {
    validationRules = "Integer >=0"
    description = "The start index of the results."
}
pathParam("id") {
    validationRules = "Integer >=0"
    description = "A valid title id."
}
queryParam("field") { description = """
If present, instead of returning all the details only this field will be returned. 
Multiple fields can be specified in the same request.
"""  }



