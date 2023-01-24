package com.example.gen;

import java.util.Collections;
import java.util.List;

public class ClassSpecification {
    private String name;
    private String packageName;
    private List<FieldSpecification> fieldSpecifications;

    public ClassSpecification(String className, List<FieldSpecification> fieldSpecifications) {
        this.name = className;
        this.fieldSpecifications = fieldSpecifications;
    }

    public String getName() {
        return name;
    }

    public List<FieldSpecification> getFieldSpecifications() {
        return Collections.unmodifiableList(fieldSpecifications);
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }
}