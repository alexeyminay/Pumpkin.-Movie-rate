package com.alesno.ratingkino.network;

import com.alesno.ratingkino.model.Movie;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class KinopoiskHTMLParser {

    private Document document = null;

    private Document getDocument(){
        if(document == null) throw new RuntimeException("getHTMLPage() method didn't invoked");
        return document;
    }

    public void getHtmlPage(Movie movie){
        try{
            StringBuilder url = new StringBuilder("https://www.kinopoisk.ru/s/type/film/find/")
                    .append(movie.getName())
                    .append("/m_act%5Byear%5D/")
                    .append(movie.getYears());
            document = Jsoup.connect(url.toString()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getIdFilm(){
        if (!getDocument()
                .getElementsByAttribute("data-id").isEmpty()) {
            String dataByAttribute = String.valueOf(getDocument()
                    .getElementsByAttribute("data-id").get(0));
            return dataByAttribute.split("\"")[7];
        }else {
            return null;
        }
    }

    public String getFilmYear(){
        return getDocument().getElementsByClass("year").get(0).ownText();
    }

    public String getFilmName(){
        return getDocument().getElementsByClass("gray").get(0).ownText();
    }
}
