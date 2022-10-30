package com.fortlom.account.cucumber;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortlom.account.domain.UserAggregate.entity.Tag;
import com.fortlom.account.domain.UserAggregate.entity.childentity.Artist;
import com.fortlom.account.interfaces.dto.tag.CreateTagResource;
import com.fortlom.account.interfaces.dto.tag.TagResource;
import com.fortlom.account.interfaces.dto.useraccoount.UpdatePersonResource;
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
import org.springframework.data.domain.Page;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class updateProfileSteps {
    private RestTemplate restTemplate = new RestTemplate();

    private ObjectMapper mapper = new ObjectMapper();
    private String url = "http://localhost:8081/api/v1";
    CreateTagResource createTagResource=new CreateTagResource();

    @Given("enter the “Configure” section")
    public void enter_the_configure_section() {
        url=url+"/userservice/";
        //http://localhost:8081/api/v1/userservice/users/changeprofile/{{userId}}
        //http://localhost:8081/api/v1/userservice/artists/artist/{{artistId}}/newtag
    }

    @When("fill in the form data correctly")
    public void fill_in_the_form_data_correctly() {
        url=url+"users/changeprofile/1";

    }
    @When("click on the “Change Profile” button")
    public void click_on_the_change_profile_button() {
        UpdatePersonResource updatePersonResource=new UpdatePersonResource();
        updatePersonResource.setRealname("alejo");
        updatePersonResource.setLastname("texmp");
        updatePersonResource.setEmail("kas@gmail.com");
        restTemplate.put(url,updatePersonResource);
    }
    @Then("will be able to see your change in your profile name.")
    public void will_be_able_to_see_your_change_in_your_profile_name() {
        Artist artist= restTemplate.getForObject("http://localhost:8081/api/v1/userservice/artists/1",Artist.class);
        assertEquals(artist.getRealname(), "alejo");
    }
    @When("you click on the “Add” button")
    public void you_click_on_the_add_button() {
        url=url+"artists/artist/1/newtag";

        createTagResource.setName("cola");

    }
    @Then("your art will be defined by a specific tag")
    public void your_art_will_be_defined_by_a_specific_tag() {
        restTemplate.postForObject(url,createTagResource, TagResource.class);
    }

}
