package com.example.vishnuchelle.mydairy;

import android.os.AsyncTask;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

/**
 * Created by Vishnu Chelle on 5/4/2015.
 */
public class YelpAPI {

    private static final String API_HOST = "api.yelp.com";
    private static final String DEFAULT_TERM = "dinner";
    private static final String DEFAULT_LOCATION = "Kansas City, MO";
    private static final int SEARCH_LIMIT = 10;
    private static final String SEARCH_PATH = "/v2/search";
    private static final String BUSINESS_PATH = "/v2/business";

    public static final String CONSUMER_KEY = "I0r0gKANvKF12QpVjQqRWQ";
    public static final String CONSUMER_SECRET = "velRjle8lC6_TYNF83wKJzZqJIs";
    public static final String TOKEN = "yvRo5AYj6eaecpsFLQD7hmTtq--y_Lj4";
    public static final String TOKEN_SECRET = "msBCSm1EG_WyJAXKZfpOTzPwg7s";


    OAuthService service;
    Token accessToken;


    public YelpAPI(String consumerKey, String consumerSecret, String token, String tokenSecret) {
        this.service =
                new ServiceBuilder().provider(TwoStepOAuth.class).apiKey(consumerKey)
                        .apiSecret(consumerSecret).build();
        this.accessToken = new Token(token, tokenSecret);
    }

    public String searchForBusinessesByLocation(String term, String location) {
        OAuthRequest request = createOAuthRequest(SEARCH_PATH);
        request.addQuerystringParameter("term", term);
        request.addQuerystringParameter("location", location);
        request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));
//    System.out.println("I am printing =" + request);
        return sendRequestAndGetResponse(request);

    }

    private OAuthRequest createOAuthRequest(String path) {
        OAuthRequest request = new OAuthRequest(Verb.GET, "http://" + API_HOST + path);
        return request;
    }


    private String sendRequestAndGetResponse(OAuthRequest request) {
//    System.out.println("Querying " + request.getCompleteUrl() + " ...");
        this.service.signRequest(this.accessToken, request);
        Response response = request.send();
        return response.getBody();
    }

    public static String queryAPI(YelpAPI yelpApi) {
        String searchResponseJSON =
                yelpApi.searchForBusinessesByLocation(DEFAULT_TERM, DEFAULT_LOCATION);
//       System.out.println("Next printing = "+searchResponseJSON);
        return searchResponseJSON;

    }


}

