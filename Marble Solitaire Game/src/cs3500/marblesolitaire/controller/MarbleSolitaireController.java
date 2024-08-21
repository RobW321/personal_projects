package cs3500.marblesolitaire.controller;

/**
 * The MarbleSolitaireController interface
 */
public interface MarbleSolitaireController {

  /**
   * Plays a new game of MarbleSolitaire
   * @throws IllegalStateException if the controller is unable to successfully read input or
   * transmit output
   */
  void playGame() throws IllegalStateException;
}
