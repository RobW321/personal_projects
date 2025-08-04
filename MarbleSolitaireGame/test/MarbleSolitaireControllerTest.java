import org.junit.Test;
import java.io.StringReader;
import cs3500.marblesolitaire.controller.MarbleSolitaireController;
import cs3500.marblesolitaire.controller.MarbleSolitaireControllerImpl;
import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;
import static org.junit.Assert.assertEquals;

/**
 * Tests for MarbleSolitaireControllerImpl
 */
public class MarbleSolitaireControllerTest {

  /**
   * Tests the playGame method in the MarbleSolitaireControllerImpl class
   */
  @Test
  public void testGameQuit() {
    Appendable appendable = new StringBuilder();
    EnglishSolitaireModel model = new EnglishSolitaireModel();
    MarbleSolitaireTextView view = new MarbleSolitaireTextView(model, appendable);
    Readable readable = new StringReader("q");
    MarbleSolitaireController controller =
            new MarbleSolitaireControllerImpl(model, view, readable);
    controller.playGame();

    String expected = "Game Quit!";

    String[] lines = appendable.toString().split("\n");
    StringBuilder printedMessage = new StringBuilder();
    appendable.toString().split("\n");

    printedMessage.append(lines[9]);

    assertEquals(expected, printedMessage.toString());
  }

  /**
   * Tests the playGame method in the MarbleSolitaireControllerImpl class
   */
  @Test
  public void testPlayGame() {
    Appendable appendable = new StringBuilder();
    EnglishSolitaireModel model = new EnglishSolitaireModel();
    MarbleSolitaireTextView view = new MarbleSolitaireTextView(model, appendable);
    Readable readable = new StringReader("2 4 4 4 q");
    MarbleSolitaireController controller =
            new MarbleSolitaireControllerImpl(model, view, readable);
    controller.playGame();

    String expected = "Score: 32";

    String[] lines = appendable.toString().split("\n");
    StringBuilder printedMessage = new StringBuilder();
    appendable.toString().split("\n");

    printedMessage.append(lines[7]);

    assertEquals(expected, printedMessage.toString());
  }

  /**
   * Tests the exception in the MarbleSolitaireControllerImpl constructor
   */
  @Test (expected = IllegalArgumentException.class)
  public void testControllerException() {
    new MarbleSolitaireControllerImpl(null, null, null);
  }
}
