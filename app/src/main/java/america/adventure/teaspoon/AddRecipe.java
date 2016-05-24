package america.adventure.teaspoon;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AddRecipe extends AppCompatActivity {
    private ArrayList<ArrayList<IngredientTuple>> recipes = new ArrayList<>();
    private ArrayList<String> showList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private ArrayList<IngredientTuple> old_recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, showList);
        final ListView listview = (ListView) findViewById(R.id.recipe_list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                old_recipe = recipes.get(showList.indexOf(item));

                Intent i = new Intent(getApplicationContext(), AddIngredient.class);
                i.putExtra("recipe", old_recipe);
                startActivityForResult(i, Utils.UPDATE_RECIPE_CODE);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                Intent i = new Intent(getApplicationContext(), AddIngredient.class);
                i.putExtra("recipe", new ArrayList<IngredientTuple>());
                startActivityForResult(i, Utils.NEW_RECIPE_CODE);
            }
        });
    }

    // Function to read the result from newly created activity
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Utils.NEW_RECIPE_CODE || resultCode == Utils.UPDATE_RECIPE_CODE) {
            // Storing result in a variable called myvar
            ArrayList<IngredientTuple> new_recipe = (ArrayList<IngredientTuple>) data.getExtras().get("recipe");

            switch (resultCode) {
                case Utils.NEW_RECIPE_CODE:
                    recipes.add(new_recipe);
                    showList.add("Recipe " + recipes.size());
                    break;
                case Utils.UPDATE_RECIPE_CODE:
                    recipes.remove(old_recipe);
                    recipes.add(new_recipe);
                    break;
            }

            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
