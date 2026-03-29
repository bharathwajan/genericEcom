package com.generic.ecom.view.Services;

import com.learning.notification.SendNotificationRequest; // from common project
import com.learning.notification.SendNotificationResponse; // from common project
import com.learning.notification.NotificationServiceGrpc; // from common project
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceGRPCClient {
    @GrpcClient("NOTIFICATION-SERVICE")
    private NotificationServiceGrpc.NotificationServiceBlockingStub notificationServiceStub; // This is blocking stub used for unary calls

    public SendNotificationResponse sendNotification(String to,String message) {
        SendNotificationRequest request = SendNotificationRequest.newBuilder()
                .setTo(to)
                .setMessage(message)
                .build();

        return notificationServiceStub.sendNotification(request);
    }
}
