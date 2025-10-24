package demo;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.OrderDetail;
import pojo.Orders;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

public class ECommerceAPITest {
	
		public static void main(String[] args) {
			
			RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
			
			LoginRequest loginRequest = new LoginRequest();
			
			loginRequest.setUserEmail("lukasz.jankowski@cognizant.com");
			loginRequest.setUserPassword("Pass100!");
			
			RequestSpecification reqLogin = given().log().all().spec(req).body(loginRequest);
			
			LoginResponse loginResponse = reqLogin.when().post("api/ecom/auth/login").then().extract().response().as(LoginResponse.class);
			
			System.out.println(loginResponse.getUserId());
			String userId = loginResponse.getUserId();
			System.out.println(loginResponse.getToken());
			String token = loginResponse.getToken();
			
			//Add product
			
			RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
					.addHeader("Authorization", token)
					.build();
			
			RequestSpecification reqAddProduct = given().log().all().spec(addProductBaseReq)
			.param("productName", "laptop")
			.param("productAddedBy",userId)
			.param("productCategory", "fashion")
			.param("productSubCategory", "shirts")
			.param("productPrice", 1990)
			.param("productDescription", "Blowing out battery excluded.")
			.param("productFor", "men")
			.multiPart("productImage", new File("C://Users//2373058//Downloads//laptop.png"));
			
			String addProductResponse = reqAddProduct.when().post("/api/ecom/product/add-product")
			.then()
			.log().all()
			.extract()
			.response().asString();
			
			JsonPath js = new JsonPath(addProductResponse);
			String productId = js.getString("productId");
			System.out.println(productId);
			
			
			//Create order
			
			RequestSpecification createOrderBaseReq = new RequestSpecBuilder()
					.setBaseUri("https://rahulshettyacademy.com")
					.setContentType(ContentType.JSON)
					.addHeader("Authorization", token)
					.build();
			
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setCountry("Uzbekistan");
			orderDetail.setProductOrderedId(productId);
			
			List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
			orderDetailList.add(orderDetail);
			
			Orders orders = new Orders();
			orders.setOrders(orderDetailList); 
			
			RequestSpecification  reqCreateOrder = given().log().all()
					.spec(createOrderBaseReq)
					.body(orders);
			
			String createOrderResponse = reqCreateOrder
					.when()
					.post("/api/ecom/order/create-order")
					.then().log().all()
					.extract().response().asString();
			System.out.println(createOrderResponse);
			
			JsonPath jsorder = new JsonPath(createOrderResponse);
			String orderId = jsorder.getString("orders[0]");
			System.out.println(orderId);
		
			//Delete Product
		
			RequestSpecification deleteProductBaseReq = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token)
				.build();
			
			RequestSpecification reqDeleteProduct = given().log().all()
					.spec(deleteProductBaseReq)
					.pathParam("productId", productId);
			
			String deleteProductResponse = reqDeleteProduct
					.when()
					.delete("/api/ecom/product/delete-product/{productId}")
					.then()
					.log().all()
					.extract().response().asString();
			
			JsonPath js1 = new JsonPath(deleteProductResponse);
			System.out.println(js1.getString("message"));
			
			Assert.assertEquals("Product Deleted Successfully", js1.getString("message"));
			
			//Delete Order
			
			RequestSpecification deleteOrderBaseReq = new RequestSpecBuilder()
					.setBaseUri("https://rahulshettyacademy.com")
					.addHeader("Authorization", token)
					.build();
			
			RequestSpecification reqDeleteOrder = given().log().all()
					.spec(deleteOrderBaseReq)
					.pathParam("orderId", orderId);
			
			String deleteOrderResponse = reqDeleteOrder
					.when()
					.delete("/api/ecom/order/delete-order/{orderId}")
					.then()
					.log().all()
					.extract().response()
					.asString();
			
			System.out.println(deleteOrderResponse);
			
			
			
		}
}
			
			
