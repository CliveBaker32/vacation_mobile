package app.main.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import app.main.R;
import app.main.database.Repository;
import app.main.entities.Part;
import app.main.entities.Product;

public class ProductDetails extends AppCompatActivity {
    String name;
    int productID;
    Repository repository;
    Product currentProduct;
    int numParts;

    EditText editName;
    EditText editHotel;
    EditText editStartDate;
    EditText editEndDate;
    private static final Object lock = new Object();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);

        name = getIntent().getStringExtra("name");
        editName = findViewById(R.id.titletext);
        editName.setText(name);

        name = getIntent().getStringExtra("hotel");
        editHotel = findViewById(R.id.hotelText);
        editHotel.setText(name);

        name = getIntent().getStringExtra("startDate");
        editStartDate = findViewById(R.id.startDateText);
        editStartDate.setText(name);


        name = getIntent().getStringExtra("endDate");
        editEndDate = findViewById(R.id.endDateText);
        editEndDate.setText(name);


        productID = getIntent().getIntExtra("id", -1);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetails.this, PartDetails.class);
                intent.putExtra("prodID", productID);


                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.partrecyclerview);
        repository = new Repository(getApplication());
        final PartAdapter partAdapter = new PartAdapter(this);
        recyclerView.setAdapter(partAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        partAdapter.setParts(repository.getAllAssociatedParts(productID));

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_productdetails, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.productsave) {
            String regex = "^(0[1-9]|1[0-2])/(0[1-9]|[12]\\d|3[01])/\\d{2}$";

            // Check for correct date format and if end date is after the start date before saving anything.
            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            try {

                // Parse the editEndDate and editStartDate strings using the specified format
                sdf.setLenient(false); // Ensure strict parsing
                Date startDate = sdf.parse(editStartDate.getText().toString());
                Date endDate = sdf.parse(editEndDate.getText().toString());


                // Check if the formatted dates match the original input
                if (!editStartDate.getText().toString().matches(regex)) {
                    // Dates do not match the expected format
                    // Show error message
                    Toast.makeText(this, "Dates should be in the format MM/dd/yy.", Toast.LENGTH_SHORT).show();
                    return true; // Exit the method without further processing
                } else if (!editEndDate.getText().toString().matches(regex)) {
                    // Dates do not match the expected format
                    // Show error message
                    Toast.makeText(this, "Dates should be in the format MM/dd/yy.", Toast.LENGTH_SHORT).show();
                    return true; // Exit the method without further processing
                } else if (startDate.after(endDate)) {
                    Toast.makeText(this, "End date should be after start date.", Toast.LENGTH_SHORT).show();
                    return true; // Exit the method without further processing
                }
            } catch (Exception e) {
                Log.e("ERROR", "Exception occurred: " + e.getMessage(), e);
                Toast.makeText(this, "Dates should be in the format MM/dd/yy.", Toast.LENGTH_SHORT).show();
                return true;

            }
            // Now create the product or update it.
            Product product;
            if (productID == -1) {
                if (repository.getmAllProducts().size() == 0) productID = 1;
                else
                    productID = repository.getmAllProducts().get(repository.getmAllProducts().size() - 1).getProductID() + 1;
                product = new Product(productID, editName.getText().toString(), editHotel.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                repository.insert(product);
                this.finish();
            } else {
                try {
                    product = new Product(productID, editName.getText().toString(), editHotel.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                    repository.update(product);
                    this.finish();
                } catch (Exception e) {

                }
            }
        }
        if (item.getItemId() == R.id.productdelete) {
            for (Product prod : repository.getmAllProducts()) {
                if (prod.getProductID() == productID) currentProduct = prod;
            }

            numParts = 0;
            for (Part part : repository.getAllParts()) {
                if (part.getProductID() == productID) ++numParts;
            }

            if (numParts == 0) {
                repository.delete(currentProduct);
                Toast.makeText(ProductDetails.this, currentProduct.getProductName() + " was deleted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ProductDetails.this, "Can't delete a Vacation with Excursions.", Toast.LENGTH_LONG).show();
            }
            return true;
        }


        if (item.getItemId() == R.id.setAlert) {
            String regex = "^(0[1-9]|1[0-2])/(0[1-9]|[12]\\d|3[01])/\\d{2}$";

            // Check for correct date format and if end date is after the start date before saving anything.
            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            try {

                // Parse the editEndDate and editStartDate strings using the specified format
                sdf.setLenient(false); // Ensure strict parsing
                Date startDate = sdf.parse(editStartDate.getText().toString());
                Date endDate = sdf.parse(editEndDate.getText().toString());


                // Check if the formatted dates match the original input
                if (!editStartDate.getText().toString().matches(regex)) {
                    // Dates do not match the expected format
                    // Show error message
                    Toast.makeText(this, "Dates should be in the format MM/dd/yy.", Toast.LENGTH_SHORT).show();
                    return true; // Exit the method without further processing
                } else if (!editEndDate.getText().toString().matches(regex)) {
                    // Dates do not match the expected format
                    // Show error message
                    Toast.makeText(this, "Dates should be in the format MM/dd/yy.", Toast.LENGTH_SHORT).show();
                    return true; // Exit the method without further processing
                } else if (startDate.after(endDate)) {
                    Toast.makeText(this, "End date should be after start date.", Toast.LENGTH_SHORT).show();
                    return true; // Exit the method without further processing
                }
            } catch (Exception e) {
                Log.e("ERROR", "Exception occurred: " + e.getMessage(), e);
                Toast.makeText(this, "Dates should be in the format MM/dd/yy.", Toast.LENGTH_SHORT).show();
                return true;
            }


            String startDateFromScreen = editStartDate.getText().toString();
            String endDateFromScreen = editEndDate.getText().toString();
            Date myStartDate = null;
            Date myEndDate = null;


            try {
                myStartDate = sdf.parse(startDateFromScreen);
                myEndDate = sdf.parse(endDateFromScreen);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            // Define an object to use as a lock
            try {
                // Synchronize on the lock object to ensure only one thread can execute this block at a time
                synchronized (lock) {
                    // Your code to set the first alarm
                    Long trigger = myStartDate.getTime();
                    Intent intent = new Intent(ProductDetails.this, MyReceiver.class);
                    intent.setAction("START_DATE");
                    intent.putExtra("key", editName.getText().toString() + " - starting");
                    PendingIntent sender = PendingIntent.getBroadcast(ProductDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);

                    // Sleep for a short period to allow the first alarm to be set
                    Thread.sleep(1000); // You can adjust the duration as needed

                    // Your code to set the second alarm
                    Long trigger2 = myEndDate.getTime();
                    Intent intent2 = new Intent(ProductDetails.this, MyReceiver.class);
                    intent2.setAction("END_DATE");
                    intent2.putExtra("key", editName.getText().toString() + " - ending");
                    PendingIntent sender2 = PendingIntent.getBroadcast(ProductDetails.this, ++MainActivity.numAlert, intent2, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager2.set(AlarmManager.RTC_WAKEUP, trigger2, sender2);
                }
            } catch (Exception e) {
                return true;
            }
            return true;
        }

        if (item.getItemId() == R.id.shareVacation) {
            String allAssociatedParts = "";
            for (Part part: repository.getAllAssociatedParts(productID)) {
                allAssociatedParts = allAssociatedParts + "\n" + part.getPartName();
            }

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Title: " + editName.getText().toString() + "\n" + "Hotel: " + editHotel.getText().toString()
                    + "\n" + "Start Date: " + editStartDate.getText().toString() + "\n" + "End Date: " +
                    editEndDate.getText().toString() + "\n" + "Excursions: " + allAssociatedParts);
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Share Vacation");
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }


        return true;
    }

    @Override
    protected void onResume() {

        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.partrecyclerview);
        final PartAdapter partAdapter = new PartAdapter(this);
        recyclerView.setAdapter(partAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        partAdapter.setParts(repository.getAllAssociatedParts(productID));

        //Toast.makeText(ProductDetails.this,"refresh list",Toast.LENGTH_LONG).show();
    }

    public boolean excursionInDateRange(String excurDate) {
        // Check for correct date format and if end date is after the start date before saving anything.
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        try {
            // Parse the editEndDate and editStartDate strings using the specified format
            sdf.setLenient(false); // Ensure strict parsing
            Date startDate = sdf.parse(editStartDate.getText().toString());
            Date endDate = sdf.parse(editEndDate.getText().toString());
            Date excursionDate = sdf.parse(excurDate);

            if (startDate.after(excursionDate)) {
                return false; // Exit the method without further processing

            } else if (endDate.before(excursionDate)) {
                return false; // Exit the method without further processing
            }

        } catch (ParseException e) {

        }
        return true;
    }

}
