package com.github.fantac.parser;

import java.util.*;

/**
 * @program: JavaParser
 * @description: 存放抽取到的信息
 * @author: FantaC
 * @create: 2024-03-31-17-17
 **/
public class ParsedInfo {
    private String methodBody;
    private String declaredPackage;
    private String className;
    private Map<String, Object> declaredFieldMap = new LinkedHashMap<>();

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
        System.out.println("已经存放"+fieldSignature);
    }
}
