package tests;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.io.File;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.*;

public class RequestPOSTTests extends BaseTest{

	public static Response sendRequest() {
		File json = new File("src/test/java/data/postRequestBody.json");

		return given()
				.contentType("application/json")
				.body(json)
				.when()
				.post("/posts");
	}

	public static Response sendRequest(String title, String body, int userId) {
		String requestBody = String.format(
				"{\"title\": \"%s\", \"body\": \"%s\", \"userId\": %d}",
				title, body, userId);

		return given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/posts");
	}

	@Test
	public void validateResponseCode(){
		Response response = sendRequest();

		response.then().assertThat()
				.statusCode(
						equalTo(201));

	}

	@Test
	public void validateResponseBody(){
		Response response = sendRequest();

		response.then().assertThat()
				.body("title", equalTo("title1"))
				.body("body", equalTo("body1"))
				.body("userId", equalTo(1))
				.body("id", notNullValue());
	}

	@Test
	public void validateCustomResponseBody(){
		String title = "title1";
		String body = "body1";
		int userId = 1;
		Response response = sendRequest(title, body, userId);

		response.then().assertThat()
				.body("title", equalTo(title))
				.body("body", equalTo(body))
				.body("userId", equalTo(userId))
				.body("id", notNullValue());
	}

	@Test
	public void validateResponseSchema(){
		File jsonSchema = new File("src/test/resources/jsonSchema.json");
		Response response = sendRequest();

		response.then().assertThat()
				.body(matchesJsonSchema(jsonSchema));
	}
}