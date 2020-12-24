package com.letKodeIt.statuses;

import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TwitterEndToEndWorkflow {

	String consumerKey = "Ha84rBnYY8FBCxnp9fkhLe8c6";
	String consumerSecret = "WlXUfI3ycUEP7qIH3g8JXLIph98PiE7UVOIQYFty3kRNP8q3nH";
	String accessToken = "1339827572419383297-QM3MXOJHoBa05YQ8IzLkxO57OyZcn5";
	String accessSecret = "2hHvk8E9ZaXtQsfO1FFCF7LvLVtVF9VKElW6jx7hDmH62";
	String tweetId = "";

	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://api.twitter.com";
		RestAssured.basePath = "/1.1/statuses";
	}

	@Test
	public void postTweet() {
		Response response =
			given()
				.auth()
				.oauth(consumerKey, consumerSecret, accessToken, accessSecret)
				.queryParam("status", "My First Tweet")
			.when()
				.post("/update.json")
			.then()
				.statusCode(200)
				.extract()
				.response();
		
		tweetId = response.path("id_str");
		System.out.println("The response.path: " + tweetId);
	}
	
	@Test(dependsOnMethods={"postTweet"})
	public void readTweet() {
		Response response =
			given()
				.auth()
				.oauth(consumerKey, consumerSecret, accessToken, accessSecret)
				.queryParam("id", tweetId)
			.when()
				.get("/show.json")
			.then()
				.extract()
				.response();
		
		String text = response.path("text");
		System.out.println("The tweet text is: " + text);
	}

	@Test(dependsOnMethods={"readTweet"})
	public void deleteTweet() {
			given()
				.auth()
				.oauth(consumerKey, consumerSecret, accessToken, accessSecret)
				.pathParam("id", tweetId)
			.when()
				.post("/destroy/{id}.json")
			.then()
				.statusCode(200);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}