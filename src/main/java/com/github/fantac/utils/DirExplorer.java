package com.github.fantac.utils;

import java.io.File;

/**
 * @program: JavaParser
 * @description: 遍历文件夹工具类
 * @author: FantaC
 * @create: 2024-03-31 15:54
 **/
public class DirExplorer {
    /** 
    * @description: 对文件夹进行遍历
    * @author: FantaC 
    * @date: 2024/3/31
    */
    public interface FileHandler {
        void handle(int level, String path, File file);
    }
    /** 
    * @description: 对遍历的文件进行筛选
    * @author: FantaC 
    * @date: 2024/3/31
    */
    public interface Filter {
        boolean interested(int level, String path, File file);
    }

    private FileHandler fileHandler;
    private Filter filter;

    public DirExplorer(Filter filter, FileHandler fileHandler) {
        this.filter = filter;
        this.fileHandler = fileHandler;
    }

    public void explore(File root) {
        explore(0, "", root);
    }

    /**
    * @description: 对文件夹进行遍历
    * @param:
     * @param level 当前文件或目录在文件中的深度
     * @param path 从跟目录到当前文件或目录的路径
     * @param file 当前正在遍历的文件或目录
    * @return: void
    * @author: FantaC
    * @date: 2024/3/31
    */
    private void explore(int level, String path, File file) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                explore(level + 1, path + "/" + child.getName(), child);
            }
        } else {
            if (filter.interested(level, path, file)) {
                fileHandler.handle(level, path, file);
            }
        }
    }
}
