package com.smartiecards.network;


import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIInterface {


    @GET("login.php?")
    Call<ResponseBody>
    login(
            @Query("email") String email,
            @Query("password") String password,
            @Query("ip_address") String ipaddress
    );


    @GET("contactus.php?")
    Call<ResponseBody>
    contactus(
            @Query("userid") String userid,
            @Query("name") String name,
            @Query("email") String email,
            @Query("subject") String subject,
            @Query("message") String message
    );


    @GET("fb_register.php?")
    Call<ResponseBody>
    fbRegister(
            @Query("first_name") String first_name,
            @Query("last_name") String last_name,
            @Query("username") String username,
            @Query("email") String email,
            @Query("profilepic") String profilepic
    );


    @GET("register.php?")
    Call<ResponseBody>
    register(
            @Query("username") String username,
            @Query("email") String email,
            @Query("password") String password,
            @Query("dob") String dob,
            @Query("address") String address,
            @Query("city") String city,
            @Query("country") String country,
            @Query("first_name") String first_name,
            @Query("last_name") String last_name,
            @Query("ip_address") String ipaddress
    );


//    @Multipart
    @GET("forgot_password.php?")
    Call<ResponseBody> forgotPassword(@Query("email") String first_name);


//    @Multipart
    @POST("get_countries.php?")
    Call<ResponseBody> getCountries();


    @GET("subject_category.php?")
    Call<ResponseBody>
    subjectCategory(
            @Query("userid") String username
    );


    @GET("top_subjects.php?")
    Call<ResponseBody>
    topSubjects(
            @Query("userid") String username
    );


    @GET("profileinfo.php?")
    Call<ResponseBody>
    profileinfo(
            @Query("userid") String username
    );




    @GET("check-multi-login.php?")
    Call<ResponseBody>
    deviceCheck(
            @Query("userid") String username,
            @Query("ip_address") String ipaddress
    );



    @Multipart
    @POST("editprofile.php?")
    Call<ResponseBody>
    editProfile(
            @Part("userid") RequestBody userid,
            @Part("username") RequestBody username,
            @Part("email") RequestBody email,
            @Part("dob") RequestBody dob,
            @Part("address") RequestBody address,
            @Part("city") RequestBody city,
            @Part("country") RequestBody country,
            @Part("first_name") RequestBody first_name,
            @Part("last_name") RequestBody last_name,
            @Part MultipartBody.Part file
    );



    @GET("ads.php?")
    Call<ResponseBody>
    ads(
            @Query("id") String id
    );


    @GET("coupan-code.php?")
    Call<ResponseBody>
    coupanCode(
            @Query("ads") String ads
    );



    @GET("subject-payment.php?")
    Call<ResponseBody>
    subjectPayment(
            @Query("uid") String uid,
            @Query("subid") String subid,
             @Query("txn_id") String txn_id,
                    @Query("amnt") String amt,
                    @Query("coupon") String coupon
    );



    @GET("multiplepayments.php?")
    Call<ResponseBody>
    multiplePayments(
            @Query("userid") String uid,
            @Query("data_result") String subid,
            @Query("txn_id") String txn_id,
            @Query("payable_amount") String amt,
            @Query("coupon") String coupon
    );

//(prefId, jsonData, txid, amount, coupon);



    @GET("subject_topic.php?")
    Call<ResponseBody>
    subjectTopic(
            @Query("id") String id
    );



//    @Multipart
    @GET("change_password.php?")
    Call<ResponseBody> changePassword(@Query("id") String id,
                                      @Query("oldpassword") String oldpassword,
                                      @Query("newpassword") String newpassword);


    @GET("mysubjects.php?")
    Call<ResponseBody>
    mySubjects(
            @Query("userid") String userid
    );


    @GET("purchased_subjects.php?")
    Call<ResponseBody>
    purchasedSubjects(
            @Query("userid") String userid
    );



    @GET("topic-que-ans.php?")
    Call<ResponseBody>
    topicQueAns(
            @Query("topicid") String topicid,
            @Query("userid") String userid,
            @Query("typ") String typ
    );



    @GET("add-star-card.php?")
    Call<ResponseBody>
    addStarCard(
            @Query("userid") String userid,
            @Query("subid") String subid,
            @Query("cardid") String cardid,
            @Query("topicid") String topicid,
            @Query("typ") String typ
    );




    @GET("unstar-card.php?")
    Call<ResponseBody>
    unstarCard(
            @Query("userid") String userid,
            @Query("subid") String subid,
            @Query("cardid") String cardid,
            @Query("topicid") String topicid,
            @Query("typ") String typ
    );


    @GET("gamesubject.php?")
    Call<ResponseBody>
    gameSubject(
            @Query("userid") String topicid,
            @Query("type") String userid
    );


    @GET("game_subject_topic.php?")
    Call<ResponseBody>
    gameSubjectTopic(
            @Query("id") String topicid,
            @Query("type") String userid
    );


    @GET("games-timed.php?")
    Call<ResponseBody>
    gamesTimed(
            @Query("subjectid") String topicid,
            @Query("topicid") String userid
    );


    @GET("games-matches.php?")
    Call<ResponseBody>
    gameMatches(
            @Query("subjectid") String subjectid,
            @Query("topicid") String topicid
    );


    @GET("subject_topic.php?")
    Call<ResponseBody>
    subjectTopic(
            @Query("id") String topicid,
            @Query("type") String userid
    );



    @GET("game-shuffle.php?")
    Call<ResponseBody>
    gameShuffle(
            @Query("subject_id") String subject_id,
            @Query("topic_id") String topic_id,
            @Query("userid") String userid,
            @Query("typ") String typ

            );





//    @Part MultipartBody.Part file
//
//  entity.addPart("userid", new StringBody(""+params[0]));
//        entity.addPart("username", new StringBody(""+params[1]));
//        entity.addPart("email", new StringBody(params[2]));
//        entity.addPart("dob", new StringBody(params[3]));
//        entity.addPart("address", new StringBody(params[4]));
//        entity.addPart("city", new StringBody(params[6]));
//        entity.addPart("country", new StringBody(params[7]));
//        entity.addPart("first_name", new StringBody(params[8]));
//        entity.addPart("last_name", new StringBody(params[9]));



//    @Multipart
//    @POST("top_subjects.php?")
//    Call<ResponseBody> topSubjects(@Part ArrayList<MultipartBody.Part> hashMap);
//
//
//    @Multipart
//    @GET("subject_category.php?")
//    Call<ResponseBody> subjectCategory(@Part ArrayList<MultipartBody.Part> hashMap);
//
//
//    @Multipart
//    @POST("contactus.php?")
//    Call<ResponseBody> contactus(@Part ArrayList<MultipartBody.Part> hashMap);
//
//
//    @Multipart
//    @POST("login.php?")
//    Call<ResponseBody> login(@Part ArrayList<MultipartBody.Part> hashMap);
//
//
////    @Multipart
////    @POST("get_countries.php?")
////    Call<ResponseBody> getCountries(@Part ArrayList<MultipartBody.Part> hashMap);
//
//
//    @Multipart
//    @POST("forgot_password.php?")
//    Call<ResponseBody> forgotPassword(@Part ArrayList<MultipartBody.Part> hashMap);
//
//
//    @Multipart
//    @POST("register.php?")
//    Call<ResponseBody> register(@Part ArrayList<MultipartBody.Part> hashMap);


//    @Multipart
//    @POST("fb_register.php?")
//    Call<ResponseBody> fbRegister(@Part ArrayList<MultipartBody.Part> hashMap);


//    @Multipart
//    @POST("ads.php?")
//    Call<ResponseBody> ads(@Part ArrayList<MultipartBody.Part> hashMap);
//
//
    @Multipart
    @POST("coupan-code.php?")
    Call<ResponseBody> coupanCode(@Part ArrayList<MultipartBody.Part> hashMap);

//
//    @Multipart
//    @POST("subject-payment.php?")
//    Call<ResponseBody> subjectPayment(@Part ArrayList<MultipartBody.Part> hashMap);




}
