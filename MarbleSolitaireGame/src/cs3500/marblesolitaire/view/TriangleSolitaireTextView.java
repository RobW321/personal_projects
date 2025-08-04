package cs3500.marblesolitaire.view;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

/**
 * TriangleSolitaireTextView class representing the view for triangle solitaire
 */
public class TriangleSolitaireTextView extends MarbleSolitaireTextView implements
        MarbleSolitaireView {
  private MarbleSolitaireModelState modelState;
  private Appendable appendable;

  /**
   * Constructor for TriangleSolitaireTextView which initializes given modelState and sets
   * appendable to System.out
   * @param modelState the modelState of the game
   * @throws IllegalArgumentException if this modelState is null
   */
  public TriangleSolitaireTextView(MarbleSolitaireModelState modelState) {
    this(modelState, System.out);
  }

  /**
   * Constructor for TriangleSolitaireTextView which initializes given modelState and appendable
   * @param modelState the modelState of the game
   * @param appendable the appendable object which appends the output
   * @throws IllegalArgumentException if modelState or appendable is not defined
   */
  public TriangleSolitaireTextView(MarbleSolitaireModelState modelState, Appendable appendable)
          throws IllegalArgumentException{
    super(modelState, appendable);
    this.modelState = modelState;
    this.appendable = appendable;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    int spaces = modelState.getBoardSize() / 2;
    int count = 0;
    for (int row = 0; row < modelState.getBoardSize(); row++) {
      if (row % 2 != 0) {
        str.append(" ");
      }
      while (count < spaces) {
        str.append("  ");
        count++;
      }
      if (row % 2 == 0) {
        spaces--;
      }
      count = 0;
      for (int col = 0; col <= row; col++) {
        if (this.modelState.getSlotAt(row, col) == MarbleSolitaireModelState.SlotState.Marble) {
          str.append("0");
        }
        else if (this.modelState.getSlotAt(row, col) == MarbleSolitaireModelState.SlotState.Empty) {
          str.append("_");
        }
        else {
          str.append(" ");
        }
        if (col < row) {
          str.append(" ");
        }
      }
      str.append("\n");
    }
    return str.toString();
  }
}
