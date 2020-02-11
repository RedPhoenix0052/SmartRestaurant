package com.example.smartrestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class PlaceOrder extends AppCompatActivity {
	TextView viewfinalorder;
	Button btnct;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place_order);
		viewfinalorder = findViewById(R.id.viewfinalorder);
		btnct = findViewById(R.id.btnct);
		Intent intnt = getIntent();
		final String rcvorder = intnt.getStringExtra("message");
		final double rcvtprice = intnt.getDoubleExtra("totalprice",0);
		final String rcvtablen = intnt.getStringExtra("table");

		viewfinalorder.setText(rcvorder+"\n"+"Rs. "+rcvtprice);
		btnct.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FirebaseDatabase.getInstance().getReference().child("order").push().setValue(new MakeOrder(rcvorder, ""+rcvtprice, rcvtablen));
				Intent intent = new Intent(getApplicationContext(),Customer.class);
				startActivity(intent);
			}
		});
	}
}
