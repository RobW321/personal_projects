package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * TriangleSolitaireModel class which represents the model for triangle solitaire game
 */
public class TriangleSolitaireModel implements MarbleSolitaireModel {
  private int length;
  private int boardSize;
  private SlotState[][] gameBoard;

  /**
   * First constructor
   * Initializes triangle dimensions to 5 and sets the top point of the triangle to an empty slot
   */
  public TriangleSolitaireModel() {
    this.length = 5;
    this.boardSize = this.getBoardSize();
    this.gameBoard = new SlotState[boardSize][boardSize];
    this.initBoard();
    this.setSlotAt(0,0, SlotState.Empty);
  }

  /**
   * Second constructor
   * Initializes the dimensions to the given number
   * @param length the dimensions of the triangle
   * @throws IllegalArgumentException if given dimensions are not positive
   */
  public TriangleSolitaireModel(int length) {
    if (length <= 0) {
      throw new IllegalArgumentException("Dimensions must be positive");
    }
    this.length = length;
    this.boardSize = this.getBoardSize();
    this.gameBoard = new SlotState[boardSize][boardSize];
    this.initBoard();
    this.setSlotAt(0,0, SlotState.Empty);
  }

  /**
   * Third constructor
   * Initializes dimensions to 5 and sets the given coordinates to an empty slot
   * @param row the row of the position sought, starting at 0
   * @param col the column of the position sought, starting at 0
   * @throws IllegalArgumentException if given cell position is out of bounds
   */
  public TriangleSolitaireModel(int row, int col) {
    this.length = 5;
    this.boardSize = this.getBoardSize();
    this.gameBoard = new SlotState[boardSize][boardSize];
    this.initBoard();
    this.setSlotAt(row,col, SlotState.Empty);
    if (row < 0 || row > this.boardSize - 1 || col < 0 || col > this.boardSize - 1) {
      throw new IllegalArgumentException("Position out of bounds (" + row + ", " + col + ")");
    }
  }

  /**
   * Fourth constructor
   * Initializes the dimensions of the triangle to the given number and the empty slot at the
   * given coordinates
   * @param length the dimensions of the triangle
   * @param row the row of the position sought, starting at 0
   * @param col the column of the position sought, starting at 0
   * @throws IllegalArgumentException if given dimension is not positive or given position is out
   * of bounds
   */
  public TriangleSolitaireModel(int length, int row, int col) {
    this.length = length;
    this.boardSize = this.getBoardSize();
    this.gameBoard = new SlotState[boardSize][boardSize];
    this.initBoard();
    this.setSlotAt(row,col, SlotState.Empty);
    if (length <= 0) {
      throw new IllegalArgumentException("Dimensions must be positive");
    }
    if (row < 0 || row >= this.boardSize - 1 || col < 0 || col >= this.boardSize - 1) {
      throw new IllegalArgumentException("Position out of bounds (" + row + ", " + col + ")");
    }
  }

  @Override
  public int getBoardSize() {
    return this.length;
  }

  @Override
  public SlotState getSlotAt(int row, int col) throws IllegalArgumentException {
    if (row < 0 || row >= this.boardSize || col < 0 || col >= this.boardSize) {
      throw new IllegalArgumentException("This position is out of bounds");
    }
    return this.gameBoard[row][col];
  }

  /**
   * Sets the slot state at the given board position
   * @param row the row of the position sought, starting at 0
   * @param col the column of the position sought, starting at 0
   * @param newState the new intended slot state
   * @throws IllegalArgumentException if the row or the column are beyond
   * the dimensions of the board
   */
  private void setSlotAt(int row, int col, SlotState newState) throws IllegalArgumentException {
    if (row < 0 || row >= this.boardSize || col < 0 || col >= this.boardSize) {
      throw new IllegalArgumentException("This position is out of bounds");
    }
    this.gameBoard[row][col] = newState;
  }

  @Override
  public int getScore() {
    int score = 0;
    for (int i = 0; i < this.boardSize; i++) {
      for (int j = 0; j < this.boardSize; j++) {
        if (this.gameBoard[i][j] == SlotState.Marble) {
          score++;
        }
      }
    }
    return score;
  }

  /**
   * Initializes the game board based on the initial length
   */
  private void initBoard() {
    for (int i = 0; i < this.length; i++) {
      for (int j = 0; j <= i; j++) {
        this.setSlotAt(i, j, SlotState.Marble);
      }
    }
  }

  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {
    boolean validPosition = fromRow >= 0 && fromRow < this.boardSize
            && fromCol >= 0 && fromCol < this.boardSize
            && toRow >= 0 && toRow < this.boardSize
            && toCol >= 0 && toCol < this.boardSize;

    if (this.validMovement(fromRow, fromCol, toRow, toCol) && validPosition) {
      if (toRow < fromRow && toCol < fromCol) {
        this.setSlotAt(fromRow, fromCol, SlotState.Empty);
        this.setSlotAt(fromRow - 1, fromCol - 1, SlotState.Empty);
        this.setSlotAt(toRow, toCol, SlotState.Marble);
      }
      else if (toRow > fromRow && toCol > fromCol) {
        this.setSlotAt(fromRow, fromCol, SlotState.Empty);
        this.setSlotAt(fromRow + 1, fromCol + 1, SlotState.Empty);
        this.setSlotAt(toRow, toCol, SlotState.Marble);
      }
      else if (toRow < fromRow) {
        this.setSlotAt(fromRow, fromCol, SlotState.Empty);
        this.setSlotAt(fromRow - 1, fromCol, SlotState.Empty);
        this.setSlotAt(toRow, toCol, SlotState.Marble);
      }
      else if (toRow > fromRow) {
        this.setSlotAt(fromRow, fromCol, SlotState.Empty);
        this.setSlotAt(fromRow + 1, fromCol, SlotState.Empty);
        this.setSlotAt(toRow, toCol, SlotState.Marble);
      }
      else if (toCol < fromCol) {
        this.setSlotAt(fromRow, fromCol, SlotState.Empty);
        this.setSlotAt(fromRow, fromCol - 1, SlotState.Empty);
        this.setSlotAt(toRow, toCol, SlotState.Marble);
      }
      else if (toCol > fromCol) {
        this.setSlotAt(fromRow, fromCol, SlotState.Empty);
        this.setSlotAt(fromRow, fromCol + 1, SlotState.Empty);
        this.setSlotAt(toRow, toCol, SlotState.Marble);
      }
    } else {
      throw new IllegalArgumentException("Invalid movement");
    }
  }

  /**
   * Checks if the given position is out of bounds
   * @param fromRow the row where the marble is starting from
   * @param fromCol the column where the marble is starting from
   * @param toRow the row where the marble is moving to
   * @param toCol the column where the marble is moving to
   * @return true if the given position is out of bounds
   */
  private boolean checkOutOfBounds(int fromRow, int fromCol, int toRow, int toCol) {
    return (toRow < 0 || fromRow < 0 ||
            toRow > boardSize - 1 || fromRow > boardSize - 1 ||
            toCol < 0 || fromCol < 0 ||
            toCol > boardSize - 1 || fromCol > boardSize - 1);
  }

  /**
   * Checks if the "from" position of the marble is valid
   * @param fromRow the row where the marble is starting from
   * @param fromCol the column where the marble is starting from
   * @return true if the "from" position is a marble
   */
  private boolean fromValid(int fromRow, int fromCol) {
    return this.getSlotAt(fromRow, fromCol) == SlotState.Marble;
  }

  /**
   * Checks if the "to" position of the marble is valid
   * @param toRow the row where the marble is moving to
   * @param toCol the column where the marble is moving to
   * @return true if the "to" position is empty
   */
  private boolean toValid(int toRow, int toCol) {
    return this.getSlotAt(toRow, toCol) == SlotState.Empty;
  }

  /**
   * Checks if the intended marble movement is valid
   * @param fromRow the row where the marble is starting from
   * @param fromCol the column where the marble is starting from
   * @param toRow the row where the marble is moving to
   * @param toCol the column where the marble is moving to
   * @return true if the "from" position is 2 units away from "to" position
   */
  private boolean validMovement(int fromRow, int fromCol, int toRow, int toCol) {
    // checks if any position is out of bounds
    if (checkOutOfBounds(fromRow, fromCol, toRow, toCol)) {
      return false;
    }
    // checks if from position has a marble
    if (!this.fromValid(fromRow, fromCol)) {
      return false;
    }
    // checks if to position is empty
    if (!this.toValid(toRow, toCol)) {
      return false;
    }
    if (Math.abs(toCol - fromCol) == 2 &&
            toRow == fromRow) {
      int middleRow = (fromCol + toCol) / 2;
      return this.getSlotAt(fromRow, middleRow) == SlotState.Marble;
    }
    if (Math.abs(toRow - fromRow) == 2 && toCol == fromCol) {
      int middleRow = (fromRow + toRow) / 2;
      return this.getSlotAt(middleRow, fromCol) == SlotState.Marble;
    }
    if (Math.abs(toRow - fromRow) == 2 && Math.abs(toCol - fromCol) == 2) {
      int middleRow = (fromRow + toRow) / 2;
      int middleCol = (fromCol + toCol) / 2;
      return this.getSlotAt(middleRow, middleCol) == SlotState.Marble;
    }
    return false;
  }

  @Override
  public boolean isGameOver() {
    for (int row = 0; row < this.boardSize; row++) {
      for (int col = 0; col < this.boardSize; col++) {
        if (this.gameBoard[row][col] == SlotState.Marble) {
          if (validMovement(row, col, row - 2, col) ||
                  validMovement(row, col, row, col + 2) ||
                  validMovement(row, col, row, col - 2) ||
                  validMovement(row, col, row + 2, col)) {
            return false;
          }
        }
      }
    }
    return true;
  }
}
