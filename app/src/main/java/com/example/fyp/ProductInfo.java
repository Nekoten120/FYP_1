package com.example.fyp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class ProductInfo extends Login_MainActivity {
    private static final String URL_PRODUCTS = "https://shopapp-fyp.000webhostapp.com/Test/Api.php";
    List<Product> productList;
    RecyclerView recyclerView;
    MainActivity ma = new MainActivity();
    TextView text;
    int y;
    private Button button;

    //Initialize variable
    DrawerLayout drawerLayout;

    //Login
    TextView nameTv;
    TextView emailTv;
    TextView logoutbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productinfo);
        //logout

        context = this;
        init();

        //Show the user name and email
        nameTv = findViewById(R.id.nameTv);
        nameTv.setText(sharedPreferences.getString("name",""));
        emailTv = findViewById(R.id.emailTv);
        emailTv.setText(sharedPreferences.getString("email",""));


        button = (Button) findViewById(R.id.btn_back);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        productList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        loadProducts();
        text = findViewById(R.id.nexttext);
        String message = getIntent().getStringExtra("keytext");
        text.setText(message);
        String bb = text.getText().toString();
        y = Integer.parseInt(bb);
        //Toast.makeText(ProductInfo.this, bb, Toast.LENGTH_LONG).show();

        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);


        button = (Button) findViewById(R.id.btn_shopping);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (y == 0) {
                    Intent intent = new Intent(ProductInfo.this, InfoActivity.class);
                    startActivity(intent);
                }
                if (y == 1) {
                    Intent intent = new Intent(ProductInfo.this, LatteActivity.class);
                    startActivity(intent);
                }
                if (y == 2) {
                    Intent intent = new Intent(ProductInfo.this, BubbleTeaActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    private void loadProducts() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object



                            //getting product object from json array
                            JSONObject product = array.getJSONObject(y);

                            //adding the product to product list
                            productList.add(new Product(
                                    product.getInt("id"),
                                    product.getString("name"),
                                    product.getString("shortdesc"),
                                    product.getDouble("rating"),
                                    product.getDouble("price"),
                                    product.getString("image")
                            ));


                            //creating adapter object and setting it to recyclerview
                            ProductsAdapter adapter = new ProductsAdapter(ProductInfo.this, productList);
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
        MainActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view ){
        //Close drawer
        MainActivity.closeDrawer(drawerLayout);
    }

    public void ClickShoppingcart(View view){
        //Redirect activity to Vtube service
        MainActivity.redirectActivity(this,SummaryActivity.class);
    }

    public void ClickHome(View view){
        //Redirect activity to home
        MainActivity.redirectActivity(this,MainActivity.class);

    }

    public void ClickScanner(View view){
        //Redirect activity to home
        MainActivity.redirectActivity(this,Scanner.class);

    }


    public void ClickVtube(View view){
        //Redirect activity to Vtube
        MainActivity.redirectActivity(this,Vtube.class);


    }

    public void ClickVtube_Customer_service(View view){
        //Redirect activity to Vtube
        MainActivity.redirectActivity(this,Vtube_Customer_service.class);

    }

    public void ClickAboutUs(View view){
        //Recreate activity
        MainActivity.redirectActivity(this,AboutUs.class);

    }

    public void ClickLogout(View view){
        //Close app
        //Logout
        logout();
        intent = new Intent(context,LoginActivity.class);
        //remove all previous stack activities
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Close drawer
        MainActivity.closeDrawer(drawerLayout);
    }

}
