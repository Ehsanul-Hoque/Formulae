package com.example.formulae;

import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import bsh.EvalError;
import bsh.Interpreter;
import io.github.kexanie.library.MathView;

public class DetailsActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    static SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private static String topicName;
    static FormulasItem selectedFormulaItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        topicName = getIntent().getStringExtra("topicName");
        setTitle(topicName);
        selectedFormulaItem = new FormulasItem("", "", "", new String[]{""}, "result = 0", "result");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        View rootView;

        TextView textView_description;
        WebView webView_description;

        RecyclerView recyclerView_formulas;

        TextView textView_calculatorNotification;
        TextView textView_formulaTitle;
        MathView mathView_formula;
        MathView textView_notations;

        LinearLayout linearLayout_description;
        LinearLayout linearLayout_formulae;
        LinearLayout linearLayout_calculator;

        Button calculateButton;

        TextView[] myTextViews;   // create an empty array
        EditText[] myEditTexts;   // create an empty array
        TextView resultTextView;

        private RecyclerView.Adapter formulasAdapter;
        private List < FormulasItem > formulasItems;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_details, container, false);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            textView_description = (TextView) rootView.findViewById(R.id.textView_description);
            webView_description = (WebView) rootView.findViewById(R.id.webView_description);
            recyclerView_formulas = (RecyclerView) rootView.findViewById(R.id.recyclerView_formulas);
            textView_calculatorNotification = (TextView) rootView.findViewById(R.id.textView_calculatorNotification);
            textView_formulaTitle = (TextView) rootView.findViewById(R.id.textView_formulaTitle);
            mathView_formula = (MathView) rootView.findViewById(R.id.mathView_formula);
            textView_notations = (MathView) rootView.findViewById(R.id.textView_notations);

            linearLayout_description = (LinearLayout) rootView.findViewById(R.id.linearLayout_description);
            linearLayout_formulae = (LinearLayout) rootView.findViewById(R.id.linearLayout_formulae);
            linearLayout_calculator = (LinearLayout) rootView.findViewById(R.id.linearLayout_calculator);

            calculateButton = new Button(getContext());
            calculateButton.setId(R.id.button_calculate);
            calculateButton.setOnClickListener(this);

            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 0:
                    descriptionFragment();
                    break;
                case 1:
                    formulaeFragment();
                    break;
                case 2:
                    calculatorFragment();
                    break;
            }
            return rootView;
        }

        void descriptionFragment()
        {
            /*textView_description.setVisibility(View.GONE); // VISIBLE
            webView_description.setVisibility(View.VISIBLE);
            recyclerView_formulas.setVisibility(View.GONE);
            textView_formulaTitle.setVisibility(View.GONE);
            mathView_formula.setVisibility(View.GONE);
            textView_notations.setVisibility(View.GONE);*/
            textView_calculatorNotification.setVisibility(View.GONE);

            linearLayout_description.setVisibility(View.VISIBLE);
            linearLayout_formulae.setVisibility(View.GONE);
            linearLayout_calculator.setVisibility(View.GONE);

            linearLayout_calculator.removeView(calculateButton);

            textView_description.setText("This is the description.");
            //textView_description.setText(Html.fromHtml(Home.descriptions.get("Circle")));
            /*Drawable img;
            img = getResources().getDrawable(R.drawable.image_circle);
            //You need to setBounds before setCompoundDrawables , or it couldn't display
            img.setBounds(0, 0, 500, 500 * img.getMinimumHeight() / img.getMinimumWidth());

            textView_description.setCompoundDrawables(null, img, null, null);*/

            WebView descriptionContent;

            descriptionContent = (WebView) rootView.findViewById(R.id.webView_description);
            //descriptionContent.loadData(Home.descriptions.get("Circle"), "text/html", null);
            String data = "<body style=\"text-align:justify\">" + Home.descriptions.get(topicName) + "</body>";

            descriptionContent.loadDataWithBaseURL("file:///android_asset/", data, "text/html", "utf-8", null);
        }

        void formulaeFragment()
        {
            /*textView_description.setVisibility(View.GONE);
            webView_description.setVisibility(View.GONE);
            recyclerView_formulas.setVisibility(View.VISIBLE);
            textView_formulaTitle.setVisibility(View.GONE);
            mathView_formula.setVisibility(View.GONE);
            textView_notations.setVisibility(View.GONE);*/
            textView_calculatorNotification.setVisibility(View.GONE);

            linearLayout_description.setVisibility(View.GONE);
            linearLayout_formulae.setVisibility(View.VISIBLE);
            linearLayout_calculator.setVisibility(View.GONE);

            linearLayout_calculator.removeView(calculateButton);

            recyclerView_formulas.setHasFixedSize(true);
            recyclerView_formulas.setLayoutManager(new LinearLayoutManager(getContext()));

            /*formulasItems = new ArrayList<>();

            formulasItems.add(new FormulasItem("Area of circle", "$$\\pi r^2$$"));
            formulasItems.add(new FormulasItem("Circumference of circle", "$$2 \\pi r$$"));*/

            formulasAdapter = new FormulasRecyclerViewAdapter(Home.formulas.get(topicName), getContext(), getActivity());
            //formulasAdapter = new FormulasRecyclerViewAdapter(formulasItems, getContext(), getActivity());
            recyclerView_formulas.setAdapter(formulasAdapter);
            formulasAdapter.notifyDataSetChanged();

            /*RecyclerView recyclerView_formulas;
            RecyclerView.Adapter formulasAdapter;
            List<TopicsItem> formulasItems;
            //textView_description.setText("Here are all the formulas.");

            recyclerView_formulas = (RecyclerView) rootView.findViewById(R.id.recyclerView_formulas);
            recyclerView_formulas.setHasFixedSize(true);
            recyclerView_formulas.setLayoutManager(new LinearLayoutManager(getContext()));

            formulasItems = new ArrayList<>();

            formulasItems.add(new TopicsItem("Projectile"));
            formulasItems.add(new TopicsItem("Lami's Theorem"));
            formulasItems.add(new TopicsItem("Pythagorous's Theorem"));

            formulasAdapter = new TopicsRecyclerViewAdapter(formulasItems, getContext());
            recyclerView_formulas.setAdapter(formulasAdapter);
            formulasAdapter.notifyDataSetChanged();*/

            /*WebView articleContent;

            articleContent = (WebView) rootView.findViewById(R.id.webView_formula);
            articleContent.getSettings().setJavaScriptEnabled(true);
            articleContent.getSettings().setBuiltInZoomControls(true);*/

            /*try {
				InputStream is = getContext().getAssets().open("mathscribe.html");
				Reader r = new InputStreamReader(is);
				int size = is.available();
				char[] buffer = new char[size];
				r.read(buffer);
				r.close();
				String data = new String(buffer);
				is.close();

				data = data.replace("<body></body>", "<body>" + getString(R.string.formulas) + "</body>");

                //articleContent.loadUrl("file:///android_asset/mathscribe.html");
				//articleContent.loadData(data, "text/html", null);
				articleContent.loadDataWithBaseURL("file:///android_asset/", data, "text/html", "UTF-8", null);


            } catch (Exception e)
                e.printStackTrace();
            }*/
        }

        void calculatorFragment()
        {
            /*textView_description.setVisibility(View.GONE);
            webView_description.setVisibility(View.GONE);
            recyclerView_formulas.setVisibility(View.GONE);
            textView_formulaTitle.setVisibility(View.VISIBLE);
            mathView_formula.setVisibility(View.VISIBLE);
            textView_notations.setVisibility(View.VISIBLE);*/
            linearLayout_description.setVisibility(View.GONE);
            linearLayout_formulae.setVisibility(View.GONE);
            if (TextUtils.isEmpty(selectedFormulaItem.getTitle())) {
                textView_calculatorNotification.setVisibility(View.VISIBLE);
                linearLayout_calculator.setVisibility(View.GONE);
            } else {
                textView_calculatorNotification.setVisibility(View.GONE);
                linearLayout_calculator.setVisibility(View.VISIBLE);
            }

            textView_formulaTitle.setText(selectedFormulaItem.getTitle());
            //Toast.makeText(getContext(), "In : " + DetailsActivity.selectedFormulaItem.getTitle(), Toast.LENGTH_SHORT).show();
            //mathView_formula.setText("When \\(a \\ne 0\\), there are two solutions to \\(ax^2 + bx + c = 0\\) and they are $$x = {-b \\pm \\sqrt{b^2-4ac} \\over 2a}.$$");
            //mathView_formula.setText("This come from string. You can insert inline formula: \\(ax^2 + bx + c = 0\\) or displayed formula: $$\\sum_{i=0}^n i^2 = \\frac{(n^2+n)(2n+1)}{6}$$");
            mathView_formula.setText(selectedFormulaItem.getFormula());
            textView_notations.setText(selectedFormulaItem.getNotations());

            int len = selectedFormulaItem.getVariables().length;
            myTextViews = new TextView[len];
            myEditTexts = new EditText[len];

            for (int i = 0; i < len; i++) {
                // create a new LinearLayout
                final LinearLayout ioHolder = new LinearLayout(getContext());
                ioHolder.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ioHolder.setOrientation(LinearLayout.HORIZONTAL);

                // create a new TextView
                final TextView rowTextView = new TextView(getContext());
                // set some properties of rowTextView or something
                LinearLayout.LayoutParams rowTextViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                rowTextViewParams.setMargins(64, 16, 0, 0);
                rowTextView.setText(selectedFormulaItem.getVariables()[i] + " : ");
                rowTextView.setLayoutParams(rowTextViewParams);
                // add the TextView to the LinearLayout
                ioHolder.addView(rowTextView);
                // save a reference to the textview for later
                myTextViews[i] = rowTextView;

                // create a new EditText
                final EditText rowEditText = new EditText(getContext());
                // set some properties of rowEditText or something
                LinearLayout.LayoutParams rowEditTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                rowEditTextParams.setMargins(0, 16, 64, 0);
                rowEditText.setHint("Set value of " + selectedFormulaItem.getVariables()[i]);
                rowEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                rowEditText.setMaxLines(1);
                rowEditText.setLayoutParams(rowEditTextParams);
                //rowEditText.setId((int) selectedFormulaItem.getVariables()[i].charAt(0));
                // add the EditText and the EditText to the LinearLayout
                ioHolder.addView(rowEditText);
                // save a reference to the EditText for later
                myEditTexts[i] = rowEditText;

                linearLayout_calculator.addView(ioHolder);
            }

            calculateButton.setText("Calculate");
            LinearLayout.LayoutParams calculateButtonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            calculateButtonParams.setMargins(64, 32, 64, 0);
            calculateButton.setLayoutParams(calculateButtonParams);

            //LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            linearLayout_calculator.addView(calculateButton);

            // create a new TextView
            resultTextView = new TextView(getContext());
            // set some properties of rowTextView or something
            LinearLayout.LayoutParams rowTextViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            rowTextViewParams.setMargins(64, 32, 64, 0);
            resultTextView.setText("Result");
            resultTextView.setGravity(Gravity.CENTER);
            resultTextView.setLayoutParams(rowTextViewParams);
            resultTextView.setTextSize(20);
            resultTextView.setTextColor(getResources().getColor(R.color.primaryText));
            // add the TextView to the LinearLayout
            linearLayout_calculator.addView(resultTextView);
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            int len = selectedFormulaItem.getVariables().length;

            switch (id) {
                case R.id.button_calculate:
                    Interpreter interpreter = new Interpreter();
                    try {
                        for (int i = 0; i < len; i++) {
                            if (TextUtils.isEmpty(myEditTexts[i].getText().toString()))
                                interpreter.set(selectedFormulaItem.getVariables()[i], 0.0);
                            else
                                interpreter.set(selectedFormulaItem.getVariables()[i], Double.parseDouble(myEditTexts[i].getText().toString()));
                        }
                        interpreter.set("PI", Math.acos(-1.0));
                        interpreter.set("g", 9.81);
                        interpreter.eval(selectedFormulaItem.getCalculationCode());
                        //Toast.makeText(getContext(), interpreter.get("result").toString(), Toast.LENGTH_SHORT).show();

                        resultTextView.setText(selectedFormulaItem.getOutputTitle() + " = " + interpreter.get("result").toString());
                    } catch (EvalError evalError) {
                        evalError.printStackTrace();
                    }

                    break;
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Description";
                case 1:
                    return "Formulae";
                case 2:
                    return "Calculator";
            }
            return null;
        }
    }
}
