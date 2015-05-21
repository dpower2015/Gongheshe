package zy.zh;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class RoundImageView extends ImageView {

	public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public RoundImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RoundImageView(Context context) {
		super(context);
	}

	@Override
	public void draw(Canvas canvas) {
		Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
				Bitmap.Config.ARGB_8888);
		Canvas canvas2 = new Canvas(bitmap);
		super.draw(canvas2);
		bitmap = toRoundBitmap(bitmap);
		Paint paint2 = new Paint();
		canvas.drawBitmap(bitmap, 0, 0, paint2);
		bitmap.recycle();
	}

	/**
	 * @author 郑志英 2013年6月7日9:41:49
	 * @describe 把当前的位图绘为圆形
	 * */
	public Bitmap toRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Log.i("circle imageview", width + "," + height);
		float roundPx;
		float dst_left, dst_top, dst_right, dst_bottom;
		width = width > height ? height : width;
		roundPx = width / 2;
		height = width;
		dst_left = 0;
		dst_top = 0;
		dst_right = width;
		dst_bottom = width;

		Bitmap output = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);

		paint.setColor(color);

		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);// 画一个圆形
		// android.graphics.AvoidXfermode
		// android.graphics.PorterDuff
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));// 设置取图模式
		canvas.drawBitmap(bitmap, dst, dst, paint);
		return output;
	}

}
