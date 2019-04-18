package com.vytenis.transfer.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

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
                .param("fullName", "Michael Jackson")
                .post("/users");
        given().when()
                .get("/users").then()
                .statusCode(200)
                .assertThat()
                .body("fullName", hasItems("Viktor Tsoy", "John Smith", "Michael Jackson"));
    }
}