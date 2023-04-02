package jiraticketsmanager.utils;

public class RecurUtils {

    public static boolean isInteger(String s) {
        try{
            Integer.parseInt(s);
            return true;
        }catch (NumberFormatException e) {
            return false;
        }
        
    }
}
