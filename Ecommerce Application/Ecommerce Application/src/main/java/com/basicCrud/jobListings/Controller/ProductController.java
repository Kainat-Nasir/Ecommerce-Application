package com.basicCrud.jobListings.Controller;

import com.basicCrud.jobListings.entity.Product;
import com.basicCrud.jobListings.model.productDTO;
import com.basicCrud.jobListings.service.JwtService;
import com.basicCrud.jobListings.service.ProductService;
import com.basicCrud.jobListings.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService service;
    @Autowired
    JwtService jwtService;

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return service.getProducts();
    }

    @GetMapping("/id/{prodId}")
    public Product getProductById(@PathVariable int prodId) {
        return service.getProductById(prodId);
    }

    //products by userid
    @GetMapping("/userid/{id}")
    public List<Product> findByUserId(@PathVariable Long id){
        return service.findByUserId(id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<?> addProduct(@RequestPart String name,
                                        @RequestPart String description,
                                        @RequestPart String price,
                                        @RequestPart String productAvailable,
                                        @RequestPart String stockQuantity,
                                        @RequestPart MultipartFile image) {
        try {
            Long userID = SecurityUtil.getCurrentUserId();

            productDTO productModel = new productDTO();
            productModel.setName(name);
            productModel.setDescription(description);
            productModel.setPrice(Double.valueOf(price));
            productModel.setProductAvailable(Boolean.parseBoolean(productAvailable));
            productModel.setStockQuantity(Integer.valueOf(stockQuantity));
            productModel.setUserID(userID); // Set the user ID from the JWT token

            String imagePath = null;
            if (image != null && !image.isEmpty()) {
                try {
                    String directoryPath = "C:\\Users\\Kainat Nasir\\Practice Projects\\Ecommerce Application\\Frontend\\Ecommerce_Application\\src\\assets";
                    File directory = new File(directoryPath);
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }
                    String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                    File file = new File(directory, fileName);
                    image.transferTo(file); // Save the image

                    // Set the image path
                    imagePath = file.getAbsolutePath();
                } catch (IOException e) {
                    e.printStackTrace(); // Log the exception for debugging
                    return new ResponseEntity<>("Failed to save image", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            // Set the image path in the productDTO
            productModel.setImageUrl(imagePath);

            // Add the product using the service
            Product product = service.addProduct(productModel);
            return new ResponseEntity<>(product, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            return new ResponseEntity<>("An error occurred while adding the product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //update product
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public Product updateProduct(@PathVariable("id") int id,@RequestPart String name,
                                 @RequestPart String description,
                                 @RequestPart String price,
                                 @RequestPart String productAvailable,
                                 @RequestPart String stockQuantity,
                                 @RequestPart MultipartFile image) throws Exception {

            Long userID = SecurityUtil.getCurrentUserId();

            productDTO productModel = new productDTO();
            productModel.setName(name);
            productModel.setDescription(description);
            productModel.setPrice(Double.valueOf(price));
            productModel.setProductAvailable(Boolean.parseBoolean(productAvailable));
            productModel.setStockQuantity(Integer.valueOf(stockQuantity));
            productModel.setUserID(userID); // Set the user ID from the JWT token

            String imagePath = null;
            if (image != null && !image.isEmpty()) {
                try {
                    String directoryPath = "C:\\Users\\Kainat Nasir\\Practice Projects\\Ecommerce Application\\Frontend\\Ecommerce_Application\\src\\assets";
                    File directory = new File(directoryPath);
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }
                    String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                    File file = new File(directory, fileName);
                    image.transferTo(file); // Save the image

                    // Set the image path
                    imagePath = file.getAbsolutePath();
                } catch (IOException e) {
                    e.printStackTrace(); // Log the exception for debugging
                    throw new Exception("Failed to save image");
                }
            }

            // Set the image path in the productDTO
            productModel.setImageUrl(imagePath);
            return service.updateProduct(id,productModel);
    }


    //delete product
    @DeleteMapping("/delete/{prodId}")
    @PreAuthorize("hasAuthority('Admin')")
    public void deleteProduct(@PathVariable int prodId) {
        service.deleteProduct(prodId);
    }

}

