package org.sample.onboarding_process_app;

import java.util.logging.Logger;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;


public class AddTrelloCardDelegate implements JavaDelegate {

	private final Logger LOGGER = Logger.getLogger(AddTrelloCardDelegate.class.getName());

	public void execute(DelegateExecution execution) throws Exception {

		HttpResponse<JsonNode> jsonResponse;
		String cardId;

		// name and listId are input parameters

		jsonResponse = Unirest.post(
				"https://api.trello.com/1/cards?key=XXX&token=XXX")
				.header("Accept", "application/json").queryString("idList", execution.getVariable("listId"))
				.queryString("name", execution.getVariable("name")).asJson();

		if (jsonResponse.isSuccess()) {

			cardId = jsonResponse.getBody().getObject().getString("id");
			execution.setVariableLocal("cardId", cardId);

			LOGGER.info("\nCard Id: " + cardId);

		} else {

			throw new ProcessEngineException(jsonResponse.getParsingError().get().getOriginalBody());
		}
	}

}
