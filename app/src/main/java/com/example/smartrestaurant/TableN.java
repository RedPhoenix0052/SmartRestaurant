package com.example.smartrestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TableN extends AppCompatActivity {
	EditText ettablen;
	Button btntablen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_table_n);
		ettablen = findViewById(R.id.ettablen);
		btntablen = findViewById(R.id.btntablen);
		btntablen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(TableN.this, Customer.class);
				intent.putExtra("table",ettablen.getText().toString());
				startActivity(intent);
			}
		});
	}
}
