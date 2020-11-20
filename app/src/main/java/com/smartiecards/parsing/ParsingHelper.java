package com.smartiecards.parsing;


import android.util.Log;


import com.smartiecards.account.ItemCountry;
import com.smartiecards.dashboard.ItemPaymentHistory;
import com.smartiecards.game.ItemAnswers;
import com.smartiecards.game.ItemGameMatches;
import com.smartiecards.game.ItemGameShuffle;
import com.smartiecards.game.ItemGameTopic;
import com.smartiecards.game.ItemTimedGame;
import com.smartiecards.home.ItemCardFlip;
import com.smartiecards.home.ItemSubject;
import com.smartiecards.home.ItemSubjectTopic;
import com.smartiecards.util.Utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Handler;

import org.json.JSONArray;
import org.json.JSONObject;



public class ParsingHelper {


	private static final String TAG = "ParsingHelper";


	public ArrayList<ItemSubject> getItemSubjectCategory(String s) {
		ArrayList<ItemSubject> itemSubjects = new ArrayList<ItemSubject>();
		try{
			JSONArray jsonArrayData = new JSONArray(s);
			if(jsonArrayData.length() > 0){
				for(int i = 0 ; i < jsonArrayData.length() ; i ++){
					JSONObject jsonArrayDataJSONObject = jsonArrayData.getJSONObject(i);

					String id = "";
					if(jsonArrayDataJSONObject.has("id")){
						id = jsonArrayDataJSONObject.getString("id");
					}


					String subjectname = "";
					if(jsonArrayDataJSONObject.has("subjectname")){
						subjectname = jsonArrayDataJSONObject.getString("subjectname");
					}


					String photo = "";
					if(jsonArrayDataJSONObject.has("photo")){
						photo = jsonArrayDataJSONObject.getString("photo");
					}


					String subjecttime = "";
					if(jsonArrayDataJSONObject.has("subjecttime")){
						subjecttime = jsonArrayDataJSONObject.getString("subjecttime");
					}


					String amount = "";
					if(jsonArrayDataJSONObject.has("amount")){
						amount = jsonArrayDataJSONObject.getString("amount");
					}


					String purchase_status = "";
					if(jsonArrayDataJSONObject.has("purchase_status")){
						purchase_status = jsonArrayDataJSONObject.getString("purchase_status");
					}



					if(jsonArrayDataJSONObject.has("subjectid")){
						id = jsonArrayDataJSONObject.getString("subjectid");
					}


					String total_count = "";
					if(jsonArrayDataJSONObject.has("total_count")){
						total_count = jsonArrayDataJSONObject.getString("total_count");
					}



					if(jsonArrayDataJSONObject.has("status")){
						purchase_status = jsonArrayDataJSONObject.getString("status");
					}

					if(jsonArrayDataJSONObject.has("sname")){
						subjectname = jsonArrayDataJSONObject.getString("sname");
					}


					String type = "";
					if(jsonArrayDataJSONObject.has("type")){
						type = jsonArrayDataJSONObject.getString("type");
					}


					String discription = "";
					if(jsonArrayDataJSONObject.has("discription")){
						discription = jsonArrayDataJSONObject.getString("discription");
					}

					ItemSubject subject = new ItemSubject();

					subject.setId(id);
					subject.setSubjectname(subjectname);
					subject.setPhoto(photo);
					subject.setSubjecttime(subjecttime);
					subject.setAmount(amount);
					subject.setPurchase_status(purchase_status);
					subject.setTotal_count(total_count);
					subject.setType(type);
					subject.setDiscription(discription);

					int ii  = i + 1;
					subject.setId_count(ii);

					ArrayList<String> categories = new ArrayList<String>();
					categories.add("Automobile");
					categories.add("Business Services");
					categories.add("Computers");
					categories.add("Education");
					categories.add("Personal");
					categories.add("Travel");

					subject.setCategories(categories);

					itemSubjects.add(subject);
				}
			}
		}catch (Exception e){

		}
		return  itemSubjects;
	}






	public ArrayList<ItemSubject> getItemSubjectCategoryMySubject(String s) {
		ArrayList<ItemSubject> itemSubjects = new ArrayList<ItemSubject>();
		try{

			JSONObject jsonObject = new JSONObject(s);
			if(jsonObject.getString("status").equalsIgnoreCase("1")){
				JSONArray jsonArrayData = jsonObject.getJSONArray("data");
				if(jsonArrayData.length() > 0) {
					for (int i = 0; i < jsonArrayData.length(); i++) {
						JSONObject jsonArrayDataJSONObject = jsonArrayData.getJSONObject(i);

						String id = "";
						if (jsonArrayDataJSONObject.has("id")) {
							id = jsonArrayDataJSONObject.getString("id");
						}


						String subjectname = "";
						if (jsonArrayDataJSONObject.has("subjectname")) {
							subjectname = jsonArrayDataJSONObject.getString("subjectname");
						}


						String photo = "";
						if (jsonArrayDataJSONObject.has("photo")) {
							photo = jsonArrayDataJSONObject.getString("photo");
						}


						String subjecttime = "";
						if (jsonArrayDataJSONObject.has("subjecttime")) {
							subjecttime = jsonArrayDataJSONObject.getString("subjecttime");
						}


						String amount = "";
						if (jsonArrayDataJSONObject.has("amount")) {
							amount = jsonArrayDataJSONObject.getString("amount");
						}


						String purchase_status = "";
						if (jsonArrayDataJSONObject.has("purchase_status")) {
							purchase_status = jsonArrayDataJSONObject.getString("purchase_status");
						}


						if (jsonArrayDataJSONObject.has("subjectid")) {
							id = jsonArrayDataJSONObject.getString("subjectid");
						}


						String total_count = "";
						if (jsonArrayDataJSONObject.has("total_count")) {
							total_count = jsonArrayDataJSONObject.getString("total_count");
						}


						if (jsonArrayDataJSONObject.has("status")) {
							purchase_status = jsonArrayDataJSONObject.getString("status");
						}

						if (jsonArrayDataJSONObject.has("sname")) {
							subjectname = jsonArrayDataJSONObject.getString("sname");
						}


						String type = "";
						if (jsonArrayDataJSONObject.has("type")) {
							type = jsonArrayDataJSONObject.getString("type");
						}


						ItemSubject subject = new ItemSubject();

						subject.setId(id);
						subject.setSubjectname(subjectname);
						subject.setPhoto(photo);
						subject.setSubjecttime(subjecttime);
						subject.setAmount(amount);
						subject.setPurchase_status(purchase_status);
						subject.setTotal_count(total_count);
						subject.setType(type);


						if(purchase_status.equalsIgnoreCase("1")){
							if(!subjectname.equalsIgnoreCase("null")){
								boolean dd = getValue(itemSubjects , id);
								//Log.e(TAG , id+" getValue "+dd);
								if(dd == false){
									itemSubjects.add(subject);
								}else{

								}
							}else{

							}
						}else{

						}
					}
				}
			}



		}catch (Exception e){

		}
		return  itemSubjects;
	}





	public ArrayList<ItemSubject> getItemTopSubject(String s) {
		ArrayList<ItemSubject> itemSubjects = new ArrayList<ItemSubject>();

		try{
			JSONArray jsonArrayData = new JSONArray(s);
			if(jsonArrayData.length() > 0){
				for(int i = 0 ; i < jsonArrayData.length() ; i ++){
					JSONObject jsonArrayDataJSONObject = jsonArrayData.getJSONObject(i);

					String userid = jsonArrayDataJSONObject.getString("userid");
					String subjectid = jsonArrayDataJSONObject.getString("subjectid");
					String purchase_status = jsonArrayDataJSONObject.getString("purchase_status");
					String subjectname = jsonArrayDataJSONObject.getString("subjectname");
					String photo = jsonArrayDataJSONObject.getString("photo");
					String subjecttime = jsonArrayDataJSONObject.getString("subjecttime");
					String amount = jsonArrayDataJSONObject.getString("amount");

					String discription = jsonArrayDataJSONObject.getString("discription");

					ItemSubject subject = new ItemSubject();

					subject.setUserid(userid);
					subject.setId(subjectid);
					subject.setPhoto(photo);
					subject.setPurchase_status(purchase_status);
					subject.setSubjectname(subjectname);
					subject.setSubjecttime(subjecttime);
					subject.setDiscription(discription);
					subject.setAmount(amount);


						if(purchase_status.equalsIgnoreCase("1")){
							if(!subjectname.equalsIgnoreCase("null")){
								boolean dd = getValue(itemSubjects , subjectid);
								//Log.e(TAG , subjectid+" getValue "+dd);
								if(dd == false){
//									int ii  = i + 1;
//									subject.setId_count(ii);
									itemSubjects.add(subject);
								}else{

								}
							}else{

							}
						}else{

						}

					}






				for(int i = 0 ; i < jsonArrayData.length() ; i ++){
					JSONObject jsonArrayDataJSONObject = jsonArrayData.getJSONObject(i);

					String userid = jsonArrayDataJSONObject.getString("userid");
					String subjectid = jsonArrayDataJSONObject.getString("subjectid");
					String purchase_status = jsonArrayDataJSONObject.getString("purchase_status");
					String subjectname = jsonArrayDataJSONObject.getString("subjectname");
					String photo = jsonArrayDataJSONObject.getString("photo");
					String subjecttime = jsonArrayDataJSONObject.getString("subjecttime");
					String amount = jsonArrayDataJSONObject.getString("amount");

					String discription = jsonArrayDataJSONObject.getString("discription");



				//	Log.e(TAG , "subjectname:: "+subjectname);


					ItemSubject subject = new ItemSubject();

					subject.setUserid(userid);
					subject.setId(subjectid);
					subject.setPhoto(photo);
					subject.setPurchase_status(purchase_status);
					subject.setSubjectname(subjectname);
					subject.setDiscription(discription);
					subject.setAmount(amount);

					if(!subjectname.equalsIgnoreCase("null")){
						boolean dd = getValue(itemSubjects , subjectid);
								//Log.e(TAG , subjectid+" getValue "+dd);
								if(dd == false){
									int ii  = i + 1;
									subject.setId_count(ii);
									itemSubjects.add(subject);
								}else{

								}
					}

				}


			}
		}catch (Exception e){

		}
		return  itemSubjects;
	}





	public ArrayList<ItemSubject> getItemTopSubjectWithoutId(String s) {
		ArrayList<ItemSubject> itemSubjects = new ArrayList<ItemSubject>();
		try{
			JSONArray jsonArrayData = new JSONArray(s);
			if(jsonArrayData.length() > 0){
				for(int i = 0 ; i < jsonArrayData.length() ; i ++){
					JSONObject jsonArrayDataJSONObject = jsonArrayData.getJSONObject(i);

					String userid = "";
					String subjectid = jsonArrayDataJSONObject.getString("subjectid");
					String purchase_status = jsonArrayDataJSONObject.getString("purchase_status");
					String subjectname = jsonArrayDataJSONObject.getString("subjectname");
					String photo = jsonArrayDataJSONObject.getString("photo");
					String subjecttime = jsonArrayDataJSONObject.getString("subjecttime");
					String amount = jsonArrayDataJSONObject.getString("amount");

					String discription = jsonArrayDataJSONObject.getString("discription");

				//	Log.e(TAG , "subjectname:: "+subjectname);

					if(jsonArrayDataJSONObject.has("userid")){
						userid = jsonArrayDataJSONObject.getString("userid");
					}


					ItemSubject subject = new ItemSubject();

					subject.setUserid(userid);
					subject.setId(subjectid);
					subject.setPhoto(photo);
					subject.setPurchase_status(purchase_status);
					subject.setSubjectname(subjectname);
					subject.setSubjecttime(subjecttime);
					subject.setDiscription(discription);
					subject.setAmount(amount);

					if(!subjectname.equalsIgnoreCase("null")){
						boolean dd = getValue(itemSubjects , subjectid);
					//	Log.e(TAG , subjectid+" getValue "+dd);
						if(dd == false){
							int ii  = i + 1;
							subject.setId_count(ii);
							itemSubjects.add(subject);
						}else{

						}
					}

				}

			}
		}catch (Exception e){

		}
		return  itemSubjects;
	}





	private boolean getValue(ArrayList<ItemSubject> arrayList, String string) {
		// TODO Auto-generated method stub
		for(int i = 0 ; i < arrayList.size() ; i++){
			if(arrayList.get(i).getId().equalsIgnoreCase(string)){
				return true;
//				if(arrayList.get(i).getPurchase_status().equalsIgnoreCase("1")){
//                    return true;
//				}else{
//					//return false;
//				}
			}
		}
		return false;
	}





	private boolean getValueStatus(ArrayList<ItemSubject> arrayList, String string) {
		// TODO Auto-generated method stub
		for(int i = 0 ; i < arrayList.size() ; i++){
//			if(arrayList.get(i).getId().equalsIgnoreCase(string)){
				if(arrayList.get(i).getPurchase_status().equalsIgnoreCase("1")){
					return true;
				}else{
//					return false;
				}
//			}
		}
		return false;
	}




	public ArrayList<ItemSubjectTopic> getItemSubjectTopic(String s) {
		ArrayList<ItemSubjectTopic> itemSubjects = new ArrayList<ItemSubjectTopic>();
		try{

			JSONObject jsonObject = new JSONObject(s);
			//JSONObject jsonObjectData = jsonObject.getJSONObject("data");

			JSONArray jsonArrayData = jsonObject.getJSONArray("data");
			if(jsonArrayData.length() > 0){
				for(int i = 0 ; i < jsonArrayData.length() ; i ++){
					JSONObject jsonArrayDataJSONObject = jsonArrayData.getJSONObject(i);

					String id = jsonArrayDataJSONObject.getString("id");
					String subjectid = jsonArrayDataJSONObject.getString("subjectid");
					String ftopic = jsonArrayDataJSONObject.getString("ftopic");
					String image = jsonArrayDataJSONObject.getString("image");
					String color = jsonArrayDataJSONObject.getString("color");
					String sname = jsonArrayDataJSONObject.getString("sname");


					ItemSubjectTopic subject = new ItemSubjectTopic();

					subject.setSubjectid(subjectid);
					subject.setFtopic(ftopic);
					subject.setImage(image);
					subject.setColor(color);
					subject.setSname(sname);
					subject.setId(id);
//					subject.setSubjectid(subjectid);
//					subject.setSname(sname);
//					//	subject.setPhoto(photo);
//					subject.setStime(stime);
//					subject.setAmount(amount);

					itemSubjects.add(subject);
				}
			}
		}catch (Exception e){

		}
		return  itemSubjects;
	}



	public ArrayList<String> getItemSubjectTopicDescription (String s) {
		ArrayList<String> itemSubjects = new ArrayList<String>();
		try{

			JSONObject jsonObject = new JSONObject(s);
			JSONArray jsonArrayData = jsonObject.getJSONArray("data2");
			if(jsonArrayData.length() > 0){
				for(int i = 0 ; i < jsonArrayData.length() ; i ++){
					JSONObject jsonArrayDataJSONObject = jsonArrayData.getJSONObject(i);

					String title = jsonArrayDataJSONObject.getString("title");
					String content = jsonArrayDataJSONObject.getString("content");

					itemSubjects.add(title);
					itemSubjects.add(content);
				}
			}
		}catch (Exception e){

		}
		return  itemSubjects;
	}




    public ArrayList<ItemCardFlip> getItemTopicQueAns(String s) {
		ArrayList<ItemCardFlip> arrayList = new ArrayList<ItemCardFlip>();
		try{
			JSONObject jsonObject = new JSONObject(s);
			JSONArray jsonArray = jsonObject.getJSONArray("data");

			if(jsonArray.length() > 0){
				for(int i = 0 ; i < jsonArray.length() ; i++){
					JSONObject jsonObject1 = jsonArray.getJSONObject(i);

					String id = jsonObject1.getString("id");
					String cardid = jsonObject1.getString("cardid");
					String subjectid = jsonObject1.getString("subjectid");
					String formula = jsonObject1.getString("formula");
					String answer = jsonObject1.getString("answer");
					String dat = jsonObject1.getString("dat");
					String star_status = jsonObject1.getString("star_status");
					String fontsize = jsonObject1.getString("fontsize");
                    String formulaimage = jsonObject1.getString("formulaimage");
                    String answerimage = jsonObject1.getString("answerimage");
					String answerimage2 = jsonObject1.getString("answerimage2");

					ItemCardFlip flip = new ItemCardFlip();
					flip.setId(id);
					flip.setCardid(cardid);
					flip.setSubjectid(subjectid);
					flip.setFormula(formula);
					flip.setAnswer(answer);
					flip.setDat(dat);
					flip.setStar_status(star_status);
					flip.setNumber(i+1);
					flip.setFontsize(fontsize);
                    flip.setFormulaimage(formulaimage);
                    flip.setAnswerimage(answerimage);
					if(i == 0 ){
						flip.setAnswerimage2(answerimage2);
					}

                    arrayList.add(flip);

				}



//				if(arrayList.size() > 0){
//					for(int i = 0 ; i < arrayList.size() ; i++){
//						ItemCardFlip flip = arrayList.get(i);
//						flip.setNumber(i);
//
//						//arrayList.set(i , flip);
//					}
//				}

			}
		}catch (Exception e){

		}
		return arrayList;
    }



//====================================================================================================
	public ArrayList<ItemCardFlip> getItemTopicQueAnsOnlyStar(String s) {
		ArrayList<ItemCardFlip> arrayList = new ArrayList<ItemCardFlip>();
		try{
			JSONObject jsonObject = new JSONObject(s);
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			int j = 0;
			if(jsonArray.length() > 0){
				for(int i = 0 ; i < jsonArray.length() ; i++){
					JSONObject jsonObject1 = jsonArray.getJSONObject(i);

					String id = jsonObject1.getString("id");
					String cardid = jsonObject1.getString("cardid");
					String subjectid = jsonObject1.getString("subjectid");
					String formula = jsonObject1.getString("formula");
					String answer = jsonObject1.getString("answer");
					String dat = jsonObject1.getString("dat");
					String star_status = jsonObject1.getString("star_status");
					String fontsize = jsonObject1.getString("fontsize");
                    String formulaimage = jsonObject1.getString("formulaimage");
                    String answerimage = jsonObject1.getString("answerimage");
					String answerimage2 = jsonObject1.getString("answerimage2");

					if(star_status.equalsIgnoreCase("1")){
						ItemCardFlip flip = new ItemCardFlip();
						flip.setId(id);
						flip.setCardid(cardid);
						flip.setSubjectid(subjectid);
						flip.setFormula(formula);
						flip.setAnswer(answer);
						flip.setDat(dat);
						flip.setStar_status(star_status);
						flip.setFontsize(fontsize);
                        flip.setFormulaimage(formulaimage);
                        flip.setAnswerimage(answerimage);

                        if(i == 0 ){
							flip.setAnswerimage2(answerimage2);
						}

						j = j + 1;
						flip.setNumber(j);

						arrayList.add(flip);
					}

				}

			}
		}catch (Exception e){

		}
		return arrayList;
	}




//	public ArrayList<ItemPaymentHistory> getItemSubjectHistory(String s) {
//		ArrayList<ItemPaymentHistory> arrayList = new ArrayList<ItemPaymentHistory>();
//			try{
//				JSONArray jsonArray = new JSONArray(s);
//				if(jsonArray.length() > 0 ){
//					for(int i = 0 ; i < jsonArray.length() ; i++){
//						JSONObject jsonObject = jsonArray.getJSONObject(i);
//						String subjectid = jsonObject.getString("subjectid");
//						String amount = jsonObject.getString("amount");
//						String transid = jsonObject.getString("transid");
//						String dat = jsonObject.getString("dat");
//						String status = jsonObject.getString("status");
//						String sname = jsonObject.getString("sname");
//						String photo = jsonObject.getString("photo");
//						String stime = jsonObject.getString("stime");
//
//						ItemPaymentHistory history = new ItemPaymentHistory();
//						history.setSubjectid(subjectid);
//						history.setAmount(amount);
//						history.setTransid(transid);
//						history.setDat(dat);
//						history.setStatus(status);
//						history.setSname(sname);
//						history.setPhoto(photo);
//						history.setStime(stime);
//
//						arrayList.add(history);
//					}
//				}
//			}catch (Exception e){
//
//			}
//		return arrayList;
//	}
//


	public ArrayList<ItemGameTopic> getItemGameTopic(String s) {
		ArrayList<ItemGameTopic> arrayList = new ArrayList<ItemGameTopic>();

		try{
			JSONObject jsonObject = new JSONObject(s);
			String status = jsonObject.getString("status");

			if(status.equalsIgnoreCase("1")){
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				if(jsonArray.length() > 0 ) {
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject1 = jsonArray.getJSONObject(i);

						String id = jsonObject1.getString("id");
						String gsubid = jsonObject1.getString("gsubid");
						String gtopic_name = jsonObject1.getString("gtopic_name");
						String image = jsonObject1.getString("image");
						String dat = jsonObject1.getString("dat");
						String type = jsonObject1.getString("type");

						ItemGameTopic history = new ItemGameTopic();
						history.setId(id);
						history.setGsubid(gsubid);
						history.setGtopic_name(gtopic_name);
						history.setImage(image);
						history.setDat(dat);
						history.setType(type);

						arrayList.add(history);
					}
				}
			}

		}catch (Exception e){

		}

		return arrayList;
	}

    public ArrayList<ItemGameShuffle> getItemGameShuffle(String s) {
		ArrayList<ItemGameShuffle> arrayList = new ArrayList<ItemGameShuffle>();
		try{
			JSONObject jsonObject = new JSONObject(s);
			String status = jsonObject.getString("status");

			if(status.equalsIgnoreCase("1")){
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				if(jsonArray.length() > 0 ) {
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject1 = jsonArray.getJSONObject(i);

						String id = jsonObject1.getString("id");
						String subject_id = jsonObject1.getString("subject_id");
						String topic_id = jsonObject1.getString("topic_id");
						String question = jsonObject1.getString("question");
						String answer = jsonObject1.getString("answer");
//						String questionimage = jsonObject1.getString("questionimage");
//						String answerimage = jsonObject1.getString("answerimage");

						ItemGameShuffle history = new ItemGameShuffle();
						history.setId(id);
						history.setSubject_id(subject_id);
						history.setTopic_id(topic_id);
						history.setQuestion(question);
						history.setAnswer(answer);
//						history.setQuestionimage(questionimage);
//						history.setAnswerimage(answerimage);

						arrayList.add(history);
					}
				}
			}

		}catch (Exception e){

		}

		return arrayList;
    }




	//====================================================================================================
	public ArrayList<ItemGameShuffle> getItemGameShuffleFlip(String s) {
		ArrayList<ItemGameShuffle> arrayList = new ArrayList<ItemGameShuffle>();
		try{
			JSONObject jsonObject = new JSONObject(s);
			String status = jsonObject.getString("status");

			if(status.equalsIgnoreCase("1")){
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				if(jsonArray.length() > 0 ) {
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject1 = jsonArray.getJSONObject(i);

						String id = jsonObject1.getString("id");
						String subject_id = jsonObject1.getString("subject_id");
						String topic_id = jsonObject1.getString("topic_id");
						String question = jsonObject1.getString("question");
						String answer = jsonObject1.getString("answer");
						String star_status = jsonObject1.getString("star_status");
						String fontsize = jsonObject1.getString("fontsize");
						String questionimage = jsonObject1.getString("questionimage");
						String answerimage = jsonObject1.getString("answerimage");
						String answerimage2 = jsonObject1.getString("answerimage2");

						ItemGameShuffle history = new ItemGameShuffle();
						history.setId(id);
						history.setSubject_id(subject_id);
						history.setTopic_id(topic_id);
						history.setQuestion(question);
						history.setAnswer(answer);
						history.setStar_status(star_status);
						history.setFontsize(fontsize);

						history.setNumber(i+1);
						history.setQuestionimage(questionimage);
						history.setAnswerimage(answerimage);
						if(i == 0 ){
							history.setAnswerimage2(answerimage2);
						}

						arrayList.add(history);

					}
				}
			}

		}catch (Exception e){

		}

		return arrayList;
	}



	//====================================================================================================
	public ArrayList<ItemGameShuffle> getItemGameShuffleFlipOnlyStar(String s) {
		ArrayList<ItemGameShuffle> arrayList = new ArrayList<ItemGameShuffle>();
		try{
			JSONObject jsonObject = new JSONObject(s);
			String status = jsonObject.getString("status");

			int j = 0;
			if(status.equalsIgnoreCase("1")){
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				if(jsonArray.length() > 0 ) {
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject1 = jsonArray.getJSONObject(i);

						String id = jsonObject1.getString("id");
						String subject_id = jsonObject1.getString("subject_id");
						String topic_id = jsonObject1.getString("topic_id");
						String question = jsonObject1.getString("question");
						String answer = jsonObject1.getString("answer");
						String star_status = jsonObject1.getString("star_status");
						String fontsize = jsonObject1.getString("fontsize");
						String questionimage = jsonObject1.getString("questionimage");
						String answerimage = jsonObject1.getString("answerimage");
						String answerimage2 = jsonObject1.getString("answerimage2");

						if(star_status.equalsIgnoreCase("1")){
							ItemGameShuffle history = new ItemGameShuffle();
							history.setId(id);
							history.setSubject_id(subject_id);
							history.setTopic_id(topic_id);
							history.setQuestion(question);
							history.setAnswer(answer);
							history.setStar_status(star_status);
							history.setFontsize(fontsize);
							history.setQuestionimage(questionimage);
							history.setAnswerimage(answerimage);
							if(i == 0 ){
								history.setAnswerimage2(answerimage2);
							}

							j = j + 1;
							history.setNumber(j);

							arrayList.add(history);
						}

					}
				}
			}

		}catch (Exception e){

		}

		return arrayList;
	}




	public ArrayList<ItemGameMatches> getItemGameMatches(String s) {
		final ArrayList<ItemGameMatches> arrayList = new ArrayList<ItemGameMatches>();
		final ArrayList<ItemGameMatches> arrayListQuestions = new ArrayList<ItemGameMatches>();
		final ArrayList<ItemGameMatches> arrayListAnswers = new ArrayList<ItemGameMatches>();


		try{
			JSONObject jsonObject = new JSONObject(s);
			String status = jsonObject.getString("status");

			if(status.equalsIgnoreCase("1")){
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				if(jsonArray.length() > 0 ) {
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject1 = jsonArray.getJSONObject(i);

						String id = jsonObject1.getString("id");
						String gsubid = jsonObject1.getString("gsubid");
						String gtopicid = jsonObject1.getString("gtopicid");
						String formula = jsonObject1.getString("formula");
					//	String answer = jsonObject1.getString("answer");
						String dat = jsonObject1.getString("dat");

						ItemGameMatches history = new ItemGameMatches();
						history.setId(id);
						history.setGsubid(gsubid);
						history.setGtopicid(gtopicid);
						history.setFormula(formula);
						//history.setAnswer(answer);
						history.setDat(dat);
						history.setQuestionType("1");

						arrayListQuestions.add(history);
					}

					Collections.shuffle(arrayListQuestions);
					arrayList.addAll(arrayListQuestions);




						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonObject1 = jsonArray.getJSONObject(i);

							String id = jsonObject1.getString("id");
							String gsubid = jsonObject1.getString("gsubid");
							String gtopicid = jsonObject1.getString("gtopicid");
							//String formula = jsonObject1.getString("formula");
							String answer = jsonObject1.getString("answer");
							String dat = jsonObject1.getString("dat");

							ItemGameMatches history = new ItemGameMatches();
							history.setId(id);
							history.setGsubid(gsubid);
							history.setGtopicid(gtopicid);
							history.setFormula(answer);
							//	history.setAnswer(answer);
							history.setDat(dat);
							history.setQuestionType("2");

							arrayListAnswers.add(history);
						}

						Collections.shuffle(arrayListAnswers);
						arrayList.addAll(arrayListAnswers);




				}
			}

		}catch (Exception e){

		}



		return arrayList;
	}



	public ArrayList<ItemCountry> getItemCountry(String s) {
		ArrayList<ItemCountry> arrayList = new ArrayList<ItemCountry>();


		ItemCountry country1 = new ItemCountry();
		country1.setCountry_name("Select Country");
		arrayList.add(country1);

		try{
		JSONObject jsonObject = new JSONObject(s);
		String status = jsonObject.getString("status");
		if(status.equalsIgnoreCase("1")){
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			if (jsonArray.length() > 0){
				for(int i = 0; i < jsonArray.length() ; i++){
					JSONObject jsonObject1 = jsonArray.getJSONObject(i);
					String id = jsonObject1.getString("id");
					String country_code = jsonObject1.getString("country_code");
					String country_name = jsonObject1.getString("country_name");

					ItemCountry country = new ItemCountry();
					country.setId(id);
					country.setCountry_code(country_code);
					country.setCountry_name(country_name);

					arrayList.add(country);

				}
			}
		}


		}catch (Exception e){

		}
		return arrayList;
	}


    public ArrayList<ItemAnswers> getItemAnswers(JSONArray jsonArray) {
		ArrayList<ItemAnswers> arrayList = new ArrayList<ItemAnswers>();

		try{
			if(jsonArray.length() > 0){
				for (int i = 0 ; i < jsonArray.length() ; i++){
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					String answer_id = jsonObject.getString("answer_id");
					String question_number = jsonObject.getString("question_number");
					String is_correct = jsonObject.getString("is_correct");
					String answer = jsonObject.getString("answer");

					ItemAnswers answers = new ItemAnswers();
					answers.setAnswer_id(answer_id);
					answers.setQuestion_number(question_number);
					answers.setIs_correct(is_correct);
					answers.setAnswer(answer);

					arrayList.add(answers);
				}
			}
		}catch (Exception e){

		}
		return arrayList;
    }



    public ArrayList<ItemTimedGame> getItemTimedGame(String ss) {
        ArrayList<ItemTimedGame> arrayList = new ArrayList<ItemTimedGame>();
        try{
        JSONObject jsonObject = new JSONObject(ss);
        String status = jsonObject.getString("status");

            if(status.equalsIgnoreCase("1")) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String question_number = jsonObject1.getString("question_number");
                        String subjectid = jsonObject1.getString("subjectid");
                        String topicid = jsonObject1.getString("topicid");
                        String text = jsonObject1.getString("text");
                        String op1 = jsonObject1.getString("op1");
                        String op2 = jsonObject1.getString("op2");
                        String correct = jsonObject1.getString("correct");
                        String backcolor = jsonObject1.getString("backcolor");
                        String fontsize = jsonObject1.getString("fontsize");

                        ItemTimedGame timedGame = new ItemTimedGame();
                        timedGame.setQuestion_number(question_number);
                        timedGame.setSubjectid(subjectid);
                        timedGame.setTopicid(topicid);
                        timedGame.setText(text);
                        timedGame.setOp1(op1);
                        timedGame.setOp2(op2);
                        timedGame.setCorrect(correct);
                        timedGame.setBackcolor(backcolor);
                        timedGame.setFontsize(fontsize);

                        arrayList.add(timedGame);
                    }
                }
            }
        }catch (Exception e){

        }
        return arrayList;
    }





	public ArrayList<ItemPaymentHistory> getPaymentHistory(String s) {
		ArrayList<ItemPaymentHistory> arrayList = new ArrayList<ItemPaymentHistory>();

		try{
			JSONObject jsonObject = new JSONObject(s);
			String status = jsonObject.getString("status");
			if(status.equalsIgnoreCase("1")){
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				if(jsonArray.length() > 0){
					for(int i = 0 ; i < jsonArray.length() ; i++){
						JSONObject jsonObject1 = jsonArray.getJSONObject(i);
						String transid = jsonObject1.getString("transid");
						String amount = jsonObject1.getString("amount");
						String coupon = jsonObject1.getString("coupon");
						String date = jsonObject1.getString("date");
						String sname = jsonObject1.getString("sname");
						if(sname.length() > 2){
							ItemPaymentHistory history = new ItemPaymentHistory();

							history.setSname(sname.toString().replace("[" , "").replace("]" , "").replace("\"" , ""));
							history.setTransid(transid);
							history.setAmount(amount);
							history.setCoupon(coupon);
							history.setDate(date);

							arrayList.add(history);
						}

					}
				}
			}
		}catch (Exception e){

		}

		return arrayList;
	}
}
