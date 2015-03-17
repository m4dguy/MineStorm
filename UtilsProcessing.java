import java.util.ArrayList;

/**
 * Created by m4dguy on 25.02.2015.
 */
public final class UtilsProcessing {

    public static ArrayList<String> splitBySeparator(String line, char separator) {
        String split = "";
        ArrayList<String> result = new ArrayList<String>();

        for(int i=0; i<line.length(); ++i) {
            if((line.charAt(i) == separator)) {
                if(split != "")
                {
                    result.add(split);
                    split = "";
                }
            }else{
                split = split + line.charAt(i);
            }
        }

        if(split != "")
            result.add(split);

        return result;
    }

    public static ArrayList<String> splitByWhitespace(String line) {
        String split = "";
        ArrayList<String> result = new ArrayList<String>();

        for(int i=0; i<line.length(); ++i) {
            if(Character.isWhitespace(line.charAt(i))) {
                if (split != ""){
                    result.add(split);
                    split = "";
                }
            }else{
                split = split + line.charAt(i);
            }
        }

        if(split != "")
            result.add(split);

        return result;
    }

}
