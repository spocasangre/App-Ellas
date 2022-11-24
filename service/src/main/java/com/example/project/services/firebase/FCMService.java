package com.example.project.services.firebase;

import com.example.project.models.dtos.PushNotificationRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class FCMService {
    private Logger logger = LoggerFactory.getLogger(FCMService.class);

    public void sendMessageToToken(PushNotificationRequest request, Map<String, String> dataPP)
            throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageToTokenWithData(request, dataPP);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(message);
        //System.out.println("JSON: "+jsonOutput);
        String response = sendAndGetResponse(message);
        //logger.info("Response+ " +jsonOutput);
    }

    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }

    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH).build();
    }

    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }

    private Message getPreconfiguredMessageToTokenWithData(PushNotificationRequest request, Map<String, String> dataP) {
        //System.out.println(request.getToken()+", "+request.getTopic());
        AndroidConfig androidConfig = getAndroidConfig(request.getTopic());
        ApnsConfig apnsConfig = getApnsConfig(request.getTopic());
        return Message.builder().setApnsConfig(apnsConfig).setAndroidConfig(androidConfig)
                .setToken(request.getToken()).putAllData(dataP).build();
    }

    /*private Message.Builder getPreconfiguredMessageBuilder(PushNotificationRequest request) {
        AndroidConfig androidConfig = getAndroidConfig(request.getTopic(), request.getImageURL());
        ApnsConfig apnsConfig = getApnsConfig(request.getTopic());
        return Message.builder()
                .setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).setNotification(
                        Notification.builder().setTitle(request.getTitle()).setBody(request.getMessage()).setImage(request.getImageURL()).build()
                );
    }*/
}
