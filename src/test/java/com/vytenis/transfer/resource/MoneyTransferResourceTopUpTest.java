package com.vytenis.transfer.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

@QuarkusTest
class MoneyTransferResourceTopUpTest {

    @Test
    public void topUp_totalWas500_totalIs510() {
        given().when()
                .get("/accounts/{id}", 6).then()
                .statusCode(200)
                .body("balance.total", hasItems(500f));
        topUp("LT3212131544");
        given().when()
                .get("/accounts/{id}", 6).then()
                .statusCode(200)
                .body("balance.total", hasItems(510f));
    }

    @Test
    public void topUp4threads_totalWas100_totalIs140() throws InterruptedException {
        given().when()
                .get("/accounts/{id}", 1).then()
                .statusCode(200)
                .body("balance.total", hasItems(100f));
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        List<Callable<Void>> callables = Stream.generate(this::topUpCallable)
                .limit(4)
                .collect(Collectors.toList());
        executorService.invokeAll(callables);
        given().when()
                .get("/accounts/{id}", 1).then()
                .statusCode(200)
                .body("balance.total", hasItems(140f));
    }

    private Callable<Void> topUpCallable() {
        return () -> {
            topUp("LT4645464564");
            return (Void) null;
        };
    }

    private void topUp(String iban) {
        given().when()
                .body("{\n" +
                        "\t\"beneficiary\": \"" + iban + "\",\n" +
                        "\t\"sum\": 10,\n" +
                        "\t\"currency\": \"EUR\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON)
                .post("/transfer/topup").then()
                .statusCode(200);
    }
}