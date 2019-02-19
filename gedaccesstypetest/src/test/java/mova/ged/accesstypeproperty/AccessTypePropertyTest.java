package mova.ged.accesstypeproperty;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.EnumMap;
import java.util.Map;

/**
 * Créé par Mohicane le 19/02/2019.
 */
@RunWith(SpringRunner.class)
@DataJpaTest(showSql = false)
@EntityScan(basePackageClasses = MyEntity.class)
@ComponentScan(basePackageClasses = MyEntity.class)
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

        myEntity.setMyEnumsByProperty(myEnums);
        myEntity.setMyEnumsByField(myEnums);

        Assert.assertEquals(myEnums, myEntity.getMyEnumsByField());
        Assert.assertEquals(myEnums, myEntity.getMyEnumsByProperty());

        entityManager.persistAndFlush(myEntity);

        Assert.assertEquals(myEnums, myEntity.getMyEnumsByField());
        Assert.assertEquals(myEnums, myEntity.getMyEnumsByProperty());
    }

}
