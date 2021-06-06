package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import org.junit.Assert;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class GetPostSteps {
    String country;
    String latitude;
    String longitude;
    String Result;
    String station_name;
    String station_latitude;
    String station_longitude;
    String station_id;
    String station_freeBikes;

    @Given("I am sending a json request")
    public void iAmSendingAJSONRequest() {
        given().contentType(ContentType.JSON);
    }

    @When("I perform GET operation for id {string}")
    public void iPerformGETOperationFor(String test_id) {
       Response response = when().get(String.format("http://api.citybik.es/v2/networks/%s", test_id));
       JsonPath jsonPathEvaluator = response.jsonPath();
       JsonPath.config = new JsonPathConfig(JsonPathConfig.NumberReturnType.BIG_DECIMAL);
       country = jsonPathEvaluator.get("network.location.country");
       latitude = jsonPathEvaluator.get("network.location.latitude").toString();
       longitude = jsonPathEvaluator.get("network.location.longitude").toString();
       station_id = jsonPathEvaluator.get("network.stations[35].id");
       station_name = jsonPathEvaluator.get("network.stations[35].name");
       station_latitude = jsonPathEvaluator.get("network.stations[35].latitude").toString();
       station_longitude= jsonPathEvaluator.get("network.stations[35].longitude").toString();
       station_freeBikes = jsonPathEvaluator.get("network.stations[35].free_bikes").toString();
    }

    @Then("The country code returned will be {string}")
    public void theCountryCodeReturnedWillBe(String country_code) {
        //assert country.equals(country_code);
        Assert.assertEquals(country_code, country);
    }

    @And("The correct latitude {string} and longitude {string} have been returned")
    public void theCorrectLatitudeAndLongitudeHaveBeenReturned(String test_latitude, String test_longitude) {
        Assert.assertEquals(test_latitude, latitude);
        Assert.assertEquals(test_longitude, longitude);
    }

    @When("I perform GET operation for fields {string} and {string}")
    public void iPerformGETOperationForFields(String test_id, String test_name) {
        Response response = when().get(String.format("http://api.citybik.es/v2/networks?fields=%s,%s", test_id, test_name));
        JsonPath jsonPathEvaluator = response.jsonPath();
        Result = jsonPathEvaluator.get("networks").toString();
    }

    @Then("Results will only return the id and name fields")
    public void resultsWillOnlyReturnTheIdAndNameFields() {
        //System.out.println(Result);
        Assert.assertTrue(Result.contains("id"));
        Assert.assertTrue(Result.contains("name"));
        Assert.assertFalse(Result.contains("company"));
        Assert.assertFalse(Result.contains("href"));
        Assert.assertFalse(Result.contains("location"));
        Assert.assertFalse(Result.contains("country"));
        Assert.assertFalse(Result.contains("latitude"));
        Assert.assertFalse(Result.contains("longitude"));
    }

    @Then("A stations ID {string} and name {string} is returned")
    public void aStationsIDAndNameIsReturned(String test_id, String test_name) {
        Assert.assertEquals(test_id, station_id);
        Assert.assertEquals(test_name, station_name);
    }

    @And("The correct number of free bikes {string}, latitude {string} and longitude {string} have been returned")
    public void theCorrectNumberOfFreeBikesLatitudeAndLongitudeHaveBeenReturned
            (String test_freeBikes, String test_latitude, String test_longitude) {
        Assert.assertEquals(test_freeBikes, station_freeBikes);
        Assert.assertEquals(test_latitude, station_latitude);
        Assert.assertEquals(test_longitude, station_longitude);
    }
}

