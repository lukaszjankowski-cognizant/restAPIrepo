package demo;


import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

public class oAuthTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
	String [] webAutomationCourseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};
	
		String response = given()
		.formParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type","client_credentials")
		.formParam("scope","trust")
		.when().log().all()
		.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
		
	System.out.println(response);
	
	JsonPath js = new JsonPath(response);
	String accessToken = js.getString("access_token");
	System.out.println(accessToken);
	
		GetCourse gc = given()
		.queryParam("access_token", accessToken)
		.when().log().all()
		.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
		.as(GetCourse.class);
		
	System.out.println(gc.getLinkedIn());
	System.out.println(gc.getInstructor());
	
	List<Api> apiCourses = gc.getCourses().getApi();
			
	for (int i = 0; i<apiCourses.size(); i++) {
				if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"));
					System.out.println(apiCourses.get(i).getPrice());
	}
	
	
	//checking if list of webautomation (POINTLESS TEXT TO CHANGE ANYTHING) courses titles is exact as expected
	List<WebAutomation> webAutomationCourses = gc.getCourses().getWebAutomation();
	
	ArrayList<String> webAutoList = new ArrayList<String>();
	
	for (int i=0; i<webAutomationCourses.size(); i++) {
		webAutoList.add(webAutomationCourses.get(i).getCourseTitle());
		}
	
	System.out.println(webAutoList);
	System.out.println(Arrays.asList(webAutomationCourseTitles));
	
	List<String> expectedList = Arrays.asList(webAutomationCourseTitles);
	
	Assert.assertTrue(webAutoList.equals(expectedList));
	
	}
	
}
