package partTwoLessonOne;

import java.util.ArrayList;
import java.util.Collection;

public class MainApp {
    public static void main(String[] args) {

        /**
         * Задача 1 Написать метод, который меняет два элемента массива местами (массив может быть любого ссылочного типа);
         */

        Object[] arr1 = new Object[]{1,3,2};
        swap(arr1, 0,2);



        /**
         * Задача 2 Написать метод, который преобразует массив в ArrayList;
         */
        Object[] arr2 = new Object[]{"A","B","C"};
        System.out.println(massToArrList(arr2).toString());


        /**
         * Задача про коробки с фруктами
         */
        Apple apple1 = new Apple();
        Orange orange1 = new Orange();

        Box<Apple> boxAp1 = new Box<>();
        boxAp1.addToBox(apple1, 6);
        boxAp1.getInfo();

        Box<Orange> boxOr1 = new Box<>();
        boxOr1.addToBox(orange1, 4);
        boxOr1.getInfo();

        System.out.println(boxAp1.compare(boxOr1));

        Box<Orange> boxOr2 = new Box<>();
        boxOr2.getInfo();
        boxOr1.moveToBox(boxOr2);
        boxOr2.getInfo();
        boxOr1.getInfo();
    }

    /**
     * Обобщённый метод замены двух элементов массива
     */
    public static <T> void swap(T[] a, int i1, int i2) {
        T tmp = a[i1];
        a[i1] = a[i2];
        a[i2] = tmp;
        for ( T t: a) {
            System.out.print(t + " " );
        }
        System.out.println("\n");
    }

    /**
     * Обобщённый метод замены двух элементов массива
     */
    public static <T> ArrayList<T> massToArrList(T[] a) {
        ArrayList<T> arrT = new ArrayList<>();
        for (T t : a) {
            arrT.add(t);
        }
        return arrT;
    }
}
