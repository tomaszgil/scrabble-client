package sample.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import javax.net.ssl.HttpsURLConnection;

public class WordVerifier {
    /**
     * Word verifier uses Oxford Dictionaries API.
     * https://developer.oxforddictionaries.com/
     */

    private String language;
    private String appId;
    private String appKey;
    private ArrayList<String> valid;
    private ArrayList<String> invalid;

    public WordVerifier() {
        this.language = "en";
        this.appId = "5bede3e2";
        this.appKey = "73b537cca0798cccdb2d4c4b1b6679c1";
    }

    private String inflections(String word) {
        final String word_id = word.toLowerCase(); // word id is case sensitive and lowercase is required
        return "https://od-api.oxforddictionaries.com:443/api/v1/inflections/" + language + "/" + word_id;
    }

    public boolean verify(String word) {
        boolean correct;

        try {
            URL url = new URL(inflections(word));
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("app_id", appId);
            urlConnection.setRequestProperty("app_key", appKey);

            // read the output from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            correct = true;
        }
        catch (Exception e) {
            correct = false;
        }

        return correct;
    }

    public ArrayList<String> getValid() {
        return valid;
    }

    public ArrayList<String> getInvalid() {
        return invalid;
    }

    public boolean verifyAll(ArrayList<String> words) {
        boolean result = true;
        valid = new ArrayList<>();
        invalid = new ArrayList<>();

        for (String word : words) {
            if (verify(word)) {
                valid.add(word);
            } else {
                invalid.add(word);
                result = false;
            }
        }

        return result;
    }

}
