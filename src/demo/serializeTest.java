package demo;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;


public class serializeTest {

	public static void main(String[] args) {
RestAssured.baseURI="https://rahulshettyacademy.com";

AddPlace jsonBody = new AddPlace();
jsonBody.setAccuracy(50);

Location location1 = new Location();
location1.setLat(-38.383494);
location1.setLng(33.427362);
jsonBody.setLocation(location1);
jsonBody.setName("Backstrasseleinn house");
jsonBody.setPhone_number("(+91) 983 893 3937");
jsonBody.setAddress("29, side layout, cohen 09");

List<String> types1 = new ArrayList<>();
types1.add("shoe park");
types1.add("shop");
jsonBody.setTypes(types1);
jsonBody.setWebsite("http://google.com");
jsonBody.setLanguage("French-IN");
;

RequestSpecification reqSpec = new RequestSpecBuilder()
	.setBaseUri("https://rahulshettyacademy.com")
	.setContentType(ContentType.JSON)
	.addQueryParam("key","qaclick123")
	.build();

ResponseSpecification resSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();


RequestSpecification res = given().spec(reqSpec)
.body(jsonBody);


Response response = res.when()
.post("/maps/api/place/add/json")
.then().log().all().spec(resSpec).body("status",equalTo("OK")).extract().response();

String responseString = response.asString();
System.out.println(responseString);
}

}
