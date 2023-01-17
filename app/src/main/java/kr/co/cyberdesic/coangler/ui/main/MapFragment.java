package kr.co.cyberdesic.coangler.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import kr.co.cyberdesic.coangler.R;
import kr.co.cyberdesic.coangler.ui.fragment.FragmentBase;
import kr.co.cyberdesic.coangler.util.Utils;
import kr.co.cyberdesic.coangler.widget.webview.InnerWebViewClient;
import kr.co.cyberdesic.coangler.widget.webview.ObservableWebView;
import kr.co.cyberdesic.coangler.widget.webview.WebChromeClientBase;

/**
 * 낚시지도 프래그먼트
 */
public class MapFragment extends FragmentBase {

    private static final String LOG_TAG = "Map";

    protected ObservableWebView mWebView;
    protected WebChromeClientBase mWebChromeClient;
    protected String mUrl;

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mWebView = (ObservableWebView) view.findViewById(R.id.webview);

        WebSettings settings = mWebView.getSettings();

        settings.setDefaultTextEncodingName("UTF-8");
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
//        settings.setDatabasePath("/data/data/" + mContext.getPackageName() + "/databases/");
//        settings.setUserAgentString(settings.getUserAgentString() + " " + Utils.getUserAgentString(mContext));

        //mWebChromeClient = new WebChromeClientBase(mContext);
        //mWebChromeClient.addWebCommandHandler(this);

        mWebView.setWebChromeClient(mWebChromeClient);
        mWebView.setWebViewClient(new InnerWebViewClient()); // forces it to open in app

        //mWebView.setOnScrollChangeListener(this);


        if (savedInstanceState != null) {
            mUrl = savedInstanceState.getString("url");
        } else {
            mUrl = getString(R.string.home_url);
        }

        mWebView.loadUrl(mUrl);

        return view;
    }

}