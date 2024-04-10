package com.example.shopshoesspring.controller;

import com.example.shopshoesspring.entity.Light;
import com.example.shopshoesspring.entity.LightType;
import com.example.shopshoesspring.entity.UserLight;
import com.example.shopshoesspring.repository.LightRepository;
import com.example.shopshoesspring.repository.LightTypeRepository;
import com.example.shopshoesspring.repository.UserLightRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final LightTypeRepository lightTypeRepository;
    private final LightRepository lightRepository;
    private final UserLightRepository userLightRepository;
    @GetMapping("/home")
    public String homePage() {
        return "ahome";
    }

    @GetMapping("/addType")
    public String addTypePage(){
        return "addType";
    }

    @PostMapping("/addType")
    public String addType(@RequestParam("lightTypeName") String lightTypeName) {
        LightType lightType = LightType.builder()
                .lightTypeName(lightTypeName)
                .build();
        lightTypeRepository.save(lightType);
        return "redirect:/admin/addType";
    }

    @GetMapping("/typeList")
    public String typeListPage(Model model){
        List<LightType> lightTypes = lightTypeRepository.findAll();
        model.addAttribute("lightTypes",lightTypes);
        return "typeList";
    }

    @GetMapping("/deleteType/{id}")
    public String deleteType(@PathVariable("id") Long id) {
        try{
            lightTypeRepository.deleteById(id);
        }catch (Throwable e){
            return "redirect:/admin/typeInfo";
        }
        return "redirect:/admin/typeList";
    }
    @GetMapping("/typeInfo")
    public String typeInfoPage(){
        return "typeinfo";
    }
    @GetMapping("/editType/{id}")
    public String editTypePage(@PathVariable("id") Long id, Model model) {
        Optional<LightType> lightTypeOptional = lightTypeRepository.findById(id);
        if (lightTypeOptional.isEmpty()) {
            return "redirect:/admin/typeList";
        }
        model.addAttribute("lightType", lightTypeOptional.get());
        return "typeEdit";
    }
    @PostMapping("/editType")
    public String editType(@ModelAttribute LightType lightType) {
        Optional<LightType> existingType = lightTypeRepository.findById(lightType.getId());
        if (existingType.isPresent()) {
            LightType updatedType = existingType.get();
            updatedType.setLightTypeName(lightType.getLightTypeName());
            lightTypeRepository.save(updatedType);
        }
        return "redirect:/admin/typeList";
    }
    @GetMapping("/addLight")
    public String addLightPage(Model model){
        List<LightType> lightTypes = lightTypeRepository.findAll();
        model.addAttribute("lightTypes", lightTypes);
        return "addLight";
    }

    @PostMapping("/addLight")
    public String addLight(@RequestParam("lightName") String lightName,
                           @RequestParam("lightPower") String lightPower,
                           @RequestParam("lightSupplyVoltage") String lightSupplyVoltage,
                           @RequestParam("lightColorTemperature") String lightColorTemperature,
                           @RequestParam("lightDegreeOfProtection") String lightDegreeOfProtection,
                           @RequestParam("lightTemperatureRegime") String lightTemperatureRegime,
                           @RequestParam("lightOveralDimenssions") String lightOveralDimenssions,
                           @RequestParam("lightMass") String lightMass,
                           @RequestParam("lightCorpusMaterial") String lightCorpusMaterial,
                           @RequestParam("lightImage") MultipartFile imageFile,
                           @RequestParam("lightType") Long lightTypeId) throws IOException {
        Optional<LightType> lightTypeOptional = lightTypeRepository.findById(lightTypeId);
        if(lightTypeOptional.isEmpty()){
            return "redirect:/admin/addLight";
        }
        Light light = new Light();
        light.setLightName(lightName);
        light.setLightPower(lightPower);
        light.setLightSupplyVoltage(lightSupplyVoltage);
        light.setLightColorTemperature(lightColorTemperature);
        light.setLightDegreeOfProtection(lightDegreeOfProtection);
        light.setLightTemperatureRegime(lightTemperatureRegime);
        light.setLightOveralDimenssions(lightOveralDimenssions);
        light.setLightMass(lightMass);
        light.setLightCorpusMaterial(lightCorpusMaterial);
        light.setLightType(lightTypeOptional.get());

        RestTemplate restTemplate = new RestTemplate();
        String serverUrl = "http://217.18.62.76:8081/upload";

        String originalFileName = imageFile.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + fileExtension;

        FileSystemResource resource = new FileSystemResource(convert(imageFile, fileName));

        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("file", resource);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);

        ResponseEntity<String> response = restTemplate.exchange(serverUrl, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String imageUrl = "http://217.18.62.76:8000/uploads/" + fileName;
            light.setLightImageLink(imageUrl);
            lightRepository.save(light);
        }

        return "redirect:/admin/addLight";
    }

    private File convert(MultipartFile file, String fileName) throws IOException {
        File convFile = new File(fileName);
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }




    @GetMapping("/lightList")
    public String lightListPage(Model model){
        List<Light> lights = lightRepository.findAll();
        model.addAttribute("lights",lights);
        return "lightList";
    }
    @GetMapping("/deleteLight/{id}")
    public String deleteLight(@PathVariable Long id) {
        try{
            lightRepository.deleteById(id);
        }catch (Throwable e){
            return "redirect:/admin/status";
        }
        return "redirect:/admin/lightList";
    }
    @GetMapping("/status")
    public String statusPage(){
        return "status";
    }
    @GetMapping("/editLight/{id}")
    public String editLightPage(@PathVariable Long id, Model model) {
        Optional<Light> lightOptional = lightRepository.findById(id);
        if(lightOptional.isEmpty()){
            return "redirect:/admin/lightList";
        }
        List<LightType> lightTypes = lightTypeRepository.findAll();
        model.addAttribute("lightTypes", lightTypes);
        model.addAttribute("light",lightOptional.get());
        return "editLight";
    }
    @PostMapping("/updateLight")
    public String updateLight(@RequestParam("lightName") String lightName,
                              @RequestParam("lightPower") String lightPower,
                              @RequestParam("lightSupplyVoltage") String lightSupplyVoltage,
                              @RequestParam("lightColorTemperature") String lightColorTemperature,
                              @RequestParam("lightDegreeOfProtection") String lightDegreeOfProtection,
                              @RequestParam("lightTemperatureRegime") String lightTemperatureRegime,
                              @RequestParam("lightOveralDimenssions") String lightOveralDimenssions,
                              @RequestParam("lightMass") String lightMass,
                              @RequestParam("lightCorpusMaterial") String lightCorpusMaterial,
                              @RequestParam("lightImage") MultipartFile imageFile,
                              @RequestParam("lightType") Long lightTypeId,
                              @RequestParam("lightId") Long lightId) throws IOException {
        Optional<LightType> lightTypeOptional = lightTypeRepository.findById(lightTypeId);
        Optional<Light> lightOptional = lightRepository.findById(lightId);
        if(lightTypeOptional.isEmpty() || lightOptional.isEmpty()){
            return "redirect:/admin/addLight";
        }
        Light light = lightOptional.get();
        light.setLightName(lightName);
        light.setLightPower(lightPower);
        light.setLightSupplyVoltage(lightSupplyVoltage);
        light.setLightColorTemperature(lightColorTemperature);
        light.setLightDegreeOfProtection(lightDegreeOfProtection);
        light.setLightTemperatureRegime(lightTemperatureRegime);
        light.setLightOveralDimenssions(lightOveralDimenssions);
        light.setLightMass(lightMass);
        light.setLightCorpusMaterial(lightCorpusMaterial);
        light.setLightType(lightTypeOptional.get());

       if(imageFile!=null && !imageFile.isEmpty()){
           RestTemplate restTemplate = new RestTemplate();
           String serverUrl = "http://217.18.62.76:8081/upload";

           String originalFileName = imageFile.getOriginalFilename();
           String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
           String fileName = UUID.randomUUID().toString() + fileExtension;

           FileSystemResource resource = new FileSystemResource(convert(imageFile, fileName));

           MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
           bodyMap.add("file", resource);

           HttpHeaders headers = new HttpHeaders();
           headers.setContentType(MediaType.MULTIPART_FORM_DATA);

           HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);

           ResponseEntity<String> response = restTemplate.exchange(serverUrl, HttpMethod.POST, requestEntity, String.class);

           if (response.getStatusCode() == HttpStatus.OK) {
               String imageUrl = "http://217.18.62.76:8000/uploads/" + fileName;
               light.setLightImageLink(imageUrl);
               lightRepository.save(light);
           }
       }else {
           lightRepository.save(light);
       }

        return "redirect:/admin/lightList";
    }

    @GetMapping("/show")
    public String infoPage(Model model){
        List<UserLight> userLights = userLightRepository.findByUserLightCompletedFalse();
        model.addAttribute("userLights",userLights);
        return "info";
    }
    @GetMapping("/сhangeStatus/{id}")
    public String updateStatus(@PathVariable Long id) {
        userLightRepository.deleteById(id);
        return "redirect:/admin/show";
    }
}
