package cs3500.marblesolitaire.controller;
import java.io.IOException;
import java.util.Scanner;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireView;

/**
 * The MarbleSolitaireControllerImpl class
 */
public class MarbleSolitaireControllerImpl implements MarbleSolitaireController {
  private MarbleSolitaireModel model;
  private MarbleSolitaireView view;
  private Readable readable;
  private boolean quit = false;

  /**
   * MarbleSolitaireControllerImpl constructor which initializes the model, view, and readable
   * @param model the MarbleSolitaireModel object
   * @param view the MarbleSolitaireView object
   * @param readable the readable object to read input from
   * @throws IllegalArgumentException if model, view, or readable are not defined
   */
  public MarbleSolitaireControllerImpl(MarbleSolitaireModel model, MarbleSolitaireView view,
                                       Readable readable) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model must be defined");
    }
    if (view == null) {
      throw new IllegalArgumentException("View must be defined");
    }
    if (readable == null) {
      throw new IllegalArgumentException("Readable must be defined");
    }
    this.model = model;
    this.view = view;
    this.readable = readable;
  }

  @Override
  public void playGame() throws IllegalStateException {
    Scanner scanner = new Scanner(readable);
    try {
      while (!model.isGameOver() && !quit) {
        view.renderBoard();
        view.renderMessage("Score: " + model.getScore() + "\n");
        this.takeInput(scanner);
      }
      if (!quit) {
        view.renderMessage("Game Over!\n");
        view.renderBoard();
        view.renderMessage("Score: " + model.getScore());
      }
    }
    catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * Produces a quit message for when the user wishes to quit the game
   * @throws IOException if the transmission of the board to the provided data destination fails
   */
  private void gameQuit() throws IOException {
      view.renderMessage("Game Quit!\n");
      view.renderMessage("State of the game when quit:\n");
      view.renderBoard();
      view.renderMessage("Score: " + model.getScore());
  }

  /**
   * Takes the input of the row and column from which and to which the user is moving
   * @param scanner the scanner that reads the user's input
   * @throws IOException if the transmission of the board to the provided data destination fails
   */
  private void takeInput(Scanner scanner) throws IOException {
      try {
        view.renderMessage("Please enter movement in the order: from-row from-col to-row to-col\n");
        int fromRow, fromCol, toRow, toCol;
        fromRow = checkValidPosition(scanner);
        if (quit){return;}
        fromCol = checkValidPosition(scanner);
        if (quit){return;}
        toRow = checkValidPosition(scanner);
        if (quit){return;}
        toCol = checkValidPosition(scanner);
        if (quit){return;}
        model.move(fromRow - 1, fromCol - 1, toRow - 1, toCol - 1);
      }
      catch (IllegalArgumentException e) {
        view.renderMessage("Invalid move. Play again.\n");
        takeInput(scanner);
      }
  }

  /**
   * Returns the given position if it is valid but throws exception otherwise
   * @param scanner the scanner which reads user input
   * @return the value that the user inputs if it is a positive integer
   * @throws IOException if the transmission of the board to the provided data destination fails
   */
  private int checkValidPosition(Scanner scanner) throws IOException {
      String input = scanner.next();
      if (input.equalsIgnoreCase("q")) {
        this.gameQuit();
        quit = true;
      }
      try {
        return Integer.parseInt(input);
      } catch (NumberFormatException e) {
        if (!quit) {
          view.renderMessage("Please enter a positive integer: \n");
          return checkValidPosition(scanner);
        }
      }
    return -1;
  }
}