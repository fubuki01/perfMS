package com.hnran.perfmanagesys.Statistics.presenter;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hnran.perfmanagesys.Statistics.model.Bean.StaffSalaryInfo;
import com.hnran.perfmanagesys.Statistics.model.IsalarySeekModel;
import com.hnran.perfmanagesys.Statistics.model.SalaryRankModel;
import com.hnran.perfmanagesys.Statistics.view.IsalarySeekView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/21.
 */
public class SalaryRankPresenter implements IsalarySeekPresenter {

    private IsalarySeekView salaryRankView;
    private IsalarySeekModel salaryRankModel = new SalaryRankModel();
    private final int SHOWTOAST = 0;
    private final int LOADBANKLIST = 1;
    private final int LOADADAPTERDATA = 2;
    private int type;
    private boolean isloading = false;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(salaryRankView==null)
                return;
            switch (msg.what){
                case SHOWTOAST : salaryRankView.showToast((String)msg.obj); break;
                case LOADBANKLIST : salaryRankView.setBankList((List<String>)msg.obj); break;
                case LOADADAPTERDATA : salaryRankView.addAdapterData((List<StaffSalaryInfo>)msg.obj,msg.arg1);break;
                default:break;
            }
        }
    };

    public SalaryRankPresenter(IsalarySeekView salaryRankView,int type){
        this.salaryRankView = salaryRankView;
        this.type = type;
    }
    
    @Override
    public void loadBankList() {
        if(salaryRankView==null)
            throw new RuntimeException("Presenter异常：该Presenter没有绑定View");
        salaryRankModel.loadBankListInNetWork(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = Message.obtain();
                msg.what = SHOWTOAST;
                msg.obj = "获取支行列表失败，请检查网络连接。";
                handler.sendMessage(msg);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                JSONArray bankList = JSON.parseArray(json);
                List<String> banknames = new ArrayList<>();
                banknames.add("全行");
                for(Object i : bankList)
                    banknames.add(i.toString());
                Message msg = Message.obtain();
                msg.what = LOADBANKLIST;
                msg.obj = banknames;
                handler.sendMessage(msg);
            }
        });
    }

    @Override
    public void loadAdapterData() {
        loadAdapterData(0,10);
    }

    @Override
    public void loadAdapterData(int start, int offset) {
        if(isloading)
            return;
        isloading = true;
        if(salaryRankView==null)
            throw new RuntimeException("Presenter异常：该Presenter没有绑定View");
        salaryRankModel.loadStaffRankDataInNetWork(
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        isloading = false;
                        Message msg = Message.obtain();
                        msg.what = SHOWTOAST;
                        msg.obj = "获取数据失败，请检查网络连接。";
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        List<StaffSalaryInfo> list = new ArrayList();
                        try {
                            String json = new JSONObject(response.body().string()).getString("result");
                            if(!json.equals("[]")){
                                JSONArray jsonArray = JSON.parseArray(json);
                                for(int i=0; i<jsonArray.size(); i++){
                                    list.add(jsonArray.getObject(i,StaffSalaryInfo.class));
                                }
                            }
                            else{
                                isloading = false;
                                Message msg = Message.obtain();
                                msg.what = SHOWTOAST;
                                msg.obj = "未查到相关数据。";
                                handler.sendMessage(msg);
                            }
                        } catch (JSONException e) {
                            isloading = false;
                            Message msg = Message.obtain();
                            msg.what = SHOWTOAST;
                            msg.obj = "数据异常。";
                            handler.sendMessage(msg);
                            return;
                        }
                        isloading = false;
                        Message msg = Message.obtain();
                        msg.what = LOADADAPTERDATA;
                        msg.obj = list;
                        msg.arg1 = 0;
                        handler.sendMessage(msg);
                    }
                }, type, salaryRankView.getDate(),salaryRankView.getBankName(),null,null
        );
    }

    @Override
    public void onDestroy() {
        salaryRankView = null;
    }

}
