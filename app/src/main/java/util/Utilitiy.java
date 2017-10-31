package util;

import java.text.DecimalFormat;

/**
 * Created by Hytham on 10/31/2017.
 */

public class Utilitiy {

    public static String dataFormat(int value){
        DecimalFormat format = new DecimalFormat("#,###,###");
        String formatted = format.format(value);

        return formatted;

    }
}
