package org.scribe.examples;

import java.util.Scanner;

import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;



public class NsfocusExample
{
  private static final String PROTECTED_RESOURCE_URL = "http://127.0.0.1:8080/api/user/";
  //private static final String PROTECTED_RESOURCE_URL = "http://127.0.0.1:8080/api/user";
  private static final String API_KEY = "kA4vWNY6hW5HrDn8";
  private static final String API_SECRET = "8SvnWqqh3eKFn4nE";
  public static void main(String[] args)
  {
    OAuthService service = new ServiceBuilder()
                                .provider(NsfocusApi.class)
                                .callback("http://127.0.0.1")
                                .apiKey(API_KEY)
                                .apiSecret(API_SECRET)
                                .build();
    Scanner in = new Scanner(System.in);

    System.out.println("=== Nsfocus's OAuth Workflow ===");
    System.out.println();

    // Obtain the Request Token
    System.out.println("Fetching the Request Token...");
    Token requestToken = service.getRequestToken();
    System.out.println("Got the Request Token!");
    System.out.println();

    System.out.println("Now go and authorize Scribe here:");
    System.out.println(service.getAuthorizationUrl(requestToken));
    System.out.println("authorize?");
    System.out.print(">>");
    Verifier verifier = new Verifier(in.nextLine());
    System.out.println();
    System.out.println(verifier.getValue());
    // Trade the Request Token and Verfier for the Access Token
    System.out.println("Trading the Request Token for an Access Token...");
    Token accessToken = service.getAccessToken(requestToken,verifier);
    System.out.println("Got the Access Token!");
    System.out.println("(if your curious it looks like this: " + accessToken + " )");
    System.out.println();

    // Now let's go and ask for a protected resource!
    System.out.println("Now we're going to access a protected resource...");
    OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
    //request.addBodyParameter("domain", "www.baidu.com");
    //request.addQuerystringParameter("domain","www.baidu.com"); 
    
    service.signRequest(accessToken, request);
    Response response = request.send();
    System.out.println("Got it! Lets see what we found...");
    System.out.println();
    System.out.println(response.getBody());

    System.out.println();
    System.out.println("Thats it man! Go and build something awesome with WSP! :)");
  }

}