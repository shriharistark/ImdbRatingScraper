import org.json.JSONArray;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;

import javax.print.Doc;
import java.io.IOException;


//new technique
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by hari on 24/11/17.
 */
public class parsejson {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private static ArrayList<String> readJsonFromUrl(String url, String input) throws IOException, JSONException {

        //parsing json

        ArrayList<String> ids = new ArrayList<String>();

        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);

            int displace = 0;
            String jsontext = jsonText;

            while (displace < 100) {
                if (jsontext.startsWith("{")) {
                    jsontext = jsonText.substring(displace);
                    break;
                }
                jsontext = jsontext.substring(1);
                displace++;
            }

            //System.out.println("\ndisplace:" + displace + "\njson: " + jsontext);     //displacement value in json | refer to original json

            JSONObject json = new JSONObject(jsontext);
            JSONArray jsonArray = json.getJSONArray("d");

            //System.out.println(jsonArray);                                            //list of jsons

            int k = 0;

            //obtaining ids from json
            for (int i = 0; i < 3; i++) {

                //removing spaces and symbols
                String parse = jsonArray.getJSONObject(i).get("l").toString().toLowerCase().replaceAll("\\s", "").replaceAll("\\W+","");

                //tests whether input movie name matches with json data, returning relevant data alone
                boolean test1 = parse.contains(input.toLowerCase().replaceAll("\\s", ""));
                boolean test2 = jsonArray.getJSONObject(i).get("q").toString().toLowerCase().replaceAll("\\s", "").equals("feature");

                if (test1 && test2) {
                    String id = jsonArray.getJSONObject(i).get("id").toString();
                    ids.add(id);
                    k++;
                }
            }
        }

        catch (JSONException e){

        }
        finally {
            is.close();
        }

        return ids;

    }

    public static String[] getIds(String input) throws Exception {

        String urlinput = input.replaceAll("\\s", "");
        System.out.println("\ninput:" + input);
        String url = "https://v2.sg.media-imdb.com/suggests/" + urlinput.substring(0, 1).toLowerCase() + "/" + urlinput.toLowerCase() + ".json";

        ArrayList<String> ids = new ArrayList<String>();

        try {
            ids = readJsonFromUrl(url, input);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (ids.size() <= 0 || !ids.get(0).startsWith("tt")) {
            throw new Exception("\nNull IDs. No movie found with the name");
        }

        String resids[] = new String[ids.size()];
        return ids.toArray(resids);

    }

}
