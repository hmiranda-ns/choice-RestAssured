package tests;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class RequestPUTTests extends BaseTest{
	public static Response sendRequest() {
		File json = new File("src/test/java/data/putRequestBody.json");

		return given()
				.contentType("application/json")
				.body(json)
				.when()
				.put("/posts/1");
	}

	@Test
	public void validateResponseCode() {
		Response response = sendRequest();

		response.then().assertThat()
				.statusCode(
						equalTo(200));
	}

	@Test
	public void validateResponseBody() {
		Response response = sendRequest();

		response.then().assertThat()
				.body("title", equalTo("titlemodified"))
				.body("body", equalTo("bodymodified"))
				.body("userId", equalTo(1))
				.body("id", equalTo(1));
	}
}
