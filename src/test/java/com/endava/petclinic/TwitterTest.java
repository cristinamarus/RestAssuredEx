package com.endava.petclinic;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;


public class TwitterTest {

    String ApiKey = "xDpvjjIfui8Su72Ti1AR1CKzi";
    String SecretKey = "HobgaN3k3jn0vk11ItpV1CSWhyzpIm0Nf2FXbF01wZmDNxmyxl";
    String Token = "278136668-zJNcE6VzUubCamfnS29EEw1S0HyRUDufFl42AbUk";
    String TokenSecret = "2Q5WhderaUy2Tk8tL7n400L4ddoXm6STWtasc6yKpM3Wf";
    String Tweetid, id1;

    @Test
    public void postTwitterMessageTest() {

        Response resp = given().baseUri("https://api.twitter.com/1.1/statuses")
                .auth().oauth(ApiKey, SecretKey, Token, TokenSecret)
                .queryParam("status", "Cristina Ungureanu").log().all()
                .when()
                .post("/update.json")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK).log().all()
                .extract().response();

        String CreateTwitt = resp.asString();
        JsonPath js = new JsonPath(CreateTwitt);

        Tweetid=(js.get("id")).toString();


    }

}
