package org.scribe.examples;

import java.util.Scanner;

import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;



public class NsfocusExample
{
  //private static final String PROTECTED_RESOURCE_URL = "http://wsp.nsfocus.net/api/add/";
  private static final String PROTECTED_RESOURCE_URL = "http://wsp.nsfocus.net/api/malcheck/y181494.51host.net/?call_back_url=http://202.10.71.174/nsrs/service/nsfocus/callback ";
  private static final String API_KEY = "8ZsfMPhENQENz8E2";
  private static final String API_SECRET = "pNg22vwR/0/djF6B";
  public static void main(String[] args)
  {
    OAuthService service = new ServiceBuilder()
                                .provider(NsfocusApi.class)
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
    in.nextLine();
    //Verifier verifier = new Verifier(in.nextLine());
    System.out.println();

    // Trade the Request Token and Verfier for the Access Token
    System.out.println("Trading the Request Token for an Access Token...");
    Token accessToken = service.getAccessToken(requestToken,null);
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