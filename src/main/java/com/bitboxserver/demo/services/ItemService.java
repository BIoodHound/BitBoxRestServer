package com.bitboxserver.demo.services;

import com.bitboxserver.demo.Repositories.ItemRepository;
import com.bitboxserver.demo.Repositories.PriceReductionRepository;
import com.bitboxserver.demo.Repositories.SupplierRepository;
import com.bitboxserver.demo.models.dto.ItemDto;
import com.bitboxserver.demo.models.dto.PriceReductionInsert;
import com.bitboxserver.demo.models.dto.SupplierInsert;
import com.bitboxserver.demo.models.entities.Item;
import com.bitboxserver.demo.models.entities.PriceReduction;
import com.bitboxserver.demo.models.entities.Supplier;
import com.bitboxserver.demo.models.enums.EState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    PriceReductionRepository priceReductionRepository;

    public Boolean createItem(ItemDto item){
        if(item.getDescription()!=null){
            Item itemEntity = new Item();
            itemEntity.setDescription(item.getDescription());
            itemEntity.setPrice(item.getPrice());
            itemEntity.setState(EState.Active);
            itemEntity.setSuppliers(item.getSuppliers());
            itemEntity.setPriceReductions(item.getPriceReductions());
            itemEntity.setCreator(item.getCreator());
            itemRepository.save(itemEntity);
            return true;
        }
        return false;
    }

    public int updateItem(Item item) throws IOException {
        if(existByItemCode(item.getItemCode())){
            Item tempItem = loadByItemCode(item.getItemCode());
            tempItem.setItemCode(item.getItemCode());
            tempItem.setDescription(item.getDescription());
            tempItem.setPrice(item.getPrice());
            tempItem.setState(item.getState());
            tempItem.setCreationDate(item.getCreationDate());
            tempItem.setCreator(item.getCreator());

            itemRepository.save(tempItem);
            return 1;
        }
        return 0;
    }

    public Boolean updateItemSuppliers(SupplierInsert supplierInsert) throws IOException {
        if(existByItemCode(supplierInsert.getItemCode())){
            Item item = loadByItemCode(supplierInsert.getItemCode());

            for (Supplier supplier:supplierInsert.getSuppliers()) {
                for (Supplier supplier1:item.getSuppliers()){
                    if(supplier.getName().equals(supplier1.getName())){return false;}
                }
            }

            supplierRepository.saveAll(supplierInsert.getSuppliers());
            item.getSuppliers().addAll(supplierInsert.getSuppliers());

            itemRepository.save(item);
            return true;
        }
        return false;
    }

    public Boolean updateItemPriceReductions(PriceReductionInsert priceReductionInsert) throws IOException {
        if(existByItemCode(priceReductionInsert.getItemCode())){
            Item item = loadByItemCode(priceReductionInsert.getItemCode());

            priceReductionRepository.saveAll(priceReductionInsert.getPriceReductions());
            item.getPriceReductions().addAll(priceReductionInsert.getPriceReductions());

            itemRepository.save(item);
            return true;
        }
        return false;
    }

    public Boolean deleteItemSupplier(SupplierInsert supplierInsert) throws IOException {
        Long itemCode = supplierInsert.getItemCode();
        if(existByItemCode(itemCode)){
            Item item = loadByItemCode(itemCode);
            Set<Supplier> suppliers = supplierInsert.getSuppliers();

            item.getSuppliers().removeAll(suppliers);
            itemRepository.save(item);
        }
        return true;
    }
    public Boolean deletePriceReductions(PriceReductionInsert priceReductionInsert) throws IOException {
        Long itemCode = priceReductionInsert.getItemCode();
        if(existByItemCode(itemCode)){
            Item item = loadByItemCode(itemCode);
            Set<PriceReduction> priceReductions = priceReductionInsert.getPriceReductions();
            for(PriceReduction priceReduction: priceReductions){
                item.getPriceReductions().remove(priceReduction);
            }

            itemRepository.save(item);
            return true;
        }
        return false;
    }

    public Item loadByItemCode(Long itemCode) throws IOException {
        return itemRepository.findById(itemCode).orElseThrow(() -> new IOException("itemcode not found"));
    }

    public Boolean deactivateItem(Long itemCode) throws IOException {
        if(itemRepository.existsById(itemCode)){
            Item i = loadByItemCode(itemCode);
            i.setState(EState.Discontinued);
            updateItem(i);
            return true;
        }

        return false;

    }

    public List<Item> loadAllItems() {
        List<Item> temp = new ArrayList<>();

        for (Item item:itemRepository.findAll()) {
            Item itemTemp = new Item(
                    item.getItemCode(),
                    item.getDescription(),
                    item.getPrice(),
                    item.getState(),
                    item.getSuppliers(),
                    item.getPriceReductions(),
                    item.getCreationDate(),
                    item.getCreator());
            temp.add(itemTemp);
        }
        return temp;
    }

    public boolean existByItemCode(Long itemCode) {
        return itemRepository.existsById(itemCode);
    }
}
