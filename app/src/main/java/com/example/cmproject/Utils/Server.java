package com.example.cmproject.Utils;

public class Server {
    public static final String localhost="http://10.184.159.6:8080/restapi/webapi/";
    public static String UrlGetParking = localhost + "parkings";
    public static String UrlGetDriver = localhost + "drivers";
    public static String UrlPostDriver = localhost + "drivers/" + "postDriver";
    public static String UrlPutDriver = localhost + "drivers/" + "putDriver";

    public static String UrlSignIn = localhost + "drivers/" + "signUp";
}
