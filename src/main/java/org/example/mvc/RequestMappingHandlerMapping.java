package org.example.mvc;

import org.example.mvc.controller.Controller;
import org.example.mvc.controller.HomeContorller;

import java.util.HashMap;
import java.util.Map;

public class RequestMappingHandlerMapping {
    //key는 URL path, value는 컨트롤러를 관리하는 맵
    private Map<String, Controller> mappings = new HashMap<>();

    void init(){
        mappings.put("/", new HomeContorller());
    }

    public Controller findHandler(String urlPath) {
        return mappings.get(urlPath);
    }
}
