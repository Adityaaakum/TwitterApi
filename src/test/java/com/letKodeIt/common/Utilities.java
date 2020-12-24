package com.letKodeIt.common;

import static org.hamcrest.Matchers.lessThan;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.letKodeIt.constants.Auth;
import static io.restassured.RestAssured.given;
import com.letKodeIt.constants.Path;

import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Utilities {

	public static String END_POINT;

	public static RequestSpecBuilder SPEC_BUILDER;

	public static RequestSpecification REQUEST_SPEC;

	public static ResponseSpecBuilder RESPONSE_BUILDER;

	public static ResponseSpecification RESPONSE_SPEC;

	public static void setEndPoint(String endpont) {
		END_POINT = endpont;
	}

	public static RequestSpecification getRequestSpecififcation() {
		AuthenticationScheme scheme = RestAssured.oauth(Auth.CONSUMER_KEY, Auth.CONSUMER_SECRET, Auth.ACCESS_TOKEN,
				Auth.ACCESS_SECRET);

		SPEC_BUILDER = new RequestSpecBuilder();
		SPEC_BUILDER.setBaseUri(Path.BASE_URI);

		SPEC_BUILDER.setAuth(scheme);
		REQUEST_SPEC = SPEC_BUILDER.build();

		return REQUEST_SPEC;

	}

	public static ResponseSpecification getResponseSpecification() {
		RESPONSE_BUILDER = new ResponseSpecBuilder();
		RESPONSE_BUILDER.expectStatusCode(200);
		RESPONSE_BUILDER.expectResponseTime(lessThan(6L), TimeUnit.SECONDS);
		RESPONSE_SPEC = RESPONSE_BUILDER.build();
		return RESPONSE_SPEC;

	}

	public static RequestSpecification createQueryParam(RequestSpecification rspec, String key, String value) {
		return rspec.queryParam(key, value);
	}

	public static RequestSpecification createQueryParam(RequestSpecification rspec, Map<String, String> map) {
		return rspec.queryParams(map);
	}

	public static RequestSpecification createPathParam(RequestSpecification rspec, String key, String value) {
		return rspec.pathParam(key, value);
	}

	public static Response getResponse() {

		return given().get(END_POINT);
	}

	public static Response getResponse(RequestSpecification rspec, String type) {

		REQUEST_SPEC.spec(rspec);
		Response response = null;
		if (type.equalsIgnoreCase("get")) {
			response = given().spec(REQUEST_SPEC).when().get(END_POINT);
		} else if (type.equalsIgnoreCase("post")) {

			response = given().spec(REQUEST_SPEC).when().post(END_POINT);

		}

		else
			System.out.println("Type is not supported");

		response.then().spec(RESPONSE_SPEC).log().all();
		return response;
	}

	public static JsonPath getJasonPath(Response res) {
		String jsonPath = res.asString();
		return new JsonPath(jsonPath);
	}

	public static XmlPath getXmlPath(Response res) {
		String xmlPath = res.asString();
		return new XmlPath(xmlPath);
	}

	public static void resetBasePath() {
		RestAssured.basePath = null;
	}

	public static void setContentType(ContentType type) {
		given().contentType(type);
	}
}
