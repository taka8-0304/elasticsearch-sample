package taka8.elasticsearch.rest;

import static org.elasticsearch.rest.RestRequest.Method.GET;
import static org.elasticsearch.rest.RestRequest.Method.HEAD;

import java.io.IOException;

import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.BytesRestResponse;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.RestResponse;
import org.elasticsearch.rest.RestStatus;

public class HelloWorldAction extends BaseRestHandler {

	public HelloWorldAction(final RestController controller) {
		assert controller != null;
		controller.registerHandler(GET, "/hello", this);
		controller.registerHandler(HEAD, "/hello", this);
	}

	@Override
	public String getName() {
		return "hello_word_action";
	}

	@Override
	protected RestChannelConsumer prepareRequest(RestRequest request, NodeClient client) throws IOException {
		return channel -> {
			RestResponse response = new BytesRestResponse(RestStatus.OK, "Hello World");
			channel.sendResponse(response);
		};
	}

}
