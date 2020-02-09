package partTwoLessonOne;

import java.util.ArrayList;

public class Box <T extends Fruit> implements Specifications{

    private ArrayList<T> listOfFruits;
    private float boxWeight;
    private String name;

    public Box() {
        listOfFruits = new ArrayList<>();
    }

    /**
     * Метод добовления фрукта в коробку
     */
    public void addToBox(T fruit, int number) {
        for (int i = 0; i < number; i++) {
            listOfFruits.add(fruit);
        }
    }

    /**
     * Сравнение коробок
     */
    public boolean compare(Box<? extends Fruit> another) {
        return Math.abs(this.getWeight() - another.getWeight()) < 0.001;
    }

    /**
     * Перекидывание фруктов в другую коробку
     */
    public void moveToBox(Box<T> another) {
        for (T fr : listOfFruits){
            another.addToBox(fr,1);
        }
        listOfFruits.clear();
    }

    @Override
    public void getInfo() {
        if (listOfFruits.isEmpty()) {
            System.out.println("Коробка пуста");
        } else {
            for (int i = 0; i < listOfFruits.size(); i++) {
                System.out.println(listOfFruits.get(i).getName());
            }
            System.out.println("Вес коробки c " + getName() + ": " + getWeight());
        }
    }

    @Override
    public String getName() {
        if (listOfFruits.isEmpty()) {
            return "Нет имени";
        } else {
            return listOfFruits.get(0).getName();
        }
    }

    @Override
    public float getWeight() {
        boxWeight = 0;
        for (int i = 0; i < listOfFruits.size(); i++) {
            boxWeight += listOfFruits.get(i).getWeight();
        }
        return boxWeight;
    }
}
