package com.example.waynejackson.okhttpexample.endpoint;

/**
 * Created by wayne.jackson on 6/22/16.
 */
public class EndpointUrlProvider {

    private static final String REQUEST_URL = "http://jsonplaceholder.typicode.com/posts/1";

    public static EndPointUrl getDefaultEndPointUrl() {
        return new EndPointUrl(REQUEST_URL);
    }
}
