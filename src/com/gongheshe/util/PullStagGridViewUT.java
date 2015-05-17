package com.gongheshe.util;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.etsy.android.grid.StaggeredGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshStaggeredGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;

/**
 * @author ZhengZhiying<br>
 * @function 
 */
public class PullStagGridViewUT {

	private Context context;
	private PullToRefreshStaggeredGridView gridView;
	private OnStagGridViewListener gridViewListener;

	public interface OnStagGridViewListener {
		public void onRefreshStart();

		public void onItemClick(int position);
	}

	public PullStagGridViewUT(Context context,
			PullToRefreshStaggeredGridView gridView) {
		super();
		this.context = context;
		this.gridView = gridView;
		initView();
	}
	
	public void  setAdapter(BaseAdapter adapter){
		gridView.setAdapter(adapter);
	}

	private void initView() {
		// 
		gridView.setOnRefreshListener(new OnRefreshListener<StaggeredGridView>() {

			@Override
			public void onRefresh(
					PullToRefreshBase<StaggeredGridView> refreshView) {
				if (gridViewListener != null) {
					gridViewListener.onRefreshStart();
				}
			}
		});
		gridView.setMode(Mode.PULL_UP_TO_REFRESH);
		gridView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				boolean lastItemVisible = (totalItemCount > 0)
						&& (firstVisibleItem + visibleItemCount >= totalItemCount - 1);
				if (lastItemVisible) {
					gridView.setPullUpToRefreshing(gridView);
				}
			}
		});
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (gridViewListener != null) {
					gridViewListener.onItemClick(position);
				}
			}
		});
	}

	/**
	 * hide the foot
	 */
	public void dismissLoadingView() {
		gridView.onRefreshComplete();
	}

	public void setGridViewListener(OnStagGridViewListener gridViewListener) {
		this.gridViewListener = gridViewListener;
	}

}
