package cs3500.marblesolitaire.model.hw02;

/**
 * EnglishSolitaireModel class which represents the model for english solitaire game
 */
public class EnglishSolitaireModel implements MarbleSolitaireModel {
  protected final int armThickness;
  protected final int boardSize;
  protected int center;
  protected final SlotState[][] gameBoard;
  protected int score;
  protected boolean gameOver;

  /**
   * First constructor
   * Initializes arm thickness to 3 and sets center of board to empty
   */
  public EnglishSolitaireModel() {
    this.armThickness = 3;
    this.boardSize = this.getBoardSize();
    this.center = this.boardSize / 2;
    this.gameBoard = new SlotState[this.boardSize][this.boardSize];
    this.initializeBoard();
    this.setSlotAt(this.center, this.center, SlotState.Empty);
  }

  /**
   * Second constructor
   * Initializes arm thickness to 3 and sets center of board to empty
   *
   * @param sRow the row of the position sought, starting at 0
   * @param sCol the column of the position sought, starting at 0
   * @throws IllegalArgumentException if given cell position is Invalid
   */
  public EnglishSolitaireModel(int sRow, int sCol) {
    this.armThickness = 3;
    this.boardSize = this.getBoardSize();
    gameBoard = new SlotState[this.boardSize][this.boardSize];
    this.initializeBoard();
    if (sRow < 0 || sRow >= this.boardSize - 1 || sCol < 0 || sCol >= this.boardSize - 1) {
      throw new IllegalArgumentException("Position out of bounds (" + sRow + ", " + sCol + ")");
    } else if (this.getSlotAt(sRow, sCol) == SlotState.Invalid) {
      throw new IllegalArgumentException("Invalid empty cell position (" + sRow + ", " + sCol + ")");
    }
    this.setSlotAt(sRow, sCol, SlotState.Empty);
    this.score = this.getScore();
    this.gameOver = this.isGameOver();
  }

  /**
   * Third constructor
   * Initializes the board based on the given arm thickness
   *
   * @param armThickness the arm thickness of the board
   * @throws IllegalArgumentException if given armThickness is negative or even
   */
  public EnglishSolitaireModel(int armThickness) {
    this.armThickness = armThickness;
    this.boardSize = this.getBoardSize();
    this.center = this.boardSize / 2;
    gameBoard = new SlotState[this.boardSize][this.boardSize];
    this.initializeBoard();
    this.setSlotAt(this.center, this.center, SlotState.Empty);

    if (armThickness < 0) {
      throw new IllegalArgumentException("Arm thickness cannot be negative");
    } else if (armThickness % 2 == 0) {
      throw new IllegalArgumentException("Arm thickness must be odd");
    }
    this.score = this.getScore();
    this.gameOver = this.isGameOver();
  }

  /**
   * Fourth constructor
   * Initializes the board based on the given armThickness
   *
   * @param armThickness the arm thickness of the board
   * @param sRow the row of the position sought, starting at 0
   * @param sCol the column of the position sought, starting at 0
   * @throws IllegalArgumentException if given cell position is Invalid, or given armThickness is
   * negative or even
   */
  public EnglishSolitaireModel(int armThickness, int sRow, int sCol) {
    this.armThickness = armThickness;
    this.boardSize = this.getBoardSize();
    gameBoard = new SlotState[this.boardSize][this.boardSize];
    this.initializeBoard();
    if (sRow < 0 || sRow >= this.boardSize - 1 || sCol < 0 || sCol >= this.boardSize - 1) {
      throw new IllegalArgumentException("Position out of bounds (" + sRow + ", " + sCol + ")");
    } else if (armThickness < 0) {
      throw new IllegalArgumentException("Arm thickness cannot be negative");
    } else if (armThickness % 2 == 0) {
      throw new IllegalArgumentException("Arm thickness must be odd");
    } else if (this.getSlotAt(sRow, sCol) == SlotState.Invalid) {
      throw new IllegalArgumentException("Invalid position");
    }
    this.setSlotAt(sRow, sCol, SlotState.Empty);
    this.score = this.getScore();
    this.gameOver = this.isGameOver();
  }

  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {
    boolean validPosition = fromRow >= 0 && fromRow < this.boardSize
            && fromCol >= 0 && fromCol < this.boardSize
            && toRow >= 0 && toRow < this.boardSize
            && toCol >= 0 && toCol < this.boardSize;

    if (this.validMovement(fromRow, fromCol, toRow, toCol) && validPosition) {
      if (toRow < fromRow) {
        this.setSlotAt(fromRow, fromCol, SlotState.Empty);
        this.setSlotAt(fromRow - 1, fromCol, SlotState.Empty);
        this.setSlotAt(toRow, toCol, SlotState.Marble);
      }
      if (toRow > fromRow) {
        this.setSlotAt(fromRow, fromCol, SlotState.Empty);
        this.setSlotAt(fromRow + 1, fromCol, SlotState.Empty);
        this.setSlotAt(toRow, toCol, SlotState.Marble);
      }
      if (toCol < fromCol) {
        this.setSlotAt(fromRow, fromCol, SlotState.Empty);
        this.setSlotAt(fromRow, fromCol - 1, SlotState.Empty);
        this.setSlotAt(toRow, toCol, SlotState.Marble);
      }
      if (toCol > fromCol) {
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
    if (Math.abs(toRow - fromRow) == 2 &&
            toCol == fromCol) {
      int middleRow = (fromRow + toRow) / 2;
      return this.getSlotAt(middleRow, fromCol) == SlotState.Marble;
    }
    return false;
  }

  @Override
  public boolean isGameOver() {
    // checks if any moves are left
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
    return true; // returning true if no moves are left
  }

  @Override
  public int getBoardSize() {
    return (this.armThickness * 3) - 2;
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
   * @throws IllegalArgumentException if the row or the column are beyond the dimensions of the
   * board
   */
  protected void setSlotAt(int row, int col, SlotState newState) throws IllegalArgumentException {
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
   * Initializes the game board based on the initial arm thickness
   */
  private void initializeBoard() {
    int lower = armThickness - 1;
    int upper = boardSize - armThickness;

    for (int i = 0; i < this.boardSize; i++) {
      for (int j = 0; j < this.boardSize; j++) {
        if ((i < lower && j < lower) || (i < lower && j > upper)
                || (i > upper && j < lower) || (i > upper && j > upper)) {
          this.setSlotAt(i, j, SlotState.Invalid);
        } else {
          this.setSlotAt(i, j, SlotState.Marble);
        }
      }
    }
  }
}
