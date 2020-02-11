package com.example.smartrestaurant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;

public class Customer extends AppCompatActivity {
	Button btnmin,btnplus,btnpo;
	ListView clistView;


	ArrayList<String> mcname = new ArrayList<String>();
	ArrayList<String> mcprice = new ArrayList<String>();
	ArrayList<String> mcqty = new ArrayList<String>();
	MyCAdapter cAdapter;
	storeOrder storeorder = new storeOrder(null,null,null);
	ArrayList<storeOrder> data;
	String datastr = "",rcvtable;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer);
		btnpo = findViewById(R.id.btnpo);
		clistView = findViewById(R.id.listViewC);
		new showCMenu().execute();
		Intent intent = getIntent();
		rcvtable = intent.getStringExtra("table");
		btnpo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				datastr="";
				double caltotal=0,calprice=0,calqty=0;
				for(storeOrder st:data){
					if(st.name!=null && st.price!=null && st.qty!=null)
					{
						datastr = datastr.concat(st.name+" "+st.price+" "+st.qty+"\n");
						calprice = Double.parseDouble(st.price);
						calqty = Double.parseDouble(st.qty);
						caltotal += (calprice * calqty);
					}
				}

				Intent senddata = new Intent(Customer.this,PlaceOrder.class);
				senddata.putExtra("table",rcvtable);
				senddata.putExtra("message",datastr);
				senddata.putExtra("totalprice",caltotal);
				startActivity(senddata);
			}
		});
	}

	class showCMenu extends AsyncTask<String,String,String> {

		@Override
		protected String doInBackground(String... params) {
			String cresult = "";
			String chost = "http://192.168.43.226/test_android/index2.php";
			try {
				HttpClient client = new DefaultHttpClient();
				HttpGet request = new HttpGet();
				request.setURI(new URI(chost));
				HttpResponse response = client.execute(request);
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

				StringBuffer stringBuffer = new StringBuffer("");
				String line = "";
				while ((line = reader.readLine()) != null){
					stringBuffer.append(line);
				}
				reader.close();
				cresult = stringBuffer.toString();
			}
			catch (Exception e) {
				return new String("Exception "+e.getMessage());
			}
			return cresult;
		}

		@Override
		protected void onPostExecute(String cresult) {
			try {
				JSONObject jsonResult = new JSONObject(cresult);
				int success = jsonResult.getInt("success");
				if(success == 1)
				{
					JSONArray menu = jsonResult.getJSONArray("menu");
					for(int i = 0; i<menu.length(); i++)
					{
						JSONObject menuitem = menu.getJSONObject(i);
						String name = menuitem.getString("name");
						double price = menuitem.getDouble("price");
						mcname.add(name);
						mcprice.add(Double.toString(price));
						mcqty.add("0");
					}
					cAdapter = new MyCAdapter(getApplicationContext(),mcname,mcprice,mcqty);
					clistView.setAdapter(cAdapter);
					int size = menu.length();
					data = new ArrayList<storeOrder>(Collections.nCopies(size+1, storeorder));
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

	class MyCAdapter extends ArrayAdapter<String>
	{
		Context context;
		ArrayList<String> rcname = new ArrayList<String>();
		ArrayList<String> rcprice = new ArrayList<String>();
		ArrayList<String> rcqty = new ArrayList<String>();

		MyCAdapter(Context c, ArrayList<String> cname, ArrayList<String> cprice, ArrayList<String> cqty)
		{
			super(c,R.layout.list2,R.id.textViewname,cname);
			this.context=c;
			this.rcname=cname;
			this.rcprice=cprice;
			this.rcqty=cqty;
		}

		@NonNull
		@Override
		public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
			LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View clist = layoutInflater.inflate(R.layout.list2,parent,false);
			final TextView mycname = clist.findViewById(R.id.textViewname);
			final TextView mycprice = clist.findViewById(R.id.textViewprice);
			final TextView mycqty = clist.findViewById(R.id.textViewqty);
			btnmin = clist.findViewById(R.id.btnmin);
			btnplus = clist.findViewById(R.id.btnplus);
			if(rcname.get(0) == "" || rcname == null)
			{
				clistView.setVisibility(View.INVISIBLE);

			}
			else
			{
				mycname.setText(rcname.get(position));
				mycprice.setText(rcprice.get(position));
				mycqty.setText(rcqty.get(position));

			}
			btnmin.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					int i = Integer.parseInt(mycqty.getText().toString());
					if(i > 0) {
						i--;
						if (i == 0) {
							mycqty.setText("0");
							rcqty.set(position,"0");
							data.remove(position);
						} else {
							mycqty.setText("" + i);
							rcqty.set(position,""+i);
							storeorder = new storeOrder(mycname.getText().toString(), mycprice.getText().toString(), mycqty.getText().toString());
							data.set(position, storeorder);
						}
					}
				}
			});
			btnplus.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					int i = Integer.parseInt(mycqty.getText().toString());
					i++;
					mycqty.setText(""+i);
					rcqty.set(position,""+i);
					if(i==1) {
						storeorder = new storeOrder(mycname.getText().toString(),mycprice.getText().toString(),mycqty.getText().toString());
						data.add(position,storeorder);

						}
					else if(i>1) {
						storeorder = new storeOrder(mycname.getText().toString(),mycprice.getText().toString(),mycqty.getText().toString());
						data.set(position,storeorder);
						}

				}
			});

			return clist;
		}
	}
}
