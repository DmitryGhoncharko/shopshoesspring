package com.example.shopshoesspring.controller;

import com.example.shopshoesspring.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;
    @GetMapping("/home")
    public String homePage(){
        return "ahome";
    }
    @GetMapping("/payed")
    public String payedPage(Model model){
        model.addAttribute("payed",productService.findAll());
        return "payed";
    }
    @GetMapping("/products")
    public String getAllProducts(Model model){
        model.addAttribute("products",productService.findAllProducts());
        return "product";
    }
    @GetMapping("/create")
    public String createProduct(Model model){
        return "addproduct";
    }

    @PostMapping("/update")
    public String updateProduct(@RequestParam("productName") String name, @RequestParam("productCat") String cat,
                                @RequestParam("productPrice") String price, @RequestParam("productCountry") String country,
                                @RequestParam("productColor") String color, @RequestParam("productMat") String material,
                                @RequestParam("productType") String type, @RequestParam("productNum") String num, @RequestParam("productId") String id){
        productService.updateProduct(name,cat,price,country,color,material,type,num,id);
        return "redirect:/admin/products";
    }

    @PostMapping("/create")
    public String createProduct(@RequestParam("productName") String name, @RequestParam("productCat") String cat,
                                @RequestParam("productPrice") String price, @RequestParam("productCountry") String country,
                                @RequestParam("productColor") String color, @RequestParam("productMat") String material,
                                @RequestParam("productType") String type, @RequestParam("productNum") String num){
        productService.createProduct(name,cat,price,country,color,material,type,num);
        return "redirect:/admin/products";
    }
}
