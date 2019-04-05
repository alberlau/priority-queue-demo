package pqd.route;

import static pqd.route.Headers.SEQNUM;
import static pqd.route.Routes.PERSIST_SERVICE;
import static pqd.route.Routes.RESEQUENCER;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ResequencerRoute extends SpringRouteBuilder {

    private final int batchSize;
    private final int timeout;

    public ResequencerRoute(@Value("${pdq.batchSize}") int batchSize,
        @Value("${pdq.timeout}") int timeout) {

        this.batchSize = batchSize;
        this.timeout = timeout;
    }

    @Override
    public void configure() {
        from(RESEQUENCER.route()).resequence(header(SEQNUM.header())).batch()
            .allowDuplicates().size(batchSize).timeout(timeout)
            .to(PERSIST_SERVICE.route());
    }
}
