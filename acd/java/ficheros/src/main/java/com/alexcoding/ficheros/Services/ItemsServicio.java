package com.alexcoding.ficheros.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexcoding.ficheros.Entities.Item;
import com.alexcoding.ficheros.Repository.RepositoryItem;

@Service
public class ItemsServicio {
    
    @Autowired
    RepositoryItem ri;

    public List<Item> getItems(){
        return ri.getListaItems();
    }

    public void addItem(Item i){
        ri.getListaItems().add(i);
    }

}
