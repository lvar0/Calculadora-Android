package dam.unidad.act3_ud3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNumber; //Pantalla donde se muestran los numeros y resultados
    private double numero1 = 0;//Guarda el primer numero antes de pulsar un operadro
    private String operador = ""; //Guarda el operador pulsado ej +,-,*,/
    private boolean nuevoNumero = true;//Indica si el siguiente numero que se escriba debe
                                       //remplazar el anterior o añadirse al final

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Busca en el diseño el componente con el id editTextNumber y se guarda en una variable
        editTextNumber = findViewById(R.id.editTextNumber);
        //Hacemos un array con todos los ids de los botones
        int[] botonesNumeros = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
                R.id.btn8, R.id.btn9
        };
        //Cremos solo un listener para todos los botones de numeros en vez de uno para cada boton
        //
        View.OnClickListener listenerNumeros = v -> {
            Button btn = (Button) v;//Esto seria el boton que se pulso
            if (nuevoNumero) {
                editTextNumber.setText(btn.getText());
                nuevoNumero = false;
            } else {
                editTextNumber.append(btn.getText());
            }
        };
        //Bucle para que todos los botones de la array hagan lo mismo
        for (int id : botonesNumeros) {
            findViewById(id).setOnClickListener(listenerNumeros);
        }

        // Boton que añade un punto decimal solo si el numero actual no lo tiene ya
        //Si se empieza un nuevo numero se añade 0.
        findViewById(R.id.btnPunto).setOnClickListener(v -> {
            if (nuevoNumero) {
                editTextNumber.setText("0.");
                nuevoNumero = false;
            } else if (!editTextNumber.getText().toString().contains(".")) {
                editTextNumber.append(".");
            }
        });
        //Cada boton llama a la funcion guardarOperacion con el simbolo correspondiente
        findViewById(R.id.btnSumar).setOnClickListener(v -> guardarOperacion("+"));
        findViewById(R.id.btnRestar).setOnClickListener(v -> guardarOperacion("-"));
        findViewById(R.id.btnMultiplicar).setOnClickListener(v -> guardarOperacion("*"));
        findViewById(R.id.btnDividir).setOnClickListener(v -> guardarOperacion("/"));

        findViewById(R.id.btnResultado).setOnClickListener(v -> calcularResultado());

        findViewById(R.id.btnBorrar).setOnClickListener(v -> {
            editTextNumber.setText("0");
            numero1 = 0;
            operador = "";
            nuevoNumero = true;
        });
    }

    //Metodo para guardar el primer numero y el operador
    //Si hay un error al convertir el numero se pone a 0
    private void guardarOperacion(String operacion) {
        try {
            numero1 = Double.parseDouble(editTextNumber.getText().toString());
        } catch (Exception e) {
            numero1 = 0;
        }
        operador = operacion;
        nuevoNumero = true;
    }

    //Metodo que calcula el resultado de la operacion
    private void calcularResultado() {
        double numero2;
        double resultado = 0;

        try {
            numero2 = Double.parseDouble(editTextNumber.getText().toString());
        } catch (Exception e) {
            numero2 = 0;
        }

        switch (operador) {
            case "+":
                resultado = numero1 + numero2;
                break;
            case "-":
                resultado = numero1 - numero2;
                break;
            case "*":
                resultado = numero1 * numero2;
                break;
            case "/":
                if (numero2 == 0) {
                    editTextNumber.setText("Error");
                    nuevoNumero = true;
                    return;
                } else {
                    resultado = numero1 / numero2;
                }
                break;
            default:
                resultado = numero2;
                break;
        }

        if (resultado == (long) resultado) {
            editTextNumber.setText(String.valueOf((long) resultado));
        } else {
            editTextNumber.setText(String.valueOf(resultado));
        }

        nuevoNumero = true;
    }
}
