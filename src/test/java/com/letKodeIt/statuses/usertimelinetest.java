package com.letKodeIt.statuses;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;

import org.testng.annotations.Test;

import com.letKodeIt.common.Utilities;
import com.letKodeIt.constants.EndPoints;
import com.letKodeIt.constants.Path;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;

public class usertimelinetest {
  
	RequestSpecification reqspec;
	ResponseSpecification respec;
	
	@Test
  public void userTimeLine() {
		
		given()
		.spec(reqspec)
		
		.get(EndPoints.STATUSES_USER_TIMELINE)
		 .then()
		 .spec(respec)
		.log().all();
		
		
  }
	@Test
	public void userTimeLine2()
	{
		Utilities.setEndPoint(EndPoints.STATUSES_USER_TIMELINE);
		
		Response response = Utilities.getResponse(reqspec, "get");
		
		    ArrayList<String> str  =response.path("user.screen_name");
		    
		    
		
		
		
		
	}
  @BeforeClass
  public void beforeClass() {
	  
	  reqspec= Utilities.getRequestSpecififcation();
	  reqspec.basePath(Path.STATUSES);
	  reqspec.queryParam("user_id", "1339827572419383297");
	  
	  respec= Utilities.getResponseSpecification();
	  
	  
  }

}
