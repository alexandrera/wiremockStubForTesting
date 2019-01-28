package test;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Mystub {

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(8888);
	public String url = "http://www.urlficticia.com.br/api/v1/token/";
	public String gerar_token1, content1, fileJson1 = "./src/test/gerar_token.json";
//	public String gerar_token = "./src/test/gerar_token.json";
//	public String gerar_token = "./src/test/gerar_token.json";

	@Before
	public void stubService() throws IOException {
		gerar_token1 = new String(Files.readAllBytes(Paths.get(fileJson1)));
		WireMock.configureFor("localhost", wireMockRule.port());
		WireMock.reset();
		stubFor(get(urlEqualTo("/api/v1/token/")).willReturn(aResponse().withStatus(200).withBody(gerar_token1)));

		System.out.println("breakpoint!!!");
	}

	@org.junit.Test
	public void test() throws Exception {

		// Server is up
		RestAssured.given().when().get("http://localhost:8888/api/v1/tokfen/").then().assertThat().statusCode(404);

	}

}