public class Task5 {
    public void run() {
        int[] array = {5, 3, 8, 1, 9, 2, 7};
        int max = array[0], min = array[0];
        for (int num : array) {
            if (num > max) max = num;
            if (num < min) min = num;
        }
        System.out.println("Завдання 5: Найбільше: " + max + ", Найменше: " + min);
    }
}