package com.example.smartrestaurant;

import java.util.Date;

public class MakeOrder {

	private String orderText;
	private String orderPrice;
	private String orderUser;
	private long orderTime;

	public MakeOrder(String orderText, String orderPrice, String orderUser, String orderTime) {
		this.orderText = orderText;
		this.orderPrice = orderPrice;
		this.orderUser = orderUser;
		this.orderTime = new Date().getTime();
	}

	public MakeOrder(String orderText, String orderPrice, String orderUser) {
		this.orderText = orderText;
		this.orderPrice = orderPrice;
		this.orderUser = orderUser;
		this.orderTime = new Date().getTime();
	}

	public MakeOrder() {

	}

	public String getOrderText() {
		return orderText;
	}

	public void setOrderText(String orderText) {
		this.orderText = orderText;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getOrderUser() {
		return orderUser;
	}

	public void setOrderUser(String orderUser) {
		this.orderUser = orderUser;
	}

	public long getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(long orderTime) {
		this.orderTime = orderTime;
	}
}
