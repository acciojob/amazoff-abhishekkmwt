package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {
        this.id=id;
        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.deliveryTime=time(deliveryTime);
   }

    public Order() {

    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}

    public int time(String time){
        String arr[]=time.split(":");
        int hr=0;
        String s=arr[0];
        String m=arr[1];
        int h=0;
        for(int i=0;i<s.length();i++){
            h=(h*10)+s.charAt(i)-'0';
        }
        hr=60*h;
        int min=0;
        for(int i=0;i<m.length();i++){
            min=(min*10)+m.charAt(i)-'0';
        }
        return hr+min;
    }
}
