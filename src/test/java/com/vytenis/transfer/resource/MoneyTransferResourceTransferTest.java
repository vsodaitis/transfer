package com.vytenis.transfer.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

@QuarkusTest
@Disabled
class MoneyTransferResourceTransferTest {

    @Test
    public void transfer_move10eur_10eurMoved() {
        given().when()
                .get("/accounts/{id}", 6).then()
                .statusCode(200)
                .body("balance.total", hasItems(500f));
        given().when()
                .get("/accounts/{id}", 1).then()
                .statusCode(200)
                .body("balance.total", hasItems(100f));
        transfer("LT3212131544", "LT4645464564");
        given().when()
                .get("/accounts/{id}", 6).then()
                .statusCode(200)
                .body("balance.total", hasItems(490f));
        given().when()
                .get("/accounts/{id}", 1).then()
                .statusCode(200)
                .body("balance.total", hasItems(110f));
    }

    private void transfer(String from, String to) {
        given().when()
                .body("{\n" +
                        "\t\"debtor\": \"" + from + "\",\n" +
                        "\t\"beneficiary\": \"" + to + "\",\n" +
                        "\t\"sum\": 10,\n" +
                        "\t\"currency\": \"EUR\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON)
                .post("/transfer").then()
                .statusCode(200);
    }
}