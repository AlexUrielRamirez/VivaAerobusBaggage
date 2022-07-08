package pcm.uarm.vbbaggage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Resultados extends AppCompatActivity {

    private LinearLayout holder_steps, step_2, step_3, step_4;
    private TextView txt_vuelo, txt_nombre, txt_status_cero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Methods().CambiarColorStatusBar(this, R.color.viva_main);
        setContentView(R.layout.activity_resultados);

        View.OnClickListener click_salir = view -> Resultados.this.finish();

        findViewById(R.id.btn_terminar).setOnClickListener(click_salir);
        findViewById(R.id.back).setOnClickListener(click_salir);

        txt_vuelo = findViewById(R.id.txt_numero_vuelo);
        txt_nombre = findViewById(R.id.txt_nombre);

        holder_steps = findViewById(R.id.holder_steps);
        txt_status_cero = findViewById(R.id.txt_status_cero);
        step_2 = findViewById(R.id.step_2);
        step_3 = findViewById(R.id.step_3);
        step_4 = findViewById(R.id.step_4);

        txt_vuelo.setText(GlobalPreferences.model.getNumeroVuelo());
        txt_nombre.setText(GlobalPreferences.model.getNombre());

        setUpSteps(Integer.parseInt(GlobalPreferences.model.Status));
    }

    private void setUpSteps(int status){
        if(status != 0){
            txt_status_cero.setVisibility(View.GONE);
            for(int i = 1; i <= status; i++){
                switch (i){
                    case 1:
                        holder_steps.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        step_2.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        step_3.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        step_4.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }else{
            txt_status_cero.setVisibility(View.VISIBLE);
        }
    }

}