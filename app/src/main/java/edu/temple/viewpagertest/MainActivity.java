package edu.temple.viewpagertest;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.URI;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ViewPager pager;        //Declaring the ViewPager
    EditText editText;      //Declaring the EditText which is used for URLs
    Button button;          //Declaring the Button that is used to change URLs
    String website;         //string used to store website URL

    ArrayList<FirstFragment> fragmentList = new ArrayList<>();  //Creating a ArrayList to store all the Fragments
    int NUM_FRAGS = 1;      //Used to create and increase the number of fragments (starts with 1 fragment
    int listIndex = 0;      //Used to add Fragments to the Array (increases when user hits "New Tab" button in ActionBar
    int index;              //Used to keep track of what Fragment is currently being displayed

    FirstFragment currentFrag;  //Used to store the current Fragment that is being viewed so only its webpage will change

    PagerAdapter pa;        //The adapter used to update the data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assigns the pager and sets the adapter for it
        pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        //assigns the EditText and Button
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        //gets the data if Activity was launched with intent
        final Uri data = getIntent().getData();

        //if 'data' has some data, turn it into a string and use that to load the website
        if (data != null) {

            website = data.toString();
            currentFrag.changeWebsite(website);
        }

        //What happens when the button is clicked
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                website = editText.getText().toString();    //gets whats inside the EditText and turns it into a string
                index = pager.getCurrentItem();             //gets the index of what fragment is currently visible
                currentFrag = fragmentList.get(index);      //sets currentFrag to the currently visible fragment
                currentFrag.changeWebsite(website);         //changes the website to what was typed by the user

            }
        });

    }

    //private FragmentPagerAdapter, used for 'pager' and 'pa'
    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //creates a new Fragment and adds it to the ArrayList ('fragmentList')
        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                default:
                    FirstFragment defaultFrag = new FirstFragment();
                    fragmentList.add(listIndex, defaultFrag);
                    listIndex++;
                    return defaultFrag;
            }
        }

        //The max number of Fragments to create (starts at 1 and increases when you hit
        //the 'newTab' button in the ActionBar
        @Override
        public int getCount() {
            return NUM_FRAGS;
        }
    }

    //Inflates the OptionsMenu with my buttons (check res -> menu -> menu_main.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Gets the MenuItem that was selected and handles the correct action
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.prevTab:      //When the user clicks '<-' button, the previous fragment is displayed

                //Changes the current visible item to the item that comes before the currentFrag in the ArrayList
                pager.setCurrentItem(pager.getCurrentItem()-1);
                return true;
            case R.id.newTab:       //When user clicks the '+' button a new fragment is created

                //creates a new fragment, adds it to the ArrayList and increases the max amount of fragments
                FirstFragment fragment = new FirstFragment();
                fragmentList.add(listIndex, fragment);
                NUM_FRAGS++;

                //lets the adapter know that data has been changed (required)
                pa = pager.getAdapter();
                pa.notifyDataSetChanged();

                //sets the newly created fragment to 'currentFrag' and sets it to visible.
                currentFrag = fragment;
                pager.setCurrentItem(listIndex);
                return true;
            case R.id.nextTab:      //When the user clicks '->' button, the next fragment is displayed

                //Sets the current visible item to the item that is next in the ArrayList
                pager.setCurrentItem(pager.getCurrentItem()+1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
