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

class IngredientTuple {
    private String ingredient_name;
    private int amount;
    private String measure;

    public IngredientTuple(String ingredient_name, int amount, String measure) {
        this.ingredient_name = ingredient_name;
        this.amount = amount;
        this.measure = measure;
    }

    public String getIngredient_name() {
        return ingredient_name;
    }

    public int getAmount() {
        return amount;
    }

    public String getMeasure() {
        return measure;
    }
}

public class AddIngredient extends AppCompatActivity {
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> showList = new ArrayList<>();
    ArrayList<IngredientTuple> listItems = new ArrayList<>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, showList);

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
                                showList.remove(item);
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
                startActivityForResult(i, Utils.INGREDIENT_CODE);
            }
        });

        findViewById(R.id.ok_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent();
                // Sending param key as 'website' and value as 'androidhive.info'
                i.putExtra("website", "AndroidHive.info");
                // Setting resultCode to 100 to identify on old activity
                setResult(Utils.RECIPE_CODE, i);

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

            // Storing result in a variable called myvar
            // get("website") 'website' is the key value result data
            android.text.SpannableString ingredient_name = (android.text.SpannableString) data.getExtras().get("ingredient_name");
            android.text.SpannableString amount = (android.text.SpannableString) data.getExtras().get("amount");
            String measurement = (String) data.getExtras().get("measurement");

            listItems.add(new IngredientTuple(ingredient_name.toString(), Integer.valueOf(amount.toString()), measurement));
            showList.add(ingredient_name.toString() + ": " + amount.toString() + measurement);
            adapter.notifyDataSetChanged();
        }
    }
}
