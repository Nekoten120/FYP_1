package com.example.fyp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

;


public class MainActivity extends Login_MainActivity {
    private static final String URL_PRODUCTS = "https://shopapp-fyp.000webhostapp.com/Test/Api.php";
    //Initialize variable
    DrawerLayout drawerLayout;

    //Login
    TextView nameTv;
    TextView emailTv;
    TextView logoutbtn;

    TextView messgae;
    public int ZXC;


    List<Product> productList;
    RecyclerView recyclerView;


    View view ;

    int RecyclerViewItemPosition ;

    RecyclerView.LayoutManager layoutManagerOfrecyclerView;

    RecyclerView.Adapter recyclerViewadapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messgae = (TextView) findViewById(R.id.message);

        //logout

        context = this;
        init();


        //Show the user name and email
        nameTv = findViewById(R.id.nameTv);
        nameTv.setText(sharedPreferences.getString("name",""));
        emailTv = findViewById(R.id.emailTv);
        emailTv.setText(sharedPreferences.getString("email",""));





        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        productList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        loadProducts();

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                view = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(view != null && gestureDetector.onTouchEvent(motionEvent)) {

                    //Getting RecyclerView Clicked Item value.
                    RecyclerViewItemPosition = Recyclerview.getChildAdapterPosition(view);
                    ZXC = RecyclerViewItemPosition;

                    // Showing RecyclerView Clicked Item value using Toast.

                    String y = String.valueOf(ZXC);
                    messgae.setText(y);
                    Intent intent = new Intent(MainActivity.this, ProductInfo.class);
                    String keytext = y;
                    intent.putExtra("keytext", keytext);
                    startActivity(intent);
                }
                return false;
            }


            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }

    private void loadProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                productList.add(new Product(
                                        product.getInt("id"),
                                        product.getString("name"),
                                        product.getString("shortdesc"),
                                        product.getDouble("rating"),
                                        product.getDouble("price"),
                                        product.getString("image")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            ProductsAdapterMain adapter = new ProductsAdapterMain(MainActivity.this, productList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }






    public void ClickBack(View view){
        //Open drawer
        finish();
    }
    public void ClickMenu(View view){
        //Open drawer
        openDrawer(drawerLayout);


    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        //Open drawer layout
        drawerLayout.openDrawer(GravityCompat.START);
    }


    public void ClickLogo(View view){
        //Close drawer
        closeDrawer(drawerLayout);

    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        //Close drawer layout
        //Check condition
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            //When drawer is open
            //Close drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view){
        //Recreate activity
        recreate();
    }

    public void ClickScanner(View view){
        //Recreate activity
        redirectActivity(this,Scanner.class);
    }

    public void ClickVtube(View view){
        //Redirect activity to Vtube
        redirectActivity(this,Vtube.class);
    }

    public void ClickVtube_Customer_service(View view){
        //Redirect activity to Vtube service
        redirectActivity(this,Vtube_Customer_service.class);
    }


    public void ClickShoppingcart(View view){
        //Redirect activity to Vtube service
        redirectActivity(this,SummaryActivity.class);
    }

    public void ClickAboutUs(View view){
        //Redirect activity to about us
        redirectActivity(this,AboutUs.class);
    }


    public void ClickLogout(View view ){
        //Close app
                // Redirect back to login page
        logout();
        intent = new Intent(context,LoginActivity.class);
        //remove all previous stack activities
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
            }





    public static void redirectActivity(Activity activity, Class aClass ) {
        //Initialize intent
        Intent intent = new Intent(activity,aClass);
        //Set flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Start activity
        activity.startActivity(intent);
    }

    @Override
   protected void onPause() {
        super.onPause();
        //Close drawer
        closeDrawer(drawerLayout);
    }





}