package ru.track;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.lang3.StringUtils;


public class App2 {

    public static final String URL = "http://guarded-mesa-31536.herokuapp.com/track";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_GITHUB = "github";
    public static final String FIELD_EMAIL = "email";

    public static void main(String[] args) throws UnirestException {
        String str = StringUtils.capitalize(args[0]);
        System.out.println(str);
        HttpResponse<JsonNode> jsonResponse = Unirest.post(URL)
                .field(FIELD_NAME, "Mariam")
                .field(FIELD_GITHUB, "https://github.com/mkapry")
                .field(FIELD_EMAIL, "mkapry@yandex.ru")
                .asJson();

        Boolean s = (Boolean) jsonResponse.getBody().getObject().get("success");
        System.out.println("Result: " + s);

    }
}
