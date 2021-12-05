package com.galaxyit.service;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WordsInterceptorService {

    private static final String DATAMUSE_API_ENDPOINT = "https://api.datamuse.com/words?max=5&sl=";
    private static final Logger LOGGER = LoggerFactory.getLogger(WordsInterceptorService.class);

    private final Gson gson = new Gson();

    public String getLikeMindedWordsForGivenModel(final String model) {
        String likeMindedWords = "";
        try {
            String wordEncoded = URLEncoder.encode(model, "UTF-8");
            String json = getJSON(DATAMUSE_API_ENDPOINT + wordEncoded);
            System.out.println("json = " + json);

            List<String> word = ((ArrayList<Map>) (gson.fromJson(json, List.class))).stream().map(x -> x.get("word").toString()).collect(Collectors.toList());
            return word.stream().collect(Collectors.joining(", "));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Unable to find word for [{}]", model, e);
        }
        return likeMindedWords;
    }

    private String getJSON(String url) {
        StringBuilder matching = null;
        try {
            URL  datamuse = new URL(url);
            URLConnection dc = datamuse.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(dc.getInputStream(), "UTF-8"));
            String inputLine;
            matching = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
                matching.append(inputLine);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matching != null ? matching.toString() : null;
    }
}
