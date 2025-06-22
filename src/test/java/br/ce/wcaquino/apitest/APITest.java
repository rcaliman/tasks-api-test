package br.ce.wcaquino.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }
    
    @Test
    public void deveRetornarTarefas() {
        RestAssured.given()
            .when()
                .get("http://localhost:8001/tasks-backend/todo")
            .then()
                .statusCode(200)
            ;

    }

    @Test
    public void deveAdicionarTarefaComSucesso() {
        RestAssured.given()
            .body("{ \"task\": \"teste via api\", \"dueDate\": \"2025-12-30\" }")
            .contentType(ContentType.JSON)
        .when()
            .post("/todo")
        .then()
            .log().all()
            .statusCode(201)
        ;
    }

    @Test
     public void deveAdicionarTarefaInvalida() {
         RestAssured.given()
             .body("{ \"task\": \"teste via api\", \"dueDate\": \"2022-12-30\" }")
             .contentType(ContentType.JSON)
         .when()
             .post("/todo")
         .then()
             .log().all()
             .statusCode(400)
             .body("message", CoreMatchers.is("Due date must not be in past"));   
    }
}
