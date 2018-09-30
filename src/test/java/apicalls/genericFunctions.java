package apicalls;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class genericFunctions {
	
	static String access_token;
	
	public static String GetBearerID() throws IOException {
		baseURI=getProperty("BaseURL_outh");
		Response res= given().log().uri().
				header("x-tenant","XX").
				header("Authorization","Basic bWVyY2F0b3I6").
				header("Content-Type","application/x-www-form-urlencoded").
				config(RestAssured.config()
						.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs("x-www-form-urlencoded",
										ContentType.URLENC)))
				.contentType("application/x-www-form-urlencoded; charset=UTF-8")
				.formParam("grant_type", "password")
				.formParam("username", "PRADEEP")
				.formParam("password", "PASSWORD7").
				when().post("/uaa/oauth/token").then().extract().response();
		String r=res.asString();
		JsonPath js= new JsonPath(r);
		access_token=js.get("access_token");
		//System.out.println(access_token);
		return access_token;
	}
	
	
	public static String getProperty(String propname) throws IOException {
		Properties prop=new Properties();
		FileInputStream fis=new FileInputStream("C:\\Users\\s00344\\git\\RestAssured\\src\\test\\java\\apicalls\\xxproject.properties");
		prop.load(fis);
		return prop.get(propname).toString();
	}
}

