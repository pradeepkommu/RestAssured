package apicalls;

import org.testng.annotations.Test;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.*;

import static io.restassured.RestAssured.*;

public class JiraAPIAutomation {
	
	String sessionID;
	String jiraID;

	@Test(priority=1)
	public void loginJira() {
		baseURI="http://localhost:8080";
		Response res =given().log().ifValidationFails().
				header("Content-Type","application/json").
				body("{ \"username\": \"pradeepkommu\", \"password\": \"Skcor@3659\" }").
				when().
				post("/rest/auth/1/session").
				then().assertThat().statusCode(200).
				extract().response();
		String r=res.asString();
		JsonPath jsonFormate=new JsonPath(r);
		sessionID=jsonFormate.get("session.value");
		System.out.println("Current Session ID "+sessionID);

	}
	@Test(priority=2)
	public void createIssue() {
		baseURI="http://localhost:8080";
		Response res =given().log().ifValidationFails().
				header("Content-Type","application/json").
				header("Cookie","JSESSIONID="+sessionID).
				body("{\n" + 
						"    \"fields\": {\n" + 
						"       \"project\":\n" + 
						"       {\n" + 
						"          \"key\": \"OMS\"	\n" + 
						"       },\n" + 
						"       \"summary\": \"This is my second defect\",\n" + 
						"       \"description\": \"Creating of an issue using project keys and issue type names using the REST API\",\n" + 
						"       \"issuetype\": {\n" + 
						"          \"name\": \"Bug\"\n" + 
						"       }\n" + 
						"   }\n" + 
						"}").
				when().
				post("/rest/api/2/issue/").
				then().assertThat().statusCode(201).
				extract().response();
		String r=res.asString();
		JsonPath jsonFormate=new JsonPath(r);
		jiraID=jsonFormate.get("id");
		String key=jsonFormate.get("key");
		System.out.println("Current ID & Key "+jiraID +"-"+key);

	}
	
	@Test(priority=3)
	public void addComment() {
		baseURI="http://localhost:8080";
		Response res =given().log().ifValidationFails().
				header("Content-Type","application/json").
				header("Cookie","JSESSIONID="+sessionID).
				body("{\n" + 
						"    \"body\": \"This is a new comment that only administrators can see.\",\n" + 
						"    \"visibility\": {\n" + 
						"        \"type\": \"role\",\n" + 
						"        \"value\": \"Administrators\"\n" + 
						"    }\n" + 
						"}").
				when().
				post("/rest/api/2/issue/"+jiraID+"/comment").
				then().assertThat().statusCode(201).
				extract().response();
		String r=res.asString();
		JsonPath jsonFormate=new JsonPath(r);
		jiraID=jsonFormate.get("id");
		System.out.println("Current ID "+jiraID);
	}
	@Test(priority=4)
	public void updateComment() {

	}
}
