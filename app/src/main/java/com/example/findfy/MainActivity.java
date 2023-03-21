package com.example.findfy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
/*import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;*/



public class MainActivity extends AppCompatActivity {

    private String apiKey="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";

    private String texto_busqueda="";

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Para borrar los datos de la local Storage
        SharedPreferences sharedPreferences = getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();*/

        SearchView text_buscar = (SearchView) findViewById(R.id.searchView);
        text_buscar.findFocus();

        text_buscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Realizar la búsqueda con el texto ingresado por el usuario
                texto_busqueda = query;
                //sendMessageToChatGPT(texto_busqueda);
                callAPI(texto_busqueda);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Actualizar la interfaz de usuario en función del texto de búsqueda actual
                return true;
            }
        });
    }

    public void mostrarResultado(String result){
        Intent intent = new Intent(this, Busqueda_Screeen.class);
        intent.putExtra("Titulo", texto_busqueda);
        intent.putExtra("Respuesta", result);
        startActivity(intent);
    }

    void callAPI(String question){
        //okhttp
        //messageList.add(new Message("Typing... ",Message.SENT_BY_BOT));

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model","text-davinci-003");
            jsonBody.put("prompt",question+", responde en español");
            jsonBody.put("max_tokens",4000);
            jsonBody.put("temperature",0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(jsonBody.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .header("Authorization","Bearer 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")
                .post(body)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                //addResponse("Failed to load response due to "+e.getMessage());
                mostrarResultado("Failed to load response due to "+e.getMessage());
                System.out.println("error loading"+e.getMessage());
            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
                if(response.isSuccessful()){
                    JSONObject  jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0).getString("text");
                        System.out.println(result.trim());
                        mostrarResultado(result.trim());
                        //addResponse(result.trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }else{
                    //addResponse("Failed to load response due to "+response.body().toString());
                    mostrarResultado("Failed to load response due to "+response.body().toString());
                    System.out.println("error loading");
                }
            }
        });
    }
}