package america.adventure.teaspoon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

// TODO: delete recipe
public class AddRecipe extends AppCompatActivity {
    private ArrayList<ArrayList<IngredientTuple>> recipes = new ArrayList<>();
    private ArrayList<String> recipe_names = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private ArrayList<IngredientTuple> old_recipe = null;

    // Get the directory for the user's public download directory.
    private File xml_file = new File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS), "RECIPES.xml");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        readFromXml();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, recipe_names);
        final ListView listview = (ListView) findViewById(R.id.recipe_list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                old_recipe = recipes.get(recipe_names.indexOf(item));

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

        if (resultCode == Utils.DELETE_RECIPE_CODE) {
            if (old_recipe != null) {
                recipe_names.remove(recipes.indexOf(old_recipe));
                recipes.remove(old_recipe);
            }
        } else {
            ArrayList<IngredientTuple> new_recipe = (ArrayList<IngredientTuple>) data.getExtras().get("recipe");

            switch (resultCode) {
                case Utils.NEW_RECIPE_CODE:
                    recipes.add(new_recipe);
                    recipe_names.add("Recipe " + recipes.size());
                    break;
                case Utils.UPDATE_RECIPE_CODE:
                    recipes.remove(old_recipe);
                    recipes.add(new_recipe);
                    break;
            }
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void readFromXml() {
        Document dom;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the
            // XML file
            dom = db.parse(xml_file.getAbsolutePath());

            Element doc = dom.getDocumentElement();

            NodeList nl = doc.getElementsByTagName("*");
            Element e;
            Node n;
            NamedNodeMap nnm;

            String attrname;
            String attrval;
            int len = nl.getLength();

            for (int j = 0; j < len; j++) {
                e = (Element) nl.item(j);
                System.out.println(e.getTagName() + ":");
                nnm = e.getAttributes();

                ArrayList<IngredientTuple> recipe = new ArrayList<>();
                if (nnm != null) {
                    IngredientTuple ingredient = new IngredientTuple();
                    for (int i = 0; i < nnm.getLength(); i++) {
                        n = nnm.item(i);
                        attrname = n.getNodeName();
                        attrval = n.getNodeValue();
                        switch (attrname) {
                            case "ingredient_name":
                                ingredient.setIngredient_name(attrval);
                                break;
                            case "amount":
                                ingredient.setAmount(Integer.valueOf(attrval));
                                break;
                            case "measure":
                                ingredient.setMeasure(attrval);
                                break;
                        }
                        recipe.add(ingredient);
                        // System.out.print(" " + attrname + " = " + attrval);
                    }
                }
                recipes.add(recipe);
                // System.out.println();
            }
        } catch (Exception e) {
            Utils.showToast(e.toString(), 0, this);
        }
    }

    public void writeToXml() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            for (ArrayList<IngredientTuple> recipe : recipes) {
                int recipe_index = recipes.indexOf(recipe);
                String recipe_name = recipe_names.get(recipe_index);

                Element recipeElement = doc.createElement(recipe_name);
                doc.appendChild(recipeElement);

                for (IngredientTuple ingredientTuple : recipe) {
                    recipeElement.setAttribute("ingredient_name", ingredientTuple.getIngredient_name());
                    recipeElement.setAttribute("amount", Integer.toString(ingredientTuple.getAmount()));
                    recipeElement.setAttribute("measure", ingredientTuple.getMeasure());
                }
            }

            // write the content into xml xml_file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.transform(new DOMSource(doc), new StreamResult(xml_file));
        } catch (Exception e) {
            // Do nothing
            Utils.showToast(e.toString(), 0, this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_exit:
                finish();
                return true;
            case R.id.action_save:
                writeToXml();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
