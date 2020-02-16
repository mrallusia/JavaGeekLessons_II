public class WNApp {
    private final Object mon = new Object();
    private char letter = 'A';

    public void printA() {
        synchronized (mon) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (letter != 'A') {
                        mon.wait();
                    }
                    System.out.print("A");
                    letter = 'B';
                    mon.notifyAll();
                }
            } catch (InterruptedException e ) {
                e.printStackTrace();
            }
        }
    }
    public void printB() {
        synchronized (mon) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (letter != 'B') {
                        mon.wait();
                    }
                    System.out.print("B");
                    letter = 'C';
                    mon.notifyAll();
                }
            } catch (InterruptedException e ) {
                e.printStackTrace();
            }
        }
    }
    public void printC() {
        synchronized (mon) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (letter != 'C') {
                        mon.wait();
                    }
                    System.out.print("C");
                    letter = 'A';
                    mon.notifyAll();
                }
            } catch (InterruptedException e ) {
                e.printStackTrace();
            }
        }
    }

}
