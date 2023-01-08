package kr.co.cyberdesic.coangler.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import kr.co.cyberdesic.coangler.R;
import kr.co.cyberdesic.coangler.application.CoAnglerApplication;
import kr.co.cyberdesic.coangler.widget.CustomDialog;
import kr.co.cyberdesic.coangler.widget.SimpleProgressDialog;

public class ActivityBase extends AppCompatActivity {
	protected Context mContext;
	protected SimpleProgressDialog mProgressDlg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mContext = this;
		
	}

	/**
	 * CoAngler 어플리케이션 구하기
	 * @return
	 */
	public CoAnglerApplication getCoAnglerApplication() {
		return (CoAnglerApplication)this.getApplication();
	}

	public void showProgress() {
		showProgress(null, 0);
	}
	
	public void showProgress(String message) {
		showProgress(message, 0);
	}
			
	public void showProgress(String message, int type) {
		if (mProgressDlg == null) {
			mProgressDlg = SimpleProgressDialog.show(this, message, type);
		}
		else if (mProgressDlg.getProgressType() != type) {
			mProgressDlg = null;
			mProgressDlg = SimpleProgressDialog.show(this, message, type);
		}
		else {
			mProgressDlg.show();
		}
	}
	
	public void hideProgress() {
		if (mProgressDlg != null)
			mProgressDlg.dismiss();
	}
	
	public void setProgressMax(int max) {
		mProgressDlg.setMax(max);
	}
	
	public void setProgressPos(int value) {
		mProgressDlg.setProgress(value);
	}
	
	public void setProgressMessage(String message) {
		mProgressDlg.setMessage(message);
	}
	
	public int Alert(int msgResId) {
		return Alert(this.getResources().getString(msgResId));
		
	}

	/**
	 * Shows a {@link Snackbar} using {@code text}.
	 *
	 * @param text The Snackbar text.
	 */
	private void showSnackbar(final String text) {
		View container = findViewById(android.R.id.content);
		if (container != null) {
			Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
		}
	}

	/**
	 * Shows a {@link Snackbar}.
	 *
	 * @param mainTextStringId The id for the string resource for the Snackbar text.
	 * @param actionStringId   The text of the action item.
	 * @param listener         The listener associated with the Snackbar action.
	 */
	private void showSnackbar(final int mainTextStringId, final int actionStringId,
							  View.OnClickListener listener) {
		Snackbar.make(findViewById(android.R.id.content),
				getString(mainTextStringId),
				Snackbar.LENGTH_INDEFINITE)
				.setAction(getString(actionStringId), listener).show();
	}

	public int Alert(String msg) {
		CustomDialog.Builder alert = new CustomDialog.Builder(ActivityBase.this);
		 
		// set dialog title & message
		alert.setTitle(R.string.app_name)
			.setMessage(msg)
			.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close the dialog box and do nothing
						dialog.cancel();
					}
				});
		
		CustomDialog alertDialog = alert.create();
		alertDialog.show();
		
		return 1;
	}
	
	public int Confirm(String msg, DialogInterface.OnClickListener okListener) {
		return Confirm(msg, okListener, null);
	}
	
	public int Confirm(int msgResId, DialogInterface.OnClickListener okListener) {
		return Confirm(this.getResources().getString(msgResId), okListener, null);
	}
	
	public int Confirm(int msgResId, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {
		return Confirm(this.getResources().getString(msgResId), okListener, cancelListener);
	}
	
	public int Confirm(String msg, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {
		CustomDialog.Builder alert = new CustomDialog.Builder(ActivityBase.this);
		 
		// set dialog title & message
		alert.setTitle(R.string.app_name)
			.setMessage(msg)
			.setPositiveButton(R.string.ok, okListener);
		
		if (cancelListener != null) {
			alert.setNegativeButton(R.string.cancel, cancelListener);
		}
		else {
			alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.cancel();
						}
					});
		}
		
		CustomDialog alertDialog = alert.create();
		alertDialog.show();
		
		return 1;
	}
	
	
	public void showToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
	
	public void showToast(int resId) {
		showToast(this.getString(resId));
	}

	public void reportServerError(String errorMessage) {
		showToast(errorMessage);
	}
}
