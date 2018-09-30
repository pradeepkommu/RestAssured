package apicalls;

import org.testng.annotations.*;
import io.restassured.response.*;
import static io.restassured.RestAssured.*;
import java.io.IOException;

public class OO extends genericFunctions{

	String token;

	@BeforeSuite
	public void getToken() throws IOException {
		token=GetBearerID();
	}

	@Test(priority=1)
	public void TransportLeg() throws IOException{
		baseURI=getProperty("BaseURL_service");
		Response res= given().log().uri().
				header("x-tenant","XX").
				header("Authorization","Bearer "+token).
				param("boardPoint", "DXB").
				param("offPoint", "BOM").
				param("date", "1534310936000").
				when().get("services/transport/details").then().assertThat().statusCode(200).extract().response();
		String r=res.asString();
		System.out.println(r);
		/*JsonPath js= new JsonPath(r);
		refresh_token=js.get("refresh_token");
		System.out.println(refresh_token);*/
	}
	
	@Test(priority=2)
	public void ListCitiesinCountry() throws IOException{
		baseURI=getProperty("MDM_URL");
		Response res= given().log().uri().
				header("x-tenant","XX").
				header("Authorization","Bearer "+token).
				when().get(getProperty("ListCitiesinCountry_service")+"/AE").then().assertThat().statusCode(200).extract().response();
		String r=res.asString();
		System.out.println(r);
		/*JsonPath js= new JsonPath(r);
	refresh_token=js.get("refresh_token");
	System.out.println(refresh_token);*/
	}


}
