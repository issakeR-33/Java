import java.util.ArrayList;
import java.util.Iterator;

public class Task2_Collections {

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.+\\-]+@[\\w\\-]+\\.[a-zA-Z]{2,}$");
    }

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
