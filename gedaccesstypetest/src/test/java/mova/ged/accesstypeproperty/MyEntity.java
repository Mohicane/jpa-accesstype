package mova.ged.accesstypeproperty;

import javax.persistence.*;
import java.util.EnumMap;
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
    @ElementCollection
    @MapKeyColumn(name = "my_enum", columnDefinition = "enum('A', 'B', 'C', 'D', 'E')")
    @MapKeyEnumerated(EnumType.STRING)
    public Map<MyEnum, Integer> getMyEnumsByProperty() {
        return myEnumsByProperty;
    }

    public void setMyEnumsByProperty(Map<MyEnum, Integer> myEnumsByProperty) {
//        this.myEnumsByProperty = new EnumMap<>(myEnumsByProperty);

        this.myEnumsByProperty.clear();
        for (MyEnum myEnum : myEnumsByProperty.keySet()) this.myEnumsByProperty.put(myEnum, myEnumsByProperty.get(myEnum));
    }

    public Map<MyEnum, Integer> getMyEnumsByField() {
        return myEnumsByField;
    }

    public void setMyEnumsByField(Map<MyEnum, Integer> myEnumsByField) {
//        this.myEnumsByField = new EnumMap<>(myEnumsByField);

        this.myEnumsByField.clear();
        for (MyEnum myEnum : myEnumsByField.keySet()) this.myEnumsByField.put(myEnum, myEnumsByField.get(myEnum));
    }
}
