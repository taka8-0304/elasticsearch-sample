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

// REST APIを受け付けるRestHandlerはBaseRestHandlerを継承すると作りやすい
public class HelloWorldAction extends BaseRestHandler {

	public HelloWorldAction(final RestController controller) {
		assert controller != null;
		// 要求を受け付けるパスを定義する。
		controller.registerHandler(GET, "/hello", this);
		controller.registerHandler(HEAD, "/hello", this);
	}

	// RestHandlerの名前を定義する。
	// 人が見て分かりやすい名前にする。
	// 使用方法を返すAPIで使用される
	@Override
	public String getName() {
		return "hello_word_action";
	}

	@Override
	protected RestChannelConsumer prepareRequest(RestRequest request, NodeClient client) throws IOException {
		return channel -> {
			RestResponse response = new BytesRestResponse(RestStatus.OK, "Hello World");
			// チャンネルに応答を書き込む
			channel.sendResponse(response);
		};
	}

}
