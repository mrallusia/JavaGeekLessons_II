public class MainApp {
    public static final int CARS_COUNT = 4;
    public static void main(String[] args) {

        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];

        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }

        race.startRace(cars);


    }
}




