package at.fhtw.mtcg.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class TradingDeal {
    @JsonAlias({"Id"})
    private String id;
    @JsonAlias({"CardToTrade"})
    private String cardToTrade;
    @JsonAlias({"Type"})
    private CardType type;
    @JsonAlias({"MinimumDamage"})
    private float minimumDamage;

    public TradingDeal() {}

    public TradingDeal(String id, String cardToTrade, String type, float minimumDamage) {
        this.id = id;
        this.cardToTrade = cardToTrade;
        this.type = CardType.valueOf(type);
        this.minimumDamage = minimumDamage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardToTrade() {
        return cardToTrade;
    }

    public void setCardToTrade(String cardToTrade) {
        this.cardToTrade = cardToTrade;
    }

    public String getType() {
        return type.toString();
    }

    public void setType(String type) {
        this.type = CardType.valueOf(type);
    }

    public float getMinimumDamage() {
        return minimumDamage;
    }

    public void setMinimumDamage(float minimumDamage) {
        this.minimumDamage = minimumDamage;
    }
}
