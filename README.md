# receipt-service
standalone java application which allows users to manage their favourite recipes.
Objective
This standalone application which allows users to manage their favourite recipes. It should allow adding, updating, removing and fetching recipes. Additionally users should be able to filter available recipes based on one or more of the following criteria:

Whether the dish is vegetarian
The number of servings
Specific ingredients (either include or exclude)
Text search within the instructions.

For example, the API should be able to handle the following search requests:
•  All vegetarian recipes
•  Recipes that can serve 4 persons and have “potatoes” as an ingredient
•  Recipes without “salmon” as an ingredient that has “oven” in the instructions.

##Prerequisite

Java 8
Maven

Database 
In memory H2 database is used

Table Recipe 
Fields :
     id
     name
     instructions
     servings
     type
     ingredients
    
   Recipe table has one to many mapping with Ingredient 
   
   Table Ingredient 
   Fields :
          id
          name
To run the application:

mvn  spring-boot:run

REST API

GET ALL RECIPE:
GET /recipes

curl --location --request GET 'localhost:8080/recipes'

RESPONSE
[
    {
        "id": 2,
        "name": "pizza",
        "instructions": "bake pizza",
        "servings": 2,
        "type": "non-vegeterian",
        "ingredients": [
            {
                "id": 3,
                "name": "flour"
            }
        ]
    },
    {
        "id": 3,
        "name": "baked potato",
        "instructions": "boiled potato and bake at 200 degree celcius",
        "servings": 2,
        "type": "vegeterian",
        "ingredients": [
            {
                "id": 4,
                "name": "potato"
            },
            {
                "id": 5,
                "name": "cheese"
            }
        ]
    }
]

ADD RECIPE:

POST /recipe

REQUEST:
curl --location --request POST 'localhost:8080/recipes' \
--header 'Content-Type: application/json' \
--data-raw ' {
    "name": "baked potato",
    "instructions": "boiled potato and bake at 200 degree celcius",
    "type": "vegeterian",
    "servings": 2,
    "ingredients": [ {
        "name":"potato"
    },{
        "name":"cheese"
    }
     ]
 }'
 RESPONSE
 {
    "id": 3,
    "name": "baked potato",
    "instructions": "boiled potato and bake at 200 degree celcius",
    "servings": 2,
    "type": "vegeterian",
    "ingredients": [
        {
            "id": 4,
            "name": "potato"
        },
        {
            "id": 5,
            "name": "cheese"
        }
    ]
}

UPDATE RECIPE

PUT /recipe/name
request
curl --location --request PUT 'localhost:8080/recipes/pizza' \
--header 'Content-Type: application/json' \
--data-raw ' {
    "name": "pizza",
    "instructions": "bake pizza",
    "type": "non-vegeterian",
    "servings": 2,
    "ingredients": [ { "name" :"flour"}
     ]
 }
 '
 RESPONSE
 {
    "id": 1,
    "name": "pizza",
    "instructions": "bake pizza",
    "servings": 2,
    "type": "non-vegeterian",
    "ingredients": [
        {
            "id": 2,
            "name": "flour"
        }
    ]
}
DELETE THE RECIPE
DELETE /recipe/name

REQUEST
curl --location --request DELETE 'localhost:8080/recipes/pizza'
RESPONSE
Recipe pizza deleted successfully!

FILTER RECIPE BASED ON CRITERIA

REQUEST
curl --location --request GET 'localhost:8080/selected?ingredientCondition=include' \
--header 'Content-Type: application/json' \
--data-raw ' {
    "name":  null,
    "instructions": null,
    "type":null,
    "servings": null,
    "ingredients": [{
        "id" : "4",
        "name" : "potato"}]
 }'
 
 RESPONSE:
 recipe.getIngredients().stream().forEach(ingredientName -> {
                        if (null != ingredientName) {
                            if (StringUtils.isNotBlank(ingredientName.getName())) {
                                ingredientPredicates.add(criteriaBuilder.equal(hnJoin.get("name"), ingredientName.getName()));
                            }
                        }
                    });
