package com.training.al.tutapplication;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by resalat on 11/5/2016.
 */

 class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


