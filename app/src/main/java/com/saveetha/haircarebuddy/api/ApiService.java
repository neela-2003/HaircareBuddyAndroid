package com.saveetha.haircarebuddy.api;


import com.saveetha.haircarebuddy.models.DoctorListResponse;
import com.saveetha.haircarebuddy.models.ForgotPasswordRequest;
import com.saveetha.haircarebuddy.models.ForgotPasswordResponse;


import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {


    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> loginUser(
            @Field("Email") String email,
            @Field("Password") String password
    );


    @FormUrlEncoded
    @POST("usersignup.php")
    Call<SignupResponse> signupUser(
            @Field("Name") String name,
            @Field("Age") int age,
            @Field("Gender") String gender,
            @Field("Number") String number,
            @Field("Email") String email,
            @Field("Password") String password
    );



    @GET("myprofile.php")  // Adjust path if needed
    Call<UserProfileResponse> getUserProfile(@Query("Email") String email);



    @GET("doctorlist.php")
    Call<DoctorListResponse> getAllDoctors();

    @FormUrlEncoded
    @POST("appointmentsbooking.php")
    Call<AppointmentResponse> bookAppointment(
            @Field("doctor_id") String doctorId,
            @Field("patient_name") String patientName,
            @Field("patient_email") String patientEmail,
            @Field("appointment_date") String date,
            @Field("appointment_time") String time,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("review.php")
    Call<Map<String, Object>> submitReview(
            @Field("email") String email,
            @Field("view") String view,
            @Field("rating") float rating
    );

    @GET("otherreview.php")
    Call<Map<String, Object>> getAllReviews();

    @FormUrlEncoded
    @POST("delete_account.php")
    Call<UserProfileResponse> deleteUser(@Field("Email") String email);

    @FormUrlEncoded
    @POST("submit_hair_quiz.php")
    Call<HairQuizResponse> submitHairQuiz(
            @Field("email") String email,
            @Field("answer1") String a1,
            @Field("answer2") String a2,
            @Field("answer3") String a3,
            @Field("answer4") String a4,
            @Field("answer5") String a5,
            @Field("answer6") String a6,
            @Field("answer7") String a7,
            @Field("answer8") String a8,
            @Field("answer9") String a9,
            @Field("answer10") String a10,
            @Field("answer11") String a11,
            @Field("answer12") String a12,
            @Field("answer13") String a13,
            @Field("answer14") String a14,
            @Field("answer15") String a15,
            @Field("answer16") String a16
    );


    @FormUrlEncoded
    @POST("doctorlogin.php")
    Call<com.saveetha.haircarebuddy.api.DoctorLoginResponse> doctorLogin(
            @Field("Email") String email,
            @Field("Password") String password
    );

    @FormUrlEncoded
    @POST("doctorsignup.php")
    Call<com.saveetha.haircarebuddy.api.DoctorSignupResponse> doctorSignup(
            @Field("Name") String name,
            @Field("DoctorId") String doctorId,
            @Field("Number") String number,
            @Field("Experience") String experience,
            @Field("Email") String email,
            @Field("Password") String password
    );

    @GET("userlist.php")
    Call<UserListResponse> getUserList();

    @GET("doctorprofile.php")
    Call<DoctorProfileResponse> getDoctorProfile(@Query("doctorId") String doctorId);

    @GET("get_ai_response.php") // Adjust to your PHP filename
    Call<AiResponse> getAiResponse(@Query("email") String email);

    @FormUrlEncoded
    @POST("Forget_password.php")
    Call<ResponseBody> checkEmailForReset(@Field("email") String email);
    @POST("Forget_password.php")
    Call<ForgotPasswordResponse> resetPassword(@Body ForgotPasswordRequest request);

}




