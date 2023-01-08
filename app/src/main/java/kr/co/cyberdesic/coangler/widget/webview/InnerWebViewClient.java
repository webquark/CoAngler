package kr.co.cyberdesic.coangler.widget.webview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import kr.co.cyberdesic.coangler.R;

public class InnerWebViewClient extends WebViewClient {
	@Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    @Override     
    public void onReceivedError(WebView view, int errorcode,String description, String fallingUrl) {   
    	Log.d("WEBView", "error : "+errorcode);
    	Log.d("WEBView", "error : "+description);    	
    	Log.d("WEBView", "error : "+fallingUrl);
    	
    	
    	//mWebView.loadUrl("file:///android_asset/timeout.html");
    	new AlertDialog.Builder(view.getContext())  
        .setTitle(R.string.alert)
        .setMessage(R.string.error_bad_network_state)
        .setPositiveButton(android.R.string.ok,  
                new AlertDialog.OnClickListener()
                {
                    @Override
					public void onClick(DialogInterface dialog, int which)
                    {
                    }  
                })  
        .setCancelable(false)  
        .create()  
        .show();
    }
    
	@Override
	public void onPageFinished(WebView view, String url) {
		super.onPageFinished(view, url);
	}
}
