package demo;

import static io.restassured.RestAssured.given;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import io.restassured.path.json.JsonPath;

public class oAuthShetty {


	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver", "C://chromedriver.exe");
		/*
		 * WebDriver driver = new ChromeDriver(); driver.get(
		 * "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyfjdss"
		 * ); driver.findElement(By.cssSelector("input[type='email']")).sendKeys(
		 * "u9696822991@gmail.com");
		 * driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER
		 * ); Thread.sleep(4000);
		 * driver.findElement(By.cssSelector("input[type='password']")).sendKeys(
		 * "Pass100!");
		 		driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
		 */
			String url = "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2F0AVGzR1CC9Y9ExkDZHjevUqhV08GdS06_IAKH2ZmSt1x6bRnXH_Tzsww1IwgWV3EoudNVjA&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent";
			String partialCode = url.split("code=")[1];
			String code = partialCode.split("&scope")[0];
			System.out.println(code);
			System.out.println(code);
			System.out.println(code);
			System.out.println(code);
			System.out.println(code);
			
			
			
			String tokenResponse = given()
				.urlEncodingEnabled(false)
				.contentType("application/x-www-form-urlencoded")
				.formParam("code",code)
				.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
				.formParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.formParam("grant_type","client_credentials")
				.when().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
				.asString();
		
		System.out.println(tokenResponse);
		JsonPath js = new JsonPath(tokenResponse);
		String accessToken = js.getString("access_token");
		System.out.println(accessToken);
		
		
		String response = given().queryParam("access_token", accessToken)
		.when()
		.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
		.asString();
		
		System.out.println(response);
	}

}
