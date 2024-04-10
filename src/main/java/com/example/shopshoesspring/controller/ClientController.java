package com.example.shopshoesspring.controller;

import com.example.shopshoesspring.entity.Light;
import com.example.shopshoesspring.entity.LightType;
import com.example.shopshoesspring.entity.User;
import com.example.shopshoesspring.entity.UserLight;
import com.example.shopshoesspring.repository.LightRepository;
import com.example.shopshoesspring.repository.LightTypeRepository;
import com.example.shopshoesspring.repository.UserLightRepository;
import com.example.shopshoesspring.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/client")
public class ClientController {

    private final UserService userService;
    private final LightRepository lightRepository;
    private final LightTypeRepository lightTypeRepository;
    private final UserLightRepository userLightRepository;

    @GetMapping("/home")
    public String homePage() {
        return "сhome";
    }

    @GetMapping("/catalog")
    public String catalogPage(Model model) {
        List<LightType> lightTypes = lightTypeRepository.findAll();
        model.addAttribute("categories", lightTypes);

        return "catalog";
    }

    @GetMapping("/category/{id}")
    public String showCategoryDetails(@PathVariable("id") Long id, Model model) {
        LightType category = lightTypeRepository.findById(id).orElse(null);
        if (category == null) {
            return "redirect:/client/catalog";
        }
        List<Light> lights = lightRepository.findByLightTypeId(category.getId());
        model.addAttribute("lights", lights);
        return "lights";
    }

    @GetMapping("/light/{id}")
    public String showLightDetails(@PathVariable("id") Long id, Model model) {
        Light light = lightRepository.findById(id).orElse(null);
        if (light == null) {
            return "redirect:/client/catalog";
        }
        model.addAttribute("light", light);
        return "light_details";
    }

    @PostMapping("/add")
    public String addOrder(HttpServletRequest request, Principal principal) {
        String mobileNumber = request.getParameter("mobileNumber");
        String message = request.getParameter("message");
        Long lightId = Long.valueOf(request.getParameter("lightId"));
        String login = principal.getName();
        Optional<User> userOptional = userService.findUserByUserLogin(login);
        Optional<Light> lightOptional = lightRepository.findById(lightId);
        if (userOptional.isEmpty() || lightOptional.isEmpty()) {
            return "redirect:/client/er";
        }
        UserLight userLight = UserLight.builder()
                .userLightCompleted(false)
                .user(userOptional.get())
                .light(lightOptional.get())
                .phone(mobileNumber)
                .message(message)
                .date(new Date(new java.util.Date().getTime()))
                .build();
        userLightRepository.save(userLight);

        return "redirect:/client/sc";
    }
    @GetMapping("/sc")
    public String successPage(){
        return "sc";
    }
    @GetMapping("er")
    public String errorPage(){
        return "er";
    }

    @GetMapping("/about")
    public String aboutPage(){
        return "about";
    }
    @GetMapping("/contacts")
    public String contactsPage(){
        return "contacts";
    }
}