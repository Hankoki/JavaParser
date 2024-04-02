package com.github.fantac;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.github.fantac.parser.ParsedInfo;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;

/**
 * @program: JavaParser
 * @description: 抽取方法体
 * @author: FantaC
 * @create: 2024-03-31-16-45
 **/
public class MethodExtractor {
    private String filePath;
    private String methodName;
    private String sourcePath = "src/main/java";
    private String fullPath;
    private List<ParsedInfo> infoList= new ArrayList<>();


    public static void main(String[] args) throws IOException {
        MethodExtractor extractor = new MethodExtractor("com/github/fantac/MethodExtractor.java","main");
        extractor.methodBodyExtract();
    }

    public MethodExtractor(String filePath, String methodName) {
        this.filePath = filePath;
        this.methodName = methodName;
        this.fullPath = sourcePath + File.separator + filePath;
    }

    public void methodBodyExtract() throws IOException {
        //获取分析单元cu
        FileInputStream in = new FileInputStream(fullPath);
        CompilationUnit cu = StaticJavaParser.parse(in);
        //获取被分析的包名
        String packageName = cu.getPackageDeclaration().toString();
            for (ClassOrInterfaceDeclaration coid : cu.findAll(ClassOrInterfaceDeclaration.class)) {
                ParsedInfo parsedInfo = new ParsedInfo();
                //获取名字为main的方法的签名+方法体
                List<MethodDeclaration> targetMethod = coid.getMethodsByName("main");
                String method = targetMethod.get(0).toString();
                parsedInfo.setMethodBody(method);
//                System.out.println(method);
                //设置当前方法所在类的包声明
                parsedInfo.setDeclaredPackage(packageName);
                //设置当前类的类名
                String className = coid.getName().asString();
                parsedInfo.setClassName(className);
//                System.out.println("类名: " + className);
                // 遍历并打印每个成员变量及其默认值（如果有的话）
                for (FieldDeclaration field : coid.getFields()) {
                    //field转为字符串后以分号结尾，去掉分号
                    String fieldSignature = String.valueOf(field);
                    if (fieldSignature.endsWith(";")) {
                        fieldSignature = fieldSignature.substring(0,fieldSignature.length()-1);
                    }
                    System.out.println(fieldSignature);
                    //获取field中的variable
                    for (VariableDeclarator variable : field.getVariables()) {
                        //获取初始值，有初始值则值设为初始值，没有初始值设为"noDefaultValue"
                        Optional<Expression> initializer = variable.getInitializer();
                        if (initializer.isPresent()) {
                            Object defaultFieldValue = initializer.get();
                            parsedInfo.addDeclaredFieldMap(fieldSignature,defaultFieldValue);
                            System.out.println("，默认值: " + initializer.get());
                        } else {
                            parsedInfo.addDeclaredFieldMap(fieldSignature,"noDefaultValue");
                            System.out.println();  // 如果没有默认值，换行
                        }
                    }
                }
                this.infoList.add(parsedInfo);
            }
        System.out.println(infoList);
        }
    }


//    private class FieldVisitor extends VoidVisitorAdapter<Void> {
//        @Override
//        public void visit(FieldDeclaration n, Void arg) {
//            super.visit(n, arg); // 这一行是为了遵守良好的访问者模式实践
//            // 打印字段的详细信息，例如字段的类型和名称
//            System.out.println("Field Type: " + n.getElementType());
//            System.out.println("Field Name: " + n.getVariables().get(0).getName());
//            System.out.println("Field Type: " + n.getModifiers());
//            // 注意：一个字段声明可以声明多个变量，这里我们只取第一个作为示例
//        }
//    }

