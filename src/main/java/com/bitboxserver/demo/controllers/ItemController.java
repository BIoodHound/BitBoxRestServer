package com.bitboxserver.demo.controllers;

import com.bitboxserver.demo.Repositories.RoleRepository;
import com.bitboxserver.demo.Repositories.SupplierRepository;
import com.bitboxserver.demo.models.dto.ItemDto;
import com.bitboxserver.demo.models.dto.PriceReductionInsert;
import com.bitboxserver.demo.models.dto.SupplierInsert;
import com.bitboxserver.demo.models.entities.Item;
import com.bitboxserver.demo.payload.response.MessageResponse;
import com.bitboxserver.demo.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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



    @PutMapping(path = "/items/{itemcode}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity deactivateItem(@PathVariable("itemcode") Long itemCode) throws IOException {

        if (itemService.deactivateItem(itemCode)){
            return ResponseEntity.ok(new MessageResponse("Item deactivated successfully!"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Not a valid item"));
    }

    @PostMapping("/items")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity createItem(@RequestBody ItemDto item){
        if(itemService.createItem(item)){
            return ResponseEntity.ok(new MessageResponse("Item Registered successfully!"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("No description found"));
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
    @DeleteMapping(path = "/remove-supplier/{supplierId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity removeSupplier(@PathVariable("supplierId") Long supplierId){
        if(itemService.deleteItemSupplier(supplierId)){
            return ResponseEntity.ok(new MessageResponse("Supplier Removed Successfully!"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("invalid entry"));
    }
    @DeleteMapping(path = "/remove-price-reduction/{reductionId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity removePriceReduction(@PathVariable("reductionId") Long priceReduction){
        if(itemService.deletePriceReductions(priceReduction)){
            return ResponseEntity.ok(new MessageResponse("Price Reduction Removed Successfully!"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("invalid entry"));
    }

}
