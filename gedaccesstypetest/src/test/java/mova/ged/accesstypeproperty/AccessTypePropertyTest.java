package mova.ged.accesstypeproperty;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * Créé par Mohicane le 19/02/2019.
 */
@RunWith(SpringRunner.class)
@DataJpaTest(showSql = false)
public class AccessTypePropertyTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void test1() {
        MyEntity myEntity = new MyEntity();

        Map<MyEntity.MyEnum, Integer> myEnums = new EnumMap<>(MyEntity.MyEnum.class);
        myEnums.put(MyEntity.MyEnum.A, 10);
        myEnums.put(MyEntity.MyEnum.B, 100);
        myEnums.put(MyEntity.MyEnum.E, 1000);

        myEnums = Collections.unmodifiableMap(myEnums);

        LoggerFactory.getLogger(getClass()).info("Initializing the entity");
        myEntity.setMyEnumsByField(myEnums);
        myEntity.setMyEnumsByProperty(myEnums);

        LoggerFactory.getLogger(getClass()).info("Verifying the entity before persist it");
        Assert.assertEquals(myEnums, myEntity.getMyEnumsByField());
        Assert.assertEquals(myEnums, myEntity.getMyEnumsByProperty());

        LoggerFactory.getLogger(getClass()).info("Persisting the entity");
        entityManager.persistAndFlush(myEntity);

        LoggerFactory.getLogger(getClass()).info("Verifying the entity after persist it");
        Assert.assertEquals(myEnums, myEntity.getMyEnumsByField());
        Assert.assertEquals(myEnums, myEntity.getMyEnumsByProperty());
    }

}
