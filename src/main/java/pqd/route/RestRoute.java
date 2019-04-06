package pqd.route;

import static pqd.route.Routes.PREPROCESS;

import org.apache.camel.Exchange;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.http.HttpStatus;
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
            .route()
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpStatus.ACCEPTED.value()))
            .to(PREPROCESS.route());
    }
}
