import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import java.io.IOException;
import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModel;
import cs3500.marblesolitaire.view.EuropeanSolitaireTextView;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;
import static org.junit.Assert.assertEquals;

/**
 * Tests for EuropeanSolitaireTextView
 */
public class EuropeanSolitaireTextViewTest {

  EuropeanSolitaireModel model = new EuropeanSolitaireModel();
  EuropeanSolitaireTextView view = new EuropeanSolitaireTextView(model);

  /**
   * Tests the toString method in EuropeanSolitaireTextView
   */
  @Test
  public void testToString() {
    StringBuilder str = new StringBuilder();
    str.append("    0 0 0    \n");
    str.append("  0 0 0 0 0  \n");
    str.append("0 0 0 0 0 0 0\n");
    str.append("0 0 0 _ 0 0 0\n");
    str.append("0 0 0 0 0 0 0\n");
    str.append("  0 0 0 0 0  \n");
    str.append("    0 0 0    \n");

    assertEquals(view.toString(), str.toString());
  }

  /**
   * Tests the renderBoard method in the EuropeanSolitaireTextView class
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  @Test
  public void testRenderBoard() throws IOException {
    EuropeanSolitaireModel model = new EuropeanSolitaireModel();
    EuropeanSolitaireTextView view = new EuropeanSolitaireTextView(model);

    model.move(1, 3, 3, 3);
    StringBuilder str = new StringBuilder();
    str.append("    0 0 0    \n");
    str.append("  0 0 _ 0 0  \n");
    str.append("0 0 0 _ 0 0 0\n");
    str.append("0 0 0 0 0 0 0\n");
    str.append("0 0 0 0 0 0 0\n");
    str.append("  0 0 0 0 0  \n");
    str.append("    0 0 0    \n");

    view.renderBoard();
    assertEquals(view.toString(), str.toString());

    model.move(1, 1, 1, 3);
    StringBuilder str2 = new StringBuilder();
    str2.append("    0 0 0    \n");
    str2.append("  _ _ 0 0 0  \n");
    str2.append("0 0 0 _ 0 0 0\n");
    str2.append("0 0 0 0 0 0 0\n");
    str2.append("0 0 0 0 0 0 0\n");
    str2.append("  0 0 0 0 0  \n");
    str2.append("    0 0 0    \n");

    view.renderBoard();
    assertEquals(view.toString(), str2.toString());

    model.move(3, 1, 1, 1);
    StringBuilder str3 = new StringBuilder();
    str3.append("    0 0 0    \n");
    str3.append("  0 _ 0 0 0  \n");
    str3.append("0 _ 0 _ 0 0 0\n");
    str3.append("0 _ 0 0 0 0 0\n");
    str3.append("0 0 0 0 0 0 0\n");
    str3.append("  0 0 0 0 0  \n");
    str3.append("    0 0 0    \n");

    view.renderBoard();
    assertEquals(view.toString(), str3.toString());
  }

  /**
   * Tests the renderMessage method in the EuropeanSolitaireTextView class
   * @throws IOException if transmission of the board to the provided data destination fails
   */

  @Test
  public void testRenderMessage() throws IOException {
    Appendable appendable = new StringBuilder();
    EnglishSolitaireModel model = new EnglishSolitaireModel();
    MarbleSolitaireTextView view = new MarbleSolitaireTextView(model, appendable);

    view.renderMessage("Score: 15");
    String expected = "Score: 15";

    Assertions.assertEquals(expected, appendable.toString());
  }

  /**
   * Tests the exception in the EuropeanSolitaireTextView constructor
   */

  @Test (expected = IllegalArgumentException.class)
  public void testConstructor() {
    new EuropeanSolitaireTextView(null, null);
  }
}
