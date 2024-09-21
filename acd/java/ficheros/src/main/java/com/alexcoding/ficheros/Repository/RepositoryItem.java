package com.alexcoding.ficheros.Repository;

import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.ArrayList;
import com.alexcoding.ficheros.Entities.Item;
import java.util.Objects;

@Repository
public class RepositoryItem {
    protected List<Item> listaItems = new ArrayList<>();
    

    public RepositoryItem() {
        listaItems.add(new Item(1, "Espada", "Espada de acero"));
        listaItems.add(new Item(2, "Escudo", "Escudo de acero"));
        listaItems.add(new Item(3, "Arco", "Arco de acero"));
        listaItems.add(new Item(4, "Flecha", "Flecha de acero"));
        listaItems.add(new Item(5, "Pocion", "Pocion de vida"));
        listaItems.add(new Item(6, "Pocion", "Pocion de mana"));
        listaItems.add(new Item(7, "Pocion", "Pocion de fuerza"));
        listaItems.add(new Item(8, "Pocion", "Pocion de velocidad"));
        listaItems.add(new Item(9, "Pocion", "Pocion de salud"));
        listaItems.add(new Item(10, "Pocion", "Pocion de mana"));
        listaItems.add(new Item(11, "Pocion", "Pocion de fuerza"));
        
    }

    public RepositoryItem(List<Item> listaItems) {
        this.listaItems = listaItems;
    }

    public List<Item> getListaItems() {
        return this.listaItems;
    }

    public void setListaItems(List<Item> listaItems) {
        this.listaItems = listaItems;
    }

    public RepositoryItem listaItems(List<Item> listaItems) {
        setListaItems(listaItems);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RepositoryItem)) {
            return false;
        }
        RepositoryItem repositoryItem = (RepositoryItem) o;
        return Objects.equals(listaItems, repositoryItem.listaItems);
    }

    @Override
    public String toString() {
        return "{" +
            " listaItems='" + getListaItems() + "'" +
            "}";
    }

}