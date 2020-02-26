package com.geekbrains.reflection;

public class MainApp {
    public static void main(String[] args)  {

        try {
            MethodRunner.start(TestClass.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
