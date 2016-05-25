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
public class AddIngredient extends AppCompatActivity {
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> ingredient_names = new ArrayList<>();
    ArrayList<IngredientTuple> ingredients;

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
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
                view.animate().setDuration(1000).alpha(0).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                ingredients.remove(ingredient_names.indexOf(item));
                                ingredient_names.remove(item);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
            }
        });

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), NewIngredient.class);
                startActivityForResult(i, Utils.NEW_INGREDIENT_CODE);
            }
        });

        findViewById(R.id.ok1_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (!ingredients.isEmpty()) {
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
        if (resultCode == Utils.NEW_INGREDIENT_CODE) {

            // Storing result in a variable called myvar
            // get("website") 'website' is the key value result data
            android.text.SpannableString ingredient_name = (android.text.SpannableString) data.getExtras().get("ingredient_name");
            android.text.SpannableString amount = (android.text.SpannableString) data.getExtras().get("amount");
            String measurement = (String) data.getExtras().get("measurement");

            try {
                IngredientTuple newIngredient = new IngredientTuple(ingredient_name.toString(), Integer.valueOf(amount.toString()), measurement);
                ingredients.add(newIngredient);
                ingredient_names.add(newIngredient.toString());
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                // Do nothing
            }
        }
    }
}
