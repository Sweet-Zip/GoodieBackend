package com.example.uploadimage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ProductUploadController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/uploadFile")
    public ResponseEntity<Product> uploadFile(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam("productName") String productName,
            @RequestParam("productDetail") String productDetail,
            @RequestParam("price") double price,
            @RequestParam("category") String category)
            throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();

        String fileCode = ProductUploadUtil.saveFile(fileName, multipartFile);
        //Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product();
        product.setProductName(productName);
        product.setPrice(price);
        product.setProductDetail(productDetail);
        product.setFileName(fileName);
        product.setSize(size);
        product.setFileCode(fileCode);
        product.setDownloadUri("/downloadFile/" + fileCode);
        product.setCategory(category);
        productRepository.save(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/product/update/{id}")
    public ResponseEntity<Product> updateProductById(
            @PathVariable long id,
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam("productName") String productName,
            @RequestParam("productDetail") String productDetail,
            @RequestParam("price") double price,
            @RequestParam("category") String category) throws IOException {

        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();

        String fileCode = ProductUploadUtil.saveFile(fileName, multipartFile);
        Optional<Product> fileUploadResponse1 = productRepository.findById(id);
        Product fileUploadResponse2 = fileUploadResponse1.get();
        File file = new File("D:\\Spring Boot\\uploadimage1\\Files-Upload\\" + fileUploadResponse2.getFileCode() + " - " + fileUploadResponse2.getFileName());
        Product product = optionalProduct.get();
        product.setProductName(productName);
        product.setProductDetail(productDetail);
        product.setPrice(price);
        product.setFileName(fileName);
        product.setSize(size);
        product.setFileCode(fileCode);
        product.setDownloadUri("/downloadFile/" + fileCode);
        product.setCategory(category);
        productRepository.save(product);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteImg(@PathVariable("id") long id) {
        Optional<Product> fileUploadResponse1 = productRepository.findById(id);
        Product fileUploadResponse2 = fileUploadResponse1.get();
        File file = new File("D:\\Spring Boot\\uploadimage1\\Files-Upload\\" + fileUploadResponse2.getFileCode() + " - " + fileUploadResponse2.getFileName());
        System.out.println(fileUploadResponse2.getFileCode() + " - " + fileUploadResponse2.getFileName());
        file.delete();
        productRepository.deleteById(id);
    }
}
