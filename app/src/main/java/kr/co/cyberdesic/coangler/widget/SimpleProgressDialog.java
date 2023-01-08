package kr.co.cyberdesic.coangler.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import kr.co.cyberdesic.coangler.R;


public class SimpleProgressDialog extends CustomDialog {
	
	private static ProgressBar mProgressBar = null;
	private static TextView mTvMessage = null;
	private static int mProgressStyleType = 0;		// 0: progressBarStyleLarge, 1: progressBarStyleHorizontal
	
	public SimpleProgressDialog(Context context, int theme) {
		super(context, theme);
	}
	
	public SimpleProgressDialog(Context context) {
		super(context);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setBackKeyEnabled(false);
	}
	
	public static class Builder extends CustomDialog.Builder { 
		
		public Builder(Context context) {
            super(context);
        }
	    
		@Override
		public SimpleProgressDialog create() {
			
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			// instantiate the dialog with the custom Theme
	        final SimpleProgressDialog dialog = new SimpleProgressDialog(mContext, R.style.CustomDialog);
	        
	        View layout;
	        if (mProgressStyleType == 0) {
	        	layout = inflater.inflate(R.layout.dialog_simple_progress, null);
	        }
	        else {
	        	layout = inflater.inflate(R.layout.dialog_horizontal_progress, null);
	        }
	        
	        
	        //dialog.setContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
	        // set the dialog message
	        mTvMessage = (TextView)layout.findViewById(R.id.message);
	        mProgressBar = (ProgressBar)layout.findViewById(R.id.progressBar);
	        mTvMessage.setVisibility(View.GONE);
	        
	        if (mProgressStyleType == 1) {
	        	DisplayMetrics displaymetrics = new DisplayMetrics();
	        	((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
	        	int width = (int)(displaymetrics.widthPixels * 0.8);
	        	
	        	mProgressBar.setMinimumWidth(width);//(int)Utils.convertPixelsToDp(width, mContext));
	        }
	        
	        dialog.setContentView(layout);
	        
	        return (SimpleProgressDialog)dialog;
		}
	}

	public static SimpleProgressDialog show(Context context) {
		return show(context, null, 0);
	}
	
	public static SimpleProgressDialog show(Context context, String message, int type) {
		mProgressStyleType = type;
		
		Builder progress = new Builder(context);
		 
		SimpleProgressDialog progressDialog = progress.create();
		progressDialog.setMessage(message);
		progressDialog.show();
		
		return progressDialog;
	}

	public int getProgressType() {
		return mProgressStyleType;
	}
	
	public void setMax(int max) {
		mProgressBar.setMax(max);
	}
	
	public void setProgress(int value) {
		mProgressBar.setProgress(value);
	}
	
	public void setMessage(String message) {
		if (message != null) {
			mTvMessage.setText(message);
			mTvMessage.setVisibility(View.VISIBLE);
		}
	}
}
