package partTwoLessonOne;

public class Fruit implements Specifications{
    private String name = "Fruit";
    private float weight;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void getInfo() {
        System.out.println(name);
    };

    @Override
    public float getWeight() {
        return 0;
    };
}
