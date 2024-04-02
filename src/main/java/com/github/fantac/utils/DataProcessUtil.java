package com.github.fantac.utils;

import java.io.File;

/**
 * @program: JavaParser
 * @description: 数据处理工具类
 * @author: FantaC
 * @create: 2024-04-02-10-13
 **/
public class DataProcessUtil {

    /** 
    * @description: 从Soot的函数签名中抽取类名、返回值类型、方法名、传入参数值 
    * @param: [functionSignature] 
    * @return: java.lang.String[] 
    * @author: FantaC 
    * @date: 2024/4/2
    */
    public static String[] extractClassAndMethod(String functionSignature) {
        // 删除 "<" 和 ">" 字符
        functionSignature = functionSignature.substring(1, functionSignature.length() - 1);
        // 按 ":" 分割字符串
        String[] parts = functionSignature.split(":");
        if (parts.length != 2) {
            // 无效的签名
            System.out.println("传入的签名不符合Soot签名格式，eg：<TestCaseDroid.test.CallGraph: void main(java.lang.String[])>");
            return null;
        }
        // 抽取类名，去掉空白字符
        String className = parts[0].trim();
        // 抽取方法名和参数类型
        String methodAndParams = parts[1].trim();
        // 分离“传入参数”和“返回值类型与函数名”
        String[] methodAndParameters = methodAndParams.split("\\(");
        // 分离“返回值类型”和“方法名”
        String[] methodAndReturnType = methodAndParameters[0].split(" ");
        String returnType = methodAndReturnType[0].trim();
        String methodName = methodAndReturnType[1].trim();
        // 抽取传入参数
        String parameters;
        if (!")".equals(methodAndParameters[1].trim())){
            parameters = methodAndParameters[1].trim().split("\\)")[0].trim();
        }else{
            parameters = null;
        }
        return new String[]{className, returnType,methodName,parameters};
    }
    
    /** 
    * @description: 将类名转变为类所在路径 
    * @param: [inputString] 
    * @return: java.lang.String 
    * @author: FantaC 
    * @date: 2024/4/2
    */
    public static String PathSeparatorReplacer(String inputString){
        // 使用 File.separator 获取系统的路径分隔符
        String separator = File.separator;
        // 将点替换为路径分隔符
        return inputString.replace(".", separator);
    }
}
