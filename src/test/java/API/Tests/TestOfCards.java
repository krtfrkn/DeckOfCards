package API.Tests;

import API.POJO.*;
import API.TestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class TestOfCards extends TestBase {

    static String deck_id;
    static DrawACard drawACard;
    static int numberOfDeck = 1;
    static int drawingCards = 52;
    Integer totalAmountOfCards = 52 * numberOfDeck;

    @Test
    @Order(1)
    @DisplayName("Shuffle the Cards")
    public void shuffleTheCards() {
        Response response = given().accept(ContentType.JSON).and().queryParam("deck_count", numberOfDeck).get("/new/shuffle/");

        assertEquals(200, response.statusCode(), "Status code");
        assertEquals("application/json", response.contentType(), "Content Type");
        assertEquals(true, response.path("success"), "Success message");
        assertEquals(totalAmountOfCards, response.path("remaining"), "Remaining Total Cards");
        assertEquals(true, response.path("shuffled"), "Shuffled message");

        deck_id = response.path("deck_id");

    }

    @Test
    @Order(2)
    @DisplayName("Draw the Cards")
    public void drawTheCards() {
        Response response = given().accept(ContentType.JSON).and().queryParam("count", drawingCards).get("/" + deck_id + "/draw/");

        assertEquals(200, response.statusCode(), "Status code");
        assertEquals("application/json", response.andReturn().contentType(), "Content Type");

        drawACard = response.body().as(DrawACard.class);
    }

    @Test
    @Order(3)
    @DisplayName("Verify the remaining Cards")
    public void drawACardBodyAssertions() {

        assertEquals(true, drawACard.getSuccess(), "Success message is false");
        assertEquals(deck_id, drawACard.getDeckId(), "deck_id is wrong");
        assertEquals(drawingCards, drawACard.getCards().size(), "Number of Drawing Cards is wrong");
        assertEquals((totalAmountOfCards - drawingCards), drawACard.getRemaining(), "Number of Remaining Cards is wrong");

        for (int i = 0; i < drawACard.getCards().size(); i++) {

            if (drawACard.getCards().get(i).getCode().equals("AD")) {
                assertTrue(drawACard.getCards().get(i).getImage().contains("aceDiamonds"));
                assertTrue(drawACard.getCards().get(i).getImages().getSvg().contains("aceDiamonds"));
                assertTrue(drawACard.getCards().get(i).getImages().getPng().contains("aceDiamonds"));
            } else {
                assertTrue(drawACard.getCards().get(i).getImage().contains(drawACard.getCards().get(i).getCode()));
                assertTrue(drawACard.getCards().get(i).getImages().getSvg().contains(drawACard.getCards().get(i).getCode()));
                assertTrue(drawACard.getCards().get(i).getImages().getPng().contains(drawACard.getCards().get(i).getCode()));
            }

            if (drawACard.getCards().get(i).getValue().equals("10")) {
                assertTrue(drawACard.getCards().get(i).getCode().equals("" + drawACard.getCards().get(i).getValue().charAt(1) + drawACard.getCards().get(i).getSuit().charAt(0)));
            } else {
                assertTrue(drawACard.getCards().get(i).getCode().equals("" + drawACard.getCards().get(i).getValue().charAt(0) + drawACard.getCards().get(i).getSuit().charAt(0)));
            }

        }

    }

}

