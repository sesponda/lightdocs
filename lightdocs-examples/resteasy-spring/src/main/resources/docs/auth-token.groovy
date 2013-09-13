
description = """
Authorization token needed to execute the request, obtained during the authentication request flow.
Each token expires after 24hs. A refresh token should be requested if the application will need to access the
API beyond the lifetime of a single access token. 
"""
required = true

validationRules = "A string representation of an UUID, example: efd38f80-1bef-11e3-991a-0002a5d5c51b"