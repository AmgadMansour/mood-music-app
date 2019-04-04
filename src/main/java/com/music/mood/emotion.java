package com.music.mood;

import com.algorithmia.*;
import com.algorithmia.algo.*;
import com.algorithmia.data.DataDirectory;
import java.io.File;
import java.io.FileNotFoundException;

public class emotion {


    public static String connectToEmotionAPI() throws APIException, FileNotFoundException, AlgorithmException {

        // More information regarding the API can be found here: https://algorithmia.com/developers/clients/java
        // Steps: -Get API Key. -Uplaod snap.png to the data source on which we call the deep learning model.
        // -The result is a json array of each emotion probability(Happy, Neutral, Sad, Angry...).
        // -Emotion class with thehighest probability is considered as the user's emotion.

        AlgorithmiaClient client = Algorithmia.client("simeOMUfFVqcn/H2kRvzw/nvMjY1"); // API Key
        DataDirectory robots = client.dir("data://amgad2019/emotion");
        String dir = System.getProperty("user.dir");
        dir = dir+"/snap.png";
        robots.putFile(new File(dir));

        String input = "{"
                + "  \"image\": \"data://amgad2019/emotion/snap.png\","
                + "  \"numResults\": 7"
                + "}";
        Algorithm algo = client.algo("deeplearning/EmotionRecognitionCNNMBP/1.0.1");
        algo.setTimeout(300L, java.util.concurrent.TimeUnit.SECONDS); //optional
        AlgoResponse result = algo.pipeJson(input);
        System.out.println(result.asJsonString());
        String[] tmp = result.asJsonString().split(",");
        if(tmp.length == 1) return "Neutral"; // in case face not detected properly or the user didnt take a proper screenshot
        String mood = tmp[5].split(":")[1];
        mood = mood.substring(1, mood.length()-2);
        if(mood.equals("Surprise")) mood="Happy"; //Consider Neutral mood to be same as happy
        if(mood.equals("Disgust" )||mood.equals("Fear")) mood="Angry";
        return mood;
    }


}
