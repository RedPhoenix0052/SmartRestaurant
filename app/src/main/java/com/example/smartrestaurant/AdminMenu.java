package com.example.smartrestaurant;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;


public class AdminMenu extends AppCompatActivity {
	ListView listview;
	Button btn,button;
	EditText etid,etname,etprice;

	ArrayList<String> mid = new ArrayList<String>();
	ArrayList<String> mname = new ArrayList<String>();
	ArrayList<String> mprice = new ArrayList<String>();
	MyAdapter adapter;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_menu);
		etid = findViewById(R.id.etid);
		etname = findViewById(R.id.etname);
		etprice = findViewById(R.id.etprice);
		btn = findViewById(R.id.btn5);
		listview = findViewById(R.id.listview);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
					modifyMenu modifymenu = new modifyMenu();
					modifymenu.execute(etid.getText().toString(), etname.getText().toString(), etprice.getText().toString());
					overridePendingTransition(0, 0);
					recreate();
			}
		});

		new showMenu().execute();
	}

	class showMenu extends AsyncTask<String,String,String>{

		@Override
		protected String doInBackground(String... params) {
			String result = "";
			String host = "http://192.168.43.226/test_android/index2.php";
			try {
				HttpClient client = new DefaultHttpClient();
				HttpGet request = new HttpGet();
				request.setURI(new URI(host));
				HttpResponse response = client.execute(request);
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

				StringBuffer stringBuffer = new StringBuffer("");
				String line = "";
				while ((line = reader.readLine()) != null){
					stringBuffer.append(line);
				}
				reader.close();
				result = stringBuffer.toString();
			}
			catch (Exception e) {
				return new String("Exception "+e.getMessage());
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				JSONObject jsonResult = new JSONObject(result);
				int success = jsonResult.getInt("success");
				if(success == 1)
				{
					JSONArray menu = jsonResult.getJSONArray("menu");
					for(int i = 0; i<menu.length(); i++)
					{
						JSONObject menuitem = menu.getJSONObject(i);
						String id = menuitem.getString("id");
						String name = menuitem.getString("name");
						double price = menuitem.getDouble("price");
						mid.add(id);
						mname.add(name);
						mprice.add(Double.toString(price));
					}
					adapter = new MyAdapter(getApplicationContext(),mid,mname,mprice);
					listview.setAdapter(adapter);
				}
				else
				{
					Toast.makeText(getApplicationContext(),"Nothing to show",Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
			}
		}
	}


	class modifyMenu extends AsyncTask<String,String,JSONObject> {

		String URL= "http://192.168.43.226/test_android/index3.php";

		JSONParser jsonParser=new JSONParser();
		@Override
		protected JSONObject doInBackground(String... args) {
			String id = args[0];
			String name = args[1];
			String price = args[2];

			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id", id));
			params.add(new BasicNameValuePair("name", name));
			params.add(new BasicNameValuePair("price", price));
			JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			try {
				if (result != null) {
					Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	class MyAdapter extends ArrayAdapter<String>
	{
		Context context;
		ArrayList<String> rid = new ArrayList<String>();
		ArrayList<String> rname = new ArrayList<String>();
		ArrayList<String> rprice = new ArrayList<String>();

		MyAdapter(Context c, ArrayList<String> id, ArrayList<String> name, ArrayList<String> price)
		{
			super(c,R.layout.list,R.id.textView1,id);
			this.context=c;
			this.rid=id;
			this.rname=name;
			this.rprice=price;
		}

		@NonNull
		@Override
		public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
			LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View list = layoutInflater.inflate(R.layout.list,parent,false);
			TextView myid = list.findViewById(R.id.textView1);
			TextView myname = list.findViewById(R.id.textView2);
			TextView myprice = list.findViewById(R.id.textView3);
			button = list.findViewById(R.id.button4);
			if(rid.get(0)==""||rid==null)
			{
				listview.setVisibility(View.INVISIBLE);

			}
			else
			{
				myid.setText(rid.get(position));
				myname.setText(rname.get(position));
				myprice.setText("Rs. "+rprice.get(position));

			}
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String selectedid = (String) listview.getItemAtPosition(position);
					modifyMenu modifymenu = new modifyMenu();
					modifymenu.execute(selectedid,"","");
					overridePendingTransition( 0, 0);
					recreate();
				}
			});

			return list;
		}
	}

}
