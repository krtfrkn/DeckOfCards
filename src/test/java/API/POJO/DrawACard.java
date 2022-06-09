package API.POJO;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrawACard {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("deck_id")
    @Expose
    private String deckId;
    @SerializedName("cards")
    @Expose
    private List<Cards> cards;
    @SerializedName("remaining")
    @Expose
    private Integer remaining;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getDeckId() {
        return deckId;
    }

    public void setDeckId(String deckId) {
        this.deckId = deckId;
    }

    public List<Cards> getCards() {
        return cards;
    }

    public void setCards(List<Cards> cards) {
        this.cards = cards;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

}
