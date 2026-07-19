package com.generic.ecom.view.Services;

import com.learning.notification.SendNotificationRequest; // from common project
import com.learning.notification.SendNotificationResponse; // from common project
import com.learning.notification.NotificationServiceGrpc; // from common project
import io.github.resilience4j.retry.annotation.Retry;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceGRPCClient {

    private Logger logger = LoggerFactory.getLogger(NotificationServiceGRPCClient.class);

    @GrpcClient("NOTIFICATION-SERVICE")
    private NotificationServiceGrpc.NotificationServiceBlockingStub notificationServiceStub; // This is blocking stub used for unary calls

    @Retry(name = "notificationService", fallbackMethod = "sendNotificationFallback")
    public SendNotificationResponse sendNotification(String to,String message) {
        logger.info("Calling Notification Service to send notification to: {} " +
                "with message: {}", to, message);
        SendNotificationRequest request = SendNotificationRequest.newBuilder()
                .setTo(to)
                .setMessage(message)
                .build();

        return notificationServiceStub.sendNotification(request);
    }

    public SendNotificationResponse sendNotificationFallback(String to, String message, Exception e) {
        // Log the failure, push to a Kafka topic, or return a default response
        logger.error("Notification Service is down. Falling back. Exception: {}", e.getMessage());
        return SendNotificationResponse.newBuilder()
                // .setSuccess(false) // Set whatever default fields your proto defines
                .build();
    }
}