package com.music.mood;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.algorithmia.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;


@Controller
@SpringBootApplication
public class Run {

    public static void main(String... args)  {
        SpringApplication.run(Run.class, args);
    }


    @RequestMapping("/")
    public String home() {
        return "home";
    }


    //Handles the post request (base64 screenshot image) sent via AJAX from cam.js,
    //then transforms it to byte stream and saves the snap image in the server (snap.png)
    @RequestMapping(value = "/emotion")
    public String test(@RequestBody String data) throws JSONException, IOException {
        JSONObject jsonObject = new JSONObject(data);
        String img64String = String.valueOf(jsonObject.get("img64"));
        byte[] decByteArr = Base64.decodeBase64(img64String);
        BufferedImage buffImage = ImageIO.read(new ByteArrayInputStream(decByteArr));
        ImageIO.write(buffImage, "png", new File("snap.png"));
        return "";
    }

    //Handles the request for playing music, activates the emotion API which uploads the snap.png (user's screenshot)
    //to the API data source, then calls the deep learning model. The resulting emotion is then sent from the controller to
    //the music player HTMl and JS to play a suitable playlist.
    @RequestMapping("/player")
    public String player(Model model,@RequestParam(value="emotion", required=false, defaultValue="Neutral") String emotion) throws APIException, AlgorithmException, FileNotFoundException {
        emotion = com.music.mood.emotion.connectToEmotionAPI();
        model.addAttribute("emotion",emotion);
        return "player";
    }


}
