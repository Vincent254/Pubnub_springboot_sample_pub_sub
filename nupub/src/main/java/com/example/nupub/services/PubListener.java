package com.example.nupub.services;

import javax.annotation.PostConstruct;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PubListener{
    String channelName = "pubnub_onboarding_channel";
    PubNub pubNub;

    PNConfiguration pnConfiguration = new PNConfiguration();

    @PostConstruct
    private void init() {
        pnConfiguration.setSubscribeKey("my_subkey");
        pnConfiguration.setPublishKey("my_pub_key");
        pnConfiguration.setUuid("_my_uuid");
        this.pubNub =  new PubNub(pnConfiguration);
    }

    @Scheduled(cron = "0/60 * * * * ?") //every 60 seconds
    public void SendRandom(){
       
        String sendM = "{\n  \"response_code\": \"1017\",\n  \"merchant_category_code\": \"5814\",\n  \"system_time\": \"1608225195\",\n  \"terminal_id\": \"T_ID\",\n  \"capture_mode\": \"ECOM\",\n  \"request_time\": \"1608243193\",\n  \"merchant\": \"FOOD PANDA (THAILAND)  HUAYKWANG     THA\",\n  \"balance\": 0,\n  \"amount\": 7500,\n  \"tracking_number\": \"T_NUMBER\",\n  \"transaction_time\": \"1608217993\",\n  \"reference\": \"T_REFERENCE\",\n  \"transaction_id\": \"T_ID\",\n  \"type\": \"deduct authorisation\"\n}";
        JsonObject messagePayload = JsonParser.parseString(sendM).getAsJsonObject();
       
       this.pubNub.publish()
                .message(messagePayload)
                .channel(channelName)
                .async(new PNCallback<PNPublishResult>() {
                    @Override
                    public void onResponse(PNPublishResult result, PNStatus status) {
                        // handle status, response
                    }
                });
    }

    
}