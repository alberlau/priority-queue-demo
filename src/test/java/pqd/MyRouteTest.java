package pqd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import pqd.bean.MyBean;
import pqd.repository.MyBeanRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MyRouteTest {

    private final int ITEMS_COUNT = 20;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MyBeanRepository myBeanRepository;

    @Test
    public void test() throws JAXBException {
        submitData(ITEMS_COUNT);

        List<MyBean> myBeans = myBeanRepository.findAll(new Sort(Direction.ASC, "timestamp"));

        assertEquals(ITEMS_COUNT, myBeans.size());

        assertSavedByPriority(myBeans);
    }

    private void assertSavedByPriority(List<MyBean> myBeans) {
        int lastPriority = 0;
        for (int i = 0; i < myBeans.size(); i++) {
            MyBean myBean = myBeans.get(i);
            assertTrue("lastPriority should be last or equal to current priority", lastPriority <= myBean.getPriority());
            lastPriority = myBean.getPriority();
        }
    }

    private void submitData(int count) throws JAXBException {

        JAXBContext jaxb = JAXBContext.newInstance(MyBean.class);
        Marshaller marshaller = jaxb.createMarshaller();

        for (int value = 0; value < count; value++) {
            MyBean myBean = createMyBean();
            String xml = convertToXml(marshaller, myBean);
            ResponseEntity<String> response = restTemplate.postForEntity("/camel/api/bean", xml, String.class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }

    private String convertToXml(Marshaller marshaller, MyBean myBean) throws JAXBException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marshaller.marshal(myBean, outputStream);
        return new String(outputStream.toByteArray());
    }

    private MyBean createMyBean() {
        MyBean myBean = new MyBean();
        myBean.setPriority(RandomUtils.nextInt(0, 9));
        return myBean;
    }
}
