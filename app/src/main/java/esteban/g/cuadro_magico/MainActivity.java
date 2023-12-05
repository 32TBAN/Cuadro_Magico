package esteban.g.cuadro_magico;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

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

        // Mostrar los números en el GridLayout
        for (int i = 0; i < magicSquare.length; i++) {
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
            }
        }
    }

    private int[][] generateMagicSquare() {
        int[][] square = new int[4][4];

        // Llenar la matriz con números aleatorios de 4 dígitos hechos de 5 y/o 7
        for (int i = 0; i < square.length; i++) {
            for (int j = 0; j < square[i].length; j++) {
                square[i][j] = generateRandomNumber();
            }
        }

        return square;
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