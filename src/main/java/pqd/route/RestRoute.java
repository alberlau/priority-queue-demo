package pqd.route;

import static pqd.route.Routes.PREPROCESS;

import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;
import pqd.bean.MyBean;

@Component
public class RestRoute extends SpringRouteBuilder {

    @Override
    public void configure() {
        rest("/api/")
            .consumes("application/xml")
            .post("/bean")
            .bindingMode(RestBindingMode.xml)
            .type(MyBean.class)
            .to(PREPROCESS.route());
    }
}
