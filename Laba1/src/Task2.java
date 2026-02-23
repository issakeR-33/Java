public class Task2 {
    public void run() {
        int a = 10, b = 3, c = 7;
        int min;
        if (a < b && a < c) {
            min = a;
        } else if (b < a && b < c) {
            min = b;
        } else {
            min = c;
        }
        System.out.println("Завдання 2: Найменше число: " + min);
    }
}