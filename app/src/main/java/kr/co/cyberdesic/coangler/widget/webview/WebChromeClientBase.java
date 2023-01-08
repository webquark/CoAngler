package kr.co.cyberdesic.coangler.widget.webview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import java.util.ArrayList;

import kr.co.cyberdesic.coangler.R;

public class WebChromeClientBase extends WebChromeClient {
	
	protected Context mCtx;
	private ArrayList<WebCommandListener> mWebCommandListenerList;
	
	public interface WebCommandListener {
		public void actWebCommand(String message, WebCommand webCmd);
	}
	
	//public void setWebCommandHandler(WebCommandListener listener) {
	//	mWebCommandListener = listener;
	//}
	
	public void addWebCommandHandler(WebCommandListener listener) {
		if (mWebCommandListenerList == null) {
			mWebCommandListenerList = new ArrayList<WebCommandListener>();
		}
		
		mWebCommandListenerList.add(listener);
	}
	
	public WebChromeClientBase(Context context) {
		mCtx = context;
	}
	
	@Override
	public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
		callback.invoke(origin, true, false);
	}
	
   	@Override  
   	public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result) {
		WebCommand webCmd = WebCommand.cmdFromString(message);

		if (webCmd != null) {
			Log.i("WebCommand", String.format(">>>> %s", message));
			//command 처리.
			if (mWebCommandListenerList != null) {
				for (WebCommandListener listener : mWebCommandListenerList)
					listener.actWebCommand(message, webCmd);
			}

			result.confirm();

			return true;

		} else {
			new AlertDialog.Builder(view.getContext())
					.setTitle(R.string.alert)
					.setMessage(message)
					.setPositiveButton(android.R.string.ok,
							new AlertDialog.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									result.confirm();
								}
							})
					.setCancelable(false)
					.create()
					.show();
		}

		return true;
    }
   	
   	@Override
   	public boolean onJsConfirm(WebView view, String url, String message, final android.webkit.JsResult result) {
    	AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());  
        builder.setTitle(R.string.alert)
        		.setMessage(message)  
            	.setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {  
								                        @Override
														public void onClick(DialogInterface dialog, int which) {  
								                            result.confirm();  
								                        }  
								                    })
				.setNegativeButton(android.R.string.cancel, new AlertDialog.OnClickListener() {  
										                    @Override
															public void onClick(DialogInterface dialog, int which) {  
										                    	result.cancel();  
										                    }  
										                  })  
				.setCancelable(false)
				.create()
				.show();
   		 
        return true;
   	}
}
