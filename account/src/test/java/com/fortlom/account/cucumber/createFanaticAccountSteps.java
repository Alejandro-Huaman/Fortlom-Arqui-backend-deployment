package com.fortlom.account.cucumber;
import com.fortlom.account.domain.UserAggregate.entity.childentity.Artist;
import com.fortlom.account.domain.UserAggregate.entity.childentity.Fanatic;
import com.fortlom.account.interfaces.dto.authetication.LoginUser;
import com.fortlom.account.interfaces.dto.authetication.NewArtist;
import com.fortlom.account.interfaces.dto.authetication.NewFanatic;
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
public class createFanaticAccountSteps {
    private RestTemplate restTemplate = new RestTemplate();
    String email=UUID.randomUUID().toString().replace("-", "").substring(0,20)+"@gmail.com";
    String password=UUID.randomUUID().toString().replace("-", "").substring(0,20);
    String Username=UUID.randomUUID().toString().replace("-", "").substring(0,20);
    String name=UUID.randomUUID().toString().replace("-", "").substring(0,20);
    String lastname=UUID.randomUUID().toString().replace("-", "").substring(0,20);
    LoginUser loginUser=new LoginUser();
    private String url = "http://localhost:8081";
    NewFanatic newArtist= new NewFanatic();
    @Given("the fan to enter the platform")
    public void the_fan_to_enter_the_platform() {
        url=url+"/auth";
    }
    @When("press “Register yourself as a fan”")
    public void press_register_yourself_as_a_fan() {
        url=url+"/fanatic";
    }
    @When("complete the form with your fanatic data")
    public void complete_the_form_with_your_fanatic_data() {
        newArtist.setEmail(email);
        newArtist.setLastname(lastname);
        newArtist.setPassword(password);
        newArtist.setUsername(Username);
        newArtist.setRealname(name);
        newArtist.setFanaticalias("qwe");
    }
    @When("hit the “Register as Fanatic” button")
    public void hit_the_register_as_fanatic_button() {
        restTemplate.postForEntity(url,newArtist,NewFanatic.class);

    }
    @Then("your fanatic account was successfully created")
    public void your_fanatic_account_was_successfully_created() {
        Fanatic fanatic= restTemplate.getForObject("http://localhost:8081/api/v1/userservice/fanatics/username/"+Username, Fanatic.class);
        assertEquals(fanatic.getUsername(), Username);
    }
    @When("incorrectly complete the fanatic form")
    public void incorrectly_complete_the_fanatic_form() {
        newArtist.setEmail(email);
        newArtist.setLastname(lastname);
        newArtist.setPassword(password);
        newArtist.setUsername(Username);
        newArtist.setRealname(name);
        newArtist.setFanaticalias("qwe");
    }

    @Then("will display a message that your fanatic account was not created")
    public void will_display_a_message_that_your_fanatic_account_was_not_created() {
        Fanatic fanatic= restTemplate.getForObject("http://localhost:8081/api/v1/userservice/fanatics/username/"+Username, Fanatic.class);
        assertNotEquals(fanatic.getUsername(), Username+"2");
    }

    @Given("already has an fanatic account created on the platform")
    public void already_has_an_fanatic_account_created_on_the_platform() {
        url=url+"/login";
    }

    @When("complete your corresponding fanatic data in the form")
    public void complete_your_corresponding_fanatic_data_in_the_form() {
        loginUser.setNombreUsuario("fanticmaximo");
        loginUser.setPassword("nueva");
    }

    @When("fanatic hit the “Enter” button")
    public void fanatic_hit_the_enter_button() {
        restTemplate.postForEntity(url,loginUser,LoginUser.class);
    }

    @Then("will enter the platform with the fanatic account created")
    public void will_enter_the_platform_with_the_fanatic_account_created() {
        Fanatic fanatic= restTemplate.getForObject("http://localhost:8081/api/v1/userservice/fanatics/username/"+"fanticmaximo", Fanatic.class);
        assertNotEquals(fanatic.getUsername(), "fanticmaximo");
    }


}
