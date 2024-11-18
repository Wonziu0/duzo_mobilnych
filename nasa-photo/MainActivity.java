package com.example.nasaphotoapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://api.nasa.gov/planetary/";
    private static final String API_KEY = "Q9gSVaT4hnv6gfCou2CxGEgqj8m6Zq6vgeyea6pq";
    private static final String TAG = "MainActivity";

    private Button btnDownload;
    private TextView todayTitle;
    private TextView todayDesc;
    private ImageView todayPhoto;
    private NasaApiService apiService;
    private Button btnDate;
    private Calendar calendar;
    private TextView textPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDownload = findViewById(R.id.todayDownload);
        todayTitle = findViewById(R.id.todayTitle);
        todayDesc = findViewById(R.id.todayDesc);
        todayPhoto = findViewById(R.id.todayPhoto);
        btnDate = findViewById(R.id.pickDate);
        textPass = findViewById(R.id.timePass);
        calendar = Calendar.getInstance();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(NasaApiService.class);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData(null);
            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                String formattedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(calendar.getTime());
                Log.d(TAG, "Selected Date: " + formattedDate);
                fetchData(formattedDate);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void fetchData(String date) {
        Call<NasaPhoto> call = (date == null) ? apiService.getApod(API_KEY) : apiService.getApodForDate(API_KEY, date);
        call.enqueue(new Callback<NasaPhoto>() {
            @Override
            public void onResponse(Call<NasaPhoto> call, Response<NasaPhoto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    displayData(response.body());
                } else {
                    todayDesc.setText("Error fetching data");
                    todayTitle.setText("Error fetching data");
                    Log.e(TAG, "Response unsuccessful or empty body");
                }
            }

            @Override
            public void onFailure(Call<NasaPhoto> call, Throwable t) {
                todayDesc.setText("Error fetching data");
                todayTitle.setText("Error fetching data");
                Log.e(TAG, "Error: " + t.getMessage(), t);
            }
        });
    }

    private void displayData(NasaPhoto photo) {
        LocalDate currentDate = LocalDate.now();
        LocalDate givenDate = LocalDate.parse(photo.getDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        long daysPassed = ChronoUnit.DAYS.between(givenDate, currentDate);


        todayTitle.setText(photo.getTitle());
        todayDesc.setText(photo.getDesc());
        textPass.setText("Upłynęło: " + daysPassed + " dni");
        Glide.with(this).load(photo.getUrl()).into(todayPhoto);
    }
}
