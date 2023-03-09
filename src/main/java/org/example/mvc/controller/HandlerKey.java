package org.example.mvc.controller;

import java.util.Objects;

public class HandlerKey {

    private RequestMethod requestMethod;
    private String uriPath;
    public HandlerKey(RequestMethod method, String path) {
        this.requestMethod = method;
        this.uriPath = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HandlerKey that = (HandlerKey) o;
        return requestMethod == that.requestMethod && Objects.equals(uriPath, that.uriPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestMethod, uriPath);
    }
}
