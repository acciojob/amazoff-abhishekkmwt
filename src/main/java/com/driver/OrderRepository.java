package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {
     HashMap<String,Order> orderDb = new HashMap<>();
     HashMap<String,String> assignedDb =new HashMap<>();
    HashMap<String,DeliveryPartner> partnerDb = new HashMap<>();
    HashMap<String, List<String>> pairDb =new HashMap<>();
    public void addOrder(Order order){
        orderDb.put(order.getId(),order);
    }

    public void addPartner(String partnerId){
        partnerDb.put(partnerId, new DeliveryPartner(partnerId));
    }

    public void addOrderPartnerPair(String orderId, String partnerId){
        List<String> orders = pairDb.get(partnerId);
        if(pairDb.get(partnerId)== null){
            orders=new ArrayList<>();
        }
        orders.add(orderId);
        pairDb.put(partnerId,orders);

        DeliveryPartner partner = partnerDb.get(partnerId);
        partner.setNumberOfOrders(orders.size());

        assignedDb.put(orderId,partnerId);
    }

    public Order getOrderById(String orderId){
       return orderDb.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId){
        return partnerDb.get(partnerId);
    }
    public int getOrderCountByPartnerId(String partnerId){
        int orderCount=0;
        if(partnerDb.containsKey(partnerId)){
            orderCount=partnerDb.get(partnerId).getNumberOfOrders();
        }
        return orderCount;
    }

    public List<String> getOrdersByPartnerId(String partnerId){
        return pairDb.get(partnerId);
    }

    public List<String> getAllOrders(){
        List<String> ordersList =new ArrayList<>();
        for(String orders : orderDb.keySet()){
            ordersList.add(orders);
        }
        return ordersList;
    }

    public int getCountOfUnassignedOrders(){
          return orderDb.size()-assignedDb.size();
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
         List<String> list=pairDb.get(partnerId);
         int count=0;
         for(String s: list){
             if(orderDb.get(s).getDeliveryTime() <= new Order().time(time)){
                 count++;
             }
         }
       return count;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId){
      int larTime =0;
      List<String> list=pairDb.get(partnerId);
        for(String s: list){
            if(orderDb.get(s).getDeliveryTime() > larTime){
                larTime=orderDb.get(s).getDeliveryTime();
            }
        }
        int hr=larTime/60;
        int min=larTime%60;
        if(hr<10 && min<10){
            return "0"+hr+":0"+min;
        }
        else if(hr<10){
            return "0"+hr+":"+min;
        }
        else if(min<10){
            return hr+":0"+min;
        }
        return hr+":"+min;
    }

    public void deletePartnerById(String partnerId){
       for(String s : assignedDb.keySet()){
           if(assignedDb.get(s).equals(partnerId))
           {
               assignedDb.remove(s);
           }
       }
       if(pairDb.containsKey(partnerId)) pairDb.remove(partnerId);
       if(partnerDb.containsKey(partnerId))partnerDb.remove(partnerId);
    }

    public void deleteOrderById(String orderId){

        for(String partner: pairDb.keySet()){
            for(String orders : pairDb.get(partner)){
                if(orders.equals(orderId)){
                    pairDb.get(partner).remove(orders);
                }
            }
        }
        orderDb.remove(orderId);
    }
}
