package esteban.g.cuadro_magico;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private Button generateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridLayout);
        generateButton = findViewById(R.id.generateButton);

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateAndShowMagicSquare();
            }
        });
    }

    private void generateAndShowMagicSquare() {
        int[][] magicSquare = generateMagicSquare();

        // Limpiar el GridLayout antes de mostrar el nuevo cuadrado mágico
        gridLayout.removeAllViews();

        int[] rowSums = new int[magicSquare.length];
        int[] colSums = new int[magicSquare[0].length];
        // Mostrar los números en el GridLayout

        for (int i = 0; i < magicSquare.length; i++) {
            int rowSum = 0;
            for (int j = 0; j < magicSquare[i].length; j++) {
                TextView textView = new TextView(this);
                textView.setText(String.valueOf(magicSquare[i][j]));
                textView.setTextSize(20);
                textView.setPadding(8, 8, 8, 8);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.rowSpec = GridLayout.spec(i);
                params.columnSpec = GridLayout.spec(j);
                textView.setLayoutParams(params);

                gridLayout.addView(textView);

                rowSum += magicSquare[i][j];
                colSums[j] += magicSquare[i][j];
            }
            rowSums[i] = rowSum;
        }
        // mostrar resultados
        for (int i = 0; i < rowSums.length; i++) {
            TextView rowSumTextView = new TextView(this);
            rowSumTextView.setText("= "+ rowSums[i]);
            rowSumTextView.setTextSize(15);
            rowSumTextView.setPadding(8, 8, 8, 8);

            GridLayout.LayoutParams rowParams = new GridLayout.LayoutParams();
            rowParams.rowSpec = GridLayout.spec(i);
            rowParams.columnSpec = GridLayout.spec(4); // Columna adicional para las sumas de filas
            rowSumTextView.setLayoutParams(rowParams);

            gridLayout.addView(rowSumTextView);
        }

        for (int i = 0; i < colSums.length; i++) {
            TextView colSumTextView = new TextView(this);
            colSumTextView.setText("= "+ String.valueOf(colSums[i]));
            colSumTextView.setTextSize(15);
            colSumTextView.setPadding(8, 8, 8, 8);

            GridLayout.LayoutParams colParams = new GridLayout.LayoutParams();
            colParams.rowSpec = GridLayout.spec(4); // Fila adicional para las sumas de columnas
            colParams.columnSpec = GridLayout.spec(i);
            colSumTextView.setLayoutParams(colParams);

            gridLayout.addView(colSumTextView);
        }

        int diagonalSum1 = 0;
        for (int i = 0; i < magicSquare.length; i++) {
            diagonalSum1 += magicSquare[i][i];
        }

// Calcular la suma de la diagonal secundaria
        int diagonalSum2 = 0;
        for (int i = 0; i < magicSquare.length; i++) {
            diagonalSum2 += magicSquare[i][magicSquare.length - 1 - i];
        }

// Mostrar la suma de la diagonal principal
        TextView diagonalSum1TextView = new TextView(this);
        diagonalSum1TextView.setId(View.generateViewId()); // Genera un ID único para el TextView
        diagonalSum1TextView.setText("Suma diagonal 1: " + diagonalSum1);
        diagonalSum1TextView.setTextSize(15);
        diagonalSum1TextView.setPadding(8, 8, 8, 8);

        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params1.addRule(RelativeLayout.BELOW, R.id.gridLayout); // ID del GridView o elemento debajo del cual se quiere colocar
        params1.addRule(RelativeLayout.ALIGN_PARENT_START);
        diagonalSum1TextView.setLayoutParams(params1);

// Añadir el TextView al layout principal de tu actividad (fuera del GridLayout)
        RelativeLayout mainLayout = findViewById(R.id.mainLayout); // Cambia esto por el ID de tu layout principal
        mainLayout.addView(diagonalSum1TextView);

// Mostrar la suma de la diagonal secundaria
        TextView diagonalSum2TextView = new TextView(this);
        diagonalSum2TextView.setId(View.generateViewId()); // Genera un ID único para el TextView
        diagonalSum2TextView.setText("Suma diagonal 2: " + diagonalSum2);
        diagonalSum2TextView.setTextSize(15);
        diagonalSum2TextView.setPadding(8, 8, 8, 8);

        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params2.addRule(RelativeLayout.BELOW, R.id.gridLayout); // ID del GridView o elemento debajo del cual se quiere colocar
        params2.addRule(RelativeLayout.END_OF, diagonalSum1TextView.getId());
        params2.addRule(RelativeLayout.ALIGN_PARENT_END);
        diagonalSum2TextView.setLayoutParams(params2);

// Añadir el TextView al layout principal de tu actividad (fuera del GridLayout)
        mainLayout.addView(diagonalSum2TextView);
    }

    private int[][] generateMagicSquare() {
        int[][] square = new int[4][4];

        for (int i = 0; i < square.length; i++) {
            Arrays.fill(square[i], 0);
        }

        ArrayList<Integer> remainingNumbers = new ArrayList<>();
        for (int i = 0; i < square.length; i++) {
            for (int j = 0; j < square[i].length; j++) {
                square[i][j] = generateRandomNumber();
                if (i == 0){
                    remainingNumbers.add(square[i][j]);
                }
            }
        }

        Random random = new Random();
        while (!checkMagicSquare(square, remainingNumbers.stream().mapToInt(Integer::intValue).sum())) {
            for (int i = 1; i < square.length; i++) {
                ArrayList<Integer> aux = new ArrayList<>(remainingNumbers);
                for (int j = 0; j < square[i].length; j++) {
                    int index = 1;
                    if (aux.size() != 0){
                        index = random.nextInt(aux.size());
                    }
                    square[i][j] = aux.get(index);
                    //square[j][i] = square[i][j];
                    aux.remove(index);
                }
            }
        }

        return square;
    }

    private boolean checkMagicSquare(int[][] square, int magicSum) {
        // Verifica la suma de todas las filas, columnas y diagonales principales
        for (int i = 0; i < square.length; i++) {
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < square[i].length; j++) {
                rowSum += square[i][j];
                colSum += square[j][i];
            }
            if (rowSum != magicSum || colSum != magicSum) {
                return false;
            }
        }

        int diag1Sum = 0;
        int diag2Sum = 0;
        for (int i = 0; i < square.length; i++) {
            diag1Sum += square[i][i];
            diag2Sum += square[i][square.length - i - 1];
        }
        return diag1Sum == magicSum && diag2Sum == magicSum;
    }



    private int generateRandomNumber() {
        int number;
        do {
            number = (int) (Math.random() * 9000) + 1000; // Número de 4 dígitos
        } while (!isFiveOrSevenOnly(number));

        return number;
    }

    private boolean isFiveOrSevenOnly(int number) {
        // Convertir el número en una cadena de texto para comprobar cada dígito
        String numStr = String.valueOf(number);

        for (int i = 0; i < numStr.length(); i++) {
            char digit = numStr.charAt(i);
            if (digit != '5' && digit != '7') {
                return false; // El número contiene dígitos distintos de 5 y 7
            }
        }

        return true; // El número está compuesto solo por 5 y/o 7
    }

}