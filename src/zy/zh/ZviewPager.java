package zy.zh;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 *
 * @author ZhengZhiying
 *
 * @Funtion 默认不允许滑动的ViewPager
 * 
 *
 */
public class ZviewPager extends ViewPager {

	private boolean isCanScroll = true;  
	
	public ZviewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ZviewPager(Context context) {
		super(context);
	}

	/**
	 * @param isCanScroll true=允许滑动
	 */
	public void setScanScroll(boolean isCanScroll){  
        this.isCanScroll = isCanScroll;  
    }  
  
  
    @Override  
    public void scrollTo(int x, int y){  
        if (isCanScroll){  
            super.scrollTo(x, y);  
        }  
    }  
	
	
}
