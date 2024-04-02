package com.example.shopshoesspring.controller;

import com.example.shopshoesspring.entity.User;
import com.example.shopshoesspring.entity.UserProduct;
import com.example.shopshoesspring.service.ProductService;
import com.example.shopshoesspring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/client")
public class ClientController {
    private final ProductService productService;
    private final UserService userService;
    @GetMapping("/home")
    public String homePage(){
        return "сhome";
    }
    @GetMapping("/products")
    public String getAllProducts(Model model){
        model.addAttribute("products",productService.findAllProducts());
        return "product";
    }
    @PostMapping("/addToBucket")
    public String addToBucket(@RequestParam("quantity") Long count, @RequestParam("productId") Long productId, Principal principal){
        productService.addToBucket(productId,count,principal);
        return "redirect:/client/products";
    }

    @PostMapping("/deleteFromBucket")
    public String deleteFromBucket(@RequestParam("userProductId") Long userProductId){
        productService.deleteUserProductById(userProductId);
        return "redirect:/client/bucket";
    }

    @PostMapping("/pay")
    public String pay(@RequestParam("userProductId") Long userProductId){
        productService.pay(userProductId);
        return "redirect:/client/bucket";
    }


    @GetMapping("/bucket")
    public String bucketPage(Model model, Principal principal){
       List<UserProduct> userProductList = productService.getUserProductsByUserId(principal);
        Optional<User> userOptional = userService.findUserByUserLogin(principal.getName());
        model.addAttribute("balance",userOptional.get().getUserBalance());
        model.addAttribute("userProduct",userProductList);
       return "bucket";
    }

}
