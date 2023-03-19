package api.model;

import java.util.List;

public class OrderSerial {
    private List<String> ingredients;

    public OrderSerial(List<String> ingredients){
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
