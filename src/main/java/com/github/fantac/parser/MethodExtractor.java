package com.github.fantac.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


import com.github.fantac.utils.DataProcessUtil;
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
    private String className;
    private String methodName;
    private String sourcePath = "src/main/java";
    private String fullPath;
    private String fileExtension=".java";
//    private List<ParsedInfo> infoList= new ArrayList<>();


//    public static void main(String[] args) throws IOException {
//        MethodExtractor extractor = new MethodExtractor("com/github/fantac/MethodExtractor.java","main");
//        extractor.methodBodyExtract();
//    }

    public MethodExtractor(String filePath,String className, String methodName) {
        this.filePath = filePath;
        this.className = className;
        this.methodName = methodName;
        this.fullPath = sourcePath + File.separator + this.filePath+File.separator + this.className+fileExtension;
    }

    public ParsedInfo infoExtract() throws IOException {
        //获取分析单元cu
        FileInputStream in = new FileInputStream(fullPath);
        CompilationUnit cu = StaticJavaParser.parse(in);
        ParsedInfo parsedInfo = new ParsedInfo();
        //获取被分析的包名
        String packageName = cu.getPackageDeclaration().get().getNameAsString();
            for (ClassOrInterfaceDeclaration coid : cu.findAll(ClassOrInterfaceDeclaration.class)) {
                //获取类名
                String className = coid.getName().asString();
                //判断是否为目标类，防止一个文件中有多个类
                if(!className.equals(this.className)){
                    continue;
                }
                //设置目标函数所在类的类名
                parsedInfo.setClassName(className);
//                System.out.println("类名: " + className);

                //获取名字为methodName的方法的签名+方法体
                List<MethodDeclaration> targetMethod = coid.getMethodsByName(methodName);
                String method = targetMethod.get(0).toString();
                //设置方法名
                parsedInfo.setMethodName(methodName);
                //设置方法体
                parsedInfo.setMethodBody(method);
//                System.out.println(method);

                //设置当前方法所在类的包声明
                parsedInfo.setDeclaredPackage(packageName);

                // 遍历并打印每个成员变量及其默认值（如果有的话）
                for (FieldDeclaration field : coid.getFields()) {
                    //field转为字符串后以分号结尾，去掉分号，从"="号区分开，仅取得成员变量签名
                    String fieldSignature = String.valueOf(field);
                    if (fieldSignature.endsWith(";")) {
                        fieldSignature = fieldSignature.substring(0,fieldSignature.length()-1).split("=")[0].trim();
                    }
//                    System.out.println(fieldSignature);

                    //获取field中的variable
                    for (VariableDeclarator variable : field.getVariables()) {
                        //获取初始值，有初始值则值设为初始值，没有初始值设为"noDefaultValue"
                        Optional<Expression> initializer = variable.getInitializer();
                        if (initializer.isPresent()) {
                            Object defaultFieldValue = initializer.get();
                            //将成员变量名和初始值以键值对形式存储
                            parsedInfo.addDeclaredFieldMap(fieldSignature,defaultFieldValue);
//                            System.out.println("，默认值: " + initializer.get());
                        } else {
                            parsedInfo.addDeclaredFieldMap(fieldSignature,"noDefaultValue");
//                            System.out.println();  // 如果没有默认值，换行
                        }
                    }
                }
            }
        return parsedInfo;
    }

    /**
    * @description: 接收方法签名，抽取必要信息
    * @param: [methodSignature]
    * @return: com.github.fantac.parser.ParsedInfo
    * @author: FantaC
    * @date: 2024/4/2
    */
    public static ParsedInfo infoExtractWithSig(String methodSignature) throws IOException {
        String[] signatureInfo = DataProcessUtil.extractClassAndMethod(methodSignature);
        String packageName = signatureInfo[0];
        String className = signatureInfo[1];
        String methodName = signatureInfo[3];
        String filePath = DataProcessUtil.pathSeparatorReplacer(packageName);
        MethodExtractor extractor = new MethodExtractor(filePath,className,methodName);
        ParsedInfo parsedInfo = extractor.infoExtract();
        return parsedInfo;
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

