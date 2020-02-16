public class Main {
    public static void main(String[] args) {
        WNApp wnApp = new WNApp();

        new Thread(() -> {
            wnApp.printA();
        }).start();

        new Thread(() -> {
            wnApp.printB();
        }).start();

        new Thread(() -> {
            wnApp.printC();
        }).start();
    }
}
