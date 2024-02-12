package app.main.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import app.main.R;

public class ProductList extends AppCompatActivity {

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

        System.out.println(getIntent().getStringExtra("test"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.mysample) {
            Toast.makeText(ProductList.this, "put in sample data", Toast.LENGTH_LONG).show();
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
}
