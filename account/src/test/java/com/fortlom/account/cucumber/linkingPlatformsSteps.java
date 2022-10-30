package com.fortlom.account.cucumber;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortlom.account.domain.UserAggregate.entity.childentity.Artist;
import com.fortlom.account.interfaces.dto.artist.UpdateArtistResource;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.log4j.Log4j2;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

public class linkingPlatformsSteps {

    private RestTemplate restTemplate = new RestTemplate();

    private ObjectMapper mapper = new ObjectMapper();
    private String url = "http://localhost:8081/api/v1";

    @Given("the artist is in the configuration section")
    public void the_artist_is_in_the_configuration_section() {
        url=url+"/userservice/artists/artist/";
        //http://localhost:8081/api/v1/userservice/artists/artist/{{artistId}}/TwitterAccount
    }
    @When("press the option to link with Facebook")
    public void press_the_option_to_link_with_facebook() {
        url=url+"1/FacebookAccount";
    }
    @When("put your link to your facebook page")
    public void put_your_link_to_your_facebook_page() throws URISyntaxException {
        UpdateArtistResource updateArtistResource = new UpdateArtistResource();
        updateArtistResource.setFacebookLink("https://www.facebook.com/groups/947448799452393/");
        restTemplate.put(url,updateArtistResource);

    }

    @When("press the option to link with Instagram")
    public void press_the_option_to_link_with_instagram() {
        url=url+"1/InstagramAccount";
    }

    @When("put your link to your instagram page")
    public void put_your_link_to_your_instagram_page() {
        UpdateArtistResource updateArtistResource = new UpdateArtistResource();
        updateArtistResource.setInstagramLink("https://www.instagram.com/contentoverwatch/");
        restTemplate.put(url,updateArtistResource);
    }
    @When("opress the option to link with twitter")
    public void opress_the_option_to_link_with_twitter() {
        url=url+"1/TwitterAccount";
    }

    @When("put your link to your twitter page")
    public void put_your_link_to_your_twitter_page() {
        UpdateArtistResource updateArtistResource = new UpdateArtistResource();
        updateArtistResource.setTwitterLink("https://twitter.com/auronplay");
        restTemplate.put(url,updateArtistResource);
    }
    @Then("established the facebook link with the application")
    public void established_the_facebook_link_with_the_application() {
        Artist artist= restTemplate.getForObject("http://localhost:8081/api/v1/userservice/artists/1",Artist.class);
        assertEquals(artist.getFacebookLink(), "https://www.facebook.com/groups/947448799452393/");
    }

    @Then("established the instagram link with the application")
    public void established_the_instagram_link_with_the_application() {
        Artist artist= restTemplate.getForObject("http://localhost:8081/api/v1/userservice/artists/1",Artist.class);
        assertEquals(artist.getInstagramLink(), "https://www.instagram.com/contentoverwatch/");
    }

    @Then("established the twitter link with the application")
    public void established_the_twitter_link_with_the_application() {
        Artist artist= restTemplate.getForObject("http://localhost:8081/api/v1/userservice/artists/1",Artist.class);
        assertEquals(artist.getTwitterLink(), "https://twitter.com/auronplay");
    }


}
