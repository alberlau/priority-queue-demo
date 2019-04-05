package pqd.route;

import static pqd.route.Headers.SEQNUM;
import static pqd.route.Routes.PREPROCESS;
import static pqd.route.Routes.RESEQUENCER;

import org.apache.camel.Message;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;
import pqd.bean.MyBean;

@Component
public class PreprocessorRoute extends SpringRouteBuilder {

    @Override
    public void configure() {
        from(PREPROCESS.route()).process(exchange -> {
            Message in = exchange.getIn();
            MyBean body = in.getBody(MyBean.class);
            in.setHeader(SEQNUM.header(), body.getPriority());
        }).to(RESEQUENCER.route());
    }
}
