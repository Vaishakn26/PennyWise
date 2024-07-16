package info.accolade.trip_master.utils;

import android.content.Intent;
import android.view.View;

import info.accolade.trip_master.Login;
import info.accolade.trip_master.Register;

public class Constants {
    public static String PARAM_FNAME;
    public static String PARAM_LNAME;
    public static String PARAM_USERNAME;
    public static String PARAM_GENDER;
    public static String PARAM_DOB;
    public static String PARAM_PHONE;
    public static String PARAM_EMAIL;
    public static String PARAM_ADDR;
    public static String PARAM_CITY;
    public static String PARAM_PASS;

    public static String FNAME;
    public static String LNAME;
    public static String USERNAME;
    public static String GENDER;
    public static String DOB;
    public static String PHONE;
    public static String EMAIL;
    public static String ADDR;
    public static String CITY;
    public static String PASSWORD;

    public static String FP_PHONE;
    public static String FP_PASSWORD;

    public static String HOTEL_ID;
    public static String HOTEL_LATI;
    public static String HOTEL_LONGI;

    public static String CITY_ID;
    public static String CITY_LATI;
    public static String CITY_LONGI;

    public static String DEST_ID;
    public static String DEST_LATI;
    public static String DEST_LONGI;

    public static int CATEGORY_ID;

    static Double USER_LATI;
    static Double USER_LONGI;
    static String USER_LOCATION;


    public static int is_city;
    public static Boolean isEdited=false;

    public static String TOOLBAR_TITLE;


    public static String URL = "http://192.168.65.111/budget-php/";
//	public static String URL = "http://2018idealtravel.accoladetechsolutions.com/idealtravel/";

    public static String URL_REG = URL+"Register.php";
//    public static String URL_LOGIN = URL+"Login.php";
    public static String URL_CHANGEPASSWORD = URL+"ChangePassword.php";
    public static String URL_GETCODE = URL+"GetCode.php";
    public static String URL_MY_PROFILE = URL+"MyProfile.php";
    public static String URL_USER_REVIEWS = URL+"UserReviews.php";

    public static String URL_WEATHERDETAILS = URL+"GetWeatherDetails.php";
    public static String URL_CITYLIST = URL+"GetCityList.php";
    public static String URL_SEARCH_CITY = URL+"GetSearchCityList.php";
    public static String URL_HOTELLIST = URL+"GetHotelList.php";
    public static String URL_HOTELDETAILS = URL+"GetHotelDetails.php";
    public static String URL_SEARCH_HOTEL = URL+"GetSearchHotelList.php";
    public static String URL_CurCity_DESTLIST = URL+"GetCurrentDestList.php";
    public static String URL_City_DESTLIST = URL+"GetCityDestList.php";
    public static String URL_DESTINATIONDETAILS = URL+"GetDestinationDetails.php";
    public static String URL_HOTELREVIEWLIST = URL+"GetHotelReviewList.php";
    public static String URL_ADDHOTELREVIEW = URL+"AddHotelReview.php";
    public static String URL_USERREVIEWLIST = URL+"GetUserReviewList.php"; //edited
    public static String URL_UPDATEPROFILE = URL+"UpdateProfile.php";
    public static String URL_GET_CATEGORY = URL+"getcategory.php";

    public static String URL_FileUpload = URL+"FileUpload.php";
//    public static String URL_UPLOADS= URL+"image/";

    public static String URL_Hotel_image=URL+"hotel/";
    public static String URL_City_image=URL+"city/";
    public static String URL_Destination_image=URL+"destination/";
































    public static String URL_LOGIN=URL+"GetLogin.php";
    public static String url_reset_password = URL+"reset1.php";
    public static String url_reset_password2 = URL+"reset2.php";

    public static String URL_Reg=URL+"Register.php";
    public static String URL_HOTELS=URL+"hotel.php";
    public static String URL_MALLS=URL+"mall.php";
    public static String URL_CINEMA=URL+"movie.php";
    public static String URL_PLACE=URL+"tour.php";
    public static String URL_FOOD = URL+"food.php";
    public static String URL_ITEM = URL+"shop.php";
    public static String URL_feedback = URL+"feedback.php";
    public static String url_change_password = URL+"change_password.php";
    public static String URL_UPLOADS=URL+"admin/";
}