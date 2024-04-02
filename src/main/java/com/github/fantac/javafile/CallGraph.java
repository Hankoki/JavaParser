package com.github.fantac.javafile;

public class CallGraph {
    private String para1 = "字符串1";
    public int para2 = 3;
    String para3 = "字符串2";
    private int para4;
    private String para5;

    public static void main(String[] args) {
        doStuff();
    }
    public static void doStuff() {
        new D().foo();
    }
}

class D
{
    public void foo() {
        bar();
    }

    public void bar() {
    }
}