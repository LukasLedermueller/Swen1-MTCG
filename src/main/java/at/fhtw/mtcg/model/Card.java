package at.fhtw.mtcg.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Card {
    @JsonAlias({"Id"})
    private String id;
    @JsonAlias({"Name"})
    private CardName name;
    @JsonAlias({"Damage"})
    private float damage;

    public Card() {}

    public Card(String id, String name, float damage) {
        this.id = id;
        this.name = CardName.valueOf(name);
        this.damage = damage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name.toString();
    }

    public void setName(CardName name) {
        this.name = name;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }
}
