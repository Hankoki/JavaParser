package com.github.fantac;

import com.github.fantac.javafile.CallGraph;
import com.github.fantac.parser.MethodExtractor;
import com.github.fantac.parser.ParsedInfo;
import com.github.fantac.utils.DataProcessUtil;

import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {

        String[] information = DataProcessUtil.extractClassAndMethod("<TestCaseDroid.test.User: void <init>(int,java.lang.String)>");

        String filePath = "com/github/fantac/javafile";
        String className= "CallGraph";
        String targetMethod = "main";
        MethodExtractor extractor = new MethodExtractor(filePath,className,targetMethod);
        ParsedInfo parsedInfo = extractor.methodBodyExtract();
        parsedInfo.displayParsedInfo();
    }
}