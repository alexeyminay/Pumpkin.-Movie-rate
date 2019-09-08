package com.alesno.ratingkino.network;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class KinopoiskParser {

    private Document document = null;

    private void getHtmlPage(String filmName){
        try{
            document = Jsoup.connect("https://www.kinopoisk.ru/s/type/film/find/"+filmName).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getIdFilm(String filmName){
        getHtmlPage(filmName);
        if(document!=null){
            String dataByAttribute = String.valueOf(document.getElementsByAttribute("data-id").get(0));
            String id = dataByAttribute.split("\"")[7];
            return id;
        }else {
            return "упс неудачка((";
        }
    }
}
