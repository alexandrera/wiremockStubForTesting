package test;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

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
	public String BaseURI = "http://www.urlficticia.com.br";
	public String gerar_token, fileJson1 = "./src/test/gerar_token.json";
	public String criar_emprestimo, fileJson2 = "./src/test/criar_emprestimo.json";
	public String consultar_emprestimo, fileJson3 = "./src/test/consultar_emprestimo.json";

	@Before
	public void stubService() throws IOException {
		gerar_token = new String(Files.readAllBytes(Paths.get(fileJson1)));
		criar_emprestimo = new String(Files.readAllBytes(Paths.get(fileJson2)));
		consultar_emprestimo = new String(Files.readAllBytes(Paths.get(fileJson3)));

		WireMock.configureFor("localhost", wireMockRule.port());
		WireMock.reset();
		stubFor(get(urlEqualTo("/api/v1/token/")).willReturn(aResponse().withStatus(200).withBody(gerar_token)));
		stubFor(get(urlEqualTo("/api/v1/emprestimos/"))
				.willReturn(aResponse().withStatus(200).withBody(criar_emprestimo)));
		stubFor(get(urlEqualTo("/api/v1/emprestimos/55208"))
				.willReturn(aResponse().withStatus(200).withBody(consultar_emprestimo)));

		// indicating that we are working with JSON format
		RestAssured.defaultParser = Parser.JSON;

		//

		System.out.println("Set the breakpoint here to see the stubs working!");
	}

	@org.junit.Test
	public void serverIsUp() {

		// Server is up
		RestAssured.given().when().get("http://localhost:8888/api/v1/token/").then().assertThat().statusCode(200);

	}

	@org.junit.Test
	public void validateJsonFileGerarToken() {

		// Json to be validate
		System.out.println(gerar_token);

		// Validating all the content included in the json file
		RestAssured.given().when().get("http://localhost:8888/api/v1/token/").then().assertThat()
				.content(Matchers.equalTo(gerar_token));

	}

	@org.junit.Test
	public void validateJsonFileCriarEmprestimo() {

		// Json to be validate
		System.out.println(criar_emprestimo);

		// Validating all the content included in the json file
		RestAssured.given().when().get("http://localhost:8888/api/v1/emprestimos/").then().assertThat()
				.content(Matchers.equalTo(criar_emprestimo));

	}

	@org.junit.Test
	public void validateJsonFileConsultarEmprestimo() {

		// Json to be validate
		System.out.println(consultar_emprestimo);

		// Validating all the content included in the json file
		RestAssured.given().when().get("http://localhost:8888/api/v1/emprestimos/55208").then().assertThat()
				.content(Matchers.equalTo(consultar_emprestimo));

	}

}