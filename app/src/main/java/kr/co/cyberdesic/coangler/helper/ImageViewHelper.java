package kr.co.cyberdesic.coangler.helper;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;

/**
 * 이미지 뷰에 사용된 image 의 fillColor 를 바꿔주는 헬퍼
 * <pre>
 *    사용법:
 *       ImageViewHelper.with(context)
 *                      .withImageView(ivImageView)
 *                      .tint(color);
 * </pre>
 */
public class ImageViewHelper {
    @NonNull
    private Context mContext;

    private ImageView mImageView;

    private Drawable mWrapperDrawable;

    public ImageViewHelper(@NonNull Context context) {
        mContext = context;
    }

    public static ImageViewHelper with(@NonNull Context context) {
        return new ImageViewHelper(context);
    }

    public ImageViewHelper withImageView(@NonNull ImageView imageView) {
        mImageView = imageView;
        return this;
    }

    public ImageViewHelper tint(int color) {
        Drawable drawable = mImageView.getDrawable();

        mWrapperDrawable = drawable.mutate();
        mWrapperDrawable = DrawableCompat.wrap(mWrapperDrawable);
        DrawableCompat.setTint(mWrapperDrawable, color);
        DrawableCompat.setTintMode(mWrapperDrawable, PorterDuff.Mode.SRC_IN);

        if (mImageView != null) {
            mImageView.setImageDrawable(mWrapperDrawable);
        }

        return this;
    }

}
