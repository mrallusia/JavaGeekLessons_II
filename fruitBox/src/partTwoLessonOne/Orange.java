package partTwoLessonOne;

public class Orange extends Fruit implements Specifications{

    private String name;
    private float weight;

    public Orange() {
        super();
        this.name = "Orange";
        this.weight = 1.5f;
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
