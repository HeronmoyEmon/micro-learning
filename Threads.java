public class Threads {
    public static void main(String[] args) {
        Thread t1 = new Thread();
        t1.start();
        System.out.println(t1.getState());
    }
}