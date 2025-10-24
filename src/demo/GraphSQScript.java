package demo;

import static io.restassured.RestAssured.*;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;

public class GraphSQScript {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String QueryResponse = given().log().all().header("Content-Type","application/json")
		.body("{\"query\":\"query($characterId : Int!, $episodeId : Int!)\\n{\\n  character(characterId: $characterId) {\\n    name\\n    type\\n    gender\\n  }\\n  location(locationId: 1000) {\\n    name\\n    dimension\\n  }\\n  episode(episodeId: $episodeId) {\\n    name\\n    air_date\\n    episode\\n  }\\n  characters(filters: {name: \\\"Krishna Kamakshi Brahma\\\"}) {\\n    info {\\n      count\\n    }\\n    result {\\n      name\\n      type\\n    }\\n  }\\n  episodes(filters: {episode: \\\"Hulu\\\"}) {\\n    result {\\n      id\\n      name\\n      air_date\\n      episode\\n    }\\n  }\\n}\\n\",\"variables\":{\"characterId\":1000,\"episodeId\":1000}}")
		.when()
		.post("https://rahulshettyacademy.com/gq/graphql")
		.then()
		.extract()
		.response()
		.asString();
		
		System.out.println(QueryResponse);
		
		JsonPath js = new JsonPath(QueryResponse);
		
		String characterName = js.getString("data.character.name");
		
		Assert.assertEquals(characterName,  "Krishna Kamakshi Brahma");
		
		
		//Mutations
		String characterNameNeu = "Alfred Batman";
		String mutationResponse = given().log().all().header("Content-Type","application/json")
				.body("{\"query\":\"mutation ($locationName: String!, $characterName:String!,$episodeName:String!)\\n{\\n  createLocation(location: {name: $locationName, type: \\\"Zentral Europe\\\", dimension: \\\"2888\\\"}) {\\n    id\\n  }\\n  createCharacter(character: {name: $characterName, type: \\\"Macho\\\", status: \\\"dead\\\", species: \\\"fantasy\\\", gender: \\\"male\\\", image: \\\".png\\\", originId: 1000, locationId: 1000}) {\\n    id\\n  }\\n  createEpisode(episode: {name: $episodeName, air_date:\\\"2020\\\", episode:\\\"Netflix\\\"}) {\\n    id\\n  }\\n  deleteLocations(locationIds:[25246, 25236])\\n  {\\n    locationsDeleted\\n  }\\n}\\n\",\"variables\":{\"locationName\":\"US&A\",\"characterName\":\""+characterNameNeu+"\",\"episodeName\":\"Fluggaenkoecchicebolsen \"}}")
				.when()
				.post("https://rahulshettyacademy.com/gq/graphql")
				.then()
				.extract()
				.response()
				.asString();
				
				System.out.println(mutationResponse);
				
				JsonPath js1 = new JsonPath(mutationResponse);
				
				System.out.println(js1.getString(mutationResponse));
				
				
	}

}
