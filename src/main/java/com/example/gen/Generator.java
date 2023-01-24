package com.example.gen;

import java.io.File;
import java.util.List;

public class Generator {

    public static void main(String[] args) throws Exception {

        // Get the YAML file's handle, and the directory it's contained in
        // (generated files will be placed there)

//        final URL resource = Generator.class.getClassLoader().getResource("specification.yaml");

//        final File yamlFile = new File("specification.yaml");
        String yaml = "User:\n" +
                "name: Name\n" +
                "age: Integer\n" +
                "\n" +
                "Name:\n" +
                "firstName: String\n" +
                "lastName: String";
        final File outputDirectory = new File(args[0]);
        System.out.println(outputDirectory.getAbsolutePath());


        // Step 1: Read in the YAML file, into class specifications
        YamlClassSpecificationReader yamlReader = new YamlClassSpecificationReader();
        List<ClassSpecification> classSpecifications = yamlReader.read(yaml);

        classSpecifications.forEach(o -> o.setPackageName("com.pointware.message"));

        // Step 2: Generate Java source files from the class specifications
        JavaDataClassGenerator javaDataClassGenerator = new JavaDataClassGenerator();
        javaDataClassGenerator.generateJavaSourceFiles(classSpecifications, outputDirectory);

        System.out.println("Successfully generated files to: " + outputDirectory.getAbsolutePath());
    }
}
