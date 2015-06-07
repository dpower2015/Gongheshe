package com.gongheshe.fragment;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;
import com.gongheshe.javabean.ProductAttr;
import com.gongheshe.javabean.ProductDetailMod;
import com.gongheshe.javabean.ProductMod;
import com.gongheshe.javabean.ProjectContentMod;
import com.gongheshe.util.LoggerSZ;
import com.gongheshe.util.ShareSave;
import com.gongheshe.util.ToastUtil;
import com.gongheshe.util.ToolImgLoader;
import com.googheshe.entity.GhhConst;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author ZhengZhiying<br>
 * @function  My product in 02:14:04
 */
public class ProductThirdDetailFragment extends BaseFragment implements
		OnClickListener {

	private static final String url = GhhConst.BASE_URL + "pProduct.htm?id=";
	private static final String urlPost = GhhConst.BASE_URL + "pSave.htm";
	private String urlIsCollect = GhhConst.BASE_URL + "isCollect.htm?";
	private View view;
	private ProductMod data;
	private ToolImgLoader imgLoader = ToolImgLoader.get();
	private ProductDetailMod productDetailMod;
	private ImageView img_picture;
	private TextView txt_price_retail;
	private TextView txt_price_mark;

	private TextView txt_describe;
	private TextView txt_class;
	private TextView txt_describe_en;
	private TextView txt_brand;
	private TextView txt_unit;
	private TextView txt_size;
	private TextView txt_store;
	private TextView txt_style;
	private TextView txt_sell_days;
	private TextView txt_contact;
	private TextView txt_contact_way;
	private TextView txt_count;
	private TextView txt_retail_all_price;
	private TextView txt_belong_project;
	private EditText edt_remarks;
	private View layout_detail;
	public Button bt_submit;
	private ImageView img_showDetail;
	private GridLayout gl_productAttr;
	private ViewHolder viewHolder = new ViewHolder();
	private ImageView img_collect;
	private MyProjectListForPickFragment myProjectListF;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_product_third_detail,
				container, false);
		view.findViewById(R.id.bt_return).setOnClickListener(this);
		img_picture = (ImageView) view.findViewById(R.id.img_picture);
		txt_price_retail = findText(R.id.txt_price_retail);
		txt_price_mark = findText(R.id.txt_price_mark);
		txt_class = findText(R.id.txt_class);
		txt_describe_en = findText(R.id.txt_describe_en);
		txt_brand = findText(R.id.txt_brand);
		txt_unit = findText(R.id.txt_unit);
		txt_size = findText(R.id.txt_size);
		txt_store = findText(R.id.txt_store);
		txt_style = findText(R.id.txt_style);
		txt_sell_days = findText(R.id.txt_sell_days);
		txt_contact = findText(R.id.txt_contact);
		txt_contact_way = findText(R.id.txt_contact_way);
		txt_count = findText(R.id.txt_count);
		txt_retail_all_price = findText(R.id.txt_retail_all_price);
		txt_belong_project = findText(R.id.txt_belong_project);
		img_collect = (ImageView) view.findViewById(R.id.img_collect);
		edt_remarks = (EditText) view.findViewById(R.id.edt_remarks);

		view.findViewById(R.id.layout_collect).setOnClickListener(this);
		txt_belong_project.setOnClickListener(this);
		viewHolder.count = 1;
		txt_count.setText("" + viewHolder.count);
		view.findViewById(R.id.layout_toshow_params).setOnClickListener(this);
		view.findViewById(R.id.bt_up).setOnClickListener(this);
		view.findViewById(R.id.bt_down).setOnClickListener(this);
		bt_submit = (Button) view.findViewById(R.id.bt_submit);
		view.findViewById(R.id.bt_submit).setOnClickListener(this);
		layout_detail = view.findViewById(R.id.layout_detail);
		layout_detail.setVisibility(View.GONE);
		img_showDetail = (ImageView) view.findViewById(R.id.img_showDetail);
		gl_productAttr = (GridLayout) view.findViewById(R.id.gl_productAttr);
		txt_describe = ((TextView) view.findViewById(R.id.txt_describe));
		requestWebServerTotalInfo(data.id);
		imgLoader.show(data.androidNote2ImagesUrl, img_picture);
		myProjectListF = new MyProjectListForPickFragment();
		setListenerTomyProjectListF();
		return view;
	}

	private void setListenerTomyProjectListF() {
		myProjectListF.setOnPickItemListener(//
				new MyProjectListForPickFragment.OnPickItemListener() {

					@Override
					public void onPickItem(ProjectContentMod data) {
						getActivity().onBackPressed();
						viewHolder.projectMod = data;
						txt_belong_project.setText(data.prjName);
					}
				});
	}

	private TextView findText(int resId) {
		return ((TextView) view.findViewById(resId));
	}

	public void setTypeClassMod(ProductMod data) {
		this.data = data;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_return:
			getActivity().onBackPressed();
			break;
		case R.id.layout_toshow_params:
			if (layout_detail.getVisibility() == View.VISIBLE) {
				layout_detail.setVisibility(View.GONE);
				img_showDetail.setImageResource(R.drawable.jt_right_red);
			} else {
				layout_detail.setVisibility(View.VISIBLE);
				img_showDetail.setImageResource(R.drawable.jt_down);
			}

			break;
		case R.id.bt_up:
			if (viewHolder.attr == null
					|| TextUtils.isEmpty(viewHolder.attr.stock)) {
				break;
			}
			if (Integer.valueOf(viewHolder.attr.stock) > viewHolder.count) {
				viewHolder.count++;
				txt_count.setText("" + viewHolder.count);
				txt_retail_all_price.setText(Integer
						.valueOf(viewHolder.attr.price)
						* Integer.valueOf(txt_count.getText().toString()) + "");
			}

			break;
		case R.id.bt_down:
			if (viewHolder.attr == null
					|| TextUtils.isEmpty(viewHolder.attr.stock)) {
				break;
			}
			if (viewHolder.count > 1) {
				viewHolder.count--;
				txt_count.setText("" + viewHolder.count);
				txt_retail_all_price.setText(Integer
						.valueOf(viewHolder.attr.price)
						* Integer.valueOf(txt_count.getText().toString()) + "");
			}
			break;
		case R.id.layout_collect:
			if(viewHolder.isCollect){
				
				requestCollectOrCancel("0");
				
			}else {
				requestCollectOrCancel("1");
				
			}

			break;
		case R.id.bt_submit:
			bt_submit.setText(R.string.uploading);
			bt_submit.setClickable(false);
			postToWebServer();
			break;
		case R.id.txt_belong_project:
			// go to my project face
			((BaseActivity) getActivity())
					.replaceFragment(myProjectListF, true);
			break;
		default:
			break;
		}
	}

	private void requestCollectOrCancel(String status) {
		String url = GhhConst.BASE_URL + "isCollect.htm";
		AsyncHttpClient httpClient;
		httpClient = new AsyncHttpClient();
		AsyncHttpResponseHandler handler = null;
		handler = new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				String data = new String(response);
				System.out.println("###data :"+data);
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(data);
					Boolean state=jsonObject.getBoolean("status");
					viewHolder.isCollect = state;
					if(state){
						
						ToastUtil.showToast(getActivity(),"已收藏");
						img_collect.setImageResource(R.drawable.ic_collect_on);
					}else {
						img_collect.setImageResource(R.drawable.ic_collect_off);
						
						ToastUtil.showToast(getActivity(),"已取消收藏");
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] response, Throwable e) {
				System.out.println("###data :failed!");
			}
		};
		RequestParams params = new RequestParams();
		ShareSave shareSave = ShareSave.get();
		// params.put("userId", shareSave.getUid());
		params.put("memberId", shareSave.getUid());
		params.put("productId", productDetailMod.productDeti.id+"");
		params.put("status",status);
		System.out.println("###data status:"+status);
		Log.i("ProductThird", url);
		httpClient.post(url, params, handler);
	}

	/**
	 * post data to server
	 */
	private void postToWebServer() {

		AsyncHttpClient httpClient;
		httpClient = new AsyncHttpClient();
		AsyncHttpResponseHandler handler = null;
		handler = new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				bt_submit.setText(R.string.book_now);
				bt_submit.setClickable(true);
				try {
					JSONObject jsonObject = new JSONObject(new String(response));
					boolean status = jsonObject.getBoolean("status");
					if (status) {
						getActivity().onBackPressed();
						ToastUtil.showToast(getActivity(),
								getString(R.string.success_to_book));
					} else {
						ToastUtil.showToast(getActivity(),
								getString(R.string.fail_to_book) + "："
										+ jsonObject.getString("msg"));
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] response, Throwable e) {
				ToastUtil.showToast(getActivity(),
						getString(R.string.fail_to_book));
				bt_submit.setText(R.string.book_now);
				bt_submit.setClickable(true);
			}
		};
		RequestParams params = new RequestParams();
		ShareSave shareSave = ShareSave.get();
		// params.put("userId", shareSave.getUid());
		params.put("userName", shareSave.getUserName());
		params.put("pwd", shareSave.getPsdword());
		params.put("order.memberId", /* productDetailMod.productDeti.id+"" */
				shareSave.getUid());
		params.put("order.memberRemark", edt_remarks.getText().toString());
		params.put("productId", String.valueOf(productDetailMod.productDeti.id));
		params.put("num", String.valueOf(viewHolder.count));
		params.put("size", viewHolder.attr.size + "");
		params.put("color", viewHolder.attr.color + "");
		params.put("prjId", "" + productDetailMod.productDeti.id);// prjId :
																	// 用户（设计师）项目id
		httpClient.post(urlPost, params, handler);
	}

	/**
	 * request webServer for this product all informations
	 * @param productId
	 */
	private void requestWebServerTotalInfo(String productId) {
		AsyncHttpClient httpClient;
		httpClient = new AsyncHttpClient();
		AsyncHttpResponseHandler handler;
		handler = new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				Gson gson;
				gson = new Gson();
				productDetailMod = gson.fromJson(
						new String(response).replace("\n", ""),
						ProductDetailMod.class);
				if(productDetailMod.productAttr == null){
					ToastUtil.showToast(getActivity(), "数据错误 : "+new String(response));
					return;
				}
				updateView(productDetailMod);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] response, Throwable e) {
				// ToastUtil.showToast(getActivity(), "提交失败"+e.toString());
			}
		};
		LoggerSZ.i("log", url.toString() + productId);
		httpClient.get(url.toString() + productId, handler);
	}

	/**
	 * is collect this product
	 * 
	 * @param memberId
	 *            userId
	 * @param productId
	 *            productId
	 */
	private void requestIsCollect(String memberId, String productId) {
		AsyncHttpClient httpClient;
		httpClient = new AsyncHttpClient();
		AsyncHttpResponseHandler handler;
		handler = new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				try {
					JSONObject json = new JSONObject(new String(response));
					boolean isCollect = json.getBoolean("status");
					viewHolder.isCollect = isCollect;
					if (viewHolder.isCollect) {
						
						img_collect.setImageResource(R.drawable.ic_collect_on);
					} else {
						img_collect.setImageResource(R.drawable.ic_collect_off);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] response, Throwable e) {
			}
		};
		String url = urlIsCollect + "productId=" + productId + "&memberId="
				+ memberId;
		// urlIsCollect += "productId=" + productId;
		// urlIsCollect += "&memberId=" + memberId;
		LoggerSZ.i(getClass().getSimpleName(), url);
		httpClient.get(url.toString(), handler);
	}

	protected void updateView(final ProductDetailMod data) {
		txt_describe.setText(Html.fromHtml(data.productDeti.description));
		txt_price_retail.setText("￥" + data.productDeti.minprice);
		txt_price_mark.setText("￥" + data.productDeti.martPrice);
		txt_class.setText(getString(R.string.type) + "\t"
				+ data.productDeti.typeOneName + "/"
				+ data.productDeti.typeTwoName + "/"
				+ data.productDeti.typeThreeName);
		txt_describe_en.setText(getString(R.string.describe_en) + "\t"
				+ data.productDeti.englishName);
		txt_brand.setText(getString(R.string.brand) + "\t"
				+ data.productDeti.companyName);
		txt_unit.setText(getString(R.string.unit) + "\t"
				+ data.productDeti.unit);
		txt_size.setText(getString(R.string.size) + "\t"
				+ data.productDeti.specName);
		txt_store.setText(getString(R.string.store) + "\t"
				+ data.productDeti.minStock);
		txt_style.setText(getString(R.string.style) + "\t"
				+ data.productDeti.productStyleName);
		txt_sell_days.setText(getString(R.string.sell_days) + "\t"
				+ data.productDeti.supportDays);
		txt_contact.setText(getString(R.string.contact) + "："
				+ data.company.linkMan);
		txt_contact_way.setText(getString(R.string.telphone) + ":"
				+ data.company.mobile);
		txt_contact_way.append("\tQQ:" + data.company.qq);
		int sizeProductAttr = data.productAttr.size();
		if (sizeProductAttr > 0) {
			LayoutInflater inflater = LayoutInflater.from(getActivity());
			for (int i = 0; i < sizeProductAttr; i++) {
				final int position = i;
				View child = inflater.inflate(R.layout.item_gl_productattr,
						null, false);
				TextView txt = (TextView) child.findViewById(R.id.txt_content);
				txt.setText(data.productAttr.get(i).size + "\t"
						+ data.productAttr.get(i).color);
				final ImageView img = (ImageView) child
						.findViewById(R.id.img_check);
				if (i == 0) {
					img.setVisibility(View.VISIBLE);
					txt_store.setText(getString(R.string.store) + "\t"
							+ data.productAttr.get(0).stock);
					viewHolder.img = img;
					viewHolder.attr = data.productAttr.get(0);
				} else {
					img.setVisibility(View.GONE);
				}
				child.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (viewHolder.img != img) {
							if (img.getVisibility() == View.VISIBLE) {
								img.setVisibility(View.GONE);
								txt_store.setText(getString(R.string.store)
										+ "\t" + 0);
							} else {
								img.setVisibility(View.VISIBLE);
								txt_store.setText(getString(R.string.store)
										+ "\t"
										+ data.productAttr.get(position).stock);
								viewHolder.img.setVisibility(View.GONE);
								viewHolder.img = img;
								viewHolder.attr = data.productAttr
										.get(position);
							}
						}
					}
				});
				child.setTag(img);
				DisplayMetrics dm = new DisplayMetrics();

				getActivity().getWindowManager().getDefaultDisplay()
						.getMetrics(dm);

				// int screenWidth = dm.widthPixels;
				GridLayout.LayoutParams params = new GridLayout.LayoutParams();
				child.setLayoutParams(params);
				params.width = dm.widthPixels / 2 - 30;
				// TextView v = new TextView(getActivity());
				// params.width = 20;
				// v.setLayoutParams(params);
				// gl_productAttr.addView(v);
				gl_productAttr.addView(child);
			}
		}
		// data.productDeti.id
		if (viewHolder.attr != null
				&& !TextUtils.isEmpty(viewHolder.attr.stock)) {
			txt_retail_all_price.setText(Integer.valueOf(viewHolder.attr.price)
					* Integer.valueOf(txt_count.getText().toString()) + "");
		}
		ShareSave shareSave = ShareSave.get();
		requestIsCollect(shareSave.getUid(),
				String.valueOf(data.productDeti.id));
	}

	/**
	 * @author ZhengZhiying<br>
	 * @function for the dyn gridLayout
	 */
	class ViewHolder {
		ImageView img;
		ProductAttr attr;
		int count;
		boolean isCollect;
		ProjectContentMod projectMod;
	}

}
