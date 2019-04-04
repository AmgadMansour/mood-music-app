# mood-music-app
Music recommendation web application based on facial emotion detected with a deep learning model. 

## Usage
First snap the screnshot then go to the music player. If no snap is taken the mood is set to the "Neutral" state.

## Implementation
First the webcam is accesed using GetUserMedia in HTML5 with no need to install any extra plugins. The user has to give the permission for webcam access, then canvas is used to take a screenshot of the live video. AJAX is then used to send the screenshot from JS to the server in JSON format as a bas64 String. The controller then maps this request, saves the image to the server in png format (snap.png). The facial emotion detection API is then called and the (snap.png) is uploaded to the data soruce on the API and the model outputs the mood detected. The controller then renders the player template by passing the mood as a parameter and the suitable playlist will be loaded in Javascript. I have added some songs in the data folder to be directly loaded based on the mood for demonstraion.

## Technologies
### Frontend
* [AmplitudeJS](https://github.com/521dimensions/amplitudejs) : A lightweight JavaScript library to control the design of the media controls in the webpage. 

### Backend
 * [SpringBoot](https://spring.io/projects/spring-boot) : A Framework from “The Spring Team” to ease the bootstrapping and development of new Spring Applications.
 * [Algorithmia API](https://algorithmia.com/) : An API used for running deep learning models and managing their data sources. 
 

