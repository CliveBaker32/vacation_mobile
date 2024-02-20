package app.main.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import app.main.R;
import app.main.database.Repository;
import app.main.entities.Part;
import app.main.entities.Product;

public class ProductList extends AppCompatActivity {


    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductList.this,ProductDetails.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        repository = new Repository(getApplication());
        List<Product> allProducts = repository.getmAllProducts();
        final ProductAdapter productAdapter = new ProductAdapter(this);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter.setProducts(allProducts);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.mysample) {

            repository = new Repository(getApplication());

//            Toast.makeText(ProductList.this, "put in sample data", Toast.LENGTH_LONG).show();
            Product product = new Product(0, "Miami", 100.0);
            repository.insert(product);

            product = new Product(0, "Maine", 100.0);
            repository.insert(product);

            Part part = new Part(0, "Boat Ride", 100.0, 1);
            repository.insert(part);

            part = new Part(0, "Snow-sledding", 100.0, 1);
            repository.insert(part);


            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            this.finish();

//            Intent intent = new Intent(ProductList.this, ProductDetails.class);
//            startActivity(intent);

            return true;
        }
        return true;
    }

    @Override
    protected void onResume() {

        super.onResume();
        List<Product> allProducts = repository.getmAllProducts();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ProductAdapter productAdapter = new ProductAdapter(this);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter.setProducts(allProducts);

        Toast.makeText(this,"Vacation list is refreshed. ProductList.java - onResume Method",Toast.LENGTH_LONG).show();
    }

}
