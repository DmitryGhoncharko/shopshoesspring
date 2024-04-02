package com.example.shopshoesspring.service;

import com.example.shopshoesspring.entity.PayedUserProduct;
import com.example.shopshoesspring.entity.Product;
import com.example.shopshoesspring.entity.User;
import com.example.shopshoesspring.entity.UserProduct;
import com.example.shopshoesspring.error.CannotAddToBucketError;
import com.example.shopshoesspring.error.CannotShowUserProductBucketError;
import com.example.shopshoesspring.repository.PayedUserProductRepository;
import com.example.shopshoesspring.repository.ProductRepository;
import com.example.shopshoesspring.repository.UserProductRepository;
import com.example.shopshoesspring.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserProductRepository userProductRepository;
    private final UserRepository userRepository;
    private final PayedUserProductRepository payedUserProductRepository;
    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }
    @Transactional
    public void addToBucket(Long productId, Long count, Principal principal){
        String userLogin = principal.getName();
        Optional<User> userOptional = userRepository.findUserByUserLogin(userLogin);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            Optional<Product> productOptional = productRepository.findById(productId);
            if(productOptional.isPresent()){
                Product product = productOptional.get();
                double finalPrice = product.getProductPrice()*count;
                UserProduct userProduct = UserProduct.builder().
                        user(user).
                        product(product).
                        productCount(count).
                        productFinalPrice(finalPrice).
                        payed(false).
                        build();
                userProductRepository.save(userProduct);
                return;
            }
        }
        throw new CannotAddToBucketError("Product id " + productId + " principal " + principal);
    }
    @Transactional
    public List<UserProduct> getUserProductsByUserId(Principal principal){
        List<UserProduct> userProducts = new ArrayList<>();
        String userLogin = principal.getName();
        Optional<User> userOptional = userRepository.findUserByUserLogin(userLogin);
        if(userOptional.isPresent()){
            userProducts = userProductRepository.findUserProductByUserIdAndPayedFalse(userOptional.get().getId());
        }
        return userProducts;
    }

    public void deleteUserProductById(Long userProductId){
        userProductRepository.deleteById(userProductId);
    }


    @Transactional
    public void pay(Long userProductId){
        Optional<UserProduct> optionalUserProduct = userProductRepository.findById(userProductId);
        if(optionalUserProduct.isPresent()){
            UserProduct userProduct = optionalUserProduct.get();
            if(userProduct.getUser().getUserBalance()>=userProduct.getProductFinalPrice() && userProduct.getProduct().getProductCount()>=userProduct.getProductCount()){
                User user = userProduct.getUser();
                user.setUserBalance(userProduct.getUser().getUserBalance()-userProduct.getProductFinalPrice());
                userRepository.save(user);
                Product product = userProduct.getProduct();
                product.setProductCount(userProduct.getProduct().getProductCount()-userProduct.getProductCount());
                productRepository.save(product);
                PayedUserProduct payedUserProduct =  PayedUserProduct.builder().
                        userProduct(userProduct).
                        payedDate(new Date(new java.util.Date().getTime())).
                        build();
                userProduct.setPayed(true);
                userProductRepository.save(userProduct);
                payedUserProductRepository.save(payedUserProduct);
                return;
            }
        }
        throw new IllegalArgumentException();
    }
    public List<PayedUserProduct> findAll(){
        return payedUserProductRepository.findAll();
    }
    @Transactional
    public void updateProduct(String name, String cat, String price, String country, String color, String mat, String type, String num, String id){
        Optional<Product> productOptional = productRepository.findById(Long.valueOf(id));
        if(productOptional.isPresent()){
            Product product = productOptional.get();
            product.setProductName(name);
            product.setProductCategory(cat);
            product.setProductPrice(Double.valueOf(price));
            product.setProductManufactureCountry(country);
            product.setProductColor(color);
            product.setProductMaterial(mat);
            product.setProductType(type);
            product.setProductCount(Long.valueOf(num));
            productRepository.save(product);
        }
    }
    public void createProduct(String name, String cat, String price, String country, String color, String mat, String type, String num){
            Product product = new Product();
            product.setProductName(name);
            product.setProductCategory(cat);
            product.setProductPrice(Double.valueOf(price));
            product.setProductManufactureCountry(country);
            product.setProductColor(color);
            product.setProductMaterial(mat);
            product.setProductType(type);
            product.setProductCount(Long.valueOf(num));
            product.setProductDefaultPrice(2.2);
            productRepository.save(product);
        }
}
