public class Task1_Exception {

    public static void validateEmail(String email) throws CustomEmailFormatException {
        if (email == null || !email.matches("^[\\w.+\\-]+@[\\w\\-]+\\.[a-zA-Z]{2,}$")) {
            throw new CustomEmailFormatException(email);
        }
        System.out.println("Електронна пошта дійсна: " + email);
    }

}
