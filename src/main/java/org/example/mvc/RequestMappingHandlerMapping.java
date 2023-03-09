package org.example.mvc;

import org.example.mvc.controller.*;

import java.util.HashMap;
import java.util.Map;

public class RequestMappingHandlerMapping {
    //key는 URL path, value는 컨트롤러를 관리하는 맵
    private Map<HandlerKey, Controller> mappings = new HashMap<>();

    void init(){
        mappings.put(new HandlerKey(RequestMethod.GET, "/"), new HomeContorller());
        mappings.put(new HandlerKey(RequestMethod.GET, "/users"), new UserListController());
        mappings.put(new HandlerKey(RequestMethod.POST, "/users"), new UserCreateController());
        mappings.put(new HandlerKey(RequestMethod.GET, "/user/form"), new ForwardController("/user/form"));

    }

    public Controller findHandler(HandlerKey handlerKey) {
        return mappings.get(handlerKey);
    }
}
