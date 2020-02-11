package com.example.smartrestaurant;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
	Button admin,customer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		admin = findViewById(R.id.button);
		customer= findViewById(R.id.button2);

		admin.setOnClickListener(this);
		customer.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId())
		{
			case R.id.button:
				Intent intent = new Intent(this,login_activity.class);
				startActivity(intent);
				break;
			case R.id.button2:
				Intent intent1 = new Intent(this,TableN.class);
				startActivity(intent1);
				break;
		}
	}
}
