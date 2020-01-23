package taka8.elasticsearch.rest;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class QueryTemplateTest {

	private ObjectMapper _om;

	@Before
	public void before() {
		_om = new ObjectMapper();
		_om.configure(SerializationFeature.INDENT_OUTPUT, true);
	}

	@Test
	public void testTextOnly() throws Exception {
		String actual = QueryTemplate.getTextOnly("value");
		_assertEqualsWithResource(actual, "text_only.json", s -> _normalizeJson(s));
	}

	private String _normalizeJson(String in) {
		try {
			JsonNode node = _om.readTree(in);
			return _om.writeValueAsString(node);
		} catch (IOException e) {
			throw new IllegalArgumentException("Invalid json string <" + in + ">.", e);
		}
	}

	private void _assertEqualsWithResource(String actual, String expectedPath, Function<String, String> normalizer)
			throws Exception {
		assert actual != null;
		assert expectedPath != null;
		assert normalizer != null;
		URL resource = this.getClass().getResource(expectedPath);
		if (resource == null) {
			throw new IllegalArgumentException("Failed to get expected resource <" + expectedPath + ">.");
		}
		Path path = Paths.get(resource.toURI());
		Assert.assertEquals(normalizer.apply(actual), normalizer.apply(Files.readString(path)));
	}

}
