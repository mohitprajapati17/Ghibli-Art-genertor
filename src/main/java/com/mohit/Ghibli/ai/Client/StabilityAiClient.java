package com.mohit.Ghibli.ai.Client;


import com.mohit.Ghibli.ai.DTO.TextToImageRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(
        name ="stabilityAIClient",
        url="${stability.api.base-url}",
        configuration = com.mohit.Ghibli.ai.Config.FeignConfig.class
)
public interface StabilityAiClient {

    @PostMapping(
            value="/v1/generation/{engine_id}/text-to-image",
            consumes= MediaType.APPLICATION_JSON_VALUE,
            headers={"Accept=image/png"}
    )
    byte[] generationImageFromText(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("engine_id") String engine_Id,
            @RequestBody TextToImageRequest requestBody
            );

    @PostMapping(
            value ="/v1/generation/{engine_id}/image-to-image",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = {"Accept=image/png"}
    )
    byte[] generateImageFromImage(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("engine_id") String engineId,
            @RequestPart("init_image") MultipartFile initImage,
            @RequestPart("text_promts[0][text]") String textPrompt,
            @RequestPart("style_preset") String stylePreset

    );
}
