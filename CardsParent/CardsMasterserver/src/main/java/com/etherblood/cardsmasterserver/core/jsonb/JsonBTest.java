package com.etherblood.cardsmasterserver.core.jsonb;

import com.etherblood.cardsmasterserver.core.AbstractRepository;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Philipp
 */
@Repository
public class JsonBTest extends AbstractRepository<TestJsonBEntity> {
    public static final QTestJsonBEntity entity = QTestJsonBEntity.testJsonBEntity;

    @Transactional
//    @PostConstruct
    public void run() {
        TestJsonBEntity testy = new TestJsonBEntity();
        testy.setBar(new HashSet<JSONBCard>(Arrays.asList(new JSONBCard("one"), new JSONBCard("one"), new JSONBCard(null), null)));
//        testy.setFoo(Arrays.asList("one", "two", "three", "hello", null));
        persist(testy);
        TestJsonBEntity singleResult = from(entity).where(entity.id.eq(testy.getId())).singleResult(entity);
        System.out.println(singleResult);
    }
}
