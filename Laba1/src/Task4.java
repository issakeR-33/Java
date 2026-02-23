public class Task4 {
    public void run() {
        System.out.print("Завдання 4: Перші 10 простих чисел: ");
        int count = 0, num = 2;
        while (count < 10) {
            if (isPrime(num)) {
                System.out.print(num + " ");
                count++;
            }
            num++;
        }
        System.out.println();
    }

    private boolean isPrime(int n) {
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}