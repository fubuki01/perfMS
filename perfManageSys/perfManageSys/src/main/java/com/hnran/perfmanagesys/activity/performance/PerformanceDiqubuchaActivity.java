package com.hnran.perfmanagesys.activity.performance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hnran.perfmanagesys.activity.PMSApplication;
import com.hnran.perfmanagesys.adapter.PerfAreaMakeUpAdapter;
import com.hnran.perfmanagesys.utils.MakeUrl;
import com.hnran.perfmanagesys.utils.VolleyUtil;
import com.readboy.ssm.po.PerformanceAreaMakeUp;

import android.util.Log;

public class PerformanceDiqubuchaActivity extends PerformanceCommonActivity{

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		super.loadData();
		String url = MakeUrl.makeURL(new String[]{"findPerformanceAreaMakeUp.action"});
		StringRequest stringRequest = new StringRequest(Method.POST, url,  
				new Response.Listener<String>() {  
			@Override  
			public void onResponse(String response) {  
				Log.d("TAG", response);  
				try{
					JSONObject jsonObj = JSON.parseObject(response);  
					JSONArray array = jsonObj.getJSONArray("list"); 					
					mNum = jsonObj.getIntValue("num");
					
					final List<PerformanceAreaMakeUp> list = JSON.parseArray(array.toString(), PerformanceAreaMakeUp.class);
					
					if(adapter == null){
						adapter = new PerfAreaMakeUpAdapter();
						listView.setAdapter(adapter);
					}
					adapter.addData(list);
					
//					new Thread(new Runnable() {
//						@Override
//						public void run() {
//							// TODO Auto-generated method stub
//							BigDecimal b = new BigDecimal("0");
//							for(PerformanceAreaMakeUp l : list){
//								b = b.add(l.getGz());
//							}
//							total = b + "";
//							handler.sendEmptyMessage(ADD);
//						}
//					}).start();

				}catch(JSONException e){
					e.printStackTrace();
				}catch(Exception e){
					e.printStackTrace();
				}

				handler.sendEmptyMessage(FINISHED);

			}  
		}, new Response.ErrorListener() {  
			@Override  
			public void onErrorResponse(VolleyError error) {  
				Log.e("TAG", error.getMessage(), error); 
				handler.sendEmptyMessage(ERROR);
			}  
		}){
			@Override
			protected Map<String, String> getParams() {
				//在这里设置需要post的参数
				Map<String, String> params = new HashMap<String, String>();
				params.put("gyh", PMSApplication.gUser.getTellId());
				params.put("tjrq", date);
				int size = adapter == null ? 0 : adapter.getCount();
				params.put("currentResult", size +"");
				return params;
			}
		}; 

		VolleyUtil.getInstanceRequestQueue().add(stringRequest);

	}
}
