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

        pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        final Uri data = getIntent().getData();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                website = editText.getText().toString();
                index = pager.getCurrentItem();
                currentFrag = fragmentList.get(index);

                if (data != null) {

                    website = data.toString();
                    currentFrag.changeWebsite(website);
                } else {

                    currentFrag.changeWebsite(website);
                }

            }
        });

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

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

        @Override
        public int getCount() {
            return NUM_FRAGS;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.prevTab:

                pager.setCurrentItem(pager.getCurrentItem()-1);
                return true;
            case R.id.newTab:

                FirstFragment fragment = new FirstFragment();
                fragmentList.add(listIndex, fragment);
                NUM_FRAGS++;

                pa = pager.getAdapter();
                pa.notifyDataSetChanged();

                currentFrag = fragment;
                pager.setCurrentItem(listIndex);
                return true;
            case R.id.nextTab:

                pager.setCurrentItem(pager.getCurrentItem()+1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
