package com.bitboxserver.demo.controllers;

import com.bitboxserver.demo.Repositories.RoleRepository;
import com.bitboxserver.demo.Repositories.SupplierRepository;
import com.bitboxserver.demo.models.dto.ItemDto;
import com.bitboxserver.demo.models.dto.PriceReductionInsert;
import com.bitboxserver.demo.models.dto.SupplierInsert;
import com.bitboxserver.demo.models.entities.Item;
import com.bitboxserver.demo.models.entities.Supplier;
import com.bitboxserver.demo.payload.response.MessageResponse;
import com.bitboxserver.demo.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class ItemController {
    @Autowired
    ItemService itemService;
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/items")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public List<Item> getItems(){
        return itemService.loadAllItems();
    }

    @GetMapping(path = "/items/{itemcode}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public Item getItemByItemCode(@PathVariable("itemcode") Long itemCode) throws IOException {
        return itemService.loadByItemCode(itemCode);
    }

    @PostMapping("/items")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity createItem(@RequestBody ItemDto item){

        itemService.createItem(item);
        return ResponseEntity.ok(new MessageResponse("Item Registered successfully!"));
    }
    @PutMapping("/items")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity updateItem(@RequestBody Item item){
        try {
            if(itemService.updateItem(item) == 1){
                return ResponseEntity.ok(new MessageResponse("Item updated succesfully"));
            }else {
                return ResponseEntity.badRequest().body(new MessageResponse("Item failed to update"));
            }
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid Body"));
        }
    }
    @PutMapping("/item-insert-supplier")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity updateItemSupplier(@RequestBody SupplierInsert supplierInsert) throws IOException {
        if(itemService.updateItemSuppliers(supplierInsert)){
            return ResponseEntity.ok(new MessageResponse("Supplier inserted Successfully!"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("invalid entry"));
    }
    @PutMapping("/item-insert-price-reduction")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity updateItemPriceReduction(@RequestBody PriceReductionInsert priceReductionInsert) throws IOException {
        if(itemService.updateItemPriceReductions(priceReductionInsert)){
            return ResponseEntity.ok(new MessageResponse("Price Reduction inserted Successfully!"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("invalid entry"));
    }
}
