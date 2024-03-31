package com.github.fantac.utils;

import java.io.File;

/**
 * @program: JavaParser
 * @description: 实现仅选择java文件的接口
 * @author: FantaC
 * @create: 2024-03-31-16-35
 **/
public class JavaFileFilter implements DirExplorer.Filter{

    @Override
    public boolean interested(int level, String path, File file) {
        return path.endsWith(".java");
    }
}
