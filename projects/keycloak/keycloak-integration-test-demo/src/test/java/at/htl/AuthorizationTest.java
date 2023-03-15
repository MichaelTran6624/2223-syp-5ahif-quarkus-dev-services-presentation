package at.htl;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.keycloak.client.KeycloakTestClient;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class AuthorizationTest {

    KeycloakTestClient keycloakTestClient = new KeycloakTestClient();

    @Test
    public void adminAccessGranted_successful() {
        RestAssured.given().auth().oauth2(getAccessToken("alice"))
                .when().get("/api/admin")
                .then()
                .statusCode(200);
    }

    @Test
    public void adminAccessGranted_fail() {
        RestAssured
                .given()
                .auth()
                .oauth2(getAccessToken("bob"))
                .when()
                .get("/api/admin")
                .then()
                .statusCode(403);
    }

    @Test
    public void userAccessGranted_bob_success() {
        RestAssured
                .given()
                .auth()
                .oauth2(getAccessToken("bob"))
                .when()
                .get("/api/users/me")
                .then()
                .statusCode(200);
    }

    @Test
    public void userAccessGranted_alice_success() {
        RestAssured
                .given()
                .auth()
                .oauth2(getAccessToken("alice"))
                .when()
                .get("/api/users/me")
                .then()
                .statusCode(200);
    }

    protected String getAccessToken(String userName) {
        return keycloakTestClient.getAccessToken(userName);
    }
}
