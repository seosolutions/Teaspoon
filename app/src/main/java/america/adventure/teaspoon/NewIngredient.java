package america.adventure.teaspoon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

// TODO: delete ingredient
public class NewIngredient extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private String measurement = "";
    private IngredientTuple newIngredient;
    private IngredientTuple oldIngredient;
    private EditText ingredient_name;
    private EditText amount;
    private int request_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ingredient);

        Spinner spinner = (Spinner) findViewById(R.id.measurement);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.measurements, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        ingredient_name = (EditText)findViewById(R.id.ingredientName);
        amount = (EditText)findViewById(R.id.amount);
        request_code = (int) getIntent().getExtras().get("request_code");

        if (request_code == Utils.UPDATE_INGREDIENT_CODE) {
            newIngredient = (IngredientTuple) getIntent().getExtras().get("ingredient");
            oldIngredient = (IngredientTuple) getIntent().getExtras().get("ingredient");

            // Populate text boxes
            ingredient_name.setText(newIngredient.getIngredient_name());
            amount.setText(Double.toString(newIngredient.getAmount()));
            spinner.setSelection(Utils.UNITS.indexOf(newIngredient.getMeasure()));
        } else if (request_code == Utils.NEW_INGREDIENT_CODE) {
            newIngredient = new IngredientTuple();
        }

        findViewById(R.id.ok2_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent();

                try {
                    newIngredient.setIngredient_name(ingredient_name.getText().toString());
                    newIngredient.setAmount(Double.valueOf(amount.getText().toString()));
                    newIngredient.setMeasure(measurement);

                    if (request_code == Utils.UPDATE_INGREDIENT_CODE)
                        i.putExtra("old_ingredient", oldIngredient);

                    i.putExtra("new_ingredient", newIngredient);
                } catch (Exception e) {
                    // Do nothing
                }

                // Setting resultCode to 100 to identify on old activity
                setResult(Utils.INGREDIENT_CODE, i);

                //Closing SecondScreen Activity
                finish();
            }
        });

        findViewById(R.id.cancel2_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Closing SecondScreen Activity
                finish();
            }
        });

        findViewById(R.id.delete2_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (request_code == Utils.UPDATE_INGREDIENT_CODE) {
                    Intent i = new Intent();
                    i.putExtra("delete_ingredient", oldIngredient);
                    //Closing SecondScreen Activity
                    setResult(Utils.DELETE_INGREDIENT_CODE, i);
                }

                finish();
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // Utils.showToast(parent.getItemAtPosition(pos).toString(), 0, this);
        measurement = (String) parent.getItemAtPosition(pos);

        if (request_code == Utils.UPDATE_INGREDIENT_CODE) {
            String _ingredient_name = ingredient_name.getText().toString();
            if (!_ingredient_name.equals("")) {
                newIngredient.setIngredient_name(_ingredient_name);
                try {
                    // Do unit conversion
                    double _amount = Double.valueOf(amount.getText().toString());
                    newIngredient.setAmount(_amount);
                    newIngredient.conversion(measurement);
                    amount.setText(Double.toString(newIngredient.getAmount()));
                } catch (Exception e) {
                    // Do nothing
                }
            }
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
