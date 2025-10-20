//package com.generic.ecom.serviceInterfaces;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import java.util.Map;
//
//@FeignClient("NOTIFICATION-SERVICE") // service name identified via eureka
//public interface NotificationServiceInterface {
//    @PostMapping("/sendNotification")
//    public Map<String,String> sendNotification(@RequestBody Map<String,String> payload);
//}
