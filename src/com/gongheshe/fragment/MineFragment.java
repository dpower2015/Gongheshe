package com.gongheshe.fragment;

import java.io.File;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gongheshe.R;
import com.gongheshe.activity.BaseActivity;
import com.gongheshe.activity.LoginActivity;
import com.gongheshe.dialog.GetPhotoDlg;
import com.gongheshe.dialog.LoadingDlg;
import com.gongheshe.util.BmpToFile;
import com.gongheshe.util.HttpAssist;
import com.gongheshe.util.LoggerSZ;
import com.gongheshe.util.ShareSave;
import com.googheshe.entity.GhhConst;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MineFragment extends BaseFragment implements OnClickListener{
	
	View view;
	private BaseActivity baseActivity;
	private final int REQUEST_IMG=10;
	private final int REQUEST_CUT_IMG=11;
	private ShareSave shareSave = ShareSave.get();
	private ImageView img;
	private GetPhotoDlg getPhotoDlg;
	private File fileOfHeadPic = null;
	private final static String TAG="MineFragment";
	/** 相册返回 */
	public final static int requestCodePicture = 0x21;
	/** 相机返回 */
	public final static int requestCamera = 0x22;
	private Handler handler = new Handler();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_mine, container, false);
		view.findViewById(R.id.myproject).setOnClickListener(this);
		view.findViewById(R.id.modify_pw).setOnClickListener(this);
		view.findViewById(R.id.modify_head_img).setOnClickListener(this);
		view.findViewById(R.id.mybooks).setOnClickListener(this);
		view.findViewById(R.id.mystore).setOnClickListener(this);
		view.findViewById(R.id.exit_curuser).setOnClickListener(this);
		img=(ImageView)view.findViewById(R.id.img_user);
		baseActivity=(BaseActivity)getActivity();
		getPhotoDlg = new GetPhotoDlg(baseActivity);
		return view;
	}
	@Override 
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.myproject:
			MyprojectFragment myproject=new MyprojectFragment();
			baseActivity.replaceFragment(myproject, true);
			break;
		case R.id.modify_head_img:
			/*Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT); // "android.intent.action.GET_CONTENT"
			innerIntent.setType("image/*"); // 查看类型
			Intent wrapperIntent = Intent.createChooser(innerIntent, null);
		    startActivityForResult(wrapperIntent, REQUEST_IMG);  */
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			startActivityForResult(intent, 0x21);
			
			
            // REQUEST_CAMERA =0;
			break;
		case R.id.modify_pw:
			ModifypwFragment modifyFragment = new ModifypwFragment();
			baseActivity.replaceFragment(modifyFragment, true);
			break;
		case R.id.mybooks:
			MybooksFragment mybooksFragment =new MybooksFragment();
			baseActivity.replaceFragment(mybooksFragment, true);
			break;
		case R.id.mystore:
			MystoreFragment mystoreFragment =new MystoreFragment();
			baseActivity.replaceFragment(mystoreFragment, true);
			break;
		case R.id.exit_curuser:
			shareSave.setPsdword("");
			shareSave.setUserName("");
			shareSave.setUid("");
			baseActivity.startActivity(new Intent(baseActivity, LoginActivity.class));
			baseActivity.finish();
			break;
		default:
			break;
		 
		}
		
	}
	

	/**
	 * 上传图片完成之后调用这个方法；
	 * 
	 * @param data
	 */
	private void upLoadBack(String data) {
		LoadingDlg.get().show();
		try {
			LoggerSZ.e(TAG, "##upLoadBack：" + data);
			Boolean status;
			JSONObject jsonObject = new JSONObject(data);
			status=jsonObject.getBoolean("status");
			if(status){
				jsonObject =jsonObject.getJSONObject("data");
				String  fileId= jsonObject.getString("fileId");
				
				requestForSubmit(fileId);
			}
			
		} catch (JSONException e) {
			LoadingDlg.get().hide();
			LoggerSZ.e(TAG, "提交失败：" + data);
			//Toast.makeText(context, "提交失败", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}

	private void requestForSubmit(String iconId) {
		AsyncHttpClient httpClient = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add("member.id", ShareSave.get().getUid());
		//params.add("username", edt_username.getText().toString());
		if (!TextUtils.isEmpty(iconId)) {
			params.add("member.icon", iconId);
		}
		httpClient.post(GhhConst.MODIFYICON, params, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] response, Throwable e) {
				LoggerSZ.e(TAG, "提交失败：" + new String(response));
				//Toast.makeText(TAG, "提交失败", Toast.LENGTH_SHORT).show();
				LoadingDlg.get().hide();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				LoadingDlg.get().hide();
				LoggerSZ.i(TAG, "@@@提交返回结果：" + new String(response));
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(new String(response));
					boolean status;
					status=jsonObject.getBoolean("status");
					if(status){
						String iconUrl=jsonObject.getString("userphoto");
						shareSave.setUserIconUrl(iconUrl);
						Toast.makeText(baseActivity, "修改图标成功", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				/*Toast.makeText(context, "提交成功", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setClass(, UserInfoActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				finish();
				startActivity(intent);*/

			}

		});
	}
	private void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 0x81);
	}

	private void setToImg(Intent data) {
		// getPhotoPath(data);
		Bundle extras = data.getExtras();

		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			fileOfHeadPic = BmpToFile.get(baseActivity)
					.bitmapToFile(photo, "up.png");
			String uri = Uri.fromFile(fileOfHeadPic).toString();
			LoggerSZ.i(TAG, "显示图片=" + uri);
			ImageLoader.getInstance().displayImage(uri, img);
		}
	}
	
	
	private void admitpic(){
		
		
		if(fileOfHeadPic!=null){
			new Thread(new Runnable() {
	
				@Override
				public void run() {
					final String res = HttpAssist.uploadFile(
							fileOfHeadPic, GhhConst.ADMIT_PICTURE, "file1");
					
					handler.post(new Runnable() {
	
						@Override
						public void run() {
							upLoadBack(res);
						}
	
					});
	
				}
			}).start();
		
		}
		
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		// case GetPhotoDlg.requestCodePicture:
		case 0x21:
			// 相册返回
			System.out.println("相册返回");
			if (data != null) {
				startPhotoZoom(data.getData());
			}
			break;
		case GetPhotoDlg.requestCamera:
			// 相机返回
			System.out.println("摄像头返回");
			File picture = new File(getPhotoDlg.filePath);
			startPhotoZoom(Uri.fromFile(picture));
			break;
		case 0x81:
			// 系统截图返回
			System.out.println("截图返回");
			if (data != null) {
				LoggerSZ.i(TAG, "不为空的截图");
				setToImg(data);
				admitpic();
			}
			break;
		default:
			break;
		}	
		
		
		
		/*if (requestCode == REQUEST_IMG) {  
				Uri uri = null;
				uri = data.getData();
				if (uri != null) {
				    final Intent intent1 = new Intent("com.android.camera.action.CROP"); 
				      intent1.setDataAndType(uri, "image/*");
				      intent1.putExtra("crop", "true");
				      intent1.putExtra("aspectX", 1);
				      intent1.putExtra("aspectY", 1);
				      intent1.putExtra("outputX", 132);
				      intent1.putExtra("outputY", 132);
				      intent1.putExtra("return-data", true);
				      startActivityForResult(intent1,REQUEST_CUT_IMG);
				    
				}
			}

		else if(requestCode == REQUEST_CUT_IMG) {
				Bitmap bm=null;
				bm= data.getParcelableExtra("data");
				img.setImageBitmap(bm);
		}*/

                //获取头像图片
               /* Uri selectedImage = data.getData();
                File file=new File(selectedImage.toString());
                
                System.out.println("file :"+file.exists());
                System.out.println("file path :"+file.getAbsolutePath());
	            String[] filePathColumn = {MediaStore.Images.Media.DATA };
	            	
	            System.out.println(" selectedImage :"+ selectedImage);
	            Cursor cursor = baseActivity.getContentResolver().query(selectedImage,
	                    filePathColumn, null, null, null);
	            cursor.moveToFirst();
	  
	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	            String picturePath = cursor.getString(columnIndex);
	            cursor.close();
	  
	            img.setImageBitmap(BitmapFactory.decodeFile(picturePath));*/
		
		}
	
	
	
	
}
