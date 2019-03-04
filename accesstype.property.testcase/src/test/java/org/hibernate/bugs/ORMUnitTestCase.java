/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hibernate.bugs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class ORMUnitTestCase {

    private EntityManagerFactory entityManagerFactory;

    @Before
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
    }

    @After
    public void destroy() {
        entityManagerFactory.close();
    }

    // Add your tests, using standard JUnit.
    @Test
    public void hhh13289Test() throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();


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
        entityManager.persist(myEntity);
        entityManager.flush();

        LoggerFactory.getLogger(getClass()).info("Verifying the entity after persist it");
        Assert.assertEquals(myEnums, myEntity.getMyEnumsByField());
        Assert.assertEquals(myEnums, myEntity.getMyEnumsByProperty());

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}