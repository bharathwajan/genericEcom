package com.generic.ecom.serviceInterfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name="NOTIFICATION-SERVICE", url = "${notification.service.url}")
public interface NotificationServiceInterface {
    @PostMapping("/sendNotification")
    public Map<String,String> sendNotification(@RequestBody Map<String,String> payload);
}
