package taka8.elasticsearch.rest;

import static org.elasticsearch.rest.RestRequest.Method.GET;

import java.io.IOException;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.DeprecationHandler;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.action.RestStatusToXContentListener;
import org.elasticsearch.search.builder.SearchSourceBuilder;

// REST APIを受け付けるRestHandlerはBaseRestHandlerを継承すると作りやすい
public class TextOnlySearchAction extends BaseRestHandler {

	public TextOnlySearchAction(final RestController controller) {
		assert controller != null;
		// 要求を受け付けるパスを定義する。
		controller.registerHandler(GET, "/{index}/search_text", this);
	}

	// RestHandlerの名前を定義する。
	// 人が見て分かりやすい名前にする。
	// 使用方法を返すAPIで使用される
	@Override
	public String getName() {
		return "search_text_action";
	}

	@Override
	protected RestChannelConsumer prepareRequest(RestRequest request, NodeClient client) throws IOException {
		SearchRequest searchRequest = new SearchRequest();
		request.withContentOrSourceParamParserOrNull(parser -> _parseSearchRequest(searchRequest, request, parser));
		return channel -> {
			// 検索結果からREST APIの戻り値を作成するListenerを生成する。
			// ここでは、検索結果をそのまま返す標準のListenerにする。
			RestStatusToXContentListener<SearchResponse> listener = new RestStatusToXContentListener<>(channel);
			// インデックスノードに対して要求を投げる。Elasticsearchはインデックスを分割して複数のノードに配置する事で、検索性能と可用性を担保している。
			// このため、REST要求を受け付けるノードから実際の検索を行うノードに対して要求を投げる必要がある。
			client.search(searchRequest, listener);
		};
	}

	// RESTの入力からインデックスノードに対しての検索要求を初期化する。
	private void _parseSearchRequest(SearchRequest searchRequest, RestRequest request,
			XContentParser requestContentParser) throws IOException {
		if (searchRequest.source() == null) {
			searchRequest.source(new SearchSourceBuilder());
		}
		// クエリのtextパラメータから、ElasticsearchのJSON形式の検索要求を生成する。
		String source = QueryTemplate.getTextOnly(request.param("text"));
		// JSON形式の検索要求用のパーサ。
		XContentParser parser = XContentType.JSON.xContent().createParser(request.getXContentRegistry(),
				DeprecationHandler.THROW_UNSUPPORTED_OPERATION, source);
		searchRequest.source().parseXContent(parser);
		searchRequest.indices(Strings.splitStringByCommaToArray(request.param("index")));
	}

}
