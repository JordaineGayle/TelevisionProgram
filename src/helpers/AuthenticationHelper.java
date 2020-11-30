package helpers;

public class AuthenticationHelper {

    private static int password = 4421;

    private static boolean authenticated = false;

    public static boolean authenticateUser(int pass){
        if(pass == password){
            authenticated = true;
        }else{
            authenticated = false;
        }

        return isAuthenticated();
    }

    public static boolean isAuthenticated(){
        return authenticated;
    }
}
