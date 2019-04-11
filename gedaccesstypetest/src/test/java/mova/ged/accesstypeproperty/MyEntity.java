package mova.ged.accesstypeproperty;

import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

@Entity
public class MyEntity {

    public enum MyEnum { A, B, C, D, E }

    @Id
    @GeneratedValue
    private long id;

    @ElementCollection
    @MapKeyColumn(name = "my_enum", columnDefinition = "enum('A', 'B', 'C', 'D', 'E')")
    @MapKeyEnumerated(EnumType.STRING)
    private final Map<MyEnum, Integer> myEnumsByField = new EnumMap<>(MyEnum.class);

    private final Map<MyEnum, Integer> myEnumsByProperty = new EnumMap<>(MyEnum.class);

    public MyEntity() {}

    public long getId() {
        return id;
    }

    @Access(AccessType.PROPERTY)
    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "my_enum", columnDefinition = "enum('A', 'B', 'C', 'D', 'E')")
    @MapKeyEnumerated(EnumType.STRING)
    public Map<MyEnum, Integer> getMyEnumsByProperty() {
        return myEnumsByProperty;
    }

    public void setMyEnumsByProperty(final Map<MyEnum, Integer> myEnums) {
        LoggerFactory.getLogger(getClass()).info("Begining of the setter: " + myEnums.toString());

        // Doing this and using it insted of the one in arguments, it works
        // Map<MyEnum, Integer> temp = new EnumMap<>(myEnums);
        LoggerFactory.getLogger(getClass()).info("Are property and arg the same: " + (myEnums == this.myEnumsByProperty));

        this.myEnumsByProperty.clear();
        LoggerFactory.getLogger(getClass()).info("Clear is done: " + myEnums.toString());

        for (MyEnum myEnum : myEnums.keySet()) this.myEnumsByProperty.put(myEnum, myEnums.get(myEnum));
        LoggerFactory.getLogger(getClass()).info("Map is set: " + myEnums.toString());
    }

    public Map<MyEnum, Integer> getMyEnumsByField() {
        return myEnumsByField;
    }

    public void setMyEnumsByField(Map<MyEnum, Integer> myEnums) {
        LoggerFactory.getLogger(getClass()).info("Begining of the setter: " + myEnums.toString());

        this.myEnumsByField.clear();
        LoggerFactory.getLogger(getClass()).info("Clear is done: " + myEnums.toString());

        for (MyEnum myEnum : myEnums.keySet()) this.myEnumsByField.put(myEnum, myEnums.get(myEnum));
        LoggerFactory.getLogger(getClass()).info("Map is set: " + myEnums.toString());
    }
}
