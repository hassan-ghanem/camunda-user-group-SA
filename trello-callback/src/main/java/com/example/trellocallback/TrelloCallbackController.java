package com.example.trellocallback;

import java.util.logging.Logger;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trello.WebhookRequest;

import kong.unirest.Unirest;

@RestController
public class TrelloCallbackController {

	private final Logger LOGGER = Logger.getLogger(TrelloCallbackController.class.getName());

	@RequestMapping(value="/callback", method = {RequestMethod.GET, RequestMethod.HEAD})
	void doTest() {
		
		LOGGER.info("Test Callback");
	}
	
	@PostMapping("/callback")
	void trelloCardDone(@RequestBody WebhookRequest webhookRequest) {

		String cardId;

		if (webhookRequest != null && webhookRequest.getAction() != null && webhookRequest.getAction().getType().equals("updateCard")
				&& webhookRequest.getAction().getData().getListAfter() != null && webhookRequest.getAction().getData().getListAfter().getName().equals("Done")) {

			cardId = webhookRequest.getAction().getData().getCard().getId();

			LOGGER.info("\nCard Id: " + cardId);

			Unirest.post("http://XXX/engine-rest/message")
				.header("Accept", "application/json")
				.header("Content-Type", "application/json")
				.body(String.format("{\"messageName\":\"trello-card-done-msg\",\"localCorrelationKeys\":{\"cardId\":{\"value\":\"%s\",\"type\":\"String\"}}}", cardId))
				.asJson();
		}
	}
}
