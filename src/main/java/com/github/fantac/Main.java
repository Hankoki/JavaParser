package com.github.fantac;

import java.io.IOException;

public class Main {
    public String test1="1";
    public int test2;
    public static void main(String[] args) throws IOException {
        String hello = "hello";
        MethodExtractor me = new MethodExtractor("com/github/fantac/Main.java","main");
        me.methodBodyExtract();
    }
}