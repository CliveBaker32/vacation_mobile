package app.main.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import app.main.R;
import app.main.database.Repository;
import app.main.entities.Part;
import app.main.entities.Product;

public class PartDetails extends AppCompatActivity {
    String name;
    int partID;
    int prodID;
    EditText editName;
    TextView editDate;
    Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();

    String vacStartDate;
    String vacEndDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_details);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        repository = new Repository(getApplication());
        name = getIntent().getStringExtra("name");
        editName = findViewById(R.id.partName);
        editName.setText(name);


        partID = getIntent().getIntExtra("id", -1);
        prodID = getIntent().getIntExtra("prodID", -1);

        editDate = findViewById(R.id.date);
        editDate.setText(getIntent().getStringExtra("excursionDate"));

        Product curprod = null;
        for (Product product: repository.getmAllProducts()) {
            if (product.getProductID() == prodID) {
                curprod = product;
            }
        }
        if (curprod != null) {
            vacStartDate = curprod.getStartDate();
            vacEndDate = curprod.getEndDate();
        }


        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ArrayList<Product> productArrayList = new ArrayList<>();
        productArrayList.addAll(repository.getmAllProducts());
        ArrayAdapter<Product> productIdAdapter = new ArrayAdapter<Product>(this, android.R.layout.simple_spinner_item, productArrayList);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(productIdAdapter);

        startDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                updateLabelStart();
            }

        };

        editDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info = editDate.getText().toString();
                if (info.equals("")) info = "02/20/24";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(PartDetails.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editDate.setText(sdf.format(myCalendarStart.getTime()));
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_partdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == R.id.partsave) {
            // Check for correct date format and if end date is after the start date before saving anything.
            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            try {
                // Parse the editEndDate and editStartDate strings using the specified format
                sdf.setLenient(false); // Ensure strict parsing
                Date startDate = sdf.parse(vacStartDate);
                Date endDate = sdf.parse(vacEndDate);
                Date excursionDate = sdf.parse(editDate.getText().toString().trim());

                // Check if the formatted dates match the original input
                if (startDate.after(excursionDate)) {
                    Toast.makeText(this, "The Excursion date should during the associated vacation.", Toast.LENGTH_SHORT).show();
                    return true; // Exit the method without further processing

                } else if (endDate.before(excursionDate)) {
                    Toast.makeText(this, "The Excursion date should during the associated vacation.", Toast.LENGTH_SHORT).show();
                    return true; // Exit the method without further processing
                }

            } catch (Exception e) {
                return true;
            }

            Part part;
            if (partID == -1) {
                if (repository.getAllParts().size() == 0)
                    partID = 1;
                else
                    partID = repository.getAllParts().get(repository.getAllParts().size() - 1).getPartID() + 1;
                part = new Part(partID, editName.getText().toString(), prodID, editDate.getText().toString());
                repository.insert(part);
            } else {
                part = new Part(partID, editName.getText().toString(), prodID, editDate.getText().toString());
                repository.update(part);
            }
            return true;
        }


        if (item.getItemId() == R.id.notify) {
            // Check for correct date format and if end date is after the start date before saving anything.
            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            try {
                // Parse the editEndDate and editStartDate strings using the specified format
                sdf.setLenient(false); // Ensure strict parsing
                Date startDate = sdf.parse(vacStartDate);
                Date endDate = sdf.parse(vacEndDate);
                Date excursionDate = sdf.parse(editDate.getText().toString().trim());

                // Check if the formatted dates match the original input
                if (startDate.after(excursionDate)) {
                    Toast.makeText(this, "The Excursion date should during the associated vacation.", Toast.LENGTH_SHORT).show();
                    return true; // Exit the method without further processing

                } else if (endDate.before(excursionDate)) {
                    Toast.makeText(this, "The Excursion date should during the associated vacation.", Toast.LENGTH_SHORT).show();
                    return true; // Exit the method without further processing
                }

            } catch (Exception e) {
                return true;
            }


            String dateFromScreen = editDate.getText().toString();
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                Long trigger = myDate.getTime();
                Intent intent = new Intent(PartDetails.this, MyReceiver.class);
                intent.putExtra("key", editName.getText().toString());
                PendingIntent sender = PendingIntent.getBroadcast(PartDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            } catch (Exception e) {

            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}