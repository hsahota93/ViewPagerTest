package edu.temple.viewpagertest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;

public class FirstFragment extends Fragment {

    //Declaring the WebView
    private WebView mWebView;

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_first, container, false);

        //Assigns mWebView to the WebView in design view
        mWebView = (WebView) v.findViewById(R.id.webView);

        //Following two lines enables Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //Sets the WebViewClient to a new WebViewClient
        mWebView.setWebViewClient(new WebViewClient());

        return v;
    }

    public void changeWebsite(String website) {

        //Makes sure 'website' isn't null
        if(website != null) {

            //If user did NOT type http://
            if (!website.contains("http://")) {

                website = "http://" + website;      //Adds "http://" to the string
                mWebView.loadUrl(website);          //Loads the URL in 'website'
            } else {                                //User DID type "https://"

                mWebView.loadUrl(website);          //Load the URL in 'website'
            } //End else-if
        } //End if
    }
}
