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
import android.widget.Toast;

import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;
import com.gongheshe.adapter.MyProjectListAdapter;
import com.gongheshe.adapter.MyProjectListForPickAdapter;
import com.gongheshe.dialog.LoadingDlg;
import com.gongheshe.javabean.ProjectContentMod;
import com.gongheshe.javabean.ProjectDataMod;
import com.gongheshe.util.LoggerSZ;
import com.gongheshe.util.ShareSave;
import com.gongheshe.util.ToastUtil;
import com.googheshe.entity.GhhConst;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class MyprojectFragment extends BaseFragment implements OnClickListener {

	private String url = GhhConst.BASE_URL + "pmpList.htm?";
	private View view;
	private BaseActivity baseActivity;
	private AsyncHttpClient client;
	private final String TAG = "CopyrightFragment";
	private XListView listView_myPro;
	private int pageNumber = 0;
	private int currentPos = 0;

	// private XListView xListView;
	private MyProjectListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_myproject, container, false);
		baseActivity = (BaseActivity) getActivity();
		view.findViewById(R.id.ibtn_back).setOnClickListener(this);
		view.findViewById(R.id.add_project).setOnClickListener(this);
		listView_myPro = (XListView) view.findViewById(R.id.listView_myPro);
		listView_myPro.setPullLoadEnable(true);
		listView_myPro.setPullRefreshEnable(false);
		// adapter =new MyProjectListAdapter(baseActivity);
		adapter = new MyProjectListAdapter(getActivity());
		listView_myPro.setAdapter(adapter);
		// requestData();
		pageNumber = 1;
		requestWebServer(pageNumber);
		setOnClickToListView();
		return view;
	}

	private void setOnClickToListView() {
		adapter.setOnItemClick(new MyProjectListAdapter.OnItemClick() {

			@Override
			public void onItemDeleteClick(int position) {
				currentPos = position;
				ToastUtil.showToast(getActivity(),
						"删除" + adapter.data.data.get(currentPos).prjName);
				// ToastUtil.showToast(getActivity(), "删除" + (position - 1));
				requestForDeleteProject(adapter.data.data.get(currentPos));
			}

			@Override
			public void onItemClick(int position) {
				ToastUtil.showToast(getActivity(), "点击" + (position));
			}
		});
		listView_myPro.setPullLoadEnable(true);
		listView_myPro.setPullRefreshEnable(false);

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

	/**
	 * delete a project
	 * 
	 * @param projectContentMod
	 */
	protected void requestForDeleteProject(ProjectContentMod projectContentMod) {
		AsyncHttpClient httpClient;
		httpClient = new AsyncHttpClient();
		AsyncHttpResponseHandler handler;
		handler = new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				adapter.data.data.remove(currentPos);
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] response, Throwable e) {
			}
		};
//		httpClient.get("", handler);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if (id == R.id.ibtn_back) {
			baseActivity.onBackPressed();
		} else if (id == R.id.add_project) {
			AddProjectFragment myproject = new AddProjectFragment();
			baseActivity.replaceFragment(myproject, true);
		}
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
						MyprojectFragment.this.pageNumber--;
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
