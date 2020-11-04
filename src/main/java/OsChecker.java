public class OsChecker {
    public static void main(String[] args) {
        if(isWindows()){
            System.out.println("This is Windows");
            System.out.println("It's version is: " + getOSVerion());
        }else if(isMac()){
            System.out.println("This is Macintosh");
            System.out.println(System.getProperty("os.name"));
            System.out.println("It's version is: " + getOSVerion());
        }else if(isUnix ()){
            System.out.println("This is Unix or Linux OS");
            System.out.println("It's version is: " + getOSVerion());
        }else{
            System.out.println("This is unknown OS");
        }
        System.out.println("Version: " + getOSVerion());
    }

    public static boolean isWindows(){

        String os = System.getProperty("os.name").toLowerCase();
        //windows
        return (os.contains("win"));

    }

    public static boolean isMac(){

        String os = System.getProperty("os.name").toLowerCase();
        //Mac
        return (os.indexOf( "mac" ) >= 0);

    }

    public static boolean isUnix (){

        String os = System.getProperty("os.name").toLowerCase();
        //linux or unix
        return (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0);

    }
    public static String getOSVerion() {
        String os = System.getProperty("os.version");
        return os;
    }
}

