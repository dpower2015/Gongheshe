package com.gongheshe.fragment;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import zy.zh.xListView.XListView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.gongheshe.R;
import com.gongheshe.adapter.MyProjectListAdapter;
import com.gongheshe.javabean.ProjectContentMod;
import com.gongheshe.javabean.ProjectDataMod;
import com.gongheshe.util.LoggerSZ;
import com.gongheshe.util.ShareSave;
import com.gongheshe.util.ToastUtil;
import com.googheshe.entity.GhhConst;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class MyProjectListFragment extends BaseFragment {

	private String url = GhhConst.BASE_URL + "pmpList.htm?";
	private View view;
	private XListView listView_myPro;
	private OnPickItemListener onPickItemListener;
	private int pageNumber = 0;
	private MyProjectListAdapter adapter;

	public interface OnPickItemListener {
		public void onPickItem(ProjectContentMod data);
	}

	public void setOnPickItemListener(OnPickItemListener onPickItemListener) {
		this.onPickItemListener = onPickItemListener;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_myproject_list, container,
				false);
		adapter = new MyProjectListAdapter(getActivity());
		listView_myPro = (XListView) view.findViewById(R.id.listView_myPro);
		view.findViewById(R.id.bt_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						getActivity().onBackPressed();
					}
				});
		listView_myPro.setAdapter(adapter);
		setListenerListView();
		pageNumber = 1;
		requestWebServer(pageNumber);
		return view;
	}

	private void setListenerListView() {
		listView_myPro.setPullLoadEnable(true);
		listView_myPro.setPullRefreshEnable(false);
		listView_myPro.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (onPickItemListener != null) {
					onPickItemListener.onPickItem(adapter.data.data
							.get(position - 1));
				}
			}
		});
		listView_myPro.setXListViewListener(new XListView.IXListViewListener() {

			@Override
			public void onRefresh() {
			}

			@Override
			public void onLoadMore() {
				pageNumber++;
				requestWebServer(pageNumber);
			}
		});
	}

	private void requestWebServer(int pageNumber) {
		StringBuffer buffer = new StringBuffer(url);
		buffer.append("memberId=" + ShareSave.get().getUid());
		buffer.append("&pagesize=" + 20);
		buffer.append("&pagenumber=" + pageNumber);
		AsyncHttpClient httpClient;
		httpClient = new AsyncHttpClient();
		AsyncHttpResponseHandler handler;
		handler = new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				try {
					Gson gson = new Gson();
					JSONObject jsonObject;
					jsonObject = new JSONObject(new String(response));
					ProjectDataMod data = gson.fromJson(jsonObject
							.getJSONObject("projs").toString(),
							ProjectDataMod.class);
					if (adapter.data.data == null) {
						adapter.data = gson.fromJson(
								jsonObject.getJSONObject("projs").toString(),
								ProjectDataMod.class);
						adapter.notifyDataSetChanged();
					} else if (data.data.size() > 0) {
						adapter.data.data.addAll(data.data);
						adapter.notifyDataSetChanged();
					} else {
						ToastUtil.showToast(getActivity(),
								getString(R.string.no_more_data_loaded));
						MyProjectListFragment.this.pageNumber--;
					}
					listView_myPro.stopLoadMore();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] response, Throwable e) {
				listView_myPro.stopLoadMore();
				ToastUtil.showToast(getActivity(), "no data");
			}
		};
		LoggerSZ.i("log", "get my project list：" + buffer.toString());
		httpClient.get(buffer.toString(), handler);
	}

}
