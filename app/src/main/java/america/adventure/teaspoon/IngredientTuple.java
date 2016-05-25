package america.adventure.teaspoon;

import javax.measure.Measure;
import javax.measure.converter.UnitConverter;
import javax.measure.quantity.*;
import static javax.measure.unit.NonSI.*;
import static javax.measure.unit.SI.*;

/**
 * Created by Lumber on 5/24/16.
 */

public class IngredientTuple implements java.io.Serializable {
    private String ingredient_name;
    private double amount;
    private String measure;

    public IngredientTuple() {
        ingredient_name = "";
        amount = -1;
        measure = "";
    }

    public IngredientTuple(String ingredient_name, double amount, String measure) {
        this.ingredient_name = ingredient_name;
        this.amount = amount;
        this.measure = measure;
    }

    public String getIngredient_name() {
        return ingredient_name;
    }

    public double getAmount() {
        return amount;
    }

    public String getMeasure() {
        return measure;
    }

    public void setIngredient_name(String ingredient_name) { this.ingredient_name = ingredient_name; }

    public void setAmount(double amount) { this.amount = amount; }

    public void setMeasure(String measure) { this.measure = measure; }

    @Override
    public String toString() {
        return ingredient_name + ": " + String.format( "%.2f", amount ) + " " + measure;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IngredientTuple) {
            IngredientTuple other = (IngredientTuple) obj;
            return other.getIngredient_name().equals(ingredient_name)
                    && other.getAmount() == amount
                    && other.getMeasure().equals(measure);
        } else
            return false;
    }

    public void conversion(String newMeasure) {
        switch (newMeasure) {
            case Utils._OUNCE:
                convertToOunce();
                break;
            case Utils._GRAM:
                convertToGram();
                break;
            case Utils._ML:
                convertToMl();
                break;
            case Utils._TABLESPOON:
                convertToTablespoon();
                break;
            case Utils._TEASPOON:
                convertToTeaspoon();
                break;
        }
    }

    // Use JScience
    // http://jscience.org/
    // http://stackoverflow.com/questions/1193810/java-metric-unit-conversion-library
    private void convertToGram() {
        // ATTENTION: Only do conversion when original unit is OUNCE
        if (measure.equals(Utils._OUNCE)) {
            UnitConverter converter = OUNCE.getConverterTo(GRAM);

            measure = Utils._GRAM;
            amount = converter.convert(Measure.valueOf(amount, OUNCE).doubleValue(OUNCE));
        }
    }

    private void convertToOunce() {
        if (measure.equals(Utils._GRAM)) {
            UnitConverter converter = GRAM.getConverterTo(OUNCE);

            measure = Utils._OUNCE;
            amount = converter.convert(Measure.valueOf(amount, GRAM).doubleValue(GRAM));
        }
    }

    private void convertToMl() {
        switch (measure) {
            case Utils._TEASPOON:
                measure = Utils._ML;
                amount *= 4.92892;
                break;
            case Utils._TABLESPOON:
                measure = Utils._ML;
                amount *= 14.7868;
                break;
        }
    }

    private void convertToTeaspoon() {
        switch (measure) {
            case Utils._ML:
                measure = Utils._TEASPOON;
                amount *= 0.202884;
                break;
            case Utils._TABLESPOON:
                measure = Utils._TEASPOON;
                amount *= 3;
                break;
        }
    }

    private void convertToTablespoon() {
        switch (measure) {
            case Utils._ML:
                measure = Utils._TABLESPOON;
                amount *= 0.067628;
                break;
            case Utils._TEASPOON:
                measure = Utils._TABLESPOON;
                amount *= 0.333333;
                break;
        }
    }
}
