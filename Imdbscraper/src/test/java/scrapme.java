import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import org.jsoup.nodes.Element;
import org.jsoup.*;
import org.jsoup.select.Elements;


/**
 * Created by hari on 24/11/17.
 */

/**
 * Libraries used : Jsoup, JSON
 * Parsejson obtains JSON for the input movie and parses Ids from that JSON
 * Using the ids an url is built and is requested
 * The Rating value is scraped from the DOCUMENT
 *
 * Note:
 * Returns multiple results from the input movie | Closest possible results are generated
 */

public class scrapme {


    static String[] findRatings(String input) throws Exception{

        String rating[] = parsejson.getIds(input);
        Document doc[] = new Document[rating.length];

        try {

            //obtain ids
            String id[] = parsejson.getIds(input);
            for(String i : id){
                System.out.println("Id: "+i);
            }

            String url[] = new String[id.length];

            for(int i = 0 ; i < url.length ; i++) {
                url[i] = "http://www.imdb.com/title/" + id[i] + "/?ref_=nv_sr_2";       //generate urls using ids
                System.out.println("URL: "+url[i]);
                doc[i] = Jsoup.connect(url[i]).get();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        //List of possibly matched movies and their corresponding ratings
        for(int i = 0 ; i < doc.length ; i++) {
            rating[i] = "Movie: "+ doc[i].title() +"Rating: "+ (doc[i].getElementsByClass("ratingValue").tagName("strong").text());
        }


        for(String res : rating){
            System.out.println(res);
        }

        return rating;
    }


    public static void main(String[] args) {

        try {
            scrapme.findRatings("3 Idiots");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
