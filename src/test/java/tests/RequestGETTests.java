package tests;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class RequestGETTests extends BaseTest{

	public static Response sendRequest() {

		return given()
				.when()
				.get("/posts");
	}
	public static Response sendRequest(int id) {

		return given()
				.when()
				.get("/posts/" + id);
	}

	@Test
	public void getRandomPostFromAllPosts(){
		Response response = sendRequest();
		List<Integer> ids = response.path("id");

		Random random = new Random();
		Integer randomPostId = ids.get(random.nextInt(ids.size()));

		Response randomPost = sendRequest(randomPostId);

		Map<String, Object> originalPost = response.then()
				.extract()
				.body()
				.jsonPath()
				.getMap("find { it.id == " + randomPostId + " }");

		randomPost.then().assertThat()
				.body("title", equalTo(originalPost.get("title")))
				.body("body", equalTo(originalPost.get("body")))
				.body("userId", equalTo(originalPost.get("userId")))
				.body("id", equalTo(originalPost.get("id")));
	}
}
