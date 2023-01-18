package kr.co.cyberdesic.coangler.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import kr.co.cyberdesic.coangler.R;
import kr.co.cyberdesic.coangler.ui.activity.ActivityBase;
import kr.co.cyberdesic.coangler.widget.CustomDialog;

public class FragmentBase extends Fragment {

    private static final String LOG_TAG = "FragmentBase";

    protected Context mContext;
    protected View mView;

    protected ProgressBar mProgressBar = null;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mContext = getContext();

        if (mContext != null) {
            fetchData();
        }
    }

    protected void fetchData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);

        return view;
    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

    public void setProgressBar(ProgressBar view) {
        mProgressBar = view;
        hideProgress();
    }

    public void setProgressBar(int resId) {
        setProgressBar( mView.findViewById(resId));
    }

    public void showProgress() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgress() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    public int Alert(int msgResId) {
        return Alert( this.getResources().getString(msgResId) );
    }

    public int Alert(CharSequence msg) {
        return Alert(msg, null);
    }

    public int Alert(int msgResId, final ActivityBase.AlertDialogCallback callback) {
        return Alert(this.getResources().getString(msgResId), callback);
    }

    public int Alert(CharSequence msg, final ActivityBase.AlertDialogCallback callback) {
        CustomDialog.Builder alert = new CustomDialog.Builder(mContext);

        // set dialog title & message
        alert.setTitle(R.string.app_name)
                .setMessage(msg)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        if (callback != null) {
                            callback.onOKClick();
                        }
                    }
                })
                .create()
                .show();

        return 1;
    }

    public int Confirm(String title, String msg, DialogInterface.OnClickListener okListener) {
        return Confirm(title, msg, okListener, null);
    }

    public int Confirm(int titleResId, int msgResId, DialogInterface.OnClickListener okListener) {
        return Confirm(this.getResources().getString(titleResId), this.getResources().getString(msgResId), okListener, null);
    }

    public int Confirm(int titleResId, int msgResId, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {
        return Confirm(this.getResources().getString(titleResId), this.getResources().getString(msgResId), okListener, cancelListener);
    }

    public int Confirm(String title, String msg, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {
        CustomDialog.Builder confirm = new CustomDialog.Builder(mContext);

        // set dialog title & msg
        confirm.setTitle(title)
                .setMessage(msg)
                .setPositiveButton(R.string.ok, okListener);

        if (cancelListener != null) {
            confirm.setNegativeButton(R.string.cancel, cancelListener);

        } else {
            confirm.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }

        confirm.create().show();

        return 1;
    }

    protected void showToast(int resId) {
        showToast(getString(resId));
    }

    protected void showToast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }
}
