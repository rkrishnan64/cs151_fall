
/**
 *
 * @author rohitkrishnan
 */
//import com.sun.org.apache.xerces.internal.impl.xpath.regex.RegularExpression;

import java.io.*;
import java.net.URLConnection;
import java.util.*;
import java.net.URL;
import java.lang.String;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Parser {
    //parse incoming text file with for .jpgs
    private String domain = " ";
    public String parse() throws FileNotFoundException {

        File inFile = new File("/Users/rohitkrishnan/Desktop/test.txt");
        Scanner in = new Scanner(inFile);

        while(in.hasNextLine()) {
            String next = in.nextLine();
            String[] array = next.split(" ");
            domain = array[0];
        }
        return domain;
    }
    //Method to get URL content
    public  String start(String url) throws IOException {
        URL domain = new URL(url);
        URLConnection con = domain.openConnection();
        Pattern p = Pattern.compile("test/html;\\s+charset=([^\\s]+)\\s*");
        Matcher m = p.matcher(con.getContentType());
     /*
     If content type doesn't match this pre-conception, choose default hope for the best :)
      */
        String charset = m.matches() ? m.group(1) : "ISO-8859-1";
        Reader r = new InputStreamReader(con.getInputStream(), charset);
        StringBuilder buf = new StringBuilder();
        while(true){
            int ch = r.read();
            if(ch < 0)
                break;
            buf.append((char) ch);
        }
        String str = buf.toString();
        return str;
    }
    public static HashMap categorize(String page_data){
        String extraction = page_data;
        HashMap<String, Integer> seen = new HashMap<String, Integer>();
        // for(int i = 0; i < extraction.length(); i++){
        String extract = extraction.trim();
        String[] storage = extract.split("\\s+");
     

        for (String str : storage) {
            if (str.matches("[a-zA-Z]*")) { // make sure there are a-z0-9 in the string
                if (! str.matches("([^\\w\\s]|\\d)")) { //Change so that it extracts .jpgs 
                    Integer value = seen.get(str);
                    if (value == null) {
                        value = 0;
                    }
                    value++;
                    seen.put(str, value);
                }

            }
        }
        //}
        return seen;
    }
   



    //main
    public static void main(String[] args) throws FileNotFoundException, IOException{


        Parser domain_cat = new Parser();
        File test;
        test = new File("/Users/rohitkrishnan/Desktop/test.txt");

        String x = domain_cat.parse();

        String data = domain_cat.start(x);
        System.out.println(categorize(data));


        
    }
}

