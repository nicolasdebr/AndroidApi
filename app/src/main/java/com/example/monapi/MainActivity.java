package com.example.monapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // les champs
    private EditText editTextUserName,editTextEmail, editTextPassword;
    // le bouton
    private Button buttonRegister;
    // le progressdialog
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(this);

    }

    private void registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        final String username = editTextUserName.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        // On affiche un popup de progression
        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Enregistrement utilisateur");
        progressDialog.show();



        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

// arret du popup
            progressDialog.dismiss();
// récupération du Json de réponse
            try {
                JSONObject jsonObject = new JSONObject(response);
// affichage de la réponse
                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            progressDialog.hide();
            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

        }
    }) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {

            Map<String, String> params = new HashMap<>();
            params.put("username", username);
            params.put("email", email);
            params.put("password", password);
            return params;
        }

    };
        RequestQueue requestQueue =  Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    @Override
    public void onClick(View v) {
        // on se donne la possibilité d'avoir plusieurs actionneurs
        if (v == buttonRegister)
            registerUser();
    }
}
