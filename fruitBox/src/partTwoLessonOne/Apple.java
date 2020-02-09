package partTwoLessonOne;

public class Apple extends Fruit implements Specifications{

    private String name;
    private float weight;

    public Apple() {
        super();
        this.name = "Apple";
        this.weight = 1.0f;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void getInfo() {
        System.out.println("Название фрукта: " + name + " " + "вес: " + weight);
    }

    @Override
    public float getWeight() {
        return weight;
    }
}

