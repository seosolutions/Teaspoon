package america.adventure.teaspoon;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Lumber on 5/24/16.
 */
public class Utils {
    public static final int INGREDIENT_CODE = 100;
    public static final int NEW_RECIPE_CODE = 101;
    public static final int UPDATE_RECIPE_CODE = 102;

    public static void showToast(String s, int position, Activity thisActivity) {
        Toast toast = Toast.makeText(thisActivity.getApplicationContext(), s,
                Toast.LENGTH_SHORT);
        toast.setGravity(position, 0, 0);
        toast.show();
    }
}
