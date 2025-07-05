package com.mohit.Ghibli.ai.Controller;


import com.mohit.Ghibli.ai.DTO.TextGenerationRequestDTO;
import com.mohit.Ghibli.ai.Service.GhibliArtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
//@RequestMapping("/api/v1")
@CrossOrigin("*")
@RequiredArgsConstructor
public class GenerationController {

    private static final Logger log = LoggerFactory.getLogger(GenerationController.class);
    @Autowired
    private  GhibliArtService ghibliArtService;

//    @GetMapping("/hello")
//    public String hello(){
//        return "Hello";
//    }

    @PostMapping(value = "/generate", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateGhibliArt(@RequestParam("image") MultipartFile image,
                                                    @RequestParam("prompt") String prompt) {
        try {
            byte[] imageBytes = ghibliArtService.createGhibliArt(image, prompt);
            log.info(String.valueOf(ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes)));
            System.out.println("yes");
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping(value = "/generate-from-text",produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateGhibliArtFromText(@RequestBody TextGenerationRequestDTO requestDTO){
        try{
            byte[] imageBytes =ghibliArtService.createGhibliArtFromText(requestDTO.getPrompt(),requestDTO.getStyle());
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);

        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}
