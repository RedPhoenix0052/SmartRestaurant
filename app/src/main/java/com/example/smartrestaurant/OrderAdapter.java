package com.example.smartrestaurant;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.CustomOrderAdapter>{

	private ArrayList<MakeOrder> makeOrders;

	public OrderAdapter(ArrayList<MakeOrder> makeOrders) {
		this.makeOrders = makeOrders;
	}

	@NonNull
	@Override
	public CustomOrderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list3,parent,false);
		return new CustomOrderAdapter(view);
	}

	@Override
	public void onBindViewHolder(@NonNull CustomOrderAdapter holder, int position) {
		if(position<makeOrders.size()){
			holder.tvPrice.setText(makeOrders.get(position).getOrderPrice());
			holder.tvTable.setText(makeOrders.get(position).getOrderUser());
			holder.tvOrder.setText(makeOrders.get(position).getOrderText());
			holder.tvTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
					makeOrders.get(position).getOrderTime()));
		}
	}

	@Override
	public int getItemCount() {
		return makeOrders.size();
	}

	public class CustomOrderAdapter extends RecyclerView.ViewHolder{
		private TextView tvTable,tvOrder,tvPrice,tvTime;
		public CustomOrderAdapter(@NonNull View itemView) {
			super(itemView);
			tvOrder = itemView.findViewById(R.id.tvorder);
			tvPrice = itemView.findViewById(R.id.tvprice);
			tvTime = itemView.findViewById(R.id.tvtime);
			tvTable = itemView.findViewById(R.id.tvtable);
		}
	}
}
