package org.example;

import java.text.SimpleDateFormat;

public class CourierGenerator {

    public static Courier getDefaultCourier() {
        return new Courier("lon","1234","saske");
        //id = 142920
    }

    public static Courier getRandomCourier() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        String login =  "courier" + timestamp;
        String firstName =  "name" + timestamp;
        String password = timestamp;
        return new Courier(login,password,firstName);
    }

    public static Courier getCourierWithoutPassword() {
        return new Courier("1234","123");
        //id = 142920
    }

    public static Courier getCourierWithoutLoginAndPassword() {
        return new Courier("firstName");
        //id = 142920
    }
}
