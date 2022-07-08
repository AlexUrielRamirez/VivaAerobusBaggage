package pcm.uarm.vbbaggage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public class Inicio extends AppCompatActivity {

    private interface api_network{
        @FormUrlEncoded
        @POST("/getBaggageStatus.php")
        void setData(
                @Field("numero") String numero,
                @Field("apellidos") String Apellidos,
                Callback<Response> callback
        );
    }

    private EditText et_numero_vuelo, et_apellidos;
    private TextView txt_error;
    private final int RED = 0, YELLOW = 1;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Methods().CambiarColorStatusBar(this, R.color.viva_main);
        setContentView(R.layout.activity_inicio);

        pd = new ProgressDialog(this);
        pd.setMessage("Consultando, espere...");
        pd.setCancelable(false);
        et_numero_vuelo = findViewById(R.id.et_numero_vuelo);
        et_apellidos = findViewById(R.id.et_apellidos);
        txt_error = findViewById(R.id.txt_error);

        findViewById(R.id.btn_consultar_equipaje).setOnClickListener(v->{
            txt_error.setVisibility(View.GONE);
            String numero_vuelo = et_numero_vuelo.getText().toString();
            String apellidos = et_apellidos.getText().toString();

            if(numero_vuelo.length() >= 4 && apellidos.length() >= 3){
                pd.show();
                new RestAdapter.Builder().setEndpoint("http://192.168.1.70/vivaaerobus").build().create(api_network.class).setData(numero_vuelo, apellidos, new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        try{
                            String server_response = new BufferedReader(new InputStreamReader(response.getBody().in())).readLine();
                            JSONObject json = new JSONObject(server_response);

                            switch (json.getString("result")){
                                case "success":
                                    GlobalPreferences.model = new GlobalPreferences.ModelBag();
                                    GlobalPreferences.model.setId(json.getString("Id"));
                                    GlobalPreferences.model.setNombre(json.getString("Nombre"));
                                    GlobalPreferences.model.setNumeroVuelo(json.getString("NumeroVuelo"));
                                    GlobalPreferences.model.setStatus(json.getString("Status"));
                                    pd.hide();
                                    startActivity(new Intent(Inicio.this, Resultados.class));
                                    break;
                                case "empty":
                                    pd.hide();
                                    show_error("No se encontró el vuelo ni el nombre indicado", YELLOW);
                                    break;
                                case "error":
                                    pd.hide();
                                    show_error("Algo salió mal, intente nuevamente", YELLOW);
                                    break;
                            }
                        }catch (IOException | JSONException e){
                            pd.hide();
                            show_error("Algo salió mal, intente nuevamente", YELLOW);
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        pd.hide();
                        show_error("Verifique su conexión a internet", RED);
                        Log.e("Inicio", error.getMessage());
                    }
                });

            }else{
                show_error("Por favor, ingrese información válida", YELLOW);
            }
        });
    }

    private void show_error(String msg, int color){
        txt_error.setText(msg);
        switch (color){
            case RED:
                txt_error.setBackground(getDrawable(R.drawable.bg_error_red));
                break;
            case YELLOW:
                txt_error.setBackground(getDrawable(R.drawable.bg_error_yellow));
                break;
        }
        txt_error.setVisibility(View.VISIBLE);
    }

}