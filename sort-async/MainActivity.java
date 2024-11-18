package com.example.megasort;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNumber;
    private Button buttonStart;
    private ProgressBar progressBar;
    private SortingView sortingView;

    private ExecutorService executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumber = findViewById(R.id.editTextNumber);
        buttonStart = findViewById(R.id.buttonStart);
        progressBar = findViewById(R.id.progressBar);
        sortingView = findViewById(R.id.sortingView);

        executor = Executors.newSingleThreadExecutor();

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = editTextNumber.getText().toString();
                int numberOfElements;

                try {
                    numberOfElements = Integer.parseInt(input);
                    if (numberOfElements <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Proszę wprowadzić prawidłową liczbę.", Toast.LENGTH_SHORT).show();
                    return;
                }

                final int[] array = new int[numberOfElements];
                for (int i = 0; i < numberOfElements; i++) {
                    array[i] = (int) (Math.random() * numberOfElements) + 1;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sortingView.setArray(array);
                        progressBar.setProgress(0);
                    }
                });

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        bubbleSort(array);
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }

    private void bubbleSort(int[] array) {
        int n = array.length;
        boolean swapped;

        int totalOperations = (n - 1) * n;
        int currentOperation = 0;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                currentOperation++;

                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;

                    final int progress = (int) (((double) currentOperation / totalOperations) * 100);
                    final int[] arrayCopy = array.clone();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            sortingView.setArray(arrayCopy);
                            progressBar.setProgress(progress);
                        }
                    });

                    try {
                        Thread.sleep(1); // 1 ms} catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (!swapped) {
                break;
            }
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(100);
                sortingView.setArray(array);
                Toast.makeText(MainActivity.this, "Sortowanie zakończone!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
