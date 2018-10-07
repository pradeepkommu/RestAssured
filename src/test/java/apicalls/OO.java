package apicalls;

import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.*;
import static io.restassured.RestAssured.*;
import java.io.IOException;

public class OO extends genericFunctions{

	String token;
	ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extent.html");
	ExtentReports extent = new ExtentReports();


	@BeforeSuite
	public void getToken() throws IOException {
		token=GetBearerID();
		extent.attachReporter(htmlReporter);
	}

	@Test(priority=1,enabled=false)
	public void TransportLeg() throws IOException{
		baseURI=getProperty("BaseURL_service");
		ExtentTest test = extent.createTest("TransportLeg", "To List all the Flights Between DXB-BOM");
		test.log(Status.INFO,"Bearer Token Passed:"+token);
		test.log(Status.INFO,"Base URL:"+baseURI+"/services/transport/details");

		Response res;
		try {
			res= given().log().ifValidationFails().
					header("x-tenant","XX").
					header("Authorization","Bearer "+token).
					param("boardPoint", "DXB").
					param("offPoint", "BOM").
					param("date", "1534310936000").
					when().get("services/transport/details").then().assertThat().statusCode(200).extract().response();
			test.info("Response from the Server : "+res.headers());
			test.info("Response from the Server : "+res.asString());
			test.log(Status.PASS, "Asserion passed");

		}
		catch(java.lang.AssertionError e) {
			test.log(Status.FAIL, "Assertion failed (Response,"+e+")");
		}
		extent.flush();
	}

	@Test(priority=2,enabled=false)
	public void ListCitiesinCountry() throws IOException{
		baseURI=getProperty("MDM_URL");
		ExtentTest test = extent.createTest("ListCitiesinCountry", "Sample description");
		test.info("Request URL: "+baseURI+getProperty("ListCitiesinCountry_service")+"/AE");

		try {
			Response res= given().log().uri().
					header("x-tenant","XX").
					header("Authorization","Bearer "+token).
					when().get(getProperty("ListCitiesinCountry_service")+"/AE").then().assertThat().statusCode(200).extract().response();
			test.info("Response from the Server : "+res.asString());
			test.log(Status.PASS, "Asserion passed");
			System.out.println(res.asString());
		}
		catch(java.lang.AssertionError e) {
			test.log(Status.FAIL, "Assertion failed (Response,"+e+")");
		}
		extent.flush();
	}

	@Test(priority=3,enabled=false)
	public void Carrier() throws IOException{
		baseURI=getProperty("MDM_URL");
		ExtentTest test = extent.createTest("Carrier", "Details of Carrier");
		test.info("Request URL: "+baseURI+getProperty("Carrier_service"));
		try {
			Response res= given().log().ifValidationFails().
					header("Authorization","Bearer "+token).
					when().get(getProperty("Carrier_service")).then().assertThat().statusCode(200).extract().response();
			test.info("Response from the Server : "+res.asString());
			test.log(Status.PASS, "Asserion passed");
			System.out.println(res.asString());
		}
		catch(java.lang.AssertionError e) {
			test.log(Status.FAIL, "Assertion failed (Response,"+e+")");
			System.out.println(e);
		}
		extent.flush();
	}


	@Test(priority=4)
	public void offerSearch() throws IOException{
		baseURI="http://10.4.11.108:8093";
			Response res= given().log().all().
					header("Authorization","Bearer "+token).
					header("x-tenant","XX").
					header("Content-Type","application/json").
					body("{ \"useAllotment\": false, \"commodity\": { \"commodityCode\": \"9203\" }, \"shcs\": [], \"cargoType\": \"F\", \"sortType\": 1, \"scheduleParams\": { \"flights\": [], \"bodyTypes\": [], \"departureDate\": 1530043200000, \"origin\": { \"code\": \"DXB\" }, \"destination\": { \"code\": \"BOM\" }, \"flightExactMatch\": false }, \"spaceParams\": { \"weight\": { \"unit\": { \"code\": \"K\" }, \"value\": 5 }, \"volume\": { \"unit\": { \"code\": \"CM\" }, \"value\": 0.029999 }, \"pieces\": \"5\" }, \"priceParams\": { \"productCode\": \"GCR\", \"mop\": \"PP\", \"declaredValue\": { \"amount\": 0, \"currency\": \"AED\" } }, \"document\": { \"documentType\": \"AWB\", \"documentPrefix\": \"888\", \"documentNumber\": \"UNK\" }, \"goodsDescription\": \"TOYS. GAMES\", \"pageInfo\": { \"pageNumber\": 1, \"pageSize\": \"15\" } }").
					when().post("/services/cargo/offers").then().assertThat().statusCode(200).extract().response();
			
			System.out.println(res.asString());
		
	}



}
