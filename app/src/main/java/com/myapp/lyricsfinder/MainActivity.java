package com.myapp.lyricsfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText edtArtistName, edtSongName;            /* defining the variables */
    Button btnGetLyrics;
    TextView txtLyrics;

    //https://api.lyrics.ovh/v1/Rihanna/Diamonds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing the variables
        edtArtistName = findViewById(R.id.edtArtistName);
        edtSongName = findViewById(R.id.edtSongName);
        btnGetLyrics = findViewById(R.id.btnGetLyrics);
        txtLyrics = findViewById(R.id.txtLyrics);

        btnGetLyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(getApplicationContext(),"Button is tapped now!",Toast.LENGTH_SHORT).show();
                String url = "https://api.lyrics.ovh/v1/" + edtArtistName.getText().toString() + "/"
                                + edtSongName.getText().toString();

                /*to find lyrics of the artist having spaces in between their names
                %20 is the space a url has between two names, replacing it to " "
                 */
                url.replaceAll(" ","%20");

                //Creating a Request Queue
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                /*JSON Object request is created because the api
                contains the JSON Object. new JsonObjectRequest
                creates new object in JAVA.
                 */
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    //onResponse Method is called when the user gets a response from the server.
                    public void onResponse(JSONObject response) {

                        /*Exception Handling
                        lyrics is the name of string the url has in
                        x  which the lyrics of the song is written.
                         */
                        try {

                            txtLyrics.setText(response.getString("lyrics"));

                        } catch (JSONException e){
                            e.printStackTrace();
                        }



                    }
                },
                        //creating Response.Error to remove the error in onResponse
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                requestQueue.add(jsonObjectRequest);
            }
        });
    }
}