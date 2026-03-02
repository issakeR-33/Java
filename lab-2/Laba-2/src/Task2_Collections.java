import java.util.ArrayList;
import java.util.Iterator;

public class Task2_Collections {

    // Метод перевірки формату (повертає true/false)
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.+\\-]+@[\\w\\-]+\\.[a-zA-Z]{2,}$");
    }

    // Метод видалення недійсних адрес зі списку
    public static void removeInvalidEmails(ArrayList<String> emailList) {
        Iterator<String> iterator = emailList.iterator();
        while (iterator.hasNext()) {
            String email = iterator.next();
            try {
                if (!isValidEmail(email)) {
                    throw new CustomEmailFormatException(email);
                }
            } catch (CustomEmailFormatException e) {
                System.out.println("Видаляємо: " + e.getMessage());
                iterator.remove();
            }
        }
    }

}
