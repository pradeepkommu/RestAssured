package apicalls;

import org.testng.annotations.Test;
import io.restassured.response.*;
import static io.restassured.RestAssured.*;
import java.io.IOException;

public class OO extends genericFunctions{
	
	
	@Test(priority=1)
		public void TransportLeg() throws IOException{
		baseURI=getProperty("BaseURL_service");
		Response res= given().log().ifValidationFails().
				header("x-tenant","XX").
				header("Authorization","Bearer "+GetBearerID()).
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
	
	
}
