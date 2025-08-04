import org.junit.Test;
import java.io.IOException;
import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;
import cs3500.marblesolitaire.view.TriangleSolitaireTextView;
import static org.junit.Assert.assertEquals;

/**
 * Tests for TriangleSolitaireTextView
 */
public class TriangleSolitaireTextViewTest {
  TriangleSolitaireModel model = new TriangleSolitaireModel();
  TriangleSolitaireModel model2 = new TriangleSolitaireModel(8, 4, 3);
  TriangleSolitaireTextView view = new TriangleSolitaireTextView(model);
  TriangleSolitaireTextView view2 = new TriangleSolitaireTextView(model2);

  /**
   * Tests the toString method in the TriangleSolitaireTextView class
   */
  @Test
  public void testToString() {
    StringBuilder str = new StringBuilder();
    str.append("    _\n");
    str.append("   0 0\n");
    str.append("  0 0 0\n");
    str.append(" 0 0 0 0\n");
    str.append("0 0 0 0 0\n");

    assertEquals(view.toString(), str.toString());

    StringBuilder str2 = new StringBuilder();
    str2.append("        0\n");
    str2.append("       0 0\n");
    str2.append("      0 0 0\n");
    str2.append("     0 0 0 0\n");
    str2.append("    0 0 0 _ 0\n");
    str2.append("   0 0 0 0 0 0\n");
    str2.append("  0 0 0 0 0 0 0\n");
    str2.append(" 0 0 0 0 0 0 0 0\n");

    assertEquals(view2.toString(), str2.toString());
  }

  /**
   * Tests the renderBoard method in the TriangleSolitaireTextView class
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  @Test
  public void testRenderBoard() throws IOException {
    TriangleSolitaireModel model = new TriangleSolitaireModel(7);
    TriangleSolitaireTextView view = new TriangleSolitaireTextView(model);

    model.move(2, 2, 0, 0);
    StringBuilder str = new StringBuilder();
    str.append("      0\n");
    str.append("     0 _\n");
    str.append("    0 0 _\n");
    str.append("   0 0 0 0\n");
    str.append("  0 0 0 0 0\n");
    str.append(" 0 0 0 0 0 0\n");
    str.append("0 0 0 0 0 0 0\n");

    view.renderBoard();
    assertEquals(view.toString(), str.toString());

    model.move(4, 2, 2, 2);
    StringBuilder str2 = new StringBuilder();
    str2.append("      0\n");
    str2.append("     0 _\n");
    str2.append("    0 0 0\n");
    str2.append("   0 0 _ 0\n");
    str2.append("  0 0 _ 0 0\n");
    str2.append(" 0 0 0 0 0 0\n");
    str2.append("0 0 0 0 0 0 0\n");

    view.renderBoard();
    assertEquals(view.toString(), str2.toString());

    model.move(4, 0, 4, 2);
    StringBuilder str3 = new StringBuilder();
    str3.append("      0\n");
    str3.append("     0 _\n");
    str3.append("    0 0 0\n");
    str3.append("   0 0 _ 0\n");
    str3.append("  _ _ 0 0 0\n");
    str3.append(" 0 0 0 0 0 0\n");
    str3.append("0 0 0 0 0 0 0\n");

    view.renderBoard();
    assertEquals(view.toString(), str3.toString());
  }

  /**
   * Tests the renderMessage method in the TriangleSolitaireTextView class
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  @Test
  public void testRenderMessage() throws IOException {
    Appendable appendable = new StringBuilder();
    EnglishSolitaireModel model = new EnglishSolitaireModel();
    MarbleSolitaireTextView view = new MarbleSolitaireTextView(model, appendable);

    view.renderMessage("Invalid Movement. Please try again.");
    String expected = "Invalid Movement. Please try again.";

    assertEquals(expected, appendable.toString());
  }

  /**
   * Tests the exception in the TriangleSolitaireTextView constructor
   */
  @Test (expected = IllegalArgumentException.class)
  public void testConstructor() {
    new TriangleSolitaireTextView(null, null);
  }
}