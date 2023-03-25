package business;

import java.util.ArrayList;
import java.util.List;

public class Recipe extends Node{
    private List<String> listIngredient;

    public Recipe(String name) {
        super(name);
        listIngredient = new ArrayList<>();
    }


    public List<String> getListIngredient() {
        return listIngredient;
    }

    public void setListIngredient(List<String> listIngredient) {
        this.listIngredient = listIngredient;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
