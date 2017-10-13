package com.example.formulae;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Home extends AppCompatActivity {

    private RecyclerView topicsRecyclerView;
    private EditText editText_search;
    private RecyclerView.Adapter topicsAdapter;
    private List < TopicsItem > topicsItems;
    private ProgressDialog mProgressDialog;

    static HashMap < String, String > descriptions;
    static HashMap < String, List < FormulasItem > > formulas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /*String[] fieldArray;
        ArrayAdapter fieldAdapter;

        fieldArray = new String[]{getString(R.string.field_math), getString(R.string.field_physics)};
        fieldAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, fieldArray);

        ListView fieldListView = (ListView) findViewById(R.id.fieldList);
        fieldListView.setAdapter(fieldAdapter);*/

        editText_search = (EditText) findViewById(R.id.editText_search);

        topicsRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_contacts);
        topicsRecyclerView.setHasFixedSize(true);
        topicsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        topicsItems = new ArrayList<>();

        topicsItems.add(new TopicsItem("Circle"));
        topicsItems.add(new TopicsItem("Triangle"));
        topicsItems.add(new TopicsItem("Rectangle"));
        topicsItems.add(new TopicsItem("Pythagorean Theorem"));
        topicsItems.add(new TopicsItem("Quadratic equation"));
        topicsItems.add(new TopicsItem("Trajectory"));

        descriptions = new HashMap<>();
        formulas = new HashMap<>();

        addDescription("Circle", "<p style = \"color : #1E90FF\">A circle is a round plane figure whose boundary (the circumference) consists of points equidistant from a fixed point (the center).</p>" +
                "<p style = \"text-align : center;\"><img src = \"image_circle.png\" width = \"100%\" alt = \"image of circle\"/></p>" +
                "<p>Suppose, radius = r. So the diameter = 2r. The ratio of circumference and the diameter is always a constant." +
                " The constant is called &pi; (Pi). So the circumference = diameter * &pi; = 2r * &pi; = 2&pi;r.</p>");
        addDescription("Triangle", "<p style = \"color : #1E90FF\">A triangle is a plane figure with three straight sides and three angles." +
                " Generally a triangle is formed when three distinct non-collinear points gets connected by three straight lines.</p>" +
                "<p style = \"text-align : center;\"><img src = \"image_triangle.jpg\" width = \"100%\" alt = \"image of triangle\"/></p>" +
                "<p></p>");
        addDescription("Rectangle", "<p style = \"color : #1E90FF\">A rectangle is a quadrilateral with four right angles" +
                " (A quadrilateral is a polygon with four edges (or sides) and four vertices or corners).</p>" +
                "<p style = \"text-align : center;\"><img src = \"image_rectangle.jpg\" width = \"100%\" alt = \"image of rectangle\"/></p>" +
                "<p>All angles of a rectangle are equal to 90 degree. Any two opposite sides are equal and any two adjacent sides are unequal.</p>");
        addDescription("Pythagorean Theorem", "<p style = \"color : #1E90FF\">Pythagorean theorem states that the square of the hypotenuse (the side opposite the right angle)" +
                " is equal to the sum of the squares of the other two sides.</p>" +
                "<p style = \"text-align : center;\"><img src = \"image_pythagoreanTheorem.png\" width = \"100%\" alt = \"image of pythagorean theorem\"/></p>" +
                "<p></p>");
        addDescription("Quadratic equation", "<p style = \"color : #1E90FF\">In algebra, a quadratic equation (from the Latin quadratus for \"square\") is any equation having the form</p>" +
                "<h2 style = \"text-align : center; color : ##001BA0\">ax<sup>2</sup> + bx + c = 0</h2>" +
                "<p style = \"color : #1E90FF\">where a &ne; 0.</p>" +
                "<p>If a = 0, then the equation is linear, not quadratic.</p>");
        addDescription("Trajectory", "<p style = \"color : #1E90FF\">Trajectory is the path followed by a projectile flying or an object moving under the action of given forces.</p>" +
                "<p style = \"text-align : center;\"><img src = \"image_trajectory.png\" width = \"100%\" alt = \"image of 2D trajectory\"/></p>" +
                "<p style = \"text-align : center;\"><img src = \"image_trajectory_two.png\" width = \"100%\" alt = \"image of 2D trajectory\"/></p>" +
                "<p>Shape of trajectory is parabola.</p>");

        addFormulas("Circle", new ArrayList<> (Arrays.asList(
                new FormulasItem("Area of circle", "$$\\pi r^2$$", "where r = radius",
                        new String[]{"r"}, "result = PI*r*r", "Area of circle"),
                new FormulasItem("Circumference of circle", "$$2 \\pi r$$", "where r = radius",
                        new String[]{"r"}, "result = 2*PI*r", "Circumference of circle")
        )));
        addFormulas("Triangle",  new ArrayList<> (Arrays.asList(
                new FormulasItem("Area of triangle (1)", "$$\\Delta = \\frac{1}{2} hb$$", "where h = height, b = base",
                        new String[]{"h", "b"}, "result = 0.5*h*b", "Area of triangle"),
                new FormulasItem("Area of triangle (2)", "$$\\Delta = \\frac{1}{2} ab sinC$$", "where a,b = any two sides of the triangle, C = angle between a and b (in Degree)",
                        new String[]{"a", "b", "C"}, "result = 0.5*a*b*Math.sin(C*PI/180.0)", "Area of triangle")
        )));
        addFormulas("Rectangle",  new ArrayList<> (Arrays.asList(
                new FormulasItem("Area of rectangle", "$$L*B$$", "where L = length, B = base",
                        new String[]{"L", "B"}, "result = L*B", "Area of rectangle"),
                new FormulasItem("Length of diagonal", "$$\\sqrt{L^2 + B^2}$$", "where L = length, B = base",
                        new String[]{"L", "B"}, "result = Math.sqrt(L*L + B*B)", "Length of diagonal")
        )));
        addFormulas("Pythagorean Theorem",  new ArrayList<> (Arrays.asList(
                new FormulasItem("Pythagorean Theorem", "$$a^2 + b^2 = c^2$$", "where c = hypotenuse and a, b = other two sides",
                        new String[]{"a", "b"}, "result = Math.sqrt(a*a + b*b)", "c")
        )));
        addFormulas("Quadratic equation",  new ArrayList<> (Arrays.asList(
                new FormulasItem("Quadratic equation", "$$ax^2 + bx + c = 0$$ $$x = \\frac{-b \\pm \\sqrt{b^2-4ac}}{2a}$$", "where a, b, and c are the coefficients of the equation and a &ne; 0",
                        new String[]{"a", "b", "c"}, "result = String.valueOf((-b + Math.sqrt(b*b - 4.0*a*c)) / (2.0*a)) + \", \" + String.valueOf((-b - Math.sqrt(b*b - 4.0*a*c)) / (2.0*a))", "x")
        )));
        addFormulas("Trajectory",  new ArrayList<> (Arrays.asList(
                new FormulasItem("Maximum height reached", "$$H = \\frac{v_0^2 sin^2\\theta_0}{2g}$$", "where \\(v_0\\) = initial velocity, \\(\\theta_0\\) = initial throwing angle",
                        new String[]{"v0", "theta0"}, "result = (v0*v0 * Math.sin(theta0*PI/180.0)*Math.sin(theta0)) / (2.0 * g)", "H"),
                new FormulasItem("Time of flight", "$$T = \\frac{2v_0 sin\\theta_0}{g}$$", "where \\(v_0\\) = initial velocity, \\(\\theta_0\\) = initial throwing angle",
                        new String[]{"v0", "theta0"}, "result = (2 * v0 * Math.sin(theta0*PI/180.0)) / (g)", "T"),
                new FormulasItem("Horizontal Range", "$$T = \\frac{2v_0 sin\\theta_0}{g}$$", "where \\(v_0\\) = initial velocity, \\(\\theta_0\\) = initial throwing angle",
                        new String[]{"v0", "theta0"}, "result = (2 * v0 * Math.sin(theta0*PI/180.0)) / (g)", "T")
        )));

        topicsAdapter = new TopicsRecyclerViewAdapter(topicsItems, this);
        topicsRecyclerView.setAdapter(topicsAdapter);
        //topicsAdapter.notifyDataSetChanged();

        addTextListener();
    }

    void addDescription(String title, String description) {
        descriptions.put(title, description);
    }

    void addFormulas(String topicTitle, List < FormulasItem > newFormulas) {
        formulas.put(topicTitle, newFormulas);
    }

    public void addTextListener() {

        editText_search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();

                final List < TopicsItem > filteredList = new ArrayList<>();

                for (int i = 0; i < topicsItems.size(); i++) {
                    final String text = topicsItems.get(i).getTopicName().toLowerCase();

                    if (text.contains(query)) {
                        filteredList.add(topicsItems.get(i));
                    }
                }

                topicsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                topicsAdapter = new TopicsRecyclerViewAdapter(filteredList, getApplicationContext());
                topicsRecyclerView.setAdapter(topicsAdapter);
                topicsAdapter.notifyDataSetChanged();  // data set changed
            }
        });
    }

    /*void addSingleFormula(String topicTitle, String formulaTitle, String formula, String notations) {
        List < FormulasItem > existingFormulas = new ArrayList<>();
        if (formulas.get(topicTitle) != null) formulas.get(topicTitle);

        existingFormulas.add(new FormulasItem(formulaTitle, formula, notations));

        formulas.put(topicTitle, existingFormulas);
    }*/
}
