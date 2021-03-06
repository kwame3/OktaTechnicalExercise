package com.okta.springbootwithauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.*;
import java.net.URI;
import java.lang.String;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriBuilder;

import org.json.JSONObject;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.context.annotation.Bean;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.Clients;
import com.okta.sdk.resource.user.User;
import com.okta.sdk.resource.user.UserBuilder;
import com.okta.sdk.client.ClientBuilder;
import com.okta.sdk.authc.credentials.TokenClientCredentials;

@Controller
class SimpleAppController {
    
    @RequestMapping("/")
    String home() {
        return "home";
    }

    @RequestMapping("/restricted")  
    String restricted() {  
        return "restricted";  
    }

    /*
    @Bean
   public RestTemplate getRestTemplate() {
      return new RestTemplate();
   }
*/
    final AdminService adminService;

    @Autowired
    SimpleAppController(AdminService adminService) {
        this.adminService = adminService;

	String url = "https://dev-199195.oktapreview.com/api/v1/users";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept","application/json");
        headers.set("Authorization", "SSWS mytokenvalue");

        Profile profile = new Profile();
        profile.setEmail("test@test.com");
        profile.setFirstName("First");
        profile.setLastName("Last");
        profile.setLogin("test@test.com");



        HttpEntity<String> requestEntity = new HttpEntity<String>("body", headers);
       //String result = restTemplate.postForObject(url, requestEntity, String.class);
	//System.out.println(result);
	//restTemplate.postForEntity(url, requestEntity, String.class);
        //ResponseEntity<String> uri = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

	//System.out.println(uri.getBody());
        //System.out.println("Result - status ("+ uri.getStatusCode() + ") has body: " + uri.hasBody());

/*String url = "https://dev-199195.oktapreview.com/api/v1/users";
        RestTemplate restTemplate = getRestTemplate();
		
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept","application/json");
        headers.set("Authorization", "SSWS mytokenvalue");
        
        OKTAUser oktaUser = new OKTAUser();
        Profile profile = new Profile();
        profile.setEmail("test@test.com");
        profile.setFirstName("First");
        profile.setLastName("Last");
        profile.setLogin("test@test.com");
        //profile.setMobilePhone("111-111-1111");
        Credentials credentials = new Credentials();
        Password password = new Password();
        password.setValue("abcdefghi");
        credentials.setPassword(password);
        oktaUser.setCredentials(credentials);
        oktaUser.setProfile(profile);

        JSONObject jsonObj = new JSONObject( oktaUser );

        HttpEntity<String> requestEntity = new HttpEntity<String>(jsonObj.toString(), headers);

        ResponseEntity<String> uri = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
	*/
	/*
	Profile createUser = new Profile("New", "User", "newUser@gmail.com", "newUser@gmail.com", "555-555-5555");
	RestTemplate restTemplate = new RestTemplate();

	
	String url = "https://dev-199195.oktapreview.com/api/v1/users";
			
    		//.queryParam("activate", "true");
	//java.net.URI url = builder.build();

	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
	headers.set("Accept","application/json");
	headers.set("Authorization", "SSWS mytokenvalue");

	
	 
	//OKTAUser oktaUser = new OKTAUser();

	JSONObject jsonObj = new JSONObject();
	
	//JSONObject jsonObj = new JSONObject( oktaUser );

	HttpEntity<String> requestEntity = new HttpEntity<String>(jsonObj.toString(), headers);
	  
	ResponseEntity<String> uri = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

	System.out.println(uri);
	//ResponseEntity<Map> response = restTemplate.postforEntity("https://dev-199195.oktapreview.com/api/v1/users?activate=false", createUser, Map.Class);
	
	//System.out.println(getResponse(response));
        //HTTPheaders header = new headers();
        //headers.set()
	*///

/*
	Client client = Clients.builder()
    .setOrgUrl("https://dev-199195.oktapreview.com")  // e.g. https://dev-123456.okta.com
    .setClientCredentials(new TokenClientCredentials("00xaTOVOnbcM3TqH_bvPn3wAOaa51kUANkj_8wEMlw"))
    .build();

	User user = UserBuilder.instance()
    .setEmail("joe1.coder@example.com")
    .setFirstName("Joe")
    .setLastName("Code")
    .buildAndCreate(client);
*/
	/*
	User user = UserBuilder.instance()
    .setEmail("joe.coder@example.com")
    .setFirstName("Joe")
    .setLastName("Code")
    .buildAndCreate(client);
*/
    }  

    @RequestMapping("/admin")
    String admin() {
        adminService.ensureAdmin();
        return "admin";
    }

    /*
    @RequestMapping("/createUser")
    String createUser() {
        adminService.ensureAdmin();
        return "createUser";
    }
     */

    @GetMapping("/createUser")
  public String createUserForm(Model model) {
    model.addAttribute("createUser", new Profile());
    return "createUser";
  }

  @PostMapping("/createUser")
  public String createUserSubmit(@ModelAttribute Profile createuser, Model model) {
    model.addAttribute("createUser", createuser);

    Client client = Clients.builder()
    .setOrgUrl("https://dev-199195.oktapreview.com")  // e.g. https://dev-123456.okta.com
    .setClientCredentials(new TokenClientCredentials("00xaTOVOnbcM3TqH_bvPn3wAOaa51kUANkj_8wEMlw"))
    .build();

        User user = UserBuilder.instance()
    .setEmail(createuser.getEmail())
    .setFirstName(createuser.getFirstName())
    .setLastName(createuser.getLastName())
    .buildAndCreate(client);

    return "result";
  }



    @GetMapping("/updateUser")
  public String updateUserForm(Model model) {
    model.addAttribute("updateUser", new Profile());
    return "updateUser";
  }

  @PostMapping("/updateUser")
  public String updateUserSubmit(@ModelAttribute Profile updateuser, Model model) {
    model.addAttribute("updateUser", updateuser);

    Client client = Clients.builder()
    .setOrgUrl("https://dev-199195.oktapreview.com")
    .setClientCredentials(new TokenClientCredentials("00xaTOVOnbcM3TqH_bvPn3wAOaa51kUANkj_8wEMlw"))
    .build();

    User user = client.getUser("joe1.coder@example.com");    

    user.getProfile().setFirstName(updateuser.getFirstName());
    user.getProfile().setLastName(updateuser.getLastName());
    user.getProfile().setEmail(updateuser.getEmail());
    user.getProfile().setMobilePhone(updateuser.getPhoneNumber());
    user.getProfile().setLogin(updateuser.getLogin());
    
    user.update();

    return "result1";
  }



    @GetMapping("/readUser")
  public String readUserForm(Model model) {
    model.addAttribute("readUser", new Profile());
    model.addAttribute("userEmail", new Profile());
    return "readUser";
  }

  @PostMapping("/readUser")
  public String readUserSubmit(@ModelAttribute Profile readuser, @ModelAttribute Profile useremail, Model model) {
    model.addAttribute("readUser", readuser);
    model.addAttribute("userEmail", useremail);

    Client client = Clients.builder()
    .setOrgUrl("https://dev-199195.oktapreview.com")
    .setClientCredentials(new TokenClientCredentials("00xaTOVOnbcM3TqH_bvPn3wAOaa51kUANkj_8wEMlw"))
    .build();

    User user = client.getUser(useremail.getEmail());

    readuser.setFirstName(user.getProfile().getFirstName());
    readuser.setLastName(user.getProfile().getLastName());
    readuser.setEmail(user.getProfile().getEmail());
    readuser.setPhoneNumber(user.getProfile().getMobilePhone());
    readuser.setLogin(user.getProfile().getLogin());


    user.update();

    return "result2";
  }

  @GetMapping("/deleteUser")
  public String deleteUserForm(Model model) {
    model.addAttribute("deleteUser", new Profile());
    model.addAttribute("userEmail", new Profile());
    return "deleteUser";
  }

  @PostMapping("/deleteUser")
  public String deleteUserSubmit(@ModelAttribute Profile deleteuser, @ModelAttribute Profile useremail, Model model) {
    model.addAttribute("deleteUser", deleteuser);
    model.addAttribute("userEmail", useremail);

    Client client = Clients.builder()
    .setOrgUrl("https://dev-199195.oktapreview.com")
    .setClientCredentials(new TokenClientCredentials("00xaTOVOnbcM3TqH_bvPn3wAOaa51kUANkj_8wEMlw"))
    .build();

    User user = client.getUser(useremail.getEmail());

    deleteuser.setFirstName(user.getProfile().getFirstName());
    deleteuser.setLastName(user.getProfile().getLastName());
    deleteuser.setEmail(user.getProfile().getEmail());
    deleteuser.setPhoneNumber(user.getProfile().getMobilePhone());
    deleteuser.setLogin(user.getProfile().getLogin());

    user.deactivate();
    user.delete();

    return "result3";
  }


}
