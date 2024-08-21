package cs3500.marblesolitaire.model.hw04;
import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * EuropeanSolitaireModel class which represents the model for european solitaire game
 */
public class EuropeanSolitaireModel extends EnglishSolitaireModel implements MarbleSolitaireModel {
  private int sideLength;
  private int boardSize;
  private int center;
  private SlotState[][] gameBoard;

  /**
   * First constructor
   * Initializes side length to 3 and sets center of board to an empty slot
   */
  public EuropeanSolitaireModel() {
    this.sideLength = 3;
    this.boardSize = this.getBoardSize();
    this.center = this.boardSize / 2;
    this.gameBoard = new SlotState[this.boardSize][this.boardSize];
    this.initBoard();
    this.setSlotAt(this.center, this.center, SlotState.Empty);
  }

  /**
   * Second constructor
   * Initializes the side length to the given number
   * @param sideLength the side length of the hexagon
   * @throws IllegalArgumentException if given side length is negative or even
   */
  public EuropeanSolitaireModel(int sideLength) {
    super(sideLength);
    this.sideLength = sideLength;
    this.boardSize = this.getBoardSize();
    this.center = this.boardSize / 2;
    this.gameBoard = new SlotState[this.boardSize][this.boardSize];
    this.initBoard();
    this.setSlotAt(this.center, this.center, SlotState.Empty);

    if (sideLength < 0) {
      throw new IllegalArgumentException("Arm thickness cannot be negative");
    }
    else if (sideLength % 2 == 0) {
      throw new IllegalArgumentException("Arm thickness must be odd");
    }
  }

  /**
   * Third constructor
   * Initializes the side length of the hexagon to 3 and sets the given coordinates to an empty slot
   * @param sRow the row of the position sought, starting at 0
   * @param sCol the column of the position sought, starting at 0
   * @throws IllegalArgumentException if given cell position is out of bounds or an invalid slot
   */
  public EuropeanSolitaireModel(int sRow, int sCol) {
    this.sideLength = 3;
    this.boardSize = this.getBoardSize();
    gameBoard = new SlotState[this.boardSize][this.boardSize];
    this.initBoard();
    if (sRow < 0 || sRow >= this.boardSize - 1 || sCol < 0 || sCol >= this.boardSize - 1) {
      throw new IllegalArgumentException("Position out of bounds (" + sRow + ", " + sCol + ")");
    }
    if (this.getSlotAt(sRow, sCol) == SlotState.Invalid) {
      throw new IllegalArgumentException("Invalid empty cell position (" + sRow + ", " + sCol + ")");
    }
    this.setSlotAt(sRow, sCol, SlotState.Empty);
  }

  /**
   * Fourth constructor
   * Initializes the side length of the hexagon to the given number and the empty slot at the
   * given coordinates
   * @param sideLength the side length of the hexagon
   * @param sRow the row of the position sought, starting at 0
   * @param sCol the column of the position sought, starting at 0
   * @throws IllegalArgumentException if given cell position is Invalid or given side length is
   * negative or even
   */
  public EuropeanSolitaireModel(int sideLength, int sRow, int sCol) {
    super(sideLength, sRow, sCol);
    this.sideLength = sideLength;
    this.boardSize = this.getBoardSize();
    gameBoard = new SlotState[this.boardSize][this.boardSize];
    this.initBoard();
    if (sRow < 0 || sRow >= this.boardSize - 1 || sCol < 0 || sCol >= this.boardSize - 1) {
      throw new IllegalArgumentException("Position out of bounds (" + sRow + ", " + sCol + ")");
    }
    else if (sideLength < 0) {
      throw new IllegalArgumentException("Arm thickness cannot be negative");
    }
    else if (sideLength % 2 == 0) {
      throw new IllegalArgumentException("Arm thickness must be odd");
    }
    else if (this.getSlotAt(sRow, sCol) == SlotState.Invalid) {
      throw new IllegalArgumentException("Invalid position");
    }
    this.setSlotAt(sRow, sCol, SlotState.Empty);
  }

  /**
   * Initializes the game board based on the initial side length
   */
  private void initBoard() {
    int lower = sideLength - 1;
    int upper = boardSize - sideLength;

    for (int i = 0; i < this.boardSize; i++) {
      if (i > 0 && i < this.sideLength) {
        lower--;
        upper++;
      }
      if (i > this.sideLength * 2 - 2) {
        lower++;
        upper--;
      }
      for (int j = 0; j < this.boardSize; j++) {
        if ((i < lower && j < lower)
                || (i < lower && j > upper)
                || (i > upper && j < lower)
                || (i > upper && j > upper)) {
          this.setSlotAt(i, j, SlotState.Invalid);
        }
        else {
          this.setSlotAt(i, j, SlotState.Marble);
        }
        if (j < lower || j > upper) {
          this.setSlotAt(i, j, SlotState.Invalid);
        }
        else {
          this.setSlotAt(i, j, SlotState.Marble);
        }
        if (j < lower || j > upper) {
          this.setSlotAt(i, j, SlotState.Invalid);
        }
        else {
          this.setSlotAt(i, j, SlotState.Marble);
        }
      }
    }
  }
}
