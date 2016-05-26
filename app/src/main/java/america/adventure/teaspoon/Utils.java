package america.adventure.teaspoon;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
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

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

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
