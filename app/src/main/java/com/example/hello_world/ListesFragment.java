package com.example.hello_world;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class ListesFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<ProductItem> productItemsList;
    private ArrayList<Product> products;
    private DBHandler db;

    public static final String EXTRA_POSITION = "com.example.hello_world.EXTRA_TEXT";
    Notification notif;

    private Button delete_all;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_listes, container, false);
        getActivity().setTitle("Liste des produits");
        setHasOptionsMenu(true);

        notif = new Notification(getContext());
        db = new DBHandler(v.getContext());
        productItemsList = new ArrayList<>();
        delete_all = (Button)v.findViewById(R.id.delete_all);

        refreshRecyclerView(v);
        buildRecyclerView(v);
        onButton_AllDeleteClickListener(delete_all);

        MySwipeRefreshLayout(v);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.notification_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.notification_icon:
                //Appear a new page where can change Notification
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(getView(), getContext());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void MySwipeRefreshLayout(View v){
        // SwipeRefreshLayout
        swipeRefreshLayout=v.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /**
                 * Showing Swipe Refresh animation on activity create
                 * As animation won't start on onCreate, post runnable is used
                 */
                swipeRefreshLayout.post(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Refreshing list",Toast.LENGTH_LONG).show();
                        swipeRefreshLayout.setRefreshing(true);
                        // Update list from database
                        //startService(getView());

                        if(products != null){
                            products.clear();
                        }

                        Information_Product infoProduct = new Information_Product();
                        products = (ArrayList<Product>) db.getAllProducts();
                        for (Product product : products) {
                            infoProduct.updatePrice(product, getContext());
                        }
                        refreshRecyclerView(getView());
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();//Refresh RecyclerView
                        swipeRefreshLayout.setRefreshing(false);//stop refreshing

                    }
                });
            }
        });
    }

    public void onButton_AllDeleteClickListener(Button all_delete){
        all_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_all(v);
            }
        });

    }

    public void delete_all(View view){

        for (int i=0 ;i<productItemsList.size(); i++){
            Information_Product.deleteFileInterernalStorage(getContext(), products.get(i).getName());//Delete Internal File
            mAdapter.notifyItemRemoved(0);
        }
        productItemsList.clear();
        db.AllDelete();
        refreshRecyclerView(view);
    }

    public void buildRecyclerView(final View v){
        mRecyclerView = v.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new Adapter(productItemsList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        System.out.println("BEFORE CHECKING NUMBER OF PRODUCT");
        if(db.getProductsCount()>1){//MAYBE THERE IS ANOTHER WAY
            System.out.println("I SHOULD START SERVICE");
            startService(v);
        }

        mAdapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                removeProduct(position, v);
            }

            @Override
            public void onDetailsClick(int position) {
                openDetailActivity(position);
            }
        });
    }

    public void openDetailActivity(int position){
        Intent intent = new Intent(getContext(), ItemDetails.class);
        intent.putExtra(EXTRA_POSITION, position);
        startActivity(intent );

    }

    public void removeProduct(int position, View v){
        productItemsList.remove(position);
        Information_Product.deleteFileInterernalStorage(getContext(), products.get(position).getName());//Delete Internal File
        db.deleteProduct(products.get(position));
        products.remove(position);
        mAdapter.notifyItemRemoved(position);

        if(mAdapter.getItemCount()==0){
            delete_all.setVisibility(v.INVISIBLE);
            stopService(v);
        }
    }

    public void refreshRecyclerView(View v){
        int count = db.getProductsCount();
        if(count >0){

            if (productItemsList != null){
                productItemsList.clear();
            }
            if (products != null){
                products.clear();
            }
            products = (ArrayList<Product>) db.getAllProducts();
            for (Product product : products) {
                productItemsList.add(new ProductItem(product.getName(), Double.toString(product.getActual_price())));
            }

            delete_all.setVisibility(v.VISIBLE);

        }else{
            delete_all.setVisibility(v.INVISIBLE);
            stopService(v);
        }
    }

    public void startService(View v){
        if(TesterConnectionHTTP.isNetworkAvailable() == true){
            getActivity().startService(new Intent(getActivity(), MyService.class));
        }else{
            Toast.makeText(getContext(), "NO CONNECTION START SERVICE", Toast.LENGTH_SHORT).show();
        }
    }

    public void stopService(View v){
        getActivity().stopService(new Intent(getActivity(), MyService.class));
    }

}
