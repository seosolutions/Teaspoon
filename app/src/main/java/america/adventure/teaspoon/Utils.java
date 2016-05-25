package america.adventure.teaspoon;

import android.app.Activity;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Lumber on 5/24/16.
 */
public class Utils {
    public static final int INGREDIENT_CODE = 98;
    public static final int UPDATE_INGREDIENT_CODE = 99;
    public static final int NEW_INGREDIENT_CODE = 100;
    public static final int DELETE_INGREDIENT_CODE = 101;

    public static final int NEW_RECIPE_CODE = 102;
    public static final int UPDATE_RECIPE_CODE = 103;
    public static final int DELETE_RECIPE_CODE = 104;

    public static final String _OUNCE = "ounce";
    public static final String _GRAM = "gram";
    public static final String _ML = "ml";
    public static final String _TEASPOON = "teaspoon";
    public static final String _TABLESPOON = "tablespoon";

    // Make sure that the sequence of units here is same as the one in res/values/strings.xml
    public static ArrayList<String> UNITS = new ArrayList<String>() {{
        add(_GRAM);
        add(_OUNCE);
        add(_ML);
        add(_TEASPOON);
        add(_TABLESPOON);
    }};

    public static void showToast(String s, int position, Activity thisActivity) {
        Toast toast = Toast.makeText(thisActivity.getApplicationContext(), s,
                Toast.LENGTH_SHORT);
        toast.setGravity(position, 0, 0);
        toast.show();
    }
}
