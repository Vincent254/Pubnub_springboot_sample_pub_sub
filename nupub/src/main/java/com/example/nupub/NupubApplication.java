package com.example.nupub;

import java.util.Collections;

import com.google.gson.JsonElement;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.enums.PNStatusCategory;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.objects_api.channel.PNChannelMetadataResult;
import com.pubnub.api.models.consumer.objects_api.membership.PNMembershipResult;
import com.pubnub.api.models.consumer.objects_api.uuid.PNUUIDMetadataResult;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;
import com.pubnub.api.models.consumer.pubsub.PNSignalResult;
import com.pubnub.api.models.consumer.pubsub.files.PNFileEventResult;
import com.pubnub.api.models.consumer.pubsub.message_actions.PNMessageActionResult;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NupubApplication {

	

	public static void main(String[] args) {
		SpringApplication.run(NupubApplication.class, args);


		final PNConfiguration pnConfiguration = new PNConfiguration();
		pnConfiguration.setSubscribeKey("sub-c-dd3ddef2-5057-11ec-b60b-aa41d66f579f");
		//pnConfiguration.setPublishKey("myPublishKey");
		pnConfiguration.setUuid("Vinni_2021_Ruaka_myUniqueUUID");
		String channelName = "pubnub_onboarding_channel";
		PubNub pubnub = new PubNub(pnConfiguration);

		pubnub.addListener(new SubscribeCallback() {

			@Override
			public void status(PubNub pubnub, PNStatus status) {
				if (status.getCategory() == PNStatusCategory.PNUnexpectedDisconnectCategory) {
					// This event happens when radio / connectivity is lost
				} else if (status.getCategory() == PNStatusCategory.PNConnectedCategory) {
					// Connect event. You can do stuff like publish, and know you'll get it.
					// Or just use the connected event to confirm you are subscribed for
					// UI / internal notifications, etc



					if (status.getCategory() == PNStatusCategory.PNConnectedCategory) {
						pubnub.publish()
							.channel(channelName)
							.message(status)
							.async((result, publishStatus) -> {
								if (!publishStatus.isError()) {
									// Message successfully published to specified channel.
								}
								// Request processing failed.
								else {
									// Handle message publish error
									// Check 'category' property to find out
									// issues because of which the request failed.
									// Request can be resent using: [status retry];
								}
							});
					}
				} else if (status.getCategory() == PNStatusCategory.PNReconnectedCategory) {
					// Happens as part of our regular operation. This event happens when
					// radio / connectivity is lost then regained.
				} else if (status.getCategory() == PNStatusCategory.PNDecryptionErrorCategory) {
					// Handle message decryption error. Probably client configured to
					// encrypt messages and on live data feed it received plain text.
				}
			}
		
			@Override
			public void message(PubNub pubnub, PNMessageResult message) {
				// Handle new message stored in message.message
				if (message.getChannel() != null) {
					// Message has been received on channel group stored in
					// message.getChannel()
				} else {
					// Message has been received on channel stored in
					// message.getSubscription()
				}
		
				JsonElement receivedMessageObject = message.getMessage();
				System.out.println("Received message: " + receivedMessageObject.toString());
				// extract desired parts of the payload, using Gson
				//String msg = message.getMessage().getAsJsonObject().get("msg").getAsString();
				//System.out.println("The content of the message is: " + msg);
		
				// log the following items with your favorite logger - message.getMessage() -
				// message.getSubscription() - message.getTimetoken()
		
			}

			@Override
			public void channel(  PubNub arg0,  PNChannelMetadataResult arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void file( PubNub arg0, PNFileEventResult arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void membership(  PubNub arg0,  PNMembershipResult arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void messageAction(  PubNub arg0,  PNMessageActionResult arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void presence(  PubNub arg0,  PNPresenceEventResult arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void signal(  PubNub arg0,  PNSignalResult arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void uuid(  PubNub pubnub,  PNUUIDMetadataResult arg1) {
				// TODO Auto-generated method stub
				
				//arg1.
			}
		
		
		
		});

		pubnub.subscribe()
        .channels(Collections.singletonList(channelName))
        .execute(); 

	}

}
