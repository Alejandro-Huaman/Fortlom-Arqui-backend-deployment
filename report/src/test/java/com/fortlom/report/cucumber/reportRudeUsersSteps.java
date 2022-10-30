package com.fortlom.report.cucumber;
import com.fortlom.report.interfaces.dto.complaint.ComplaintResource;
import com.fortlom.report.interfaces.dto.complaint.CreateComplaintResource;
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
public class reportRudeUsersSteps {
    private RestTemplate restTemplate = new RestTemplate();
    private CreateComplaintResource createComplaintResource= new CreateComplaintResource();
    private String url = "http://localhost:8087";

    @Given("that the user is in the “Publication” section")
    public void that_the_user_is_in_the_publication_section() {
        url=url+"/api/v1/reportservice";
    }
    @Given("click on “the See Posts”")
    public void click_on_the_see_posts() {
        url=url+"/usersmains/";
    }
    @When("you notice there is a artist post you think is wrong.")
    public void you_notice_there_is_a_artist_post_you_think_is_wrong() {
        url=url+"1/usersreports/1/publications/2/complaints";
    }
    @When("press the fanatic “flag” button")
    public void press_the_fanatic_flag_button() {
        createComplaintResource.setDescription("prueba");
    }
    @When("write to report description")
    public void write_to_report_description() {
        createComplaintResource.setDescription("prueba");
    }
    @When("hit the “report” button")
    public void hit_the_report_button() {
        createComplaintResource.setDescription("prueba");
    }
    @Then("the fanatic report is created.")
    public void the_fanatic_report_is_created() {
        restTemplate.postForObject(url,createComplaintResource, ComplaintResource.class);
    }
    @Given("that the user is in the {string} section")
    public void that_the_user_is_in_the_section(String string) {
        url=url+"/api/v1/reportservice";
    }
    @Given("enter a fanatic forum")
    public void enter_a_fanatic_forum() {
        url=url+"/usersmains/";
    }
    @When("notice there is a fanatic forum that seems wrong to you.")
    public void notice_there_is_a_fanatic_forum_that_seems_wrong_to_you() {
        url=url+"1/usersreports/1/forums/1/complaints";
    }
    @When("you notice there is a fanatic comment that seems wrong to you.")
    public void you_notice_there_is_a_fanatic_comment_that_seems_wrong_to_you() {
        url=url+"1/usersreports/1/comments/1/complaints";
    }

}
