package com.example.yu.ad0313_bd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import java.util.ArrayList;
import java.util.List;

import static com.example.yu.ad0313_bd.R.id.city;
import static com.example.yu.ad0313_bd.R.id.keyword;

public class SearchActivity extends AppCompatActivity {
    private EditText edt_city;
    private EditText edt_keyword;
    private Button btn_POI;
    private PoiSearch mPoiSearch;
    private ArrayList<String> location = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initViews();
    }






    private void initViews() {
        edt_city = (EditText) findViewById(city);
        edt_keyword = (EditText) findViewById(keyword);
        btn_POI = (Button) findViewById(R.id.btn_POI);
        mPoiSearch = PoiSearch.newInstance();

        btn_POI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = edt_city.getText().toString().trim();
                String keyword = edt_keyword.getText().toString();
                mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
                mPoiSearch.searchInCity((new PoiCitySearchOption()
                        .city(city)
                        .keyword(keyword)

                ));

            }
        });
    }

    OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener(){
        public void onGetPoiResult(PoiResult result){

            //获取POI检索结果
            Intent intent = new Intent(SearchActivity.this,MainActivity.class);
            getLati_Longi(result);
            intent.putExtra("location",location);
            startActivity(intent);
            mPoiSearch.destroy();
//            循环遍历一下:
//            for(PoiInfo p : pi_list){
//                Log.i("yyyyyyyyyy", "地址:" + p.address + "名字:" + p.name + "电话号码:"+ p.phoneNum);
//                调用onGetPoiDetailResult()方法。
//                mPoiSearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid(p.uid));
//            }

        }

        public void onGetPoiDetailResult(PoiDetailResult result){
            //获取Place详情页检索结果
            if (result.error != SearchResult.ERRORNO.NO_ERROR) {
                //详情检索失败
                // result.error请参考SearchResult.ERRORNO

            } else {
//                检索成功
//                Log.i("ssssssssss", "onGetPoiDetailResult: " + result.detailUrl + "   " + result.getDetailUrl());
            }

        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };

    private void getLati_Longi(PoiResult result) {
        List<PoiInfo> pi_list = result.getAllPoi();
        for (int i = 0; i <pi_list.size() ; i++) {
            location.add(String.valueOf(pi_list.get(i).location.latitude));//纬度
            location.add(String.valueOf(pi_list.get(i).location.longitude));//经度
        }
    }
}
