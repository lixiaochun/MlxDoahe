package com.example.mlx.daohe.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mlx.daohe.Acvitity.Movies_desc;
import com.example.mlx.daohe.Adapter.Hot_movies_Adapter;
import com.example.mlx.daohe.R;
import com.example.mlx.daohe.Utils.L;
import com.example.mlx.daohe.Utils.SharedUtils;
import com.example.mlx.daohe.Utils.StaticClass;
import com.example.mlx.daohe.entiy.Hot_Movies;
import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * 项目名：LxChange
 * 包名：com.example.administrator.lxchange.Fragment
 * 创建者：MLX
 * 创建时间：2017/2/11 19:06
 * 用途：
 */

public class fragment_movies extends Fragment implements AdapterView.OnItemClickListener {





    private ListView listView;
    private Hot_movies_Adapter adapter;
    private Hot_Movies hot_movies;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies,container,false);
        listView= (ListView) view.findViewById(R.id.hot_movies_listview);
        listView.setOnItemClickListener(this);
        requestFormAPI();
        return view;
    }

    private void requestFormAPI() {
        RxVolley.get(StaticClass.HOT_URL, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                SharedUtils.putString(getContext().getApplicationContext(),"movies",t);
                hot_movies=new Gson().fromJson(t,Hot_Movies.class);
                adapter=new Hot_movies_Adapter(getContext(),hot_movies);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                Toast.makeText(getContext(), "获取数据失败，请检查网络连接", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getActivity(), Movies_desc.class);
        intent.putExtra("id",hot_movies.getData().getMovies().get(position).getId());
        startActivity(intent);
    }


}
