package com.vytenis.transfer.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;

@QuarkusTest
class UsersResourceTest {

    @Test
    public void getAllUsers_noArgs_twoDefaultUsersReturned() {
        given().when()
                .get("/users").then()
                .statusCode(200)
                .assertThat()
                .body("fullName", hasItems("Viktor Tsoy", "John Smith"));
    }

    @Test
    public void addUser_anotherUserAdded_userPersisted() {
        given().when()
                .body("{\"fullName\":\"Michael Jackson\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .post("/users").then()
                .statusCode(200);
        given().when()
                .get("/users").then()
                .statusCode(200)
                .assertThat()
                .body("fullName", hasItems("Viktor Tsoy", "John Smith", "Michael Jackson"));
    }
}