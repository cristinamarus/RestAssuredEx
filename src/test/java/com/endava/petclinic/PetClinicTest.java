package com.endava.petclinic;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PetClinicTest {
    @Test
    public void getOwnerById(){
        given().baseUri("http://api.petclinic.mywire.org")
                .basePath("/petclinic")
                .port(80)
                .pathParam("ownerId", 1)
                .when().log().all()
                .get("/api/owners/{ownerId}")
                .prettyPeek() //pt afisare in consola
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", is( 1))
                .body("firstName", is("George"))
                .body("firstName", containsString("org"))
                .body("lastName", startsWith("Fr"))
                .body("city", equalToIgnoringCase("MadiSon"))
                .body("telephone", hasLength(10));
    }

    @Test
    public void postOwnersTest(){
        ValidatableResponse response = given().baseUri("http://api.petclinic.mywire.org")
                .port(80)
                .basePath("/petclinic")
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"address\": \"Str Sergent\",\n" +
                        "  \"city\": \"Bucharest\",\n" +
                        "  \"firstName\": \"Cristina\",\n" +
                        "  \"id\": null,\n" +
                        "  \"lastName\": \"Ungureanu\",\n" +
                        "  \"telephone\" : \"0214598763\"\n" +
                        "}")
                .when().log().all()
                .post("/api/owners")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        Integer ownerId = response.extract().jsonPath().getInt("id");

        given().baseUri("http://api.petclinic.mywire.org")
                .basePath("/petclinic")
                .port(80)
                .pathParam("ownerId", ownerId)
                .when()
                .get("/api/owners/{ownerId}")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", is(ownerId));


    }

    //homework

    @Test
    public void postAddPet(){
        ValidatableResponse response = given().baseUri("http://api.petclinic.mywire.org")
                .port(80)
                .basePath("/petclinic")
                .contentType(ContentType.JSON)
                .body(" {\n" +
                        "  \"birthDate\": \"2022/08/01\",\n" +
                        "  \"id\": null,\n" +
                        "  \"name\": \"Memo\",\n" +
                        "  \"owner\": {\n" +
                        "    \"address\": \"Micesti\",\n" +
                        "    \"city\": \"Pitesti\",\n" +
                        "    \"firstName\": \"Cristina\",\n" +
                        "    \"id\": 101,\n" +
                        "    \"lastName\": \"Ungureanu\",\n" +
                        "    \"pets\": [\n" +
                        "      null\n" +
                        "    ],\n" +
                        "    \"telephone\": \"0758963247\"\n" +
                        "  },\n" +
                        "  \"type\": {\n" +
                        "    \"id\": 29,\n" +
                        "    \"name\": \"Dog\"\n" +
                        "  },\n" +
                        "  \"visits\": [\n" +
                        "    {\n" +
                        "      \"date\": \"yyyy/MM/dd\",\n" +
                        "      \"description\": \"string\",\n" +
                        "      \"id\": 0\n" +
                        "    }\n" +
                        "  ]\n" +
                        "} ")
                .when().log().all()
                .post("/api/pets")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        Integer petId = response.extract().jsonPath().getInt("id");

        given().baseUri("http://api.petclinic.mywire.org")
                .basePath("/petclinic")
                .port(80)
                .pathParam("petId", petId)
                .when()
                .get("/api/pets{petId}")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", is(petId));

    }


}
