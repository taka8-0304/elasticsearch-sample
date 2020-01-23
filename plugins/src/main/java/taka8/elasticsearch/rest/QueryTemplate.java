package taka8.elasticsearch.rest;

public class QueryTemplate {

//	{
//	  "query" : {
//	    "match" : {
//	      "text" : {
//	        "query" : "[[text]]",
//	        "operator" : "and"
//	      }
//	    }
//	  }
//	}
	private static final String TEXT_ONLY = "{\"query\":{\"match\":{\"text\":{\"query\":\"[[text]]\",\"operator\":\"and\"}}}}";

	public static String getTextOnly(String text) {
		return QueryTemplate.TEXT_ONLY.replace("[[text]]", text);
	}
}
