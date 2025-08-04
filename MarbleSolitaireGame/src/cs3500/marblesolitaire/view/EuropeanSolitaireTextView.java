package cs3500.marblesolitaire.view;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

/**
 * EuropeanSolitaireTextView class representing the view for european solitaire
 */
public class EuropeanSolitaireTextView extends MarbleSolitaireTextView implements
        MarbleSolitaireView {
  private MarbleSolitaireModelState modelState;
  private Appendable appendable;

  /**
   * Constructor for EuropeanSolitaireTextView which initializes given modelState and sets
   * appendable to System.out
   * @param modelState the modelState of the game
   * @throws IllegalArgumentException if this modelState is null
   */
  public EuropeanSolitaireTextView(MarbleSolitaireModelState modelState) {
    this(modelState, System.out);
  }

  /**
   * Constructor for EuropeanSolitaireTextView which initializes given modelState and appendable
   * @param modelState the modelState of the game
   * @param appendable the appendable object which appends the output
   * @throws IllegalArgumentException if modelState or appendable is not defined
   */
  public EuropeanSolitaireTextView(MarbleSolitaireModelState modelState, Appendable appendable)
          throws IllegalArgumentException {
    super(modelState, appendable);
    this.modelState = modelState;
    this.appendable = appendable;
  }
}