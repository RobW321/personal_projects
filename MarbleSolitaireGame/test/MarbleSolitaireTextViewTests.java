import org.junit.Test;
import java.io.IOException;
import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;
import cs3500.marblesolitaire.view.MarbleSolitaireView;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the MarbleSolitaireTextView
 */
public class MarbleSolitaireTextViewTests {
  static MarbleSolitaireModel model = new EnglishSolitaireModel();
  static MarbleSolitaireView view = new MarbleSolitaireTextView(model);

  /**
   * Tests the toString method in MarbleSolitaireTextView
   */
  @Test
  public void testToString() {
    StringBuilder str = new StringBuilder();
    str.append("    0 0 0    \n");
    str.append("    0 0 0    \n");
    str.append("0 0 0 0 0 0 0\n");
    str.append("0 0 0 _ 0 0 0\n");
    str.append("0 0 0 0 0 0 0\n");
    str.append("    0 0 0    \n");
    str.append("    0 0 0    \n");

    assertEquals(view.toString(), str.toString());
  }

  /**
   * Tests the renderBoard method in the MarbleSolitaireTextView class
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  @Test
  public void testRenderBoard() throws IOException {
    EnglishSolitaireModel model = new EnglishSolitaireModel();
    MarbleSolitaireTextView view = new MarbleSolitaireTextView(model);

    model.move(1, 3, 3, 3);
    StringBuilder str = new StringBuilder();
    str.append("    0 0 0    \n");
    str.append("    0 _ 0    \n");
    str.append("0 0 0 _ 0 0 0\n");
    str.append("0 0 0 0 0 0 0\n");
    str.append("0 0 0 0 0 0 0\n");
    str.append("    0 0 0    \n");
    str.append("    0 0 0    \n");

    view.renderBoard();
    assertEquals(view.toString(), str.toString());

    model.move(2, 5, 2, 3);
    StringBuilder str2 = new StringBuilder();
    str2.append("    0 0 0    \n");
    str2.append("    0 _ 0    \n");
    str2.append("0 0 0 0 _ _ 0\n");
    str2.append("0 0 0 0 0 0 0\n");
    str2.append("0 0 0 0 0 0 0\n");
    str2.append("    0 0 0    \n");
    str2.append("    0 0 0    \n");

    view.renderBoard();
    assertEquals(view.toString(), str2.toString());

    model.move(4, 5, 2, 5);
    StringBuilder str3 = new StringBuilder();
    str3.append("    0 0 0    \n");
    str3.append("    0 _ 0    \n");
    str3.append("0 0 0 0 _ 0 0\n");
    str3.append("0 0 0 0 0 _ 0\n");
    str3.append("0 0 0 0 0 _ 0\n");
    str3.append("    0 0 0    \n");
    str3.append("    0 0 0    \n");

    view.renderBoard();
    assertEquals(view.toString(), str3.toString());
  }

  /**
   * Tests the renderMessage method in the MarbleSolitaireTextView class
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  @Test
  public void testRenderMessage() throws IOException {
    Appendable appendable = new StringBuilder();
    EnglishSolitaireModel model = new EnglishSolitaireModel();
    MarbleSolitaireTextView view = new MarbleSolitaireTextView(model, appendable);

    view.renderMessage("Game Quit!");
    String expected = "Game Quit!";

    assertEquals(expected, appendable.toString());
  }

  /**
   * Tests the exception in the MarbleSolitaireTextView constructor
   */
  @Test (expected = IllegalArgumentException.class)
  public void testConstructor() {
    new MarbleSolitaireTextView(null, null);
  }
}
