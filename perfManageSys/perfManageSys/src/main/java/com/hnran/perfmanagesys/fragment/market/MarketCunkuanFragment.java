package com.hnran.perfmanagesys.fragment.market;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hnran.perfmanagesys.activity.PMSApplication;
import com.hnran.perfmanagesys.activity.market.MarketCheckActivity;
import com.hnran.perfmanagesys.utils.MakeUrl;
import com.hnran.perfmanagesys.utils.VolleyUtil;
import com.readboy.ssm.po.DepositMarketing;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MarketCunkuanFragment extends MarketCommonFragment{

	private List<DepositMarketing> mMarketings;


	public MarketCunkuanFragment(){
		super(MarketCommonFragment.CUNKUAN);
		mMarketings = new ArrayList<DepositMarketing>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		return super.onCreateView(inflater, container, savedInstanceState);

	}


	@Override
	public void loadData(final String data) {
		// TODO Auto-generated method stub
		super.loadData("");
		
		String url = MakeUrl.makeURL(new String[]{"responseDepositMarketingJson.action"});
		
		StringRequest stringRequest = new StringRequest( Method.POST, url,  
				new Response.Listener<String>() {  
			@Override  
			public void onResponse(String response) {  
				Log.d("TAG", response);  
				
				try{
//					List<DepositMarketing> tmp = JSON.parseArray(response, DepositMarketing.class);
//					mMarketings.addAll(tmp);
					
					//修改：添加条目总数
					JSONObject jsonObj = JSON.parseObject(response);  
					JSONArray list = jsonObj.getJSONArray("list"); 
					mNum = jsonObj.getIntValue("num");

					List<DepositMarketing> tmp = JSON.parseArray(list.toString(), DepositMarketing.class);
					mMarketings.addAll(tmp);
					
					
				}catch(Exception e){
					e.printStackTrace();
				}
				
				UIHandler.sendEmptyMessage(REFRESH_DATA);
				
			}  
		}, new Response.ErrorListener() {  
			@Override  
			public void onErrorResponse(VolleyError error) {  
				Log.e("TAG", error.getMessage(), error);  
				
				UIHandler.sendEmptyMessage(REQUEST_ERROR);
			}  
		}){
			@Override
			protected Map<String, String> getParams() {
				//在这里设置需要post的参数
				Map<String, String> params = new HashMap<String, String>();
				params.put("gyh", PMSApplication.gUser.getTellId());
				params.put("condition", data);
				int size = mMarketings!=null?mMarketings.size():0;
				params.put("currentResult", size+"");
				return params;
			}
		}; 

		VolleyUtil.getInstanceRequestQueue().add(stringRequest);
		

	}

	@Override
	protected boolean deleteData(final int position) {
		// TODO Auto-generated method stub
		
		Map<String, Object> map = new HashMap<String, Object>();
		String param = String.valueOf(mMarketings.get(position).getYybh());
		map.put("yybh",param);
		
		String url = MakeUrl.makeURL(new String[]{"mobileDeleteOneDepositMarketingByYybh.action"}, map);
		StringRequest stringRequest = new StringRequest(url,  
				new Response.Listener<String>() {  
			@Override  
			public void onResponse(String response) { 
				Log.d("TAG", response);  
				
			}  
		}, new Response.ErrorListener() {  
			@Override  
			public void onErrorResponse(VolleyError error) {  
				Log.e("TAG", error.getMessage(), error);  
			}  
		}); 

		VolleyUtil.getInstanceRequestQueue().add(stringRequest);
		
		
		return true;
	}

	@Override
	protected void refreshDataUpdateView() {
		// TODO Auto-generated method stub
		super.mMarketings = this.mMarketings;
		super.refreshDataUpdateView();
	}
	
	@Override
	public void gotoMarketCheckActivity(int position) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getActivity(), MarketCheckActivity.class);
		intent.putExtra("Extra_type", super.mType);	
		
		DepositMarketing dm = mMarketings.get(position);
//		String str = JSON.toJSONString(dm);
		if(dm != null){
			intent.putExtra("Extra_yybh", dm.getYybh()+"");
			intent.putExtra("Extra_jgmc", dm.getZzjc()+"");
			intent.putExtra("Extra_jgdm", dm.getJgdm()+"");
			intent.putExtra("Extra_khmc", dm.getKhmc()+"");
			intent.putExtra("Extra_ckzl", dm.getCkzl()+"");
			intent.putExtra("Extra_khzl", dm.getKhzl()+"");
			intent.putExtra("Extra_zjlb", dm.getZjlb()+"");
			intent.putExtra("Extra_zjhm", dm.getZjhm()+"");
			intent.putExtra("Extra_sjhm", dm.getSjhm()+"");
			intent.putExtra("Extra_yyrq", dm.getYyrq()+"");
			intent.putExtra("Extra_yggh", dm.getYggh()+"");
			intent.putExtra("Extra_yxbl", dm.getYxbl() != null ? dm.getYxbl() + "" : "");
			
		}
		
		startActivity(intent);
	}
	

}