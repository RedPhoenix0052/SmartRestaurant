package com.example.smartrestaurant;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class admin_activity extends AppCompatActivity implements View.OnClickListener {
	Button btnviewmenu, btnvieworder;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_activity);
		btnviewmenu = findViewById(R.id.btn1);
		btnvieworder = findViewById(R.id.btn2);
		btnviewmenu.setOnClickListener(this);
		btnvieworder.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.btn1:
				Intent intent = new Intent(this,AdminMenu.class);
				startActivity(intent);
				break;
			case R.id.btn2:
				Intent intent1 = new Intent(this,ViewOrder.class);
				startActivity(intent1);
				break;
		}
	}
}
