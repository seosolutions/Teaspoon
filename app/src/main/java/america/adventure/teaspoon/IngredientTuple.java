package america.adventure.teaspoon;

/**
 * Created by Lumber on 5/24/16.
 */
public class IngredientTuple implements java.io.Serializable {
    private String ingredient_name;
    private int amount;
    private String measure;

    public IngredientTuple() {
        ingredient_name = "";
        amount = -1;
        measure = "";
    }

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

    public void setIngredient_name(String ingredient_name) { this.ingredient_name = ingredient_name; }

    public void setAmount(int amount) { this.amount = amount; }

    public void setMeasure(String measure) { this.measure = measure; }

    public String toString() {
        return ingredient_name + ": " + amount + " " + measure;
    }
}
