public class Main {
    public static void main(String[] args) {

        // ===== ЗАДАЧА 1: Exception =====
        System.out.println("=== Задача 1: Перевірка email (Exception) ===");

        String[] emails = {"user@example.com", "invalid-email", "test@mail.org", "bad@", "@nodomain.com"};

        for (String email : emails) {
            try {
                Task1_Exception.validateEmail(email);
            } catch (CustomEmailFormatException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }

        // ===== ЗАДАЧА 2: Колекції =====
        System.out.println("\n=== Задача 2: ArrayList з видаленням недійсних email ===");

        java.util.ArrayList<String> emailList = new java.util.ArrayList<>();
        emailList.add("user@example.com");
        emailList.add("invalid-email");
        emailList.add("test@mail.org");
        emailList.add("bad@");
        emailList.add("hello@world.ua");
        emailList.add("@nodomain.com");

        System.out.println("До видалення: " + emailList);
        Task2_Collections.removeInvalidEmails(emailList);
        System.out.println("Після видалення: " + emailList);
    }
}