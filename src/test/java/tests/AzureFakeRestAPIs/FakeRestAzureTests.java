package tests.AzureFakeRestAPIs;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class FakeRestAzureTests extends BaseTest {
	int activitiesCounter;
	int id;
	String title;
	String dueDate;
	Boolean completed;

	@BeforeTest
	public void setUpCounter( ) {
		if(activitiesCounter == 0) {
			activitiesCounter = given().when().get("Activities").jsonPath().getList("id").size();
		}
		id = random.nextInt(activitiesCounter);
		title = "POST Activity " + id;
		dueDate = generateRandomDueDate();
		completed = generateRandomCompleted();
	}

	@Test
	public void validateGETActivitiesSchema(){
		given().when().get("Activities")
			.then().assertThat()
				.statusCode(200)
				.body(matchesJsonSchemaInClasspath("activitiesSchema.json"));
	}

	@Test
	public void validatePOSTActivities(){
			String requestBody = String.format(
					"{\"id\": %d,\"title\": \"%s\", \"dueDate\": \"%s\", \"completed\": %b}",
					id, title, dueDate, completed);

		given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("Activities")
				.then().assertThat()
				.statusCode(200)
				.body("id", equalTo(id))
				.body("title", equalTo(title))
				.body("dueDate", equalTo(dueDate))
				.body("completed", equalTo(completed))
				.body(matchesJsonSchemaInClasspath("activitySchema.json"));
	}

	@Test
	public void validateGETActivityById(){
		given().when().get("Activities/" + id)
				.then().assertThat()
				.statusCode(200)
				.body(matchesJsonSchemaInClasspath("activitySchema.json"));
	}

	@Test
	public void validatePUTActivityById() {
		String requestBody = String.format(
				"{\"id\": %d,\"title\": \"%s\", \"dueDate\": \"%s\", \"completed\": %b}",
				id, title, dueDate, completed);
		given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.put("Activities/" + id)
				.then().assertThat()
				.statusCode(200)
				.body("id", equalTo(id))
				.body("title", equalTo(title))
				.body("dueDate", equalTo(dueDate))
				.body("completed", equalTo(completed))
				.body(matchesJsonSchemaInClasspath("activitySchema.json"));
	}

	@Test
	public void validateDELETEActivityById() {
		given().when().delete("Activities/" + id)
				.then().assertThat()
				.statusCode(200);
	}
}
