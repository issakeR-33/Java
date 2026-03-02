public class CustomEmailFormatException extends Exception {
    public CustomEmailFormatException(String email) {
        super("Недійсний формат електронної пошти: " + email);
    }
}