package tests;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class RequestDELETETest extends BaseTest{
	public static Response sendRequest(int id) {

		return given()
				.when()
				.delete("/posts/" + id);
	}

	@Test
	public void deletePostById() {
		Response response = sendRequest(1);
		response.then().assertThat()
				.statusCode(200);
	}
}
