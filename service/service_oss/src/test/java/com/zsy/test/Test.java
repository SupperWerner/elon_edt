package com.zsy.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test {
    @org.junit.Test
    public void test(){
        String filename = "uuuuuu.zxf";
        int i = filename.indexOf(".");
        String substring = filename.substring(i);
        System.out.println(substring);

    }
}
