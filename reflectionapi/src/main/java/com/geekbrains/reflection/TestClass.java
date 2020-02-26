package com.geekbrains.reflection;

public class TestClass {

    @BeforeSuite
    public static void testbefore() {
        System.out.println("Start testing");
    }

    @Test(priority = 1)
    public static void test1() {
        System.out.println(1);
    }

    @Test(priority = 2)
    public static void test2() {
        System.out.println(2);
    }

    @Test(priority = 3)
    public static void test3() {
        System.out.println(3);
    }

    @Test(priority = 2)
    public static void test4() {
        System.out.println(4);
    }

    @Test(priority = 2)
    public static void test5() {
        System.out.println(5);
    }

    @AfterSuite
    public static void testAfter() {
        System.out.println("End of testing");
    }

}
