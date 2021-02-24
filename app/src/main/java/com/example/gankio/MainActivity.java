package com.example.gankio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.example.gankio.data.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName();
    private String url;
    private TextView tv_no_data;
    private RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPics();
        rv = (RecyclerView)findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));

    }

    private void initPics() {
        url = "https://gank.io/api/v2/data/category/Girl/type/Girl/page/1/count/10";
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new MyCallBack());
    }
    public class MyCallBack extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            Logger.d(TAG, "onError："+id);
//            tv_no_data.setVisibility(View.VISIBLE);
        }

        @Override
        public void onResponse(String response, int id) {
            Logger.d("onResponse：complete: "+id);
            if (response != null){
                processData(response);
            }
        }
    }

    private void processData(String response) {
        Picture picture = paraseJson(response);
        List<Picture.DataDTO> DataDTOS = picture.getData();

        if (picture != null && DataDTOS.size() > 0) {
            //有数据
//            tv_nodata.setVisibility(View.GONE);
            //显示适配器
            rv.setAdapter(new BaseAdapter(MainActivity.this, DataDTOS));
        } else {
            //没有数据
//            tv_nodata.setVisibility(View.VISIBLE);
        }
//        progressBar.setVisibility(View.GONE);
    }

    private Picture paraseJson(String response) {
        Picture picture = new Picture();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.optJSONArray("data");
            Logger.e(jsonArray.toString());
            if (jsonArray != null && jsonArray.length() > 0) {
                List<Picture.DataDTO> dataDTOS = new ArrayList<>();
                picture.setData(dataDTOS);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObjectItem = (JSONObject) jsonArray.get(i);
                    Logger.e( jsonObjectItem.toString());

                    if (jsonObjectItem != null) {

                        Picture.DataDTO dataDTO = new Picture.DataDTO();
                        Logger.e( dataDTO.toString());
                        String url = jsonObjectItem.optString("url");//name
                        dataDTO.setUrl(url);
                        String desc = jsonObjectItem.optString("desc");
                        dataDTO.setDesc(desc);
                        Logger.e(url);
                        Logger.e(desc);
//                        dataDTO.setTitle(desc);

                        //把数据添加到集合
                        dataDTOS.add(dataDTO);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return picture;
    }


}