package pqd.repository;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pqd.bean.MyBean;

@Repository
public interface MyBeanRepository extends CrudRepository<MyBean, String> {
    List<MyBean> findAll(Sort sort);
}
