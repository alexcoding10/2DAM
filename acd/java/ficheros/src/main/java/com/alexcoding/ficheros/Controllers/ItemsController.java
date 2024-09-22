package com.alexcoding.ficheros.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import com.alexcoding.ficheros.Entities.Item;
import com.alexcoding.ficheros.Services.ItemsServicio;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class ItemsController {
    
    @Autowired
    ItemsServicio is;

    
    @GetMapping("/items")
    @ResponseBody
    public List<Item> getAllItem() {
        return is.getItems();
    }
    
    @PostMapping("/item")
    @ResponseBody
    public Item postMethodName(@RequestBody Item item) {
        is.addItem(item);
        return item;
    }
    
}
