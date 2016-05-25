package america.adventure.teaspoon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


// TODO: customize recipe name
public class NewRecipe extends AppCompatActivity {
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    private ArrayList<String> ingredient_names = new ArrayList<>();
    private ArrayList<IngredientTuple> ingredients;

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ingredients = (ArrayList<IngredientTuple>) getIntent().getExtras().get("recipe");
        for (IngredientTuple listItem : ingredients) {
            ingredient_names.add(listItem.toString());
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ingredient_names);
        final ListView listview = (ListView) findViewById(R.id.ingredient_list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                /*view.animate().setDuration(1000).alpha(0).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                ingredients.remove(ingredient_names.indexOf(item));
                                ingredient_names.remove(item);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });*/
                IngredientTuple ingredient = ingredients.get(ingredient_names.indexOf(item));
                Intent i = new Intent(getApplicationContext(), NewIngredient.class);
                i.putExtra("request_code", Utils.UPDATE_INGREDIENT_CODE);
                i.putExtra("ingredient", ingredient);
                startActivityForResult(i, Utils.UPDATE_INGREDIENT_CODE);
            }
        });

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), NewIngredient.class);
                i.putExtra("request_code", Utils.NEW_INGREDIENT_CODE);
                startActivityForResult(i, Utils.NEW_INGREDIENT_CODE);
            }
        });

        findViewById(R.id.ok1_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent();
                // Sending param key as 'website' and value as 'androidhive.info'
                i.putExtra("recipe", ingredients);

                // If this is a new recipe
                if (((ArrayList<IngredientTuple>) getIntent().getExtras().get("recipe")).size() == 0) {
                    setResult(Utils.NEW_RECIPE_CODE, i);
                } else {
                    // If this recipe already exists
                    setResult(Utils.UPDATE_RECIPE_CODE, i);
                }

                //Closing SecondScreen Activity
                finish();
            }
        });

        findViewById(R.id.cancel1_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Closing SecondScreen Activity
                finish();
            }
        });

        findViewById(R.id.delete1_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent();
                setResult(Utils.DELETE_RECIPE_CODE, i);

                //Closing SecondScreen Activity
                finish();
            }
        });
    }

    // Function to read the result from newly created activity
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Utils.INGREDIENT_CODE) {
            try {
                IngredientTuple newIngredient = (IngredientTuple) data.getExtras().get("new_ingredient");
                if (requestCode == Utils.UPDATE_INGREDIENT_CODE) {
                    IngredientTuple oldIngredient = (IngredientTuple) data.getExtras().get("old_ingredient");
                    System.out.println(Utils.UPDATE_INGREDIENT_CODE + ": " + ingredients.remove(oldIngredient));
                    System.out.println(Utils.UPDATE_INGREDIENT_CODE + ": " + ingredient_names.remove(oldIngredient.toString()));
                }

                ingredients.add(newIngredient);
                ingredient_names.add(newIngredient.toString());
            } catch (Exception e) {
                // Do nothing
            }
        } else if (resultCode == Utils.DELETE_INGREDIENT_CODE) {
            IngredientTuple oldIngredient = (IngredientTuple) data.getExtras().get("delete_ingredient");
            System.out.println(Utils.DELETE_INGREDIENT_CODE + ": " + ingredients.remove(oldIngredient));
            System.out.println(Utils.DELETE_INGREDIENT_CODE + ": " + ingredient_names.remove(oldIngredient.toString()));
        }

        adapter.notifyDataSetChanged();
    }
}
