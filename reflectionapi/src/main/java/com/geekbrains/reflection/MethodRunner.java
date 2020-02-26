package com.geekbrains.reflection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MethodRunner {

    public static void start(Class startClass) throws Exception{

        Class c = startClass; // получаем ссылку на класс
        Map<Integer, ArrayList<Method>> order = new HashMap<>();

        boolean makeBeforeOnce = true; // для однократного выполнения BeforeSuite
        boolean makeAfterOnce = true; // для однократного выполнения AfterSuite
        int p; //приоритет

        Method[] methods = c.getDeclaredMethods(); // находим все его методы

        for (Method m: methods) { // перебираем методы по аннотациям

            if (m.isAnnotationPresent(BeforeSuite.class)) {
                if (makeBeforeOnce == false) {
                    throw new RuntimeException("Невозможно выполнить более одного @BeforeSuite метода!");
                }
                ArrayList<Method> arrBefore = new ArrayList<>();
                arrBefore.add(m);
                order.put(0, arrBefore);
                makeBeforeOnce = false;
            }

            if (m.isAnnotationPresent(Test.class)) {
                p = m.getAnnotation(Test.class).priority();
                if (p>=1 && p<=10) {
                     if (order.get(p) != null) { // если в слоте "p" уже есть ArrayList
                         order.get(p).add(m); // добавляем в него
                     }else {
                        ArrayList<Method> arrTests = new ArrayList<>();
                        arrTests.add(m);
                        order.put(p, arrTests);
                    }
                } else {
                    throw new RuntimeException("Приоритет метода @Test все разрешённого диапазона от 1 до 10!");
                }
            }

            if (m.isAnnotationPresent(AfterSuite.class)) {
                if (makeAfterOnce == false) {
                    throw new RuntimeException("Невозможно выполнить более одного @AfterSuite метода!");
                }
                ArrayList<Method> arrAfter = new ArrayList<>();
                arrAfter.add(m);
                order.put(11, arrAfter);
                makeAfterOnce = false;
            }
        }

        //пройдя по созданной последовательности запустим все тэсты
        for (int key: order.keySet()) { // проходим по HashMap получим ключи
            order.get(key); //получаем значения Arrays по ключам

            for (int i = 0; i < order.get(key).size(); i++) { // проходимся по Arrays
                order.get(key).get(i).invoke(null); //запускаем методы
            }

        }

    }
}
