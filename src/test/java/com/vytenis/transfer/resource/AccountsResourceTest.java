package com.vytenis.transfer.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;

@QuarkusTest
class AccountsResourceTest {

    @Test
    public void getAccounts_user1_twoAccountsReturned() {
        given().when()
                .get("/accounts/{id}", 1).then()
                .statusCode(200)
                .assertThat()
                .body("iban", hasItems("LT4645464564", "RB3151564887"));
    }

    @Test
    public void addAccount_addForUser1_newAccountReturned() {
        given().when()
                .body("{\n" +
                        "        \"balance\": {\n" +
                        "            \"reserved\": 1,\n" +
                        "            \"total\": 100\n" +
                        "        },\n" +
                        "        \"currency\": \"EUR\",\n" +
                        "        \"iban\": \"LT1234568779\",\n" +
                        "        \"status\": \"ACTIVE\",\n" +
                        "        \"user\": {\n" +
                        "            \"id\": 1\n" +
                        "        }\n" +
                        "    }")
                .contentType(MediaType.APPLICATION_JSON)
                .post("/accounts").then()
                .statusCode(200);
        given().when()
                .get("/accounts/{id}", 1).then()
                .statusCode(200)
                .assertThat()
                .body("iban", hasItems("LT4645464564", "RB3151564887", "LT1234568779"));
    }
}