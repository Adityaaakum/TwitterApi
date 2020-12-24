package com.letKodeIt.statuses;

import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.letKodeIt.common.Utilities;
import com.letKodeIt.constants.EndPoints;
import com.letKodeIt.constants.Path;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class TwitterEndToEndWorkflowFrameWork {

	RequestSpecification reqspec;
	ResponseSpecification respec;
	String tweetId;

	@BeforeClass
	public void setup() {
		
		reqspec= Utilities.getRequestSpecififcation();
		reqspec.basePath(Path.STATUSES);
		
		respec= Utilities.getResponseSpecification();
		
		
	}

	@Test
	public void postTweet() {
		Response response =
			given()
				
				
				.spec(Utilities.createQueryParam(reqspec,"status", "My First tweet"))
			.when()
				.post(EndPoints.STATUSES_TWEET_POST)
			.then()
               .spec(respec)			
				.extract()
				.response();
		
		tweetId = response.path("id_str");
		System.out.println("The response.path: " + tweetId);
	}
	
	@Test(dependsOnMethods={"postTweet"})
	public void readTweet() {
		Response response =
			given()
				
				.spec(Utilities.createQueryParam(reqspec,"id", tweetId))
			.when()
				.get(EndPoints.STATUSES_TWEET_READ_SINGLE)
			.then()
				.extract()
				.response();
		
		String text = response.path("text");
		System.out.println("The tweet text is: " + text);
	}

	@Test(dependsOnMethods={"readTweet"})
	public void deleteTweet() {
		
		Utilities.setEndPoint(EndPoints.STATUSES_TWEET_DESTROY);
		Utilities.getResponse(Utilities.createPathParam(reqspec,"id", tweetId),"post");
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}