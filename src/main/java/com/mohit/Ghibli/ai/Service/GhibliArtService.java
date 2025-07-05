package com.mohit.Ghibli.ai.Service;

import com.mohit.Ghibli.ai.Client.StabilityAiClient;
import com.mohit.Ghibli.ai.DTO.TextToImageRequest;
import org.springframework.beans.factory.annotation.Value;  // ✅ CORRECT!
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GhibliArtService {
    private final StabilityAiClient stabilityAIClient;


    private final String apiKey;

    public GhibliArtService(
            StabilityAiClient stabilityAIClient,
            @Value("${stability.api.key}") String apiKey
    ) {
        this.stabilityAIClient = stabilityAIClient;
        this.apiKey = apiKey;
    }

//    public byte[] createGhibliArt(MultipartFile image , String prompt){
//        String finalPrompt =prompt+", in the beautiful , detailed anime style of studio ghibli .";
//        String engineId ="stable-diffusion-v1-6";
//        String stylePreset ="anime";
//
//        return  stabilityAIClient.generateImageFromImage(
//                "Bearer"+apiKey,
//                engineId,
//                image,
//                finalPrompt,
//                stylePreset
//        );
//    }

    public byte[] createGhibliArt(MultipartFile image, String prompt) {
        String finalPrompt = prompt + ", in the beautiful, detailed anime style of studio ghibli.";

        ResponseEntity<byte[]> response = stabilityAIClient.generateImageFromImage(
                "Bearer " + apiKey,
                image,               // init_image
                finalPrompt,         // text_prompts[0][text]
                "1.0",               // text_prompts[0][weight]
                "7",                 // cfg_scale
                "30"                 // steps
        );

        return response.getBody();
    }



    public byte[] createGhibliArtFromText(String style , String prompt){
        String finalPrompt =prompt+", in the beautiful , detailed anime style of studio ghibli .";
        String engineId ="stable-diffusion-v1-6";
        String stylePreset =style.equals("general")? "anime" :style.replace("-","-");
        TextToImageRequest requestPayload =new TextToImageRequest(finalPrompt,stylePreset);
        return stabilityAIClient.generationImageFromText(
                "Bearer"+apiKey,
                engineId,
                requestPayload
        );
    }
}
