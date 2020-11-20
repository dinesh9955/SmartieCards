package com.smartiecards.storage;



import java.util.ArrayList;

//import com.caffemoro.history.ItemHistory;
//import com.caffemoro.pay.ItemCard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DataBaseHelper extends SQLiteOpenHelper {

	String TAG = "MyDatabaseHelper";

	Context context;
	

	Cursor cursor = null;
	Cursor cursor2 = null;
	
	private static final String DBName = "CaffeMoroDatabase";
	
	
	
	
	private static final String TBNameCaffeMoro = "CaffeMoro";	

	private static final String Col_CAFFEMORO_Id_AutoGen = "_id";
	private static final String Col_CAFFEMORO_ID = "id";
	private static final String Col_CAFFEMORO_Card_Name = "card_name";
	private static final String Col_CAFFEMORO_CardNo = "cardno";
	private static final String Col_CAFFEMORO_ExpiryDate = "expiry_date";
	private static final String Col_CAFFEMORO_CVV = "cvv";
	private static final String Col_CAFFEMORO_CARD_TYPE = "card_type";
	
	
	
	
	
	
	

	SQLiteDatabase db;

	public DataBaseHelper(Context context1) {
		super(context1, DBName, null, 1);
		// TODO Auto-generated constructor stub
		context = context1;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		  db.execSQL("Create table " +TBNameCaffeMoro+ " (" +Col_CAFFEMORO_Id_AutoGen+ " INTEGER PRIMARY KEY AUTOINCREMENT , "
				  +Col_CAFFEMORO_ID+ " String , "
				  +Col_CAFFEMORO_Card_Name+ " String , "
				  +Col_CAFFEMORO_CardNo+ " String , "
				  +Col_CAFFEMORO_ExpiryDate+ " String , "
				  +Col_CAFFEMORO_CVV+ " String , "
				  +Col_CAFFEMORO_CARD_TYPE+ " String);");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL(String.format(" drop %s if exists %s", TBNameCaffeMoro, DBName));
		onCreate(db);
	}
	
	
	
	
	
	
	
	
	
// insertProperty ===========================================================================================================================
	
	public void insertCart(
			String uid,
			String card_name, 
			String card_no, 
			String exipryDate, 
			String cvv,
			String cardType
			) {
		// TODO Auto-generated method stub
		
		db = getWritableDatabase();
		
	
		ContentValues values = new ContentValues();
		
		values.put(Col_CAFFEMORO_ID, uid);
		values.put(Col_CAFFEMORO_Card_Name, card_name);
		values.put(Col_CAFFEMORO_CardNo, card_no);
		values.put(Col_CAFFEMORO_ExpiryDate, exipryDate);
		values.put(Col_CAFFEMORO_CVV, cvv);
		values.put(Col_CAFFEMORO_CARD_TYPE, cardType);
		
		
		
		try{
			db.insert(TBNameCaffeMoro, null, values);
			Log.d(TAG, "TBNamePMIPlanet item data saved ");
		}catch(Exception e){
			Log.d(TAG, "TBNamePMIPlanet Error");
		}
		
		
	}
	// insertProperty ===========================================================================================================================
	

	
	
////	 getAllProperty ===========================================================================================================================
//	
//			public ArrayList<ItemAllProducts> getCart() {
//				
//				// TODO Auto-generated method stub
//				ArrayList<ItemAllProducts> itemProperties = new ArrayList<ItemAllProducts>();
//				
//				
//				db = getReadableDatabase();
//				
//				
//				
//				String table_coloums []= {Col_PMIPlanet_Id_AutoGen,
//						Col_PMIPlanet_product_id ,
//						Col_PMIPlanet_product_image, 
//						Col_PMIPlanet_product_name,
//						Col_PMIPlanet_quentity,
//						Col_PMIPlanet_price,
//						Col_PMIPlanet_total_price
//		   			
//							};
//				
//					cursor = db.query(TBNamePMIPlanet, table_coloums,null, null , null , null, null);
//					
//					
//					
//				
//				
//					 
//				if(cursor != null && cursor.getCount() > 0){
//					
//					cursor.moveToFirst(); 
//					
//						do {
//							String gen_id = cursor.getString(0);
//							String product_id = cursor.getString(1);
//							String product_image = cursor.getString(2);
//							String product_name = cursor.getString(3);
//							String quentity = cursor.getString(4);
//							String price = cursor.getString(5);
//							String total_price = cursor.getString(6);
//					
//							
//							ItemAllProducts item = new ItemAllProducts();
//							
//							item.setGen_id(gen_id);
//							item.setId(product_id);
//							item.setImage(product_image);
//							item.setProduct_name(product_name);
//							
//							item.setQuentity(quentity);
//							item.setPrice(price);
//							item.setTotal_price(total_price);
//		
//							
//							
//							
//							itemProperties.add(item);
//							
//							
//	
//						} while (cursor.moveToNext());
//					
//					}
//				
//				return itemProperties;				
//			}
//			
			
			
			
			
			
			
			
			
			
			
			

			public String deleteCartRow(String id) {
				// TODO Auto-generated method stub
				
				db = getWritableDatabase();
				
				db.delete(TBNameCaffeMoro, Col_CAFFEMORO_CardNo + "=" + id, null);
				  Log.d(TAG, "delete call call");
				  
				return "1";

			}

			
			
			
			public ArrayList<ItemCard> getAllCards() {
				// TODO Auto-generated method stub
				
				ArrayList<ItemCard> itemProperties = new ArrayList<ItemCard>();
				
				
				db = getReadableDatabase();
				
				
				
				String table_coloums []= {Col_CAFFEMORO_Id_AutoGen,
						Col_CAFFEMORO_ID ,
						Col_CAFFEMORO_Card_Name, 
						Col_CAFFEMORO_CardNo,
						Col_CAFFEMORO_ExpiryDate ,
						Col_CAFFEMORO_CVV, 
						Col_CAFFEMORO_CARD_TYPE
							};
					cursor = db.query(TBNameCaffeMoro, table_coloums, null, null , null , null, null);



				Cursor cursor = null;

				if(cursor != null && cursor.getCount() > 0){

					cursor.moveToFirst();

						do {
							String gen_id = cursor.getString(0);
							String id = cursor.getString(1);
							String card_name = cursor.getString(2);
							String card_no = cursor.getString(3);
							String expirydate = cursor.getString(4);
							String cvv = cursor.getString(7);
							String cardType = cursor.getString(6);


							ItemCard item = new ItemCard();

							item.setGen_id(gen_id);
							item.setId(id);
							item.setCard_name(card_name);
							item.setCard_no(card_no);
							item.setExpiray_date(expirydate);
							item.setCvv(cvv);
							item.setCardType(cardType);

							SavePref pref = new SavePref();
							pref.SavePref(context);

							if(pref.getId().equals(id)){
								itemProperties.add(item);
							}

						} while (cursor.moveToNext());

					}
				
				return itemProperties;
			}
			
			
			
			
			
			
			
			
			

			public ArrayList<ItemCard> getCardDetail(String string) {
				// TODO Auto-generated method stub
				
				ArrayList<ItemCard> arrayList = new ArrayList<ItemCard>();
				
				db = getReadableDatabase();
			
				cursor = db.query
				     (
				    		 TBNameCaffeMoro,
				             new String[] {Col_CAFFEMORO_Id_AutoGen,
				    				 Col_CAFFEMORO_ID ,
				    				 Col_CAFFEMORO_Card_Name, 
				    				 Col_CAFFEMORO_CardNo,
				    				 Col_CAFFEMORO_ExpiryDate,
				    				 Col_CAFFEMORO_CVV,
				    				 Col_CAFFEMORO_CARD_TYPE},
				    				 Col_CAFFEMORO_CardNo+"=?",
				          new String[] {string},
				        null, null, null
				     );
				
				
				
				
				if(cursor != null && cursor.getCount() > 0){
				
				cursor.moveToFirst(); 
				
					do {
						String gen_id = cursor.getString(0);
						String id = cursor.getString(1);
						String card_name = cursor.getString(2);
						String card_no = cursor.getString(3);
						String expirydate = cursor.getString(4);
						String cvv = cursor.getString(5);
						String cardType = cursor.getString(62);
				
						
						ItemCard item = new ItemCard();
						
						item.setGen_id(gen_id);
						item.setId(id);
						item.setCard_name(card_name);
						item.setCard_no(card_no);
						item.setExpiray_date(expirydate);
						item.setCvv(cvv);
						item.setCardType(cardType);
	
						

						SavePref pref = new SavePref();
						pref.SavePref(context);
						
						if(pref.getId().equals(id)){
							arrayList.add(item);
						}
					

					} while (cursor.moveToNext());
				
				}
				return arrayList;
			}

		
				
	
			

//			public ArrayList<ItemHistory> getAllCardDetail() {
//				// TODO Auto-generated method stub
//				
//				ArrayList<ItemHistory> arrayList = new ArrayList<ItemHistory>();
//				
//				db = getReadableDatabase();
//				
//				String table_coloums []= {Col_CAFFEMORO_Id_AutoGen,
//	    				 Col_CAFFEMORO_ID ,
//	    				 Col_CAFFEMORO_Card_Name, 
//	    				 Col_CAFFEMORO_CardNo,
//	    				 Col_CAFFEMORO_ExpiryDate,
//	    				 Col_CAFFEMORO_CVV,
//	    				 Col_CAFFEMORO_CARD_TYPE
//		   			
//							};			
//					cursor = db.query(TBNameCaffeMoro, table_coloums,null, null , null , null, null);
//
//				
//				if(cursor != null && cursor.getCount() > 0){
//				
//				cursor.moveToFirst(); 
//				
//					do {
//						String gen_id = cursor.getString(0);
//						String id = cursor.getString(1);
//						String uid = cursor.getString(2);
//						String card_no = cursor.getString(3);
//						String expirydate = cursor.getString(4);
//						String cvv = cursor.getString(5);
//						String amount = cursor.getString(6);
//						String title = cursor.getString(7);
//						String transaction_id = cursor.getString(8);
//						String time = cursor.getString(9);
//						
//						ItemHistory item = new ItemHistory();
//						
//						item.setGen_id(gen_id);
//						item.setId(id);
//						item.setUid(uid);
//						item.setCardno(card_no);
//						item.setExipry_date(expirydate);
//						item.setCvv(cvv);
//						item.setAmount(amount);
//						item.setTitle(title);
//						item.setTransaction_id(transaction_id);
//						item.setTime(time);
//						
//						arrayList.add(item);
//
//					
//
//					} while (cursor.moveToNext());
//				
//				}
//				return arrayList;
//			}

		
	
		
		
//		// getAllProperty ===========================================================================================================================
//
//		public ArrayList<ItemMessage> getMessageBySenderIdANDReceverId(String sender_id, String receiver_id) {
//			// TODO Auto-generated method stub
//			
//			Log.d(TAG, "sender_id:= "+sender_id+" :::: receiver_id:= "+receiver_id);
//			
//			ArrayList<ItemMessage> itemProperties = new ArrayList<ItemMessage>();
//			
//			db = getReadableDatabase();
//			
//			
//			SavePref pref = new SavePref();
//			pref.SavePref(context);
//			
//			
//			
////			if(pref.getId().equals(sender_id)){
////				cursor = db.query
////					     (
////					    		 TBNameBUUJIX,
////					             new String[] {Col_Buujix_Id_AutoGen,
////					    				 Col_Buujix_sender_id ,
////					    				 Col_Buujix_receiver_id, 
////					    				 Col_Buujix_content,
////					    				 Col_Buujix_sender_time,
////					    				 Col_Buujix_receiver_time,
////					    				 Col_Buujix_type,
////					    				 Col_Buujix_sendername,
////					    				 Col_Buujix_senderuser_image},
////					    				 Col_Buujix_sender_id+"=? and "+Col_Buujix_receiver_id+"=?",
////					          new String[] {sender_id, receiver_id},
////					        null, null, null
////					     );
////				
////				
////				
////				
////				
////				
////				
////				
////				
////			}if(!pref.getId().equals(sender_id)){
////				cursor = db.query
////					     (
////					    		 TBNameBUUJIX,
////					             new String[] {Col_Buujix_Id_AutoGen,
////					    				 Col_Buujix_sender_id ,
////					    				 Col_Buujix_receiver_id, 
////					    				 Col_Buujix_content,
////					    				 Col_Buujix_sender_time,
////					    				 Col_Buujix_receiver_time,
////					    				 Col_Buujix_type,
////					    				 Col_Buujix_sendername,
////					    				 Col_Buujix_senderuser_image},
////					    				 Col_Buujix_receiver_id+"=? and "+Col_Buujix_sender_id+"=?",
////					          new String[] {sender_id, receiver_id},
////					        null, null, null
////				     );
////			
////			
////			
////			
////			}
//
//		     
//	
//			
//			
////				
//			
//
//			
//			
//			
//			String table_coloums []= {Col_Buujix_Id_AutoGen,
//	   				 Col_Buujix_sender_id ,
//	   				 Col_Buujix_receiver_id, 
//	   				 Col_Buujix_content,
//	   				 Col_Buujix_sender_time,
//	   				 Col_Buujix_receiver_time,
//	   				 Col_Buujix_type,
//	   				 Col_Buujix_sendername,
//	   				 Col_Buujix_senderuser_image
//						};
//			
//				cursor = db.query(TBNameBUUJIX, table_coloums,null, null , null , null, null);
//				
//				
//				
//			
//			
//				 
//			if(cursor != null && cursor.getCount() > 0){
//				
//				cursor.moveToFirst(); 
//				
//					do {
//						String gen_id = cursor.getString(0);
//						String sender_id1 = cursor.getString(1);
//						String receiver_id1 = cursor.getString(2);
//						String content = cursor.getString(3);
//						String sender_time = cursor.getString(4);
//						String receiver_time = cursor.getString(5);
//						String type = cursor.getString(6);
//						String sendername = cursor.getString(7);
//						String senderuser_image = cursor.getString(8);
//						
//						ItemMessage item = new ItemMessage();
//						
//						item.setGenId(gen_id);
//						item.setSender_id(sender_id1);
//						item.setReceiver_id(receiver_id1);
//						item.setContent(content);
//						item.setSender_time(Words.getDate(Long.parseLong(sender_time)));
//						item.setReceiver_time(receiver_time);
//						item.setType(type);
//						item.setSendername(sendername);
//						item.setSenderuser_image(senderuser_image);
//						
//						
//						
////						if(pref.getId().equals(sender_id1) || receiver_id.equals(sender_id1)){
////							
////							itemProperties.add(item);
////
////						}
//						
//
//
////						if(receiver_id.equals(receiver_id1) || receiver_id.equals(receiver_id1)){
////						
////							itemProperties.add(item);
////
////						}	
//						
//						
//						
//						if(sender_id.equals(sender_id1) || sender_id.equals(receiver_id1)){
//							
//							if(receiver_id.equals(sender_id1) || receiver_id.equals(receiver_id1)){
//
//								itemProperties.add(item);
//							}else{
//								
//							}
//						
//						}else{
//							
//						}
//						
//
//					} while (cursor.moveToNext());
//				
//				}
//			
//			return itemProperties;
//		}
//		// getAllProperty ===========================================================================================================================
//
//		
		
		
		
		
		
		
		
		
		
		

}

