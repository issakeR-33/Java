import java.util.Scanner;

public class Task3 {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Завдання 3: Введіть тип (1-автомобіль, 2-автобус, 3-велосипед): ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                System.out.println("Тип транспорту: Автомобіль");
                break;
            case 2:
                System.out.println("Тип транспорту: Автобус");
                break;
            case 3:
                System.out.println("Тип транспорту: Велосипед");
                break;
            default:
                System.out.println("Невідомий тип транспорту");
        }
    }
}