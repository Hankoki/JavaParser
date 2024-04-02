package com.github.fantac.parser;

import java.util.*;

/**
 * @program: JavaParser
 * @description: 存放抽取到的信息
 * @author: FantaC
 * @create: 2024-03-31-17-17
 **/
public class ParsedInfo {
    private String methodName;
    private String methodBody;
    private String declaredPackage;
    private String className;
    private Map<String, Object> declaredFieldMap = new LinkedHashMap<>();

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodBody() {
        return methodBody;
    }

    public void setMethodBody(String methodBody) {
        this.methodBody = methodBody;
    }

    public String getDeclaredPackage() {
        return declaredPackage;
    }

    public void setDeclaredPackage(String declaredPackage) {
        this.declaredPackage = declaredPackage;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Map<String, Object> getDeclaredField() {
        return declaredFieldMap;
    }

    public void addDeclaredFieldMap(String fieldSignature,Object defaultFieldValue){
        this.declaredFieldMap.put(fieldSignature,defaultFieldValue);
//        System.out.println("已经存放"+fieldSignature);
    }

    public void displayParsedInfo(){
        System.out.printf("[--------------------从目标函数%s中抽取到的相关信息-------------------]\n",getMethodName());
        System.out.println("目标函数所在包:");
        System.out.println(getDeclaredPackage());
        System.out.println("目标函数所在类类名:");
        System.out.println(getClassName());
        System.out.println("目标函数所在类的成员函数:");
        if (getDeclaredField().isEmpty()){
            System.out.println("----无成员函数");
        }else {
            for (Map.Entry<String, Object> entry : getDeclaredField().entrySet()) {
                System.out.println("成员变量: " + entry.getKey() + ", 初始值: " + entry.getValue());
            }
        }
        System.out.println("目标函数方法体:");
        System.out.println(getMethodBody());
        System.out.printf("[----------------从目标函数%s中抽取到的相关信息展示结束----------------]\n",getMethodName());

    }
}
