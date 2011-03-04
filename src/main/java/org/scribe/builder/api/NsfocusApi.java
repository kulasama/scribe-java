package org.scribe.builder.api;

import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.extractors.RequestTokenExtractor;
import org.scribe.extractors.NsfocusTokenExtractorImpl;
import org.scribe.extractors.TokenExtractorImpl;
import org.scribe.model.OAuthConfig;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuth10aServiceImpl;
import org.scribe.oauth.OAuthService;
import org.scribe.oauth.NsfocusServiceImpl;

public class NsfocusApi extends DefaultApi10a
{

  //private static final String HOST = "http://wsp.nsfocus.net";
  private static final String HOST = "http://127.0.0.1:8080";
  private static final String AUTHORIZE_URL = HOST + "/oauth/authorize?oauth_token=%s";
  
  @Override
  public String getAccessTokenEndpoint()
  {
    return HOST + "/oauth/access_token/";
  }

  @Override
  public String getRequestTokenEndpoint()
  {
    return HOST + "/oauth/request_token/";
  }
  
  @Override
  public String getAuthorizationUrl(Token requestToken)
  {
    return String.format(AUTHORIZE_URL, requestToken.getToken());
  }
  
  @Override
  public Verb getRequestTokenVerb()
  {
    return Verb.GET;
  }
  
  
  @Override
  public Verb getAccessTokenVerb()
  {
    return Verb.GET;
  }
  
  /**
   * Returns the request token extractor.
   * 
   * @return request token extractor
   */
  @Override
  public RequestTokenExtractor getRequestTokenExtractor()
  {
    return new NsfocusTokenExtractorImpl();
  }
 
  @Override
  public AccessTokenExtractor getAccessTokenExtractor()
  {
    return new NsfocusTokenExtractorImpl();
  }
  
  @Override
  public OAuthService createService(OAuthConfig config, String scope)
  {
    OAuthService service = new NsfocusServiceImpl(this, config);
    service.addScope(scope);
    return service;
  }
}
