Teaspoon
========
A simple Android app that could edit and save recipe of cakes.

Run
---
1. Import the whole project into Android Studio
2. Do whatever you want

Add a New Measure
------------------
1. Add a new measure below wherever `_TABLESPOON` appears in `app/src/main/java/america/adventure/teaspoon/Utils.java`
2. Add a private method with a name like `convertToNewMeasure` in `app/src/main/java/america/adventure/teaspoon/IngredientTuple.java`
3. Add a case inside the switch statement in `conversion` method of `app/src/main/java/america/adventure/teaspoon/IngredientTuple.java` to call the private method mentioned above in Step 2


