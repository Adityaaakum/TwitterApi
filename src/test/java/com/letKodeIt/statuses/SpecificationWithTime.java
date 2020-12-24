package com.letKodeIt.statuses;

import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.*;

import java.util.concurrent.TimeUnit;

public class SpecificationWithTime {
	String consumerKey = "Ha84rBnYY8FBCxnp9fkhLe8c6";
	String consumerSecret = "WlXUfI3ycUEP7qIH3g8JXLIph98PiE7UVOIQYFty3kRNP8q3nH";
	String accessToken = "1339827572419383297-QM3MXOJHoBa05YQ8IzLkxO57OyZcn5";
	String accessSecret = "2hHvk8E9ZaXtQsfO1FFCF7LvLVtVF9VKElW6jx7hDmH62";
	String tweetId = "";
	
	RequestSpecBuilder requestBuilder;
	static RequestSpecification requestSpec;
	ResponseSpecBuilder responseBuilder;
	static ResponseSpecification responseSpec;

	@BeforeClass
	public void setup() {
		AuthenticationScheme authScheme = 
				RestAssured.oauth(consumerKey, consumerSecret, accessToken, accessSecret);
		requestBuilder = new RequestSpecBuilder();
		requestBuilder.setBaseUri("https://api.twitter.com");
		requestBuilder.setBasePath("/1.1/statuses");
		requestBuilder.addQueryParam("user_id", "Aditya54725088");
		requestBuilder.setAuth(authScheme);
		requestSpec = requestBuilder.build();
		
		responseBuilder = new ResponseSpecBuilder();
		responseBuilder.expectStatusCode(200);
		responseBuilder.expectResponseTime(lessThan(4L), TimeUnit.SECONDS);
		responseBuilder.expectBody("user.name", hasItem("Aditya"));
		responseSpec = responseBuilder.build();
	}

	@Test
	public void readTweets() {
		given()
			.spec(requestSpec)
		.when()
			.get("/user_timeline.json")
		.then()
			.spec(responseSpec)
			.body("user.screen_name", hasItem("Aditya54725088"));
	}
}