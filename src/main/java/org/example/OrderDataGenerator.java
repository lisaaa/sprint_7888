package org.example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrderDataGenerator {

    public static Order getOrder(String[] color) {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
         String firstName = "Name" + timestamp;
         String lastName = "Surname" + timestamp;
         String address = " Lenina st, 45";
         String metroStation =  "4";
         String phone = "+7 456 78 67";
         String rentTime = "5";
         String deliveryDate = "2023-01-21";
         String comment = "comment" + timestamp;
            return new Order(firstName,lastName,address,metroStation,phone,rentTime,deliveryDate,comment,color);

    }

    public static Order getOrderWithoutColor() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        String firstName = "Name" + timestamp;
        String lastName = "Surname" + timestamp;
        String address = " Lenina st, 45";
        String metroStation =  "4";
        String phone = "+7 456 78 67";
        String rentTime = "5";
        String deliveryDate = "2023-01-21";
        String comment = "comment" + timestamp;
        String[] color = {"BLACK"};
        return new Order(firstName,lastName,address,metroStation,phone,rentTime,deliveryDate,comment,color);
    }
    public static Map<String,String> paramCourierID(String courierId){

        Map<String, String> orderData = new HashMap<>();
        orderData.put("courierId",courierId);
        return  orderData;
    };

    public static Map<String,String> paramNearestStation(String nearestStation){

        Map<String, String> orderData = new HashMap<>();
        orderData.put("nearestStation",nearestStation);
        return  orderData;
    };

    public static Map<String, List<String>> paramNearestStations(){

        Map<String, List<String>> orderData = new HashMap<String, List<String>>();

        List<String> values = new ArrayList<String>();
        values.add("1");
        values.add("2");
        orderData.put("nearestStation", values);
        return  orderData;
    };

    public static Map<String,String> paramLimit(){

        Map<String, String> orderData = new HashMap<>();
        orderData.put("limit","10");
        return  orderData;
    };

    public static Map<String,String> paramPage(){

        Map<String, String> orderData = new HashMap<>();
        orderData.put("page","0");
        return  orderData;
    };
}
