package kr.co.cyberdesic.coangler.widget.webview;

import android.content.Context;
import android.webkit.WebView;

import kr.co.cyberdesic.coangler.ui.activity.WebViewActivityBase;

public class MenuWebChromeClient extends WebChromeClientBase {
	
	public MenuWebChromeClient(Context context) {
		super(context);
	}
	
	@Override
	public void onProgressChanged(WebView view, int progress) {
    	((WebViewActivityBase)mCtx).setProgress1(progress);
	}
}
