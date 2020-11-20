package com.smartiecards.storage;

public class ItemCard {
	
	String gen_id;
	String id;
	String card_name;
	String card_no;
	String expiray_date;
	String cvv;
	String cardType;


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCard_name() {
		return card_name;
	}
	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getGen_id() {
		return gen_id;
	}
	public void setGen_id(String gen_id) {
		this.gen_id = gen_id;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public String getExpiray_date() {
		return expiray_date;
	}
	public void setExpiray_date(String expiray_date) {
		this.expiray_date = expiray_date;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	
	
	
	

}
