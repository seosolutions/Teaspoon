package america.adventure.teaspoon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class NewIngredient extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String measurement = "";

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

        findViewById(R.id.ok_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent();

                EditText ingredient_name = (EditText)findViewById(R.id.ingredientName);
                EditText amount = (EditText)findViewById(R.id.amount);

                i.putExtra("ingredient_name", ingredient_name.getText());
                i.putExtra("amount", amount.getText());
                i.putExtra("measurement", measurement);

                // Setting resultCode to 100 to identify on old activity
                setResult(Utils.INGREDIENT_CODE, i);

                //Closing SecondScreen Activity
                finish();
            }
        });

        findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Closing SecondScreen Activity
                finish();
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // Utils.showToast(parent.getItemAtPosition(pos).toString(), 0, this);
        measurement = (String) parent.getItemAtPosition(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
