package org.example.mvc;

import org.example.mvc.controller.Controller;
import org.example.mvc.controller.HandlerKey;
import org.example.mvc.controller.RequestMethod;
import org.example.mvc.view.JspViewResolver;
import org.example.mvc.view.View;
import org.example.mvc.view.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

//톰켓이 실행할수 있도록 HttpServlet을 상속받음
@WebServlet(value = "/") // 어떤 경로든지 DispatcherServlet이 실행되게 하기 위해서 설정하는 어노테이션
public class DispatcherServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private RequestMappingHandlerMapping requestMappingHandlerMapping;
    private List<ViewResolver> viewResolvers;

    @Override
    public void init() throws ServletException {
        requestMappingHandlerMapping = new RequestMappingHandlerMapping();
        requestMappingHandlerMapping.init();

        viewResolvers = Collections.singletonList(new JspViewResolver());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("[DispatcherServlet] service started.");
        try {
            Controller controller = requestMappingHandlerMapping.findHandler(new HandlerKey(RequestMethod.valueOf(req.getMethod()), req.getRequestURI()));
            String viewName = controller.handleRequest(req, resp);

            for(ViewResolver viewResolver : viewResolvers) {
                View view = viewResolver.resolveView(viewName);
                view.render(new HashMap<>(), req, resp);
            }

        } catch (Exception e) {
            logger.error("exception occured : [{}]", e.getMessage(), e);
            throw new ServletException();
        }
    }
}
