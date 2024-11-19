package tests.AzureFakeRestAPIs;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class BaseTest {
	static Random random = new Random();

	@BeforeSuite
	public void setUp(){
		RestAssured.baseURI = "https://fakerestapi.azurewebsites.net/api/v1/";
	}

	public static String generateRandomDueDate() {
		ZonedDateTime randomDate = ZonedDateTime.now(ZoneOffset.UTC)
				.plusDays(random.nextInt(30));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		return randomDate.format(formatter);
	}

	public static Boolean generateRandomCompleted() {
		return random.nextBoolean();
	}
}
