package cs3500.marblesolitaire.view;
import java.io.IOException;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

/**
 * The MarbleSolitaireTextView class which implements MarbleSolitaireView
 */
public class MarbleSolitaireTextView implements MarbleSolitaireView {
  private final MarbleSolitaireModelState modelState;
  private Appendable appendable;

  /**
   * Constructor for MarbleSolitaireTextView which initializes given modelState and sets appendable
   * to System.out
   * @param modelState the modelState of the game
   * @throws IllegalArgumentException if this modelState is null
   */
  public MarbleSolitaireTextView(MarbleSolitaireModelState modelState) {
    this(modelState, System.out);
  }

  /**
   * Constructor for MarbleSolitaireTextView which initializes given modelState and appendable
   * @param modelState the modelState of the game
   * @param appendable the appendable object which appends the output
   * @throws IllegalArgumentException if modelState or appendable is not defined
   */
  public MarbleSolitaireTextView(MarbleSolitaireModelState modelState, Appendable appendable)
          throws IllegalArgumentException{
    if (modelState == null) {
      throw new IllegalArgumentException("Model state must be defined");
    }
    if (appendable == null) {
      throw new IllegalArgumentException("Appendable must be defined");
    }
    this.modelState = modelState;
    this.appendable = appendable;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    for (int row = 0; row < modelState.getBoardSize(); row++) {
      for (int col = 0; col < modelState.getBoardSize(); col++) {
        if (this.modelState.getSlotAt(row, col) == MarbleSolitaireModelState.SlotState.Marble) {
          str.append("0");
        }
        else if (this.modelState.getSlotAt(row, col) == MarbleSolitaireModelState.SlotState.Empty) {
          str.append("_");
        }
        else {
          str.append(" ");
        }
        if (col < this.modelState.getBoardSize() - 1) {
          str.append(" ");
        }
      }
      str.append('\n');
    }
    return str.toString();
  }

  @Override
  public void renderBoard() throws IOException {
    try {
      appendable.append(toString());
    }
    catch (IOException e) {
      throw new IOException(e);
    }
  }

  @Override
  public void renderMessage(String message) throws IOException {
    try {
      appendable.append(message);
    }
    catch (IOException e) {
      throw new IOException(e);
    }
  }
}
