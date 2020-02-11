package com.example.smartrestaurant;

import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class login_activity extends AppCompatActivity {


	EditText editPassword, editName;
	Button btnSignIn;

	String URL= "http://192.168.43.226/test_android/index.php";

	JSONParser jsonParser=new JSONParser();

	int i=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_activity);

		editName=findViewById(R.id.editName);
		editPassword=findViewById(R.id.editPassword);

		btnSignIn=findViewById(R.id.btnSignIn);

		btnSignIn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AttemptLogin attemptLogin= new AttemptLogin();
				attemptLogin.execute(editName.getText().toString(),editPassword.getText().toString(),"");
			}
		});

	}

	private class AttemptLogin extends AsyncTask<String, String, JSONObject> {

		@Override

		protected void onPreExecute() {

			super.onPreExecute();

		}

		@Override

		protected JSONObject doInBackground(String... args) {

			String password = args[1];
			String name= args[0];

			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", name));
			params.add(new BasicNameValuePair("password", password));

			JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);

			return json;

		}

		protected void onPostExecute(JSONObject result) {

			try {
				if (result != null) {
					Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();
					if((result.getString("message")).equals("Successfully logged in")) {
						Intent intentM = new Intent(getApplicationContext(), admin_activity.class);
						startActivity(intentM);
					}
				} else {
					Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

	}
}
