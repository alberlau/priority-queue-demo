package pqd.route;

import static pqd.route.Routes.PERSIST_SERVICE;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;
import pqd.bean.MyBean;
import pqd.repository.MyBeanRepository;

@Component
public class PersistRoute extends SpringRouteBuilder {

    private final MyBeanRepository myBeanRepository;

    public PersistRoute(MyBeanRepository myBeanRepository) {
        this.myBeanRepository = myBeanRepository;
    }

    @Override
    public void configure() {

        from(PERSIST_SERVICE.route())
            .process(exchange -> {
                MyBean bodyIn = (MyBean) exchange.getIn().getBody();
                bodyIn.setTimestamp(System.currentTimeMillis());
                myBeanRepository.save(bodyIn);
            });
    }
}
