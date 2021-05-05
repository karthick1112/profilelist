package com.posting.myproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.posting.api.Client;
import com.posting.api.Service;
import com.posting.model.Item;
import com.posting.model.ItemResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForksActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    TextView Disconnected;
    private Item item;
    List<Item> itemList = new ArrayList<>();
    ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;
    String link1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forks);

        initViews();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
         link1 = getIntent().getExtras().getString("forks_url");
         Log.v("folowers",link1);
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(){
                loadJSON();
                Toast.makeText(ForksActivity.this, "Github Users Refreshed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews(){
        pd = new ProgressDialog(this);
        pd.setMessage("Fetching Github Users...");
        pd.setCancelable(false);
        pd.show();
        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.smoothScrollToPosition(0);
        loadJSON();
    }

    private void loadJSON(){
        Disconnected = (TextView) findViewById(R.id.disconnected);

        try{
            Client Client = new Client();
            Service apiService =
                    Client.getClient().create(Service.class);
            Call<List<ItemResponse>> call = apiService.getItemsforks(link1);

            call.enqueue(new Callback<List<ItemResponse>>() {
                @Override
                public void onResponse(Call<List<ItemResponse>> call, Response<List<ItemResponse>> response) {
                    List<ItemResponse> itemResponses = response.body();
                    itemList.clear();
                    for(int i=0; i<itemResponses.size();i++){
                        Item items = new Item();
                        items.setName(itemResponses.get(i).getLabelsUrl());
                        items.setHtmlUrl("check");
                        items.setKeysUrl(itemResponses.get(i).getKeysUrl());
                        items.setForksUrl(itemResponses.get(i).getForksUrl());
                        items.setHooksUrl(itemResponses.get(i).getHooksUrl());
                        items.setAvatarUrl(itemResponses.get(i).getAvatarUrl());
                        itemList.add(items);
                    }



                    recyclerView.setAdapter(new ItemAdapter(ForksActivity.this, itemList));
                    recyclerView.smoothScrollToPosition(0);
                    swipeContainer.setRefreshing(false);
                    pd.hide();
                }

                @Override
                public void onFailure(Call<List<ItemResponse>> call, Throwable t) {
                    Toast.makeText(ForksActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                    Disconnected.setVisibility(View.VISIBLE);
                    pd.hide();

                }
            });

/*
            call.enqueue(new Callback<ItemResponse>() {
                @Override
                public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                    List<Item> items = response.body().getItems();
                  recyclerView.setAdapter(new ItemAdapter(MainActivity.this, items));
                    recyclerView.smoothScrollToPosition(0);
                    swipeContainer.setRefreshing(false);
                    pd.hide();
                }

                @Override
                public void onFailure(Call<ItemResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                    Disconnected.setVisibility(View.VISIBLE);
                    pd.hide();

                }
            });
*/

        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}