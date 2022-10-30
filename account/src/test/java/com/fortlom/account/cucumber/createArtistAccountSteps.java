package com.fortlom.account.cucumber;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortlom.account.domain.UserAggregate.entity.childentity.Artist;
import com.fortlom.account.interfaces.dto.authetication.LoginUser;
import com.fortlom.account.interfaces.dto.authetication.NewArtist;
import com.fortlom.account.interfaces.dto.tag.TagResource;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.log4j.Log4j2;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.data.domain.Page;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class createArtistAccountSteps {
    private RestTemplate restTemplate = new RestTemplate();
    String email=UUID.randomUUID().toString().replace("-", "").substring(0,20)+"@gmail.com";
    String password=UUID.randomUUID().toString().replace("-", "").substring(0,20);
    String Username=UUID.randomUUID().toString().replace("-", "").substring(0,20);
    String name=UUID.randomUUID().toString().replace("-", "").substring(0,20);
    String lastname=UUID.randomUUID().toString().replace("-", "").substring(0,20);
     LoginUser loginUser=new LoginUser();
    private String url = "http://localhost:8081";
    NewArtist newArtist= new NewArtist();

    @Given("that the artist enters the platform")
    public void that_the_artist_enters_the_platform() {
        url=url+"/auth";
    }
    @When("press “Register yourself as an Artist”")
    public void press_register_yourself_as_an_artist() {
        url=url+"/artist";
    }
    @When("complete the form with your data")
    public void complete_the_form_with_your_data() {
        newArtist.setEmail(email);
        newArtist.setLastname(lastname);
        newArtist.setPassword(password);
        newArtist.setUsername(Username);
        newArtist.setRealname(name);
    }
    @When("hit the “Register as Artist” button")
    public void hit_the_register_as_artist_button() {
        restTemplate.postForEntity(url,newArtist,NewArtist.class);
    }
    @Then("your account was successfully created")
    public void your_account_was_successfully_created() {
        Artist artist= restTemplate.getForObject("http://localhost:8081/api/v1/userservice/artists/username/"+Username,Artist.class);
        assertEquals(artist.getUsername(), Username);
    }
    @When("incorrectly complete the form")
    public void incorrectly_complete_the_form() {
        newArtist.setEmail("kas@gmail.com");
        newArtist.setLastname(lastname);
        newArtist.setPassword(password);
        newArtist.setUsername("carlosartista");
        newArtist.setRealname(name);
    }
    @Then("will display a message that your account was not created")
    public void will_display_a_message_that_your_account_was_not_created() {
        Artist artist= restTemplate.getForObject("http://localhost:8081/api/v1/userservice/artists/username/"+"carlosartista",Artist.class);
        assertEquals(artist.getUsername(), "carlosartista");
    }
    @Given("already has an account created on the platform")
    public void already_has_an_account_created_on_the_platform() {
        url=url+"/login";
    }
    @When("complete your corresponding data in the form")
    public void complete_your_corresponding_data_in_the_form() {
      loginUser.setNombreUsuario("carlosartista");
      loginUser.setPassword("nueva");
    }
    @When("hit the “Enter” button")
    public void hit_the_enter_button() {
        restTemplate.postForEntity(url,loginUser,LoginUser.class);
    }
    @Then("will enter the platform with the account created")
    public void will_enter_the_platform_with_the_account_created() {
        Artist artist= restTemplate.getForObject("http://localhost:8081/api/v1/userservice/artists/username/"+"carlosartista",Artist.class);
        assertEquals(artist.getUsername(), "carlosartista");
    }

}
