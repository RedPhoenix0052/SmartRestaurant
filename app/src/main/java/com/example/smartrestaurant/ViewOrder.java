package com.example.smartrestaurant;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class ViewOrder extends AppCompatActivity {
//	FirebaseListAdapter<MakeOrder> adapter;

	RecyclerView listoforders;
	ArrayList<MakeOrder> makeOrders = new ArrayList<>();
	private ProgressBar progressBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_order);
		listoforders = findViewById(R.id.lv);
		progressBar = findViewById(R.id.pb);

		DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("order");
		progressBar.setVisibility(View.VISIBLE);
		db.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				makeOrders.clear();
				for(DataSnapshot ds:dataSnapshot.getChildren()){
					MakeOrder makeOrder = ds.getValue(MakeOrder.class);
					makeOrders.add(makeOrder);
				}

				populateRv();
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {

			}
		});
	}

	private void populateRv() {
		OrderAdapter orderAdapter = new OrderAdapter(makeOrders);
		progressBar.setVisibility(View.GONE);
		listoforders.setLayoutManager(new GridLayoutManager(this,1));
		listoforders.setAdapter(orderAdapter);
	}
}
