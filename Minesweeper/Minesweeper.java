import java.util.ArrayList;
import java.util.Random;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

// Welcome to MineSweeper!
/*
 * INSTRUCTIONS:
 * 
 * Please run this file to start the game. The number of rows, columns, and mines 
 * that are produced can be adjusted through the bigBang method at the bottom of 
 * the file. However, there is a limit. Rows and columns must be between 10 and 50,
 * while the number of mines cannot exceed the number of cells produced. Once started,
 * the game increments points by 50 for every cell (that is not a mine) that is 
 * revealed. If a mine is clicked, you will be shown a screen telling you that 
 * you lost. If all non-mine cells are revealed, click once more on the board to
 * reveal a "win" message. In both cases, you will be shown your final score.
 * Lastly, you can flag cells that you think are mines by right clicking on the cell.
 */


// represents IWorld interface
interface IWorld {
  
  int cellSize = 20;
  Color lightGray = Color.lightGray;
  Color black = Color.black;
  Color gray = Color.gray;
  Color red = Color.red;
}


// represents the Minesweeper game
class Minesweeper extends World implements IWorld {
  int row;
  int col;
  int mines;
  ArrayList<ArrayList<Cell>> board;
  Random rand;
  
  // convenience constructor for testing
  Minesweeper(ArrayList<ArrayList<Cell>> board, int row, int col, int mines) {
    this.board = board;
    this.row = row; 
    this.col = col; 
    this.mines = mines; 
  }

  // constructor for Minesweeper
  Minesweeper(int row, int col, int mines) {
    this.row = row;
    this.col = col;
    this.mines = mines;
    this.board = new ArrayList<ArrayList<Cell>>();
    this.rand = new Random();
    
    if (row < 10 || row > 50) {
      throw new IllegalArgumentException("Number for rows must be between 5 and 50 (inclusive).");
    }
    else if (col < 10 || col > 50) {
      throw new IllegalArgumentException("Number for columns must "
          + "be between 5 and 50 (inclusive).");
    }
    else if (mines > row * col) {
      throw new IllegalArgumentException("Number of mines must be less than number of cells");
    }
    
    // adds cells to the board
    for (int i = 0; i < row; i++) {
      board.add(new ArrayList<Cell>());
      for (int j = 0; j < col; j++) {
        Cell cell = new Cell();
        board.get(i).add(cell);
      }
    }
    
    // links each cell to its neighboring cells
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        Cell cell = new Cell();
        cell = board.get(i).get(j);
        
        // top left
        if (i == 0 && j == 0) { 
          cell.linkCells(board.get(i).get(j + 1));
          cell.linkCells(board.get(i + 1).get(j));
          cell.linkCells(board.get(i + 1).get(j + 1));
        }
        // top right
        else if (i == 0 && j == col - 1) {
          cell.linkCells(board.get(i).get(j - 1));
          cell.linkCells(board.get(i + 1).get(j));
          cell.linkCells(board.get(i + 1).get(j - 1));
        }
        // bottom left
        else if (i == row - 1 && j == 0) {
          cell.linkCells(board.get(i).get(j + 1));
          cell.linkCells(board.get(i - 1).get(j));
          cell.linkCells(board.get(i - 1).get(j + 1));
        }
        // bottom right
        else if (i == row - 1 && j == col - 1) {
          cell.linkCells(board.get(i - 1).get(j));
          cell.linkCells(board.get(i).get(j - 1));
          cell.linkCells(board.get(i - 1).get(j - 1));
        }
        // top
        else if (i == 0) {
          cell.linkCells(board.get(i).get(j + 1));
          cell.linkCells(board.get(i).get(j - 1));
          cell.linkCells(board.get(i + 1).get(j));
          cell.linkCells(board.get(i + 1).get(j + 1));
          cell.linkCells(board.get(i + 1).get(j - 1));
        }
        // bottom
        else if (i == row - 1) {
          cell.linkCells(board.get(i).get(j + 1));
          cell.linkCells(board.get(i).get(j - 1));
          cell.linkCells(board.get(i - 1).get(j));
          cell.linkCells(board.get(i - 1).get(j + 1));
          cell.linkCells(board.get(i - 1).get(j - 1));
        }
        // left
        else if (j == 0) {
          cell.linkCells(board.get(i).get(j + 1));
          cell.linkCells(board.get(i + 1).get(j));
          cell.linkCells(board.get(i - 1).get(j));
          cell.linkCells(board.get(i + 1).get(j + 1));
          cell.linkCells(board.get(i - 1).get(j + 1));
        }
        // right
        else if (j == col - 1) {
          cell.linkCells(board.get(i).get(j - 1));
          cell.linkCells(board.get(i + 1).get(j));
          cell.linkCells(board.get(i - 1).get(j));
          cell.linkCells(board.get(i + 1).get(j - 1));
          cell.linkCells(board.get(i - 1).get(j - 1));
        }
        //middle
        else {
          cell.linkCells(board.get(i).get(j + 1));
          cell.linkCells(board.get(i).get(j - 1));
          cell.linkCells(board.get(i + 1).get(j));
          cell.linkCells(board.get(i + 1).get(j + 1));
          cell.linkCells(board.get(i + 1).get(j - 1));
          cell.linkCells(board.get(i - 1).get(j));
          cell.linkCells(board.get(i - 1).get(j + 1));
          cell.linkCells(board.get(i - 1).get(j - 1));
        }
      }
    }
    
    // randomly chooses coordinates of cells to turn to mines
    for (int i = 0; i < mines; i++) {
      Random rand = new Random();
      int randRow = rand.nextInt(row);
      int randCol = rand.nextInt(col);
      
      if (board.get(randRow).get(randCol).isMine) {
        i -= 1;
      }
      else {
        board.get(randRow).get(randCol).isMine = true;
      }      
    }
  }
  
  // method for generating mines in random locations;
  // for testing purposes 
  void generateRandomMineForTesting(int randInt, int randInt2) {
    for (int i = 0; i < mines; i++) {
      board.get(randInt).get(randInt2).isMine = true;    
    }
  }
  
  // draws the World Scene 
  public WorldScene makeScene() {
    int width = cellSize * col;
    int height = cellSize * row;
    WorldScene scene = new WorldScene(width, height);
    scene.placeImageXY(new RectangleImage(width, height, OutlineMode.SOLID, lightGray), 
        width / 2, height / 2);
    
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) { 
        
        // makes number visible on cell if it has neighboring mines
        if (board.get(i).get(j).countMines() > 0 && board.get(i).get(j).isNumVisible) {
          scene.placeImageXY(new RectangleImage(cellSize, cellSize, OutlineMode.SOLID, gray), 
              j * cellSize + cellSize / 2, i * cellSize + cellSize / 2);
          scene.placeImageXY(new TextImage(Integer.toString(board.get(i).get(j).countMines()), 
              board.get(i).get(j).alterColor()), j * cellSize + cellSize / 2, 
              i * cellSize + cellSize / 2);
        }
        // darkens color of cell if it has no neighboring mines
        else if (board.get(i).get(j).isBlankVisible) {
          scene.placeImageXY(new RectangleImage(cellSize, cellSize, OutlineMode.SOLID, gray), 
              j * cellSize + cellSize / 2, i * cellSize + cellSize / 2);
        }
        // places orange triangle/flag on cell if 
        else if (board.get(i).get(j).hasFlag) {
          scene.placeImageXY(new EquilateralTriangleImage(cellSize * 3 / 4, 
              OutlineMode.SOLID, Color.ORANGE), 
              j * cellSize + cellSize / 2, i * cellSize + cellSize / 2);
        }
        // unflags cell by placing original colored cell 
        else if (!board.get(i).get(j).hasFlag) {
          scene.placeImageXY(new RectangleImage(cellSize, cellSize, OutlineMode.SOLID, 
              lightGray), j * cellSize + cellSize / 2, i * cellSize + cellSize / 2);
          scene.placeImageXY(new RectangleImage(cellSize, cellSize, OutlineMode.OUTLINE, black), 
              j * cellSize + cellSize / 2, i * cellSize + cellSize / 2);
        }
        // produces a blank board 
        else {
          scene.placeImageXY(new RectangleImage(cellSize, cellSize, OutlineMode.OUTLINE, black), 
              j * cellSize + cellSize / 2, i * cellSize + cellSize / 2);
        }
      }
    }
    
    scene.placeImageXY(new TextImage("Score:", 20, FontStyle.BOLD, Color.blue),
        width - 37, height - (height - 12));
    scene.placeImageXY(new TextImage(Integer.toString(this.trackScore()), 20, 
        FontStyle.BOLD, Color.blue), width - 37, height - (height - 35));
    
    return scene;  
  }
  
  // returns the number of cells (that are not mines) that have
  // been revealed by the player
  int trackScore() {
    int score = 0;
    for (int i = 0; i < row; i++) {  
      for (int j = 0; j < col; j++) {
        if (board.get(i).get(j).isNumVisible || board.get(i).get(j).isBlankVisible) {
          score++;
        }
      }
    }
    return score * 50;
  }
  
  // ends the world and produces a "win" message if all mines have been avoided
  // and every other cell as been clicked with the "left button"
  boolean gameWon() {
    int counter = 0;
    for (int i = 0; i < row; i++) {  
      for (int j = 0; j < col; j++) {
        if (board.get(i).get(j).isNumVisible 
            || board.get(i).get(j).isBlankVisible 
            || board.get(i).get(j).isMine) {
          counter++;
        }
      }
    }
    return counter == row * col;
  }
  
  // changes the state of the world based on where the user clicks 
  // in the WorldScene
  public void onMouseClicked(Posn pos, String buttonName) {
    // clicking on mine
    if (buttonName.equals("LeftButton") 
        && board.get(Math.floorDiv(pos.y, 20)).get(Math.floorDiv(pos.x, 20)).isMine
        && !board.get(Math.floorDiv(pos.y, 20)).get(Math.floorDiv(pos.x, 20)).hasFlag) {
      this.endOfWorld("OOPS! YOU CLICKED A MINE!");
    }
    // ends world and displays "win" message if mines have been avoided
    if (this.gameWon()
        && !board.get(Math.floorDiv(pos.y, 20)).get(Math.floorDiv(pos.x, 20)).isMine) {
      this.endOfWorld("YOU AVOIDED ALL THE MINES!");
    }
    // clicking on a cell neighboring a mine
    else if (buttonName.equals("LeftButton") 
        && board.get(Math.floorDiv(pos.y, 20)).get(Math.floorDiv(pos.x, 20)).hasNeighboringMines()
        && !board.get(Math.floorDiv(pos.y, 20)).get(Math.floorDiv(pos.x, 20)).hasFlag) {
      board.get(Math.floorDiv(pos.y, 20)).get(Math.floorDiv(pos.x, 20)).isNumVisible = true;
    }
    // clicking on a cell with no neighboring mines
    else if (buttonName.equals("LeftButton") 
        && !board.get(Math.floorDiv(pos.y, 20)).get(Math.floorDiv(pos.x, 20)).hasNeighboringMines()
        && !board.get(Math.floorDiv(pos.y, 20)).get(Math.floorDiv(pos.x, 20)).hasFlag) {
      board.get(Math.floorDiv(pos.y, 20)).get(Math.floorDiv(pos.x, 20)).floodEffect(); 
    }
    // unflagging a cell
    else if (buttonName.equals("RightButton") 
        && board.get(Math.floorDiv(pos.y, 20)).get(Math.floorDiv(pos.x, 20)).hasFlag) {
      board.get(Math.floorDiv(pos.y, 20)).get(Math.floorDiv(pos.x, 20)).hasFlag = false;
    }
    // flagging a cell
    else if (buttonName.equals("RightButton") 
        && !board.get(Math.floorDiv(pos.y, 20)).get(Math.floorDiv(pos.x, 20)).isMineVisible
        && !board.get(Math.floorDiv(pos.y, 20)).get(Math.floorDiv(pos.x, 20)).isNumVisible
        && !board.get(Math.floorDiv(pos.y, 20)).get(Math.floorDiv(pos.x, 20)).isBlankVisible) {
      board.get(Math.floorDiv(pos.y, 20)).get(Math.floorDiv(pos.x, 20)).hasFlag = true;
    }
  }  
  
  // represents the end of the world and the final scene returned
  public WorldScene lastScene(String msg) {
    int width = cellSize * col; 
    int height = cellSize * row;

    WorldScene scene = new WorldScene(width, height);
    if (gameWon()) {
      scene.placeImageXY(new TextImage(msg, width / 20, FontStyle.BOLD, Color.GREEN),
          width / 2, height / 2 - 30);
      scene.placeImageXY(new TextImage("FINAL SCORE: " + Integer.toString(this.trackScore()), 
          width / 20, FontStyle.BOLD, Color.GREEN), width / 2, height / 2 + 10);
    }
    else {
      scene.placeImageXY(new TextImage(msg, width / 20, FontStyle.BOLD, Color.RED),
          width / 2, height / 2 - 30);
      scene.placeImageXY(new TextImage("FINAL SCORE: " + Integer.toString(this.trackScore()), 
          width / 20, FontStyle.BOLD, Color.RED), width / 2, height / 2 + 10);
    }
    return scene;
  }
}
 
// represents a single cell in the game 
class Cell {
  ArrayList<Cell> cells;
  boolean isMine;
  boolean isMineVisible;
  boolean isNumVisible;
  boolean isBlankVisible;
  boolean hasFlag;
  
  // constructor for Cell
  Cell(ArrayList<Cell> cells, boolean isMine) {
    this.cells = cells;
    this.isMine = isMine;
    this.isMineVisible = false;
    this.isNumVisible = false;
    this.isBlankVisible = false;
    this.hasFlag = false;
  }
  
  // second constructor for Cell
  Cell() {
    this(new ArrayList<Cell>(), false);
  }
  
  // adds the given cell to this cell's list of cells
  void linkCells(Cell c) {
    this.cells.add(c);
  }
  
  // counts the number of mines neighboring this cell
  int countMines() {
    int count = 0;
    
    for (int i = 0; i < cells.size(); i++) {
      if (cells.get(i).isMine) {
        count++;
      }
    }
    return count;  
  }
   
  // return true if this cell has any neighboring mines
  boolean hasNeighboringMines() {
    return this.countMines() > 0;
  }
  
  // changes the color that is returned based on the number
  // of mines neighboring this cell
  Color alterColor() {
    if (this.countMines() == 1) {
      return Color.blue;
    }
    else if (this.countMines() == 2) {
      return Color.green;
    }
    else if (this.countMines() == 3) {
      return Color.red;
    }
    else if (this.countMines() == 4) {
      return Color.magenta;
    }
    else {
      return Color.black;
    }
  }
  
  // creates a flood effect starting from the original cell which
  // reveals neighboring cells until a cell with neighboring mines
  // is reached
  void floodEffect() {
    if (!this.isBlankVisible && !this.hasNeighboringMines() && !this.hasFlag) {
      this.isBlankVisible = true;
      
      for (Cell c : this.cells) {
        c.floodEffect();
      }
    } 
    if (!this.isMine && !this.hasFlag) {
      this.isBlankVisible = true;
      
      if (this.hasNeighboringMines()) {
        this.isNumVisible = true;
      }
    }
  }
  
}

 
class ExamplesMinesweeper {
  
  // examples of cells
  Cell c1;
  Cell c2;
  Cell c3;
  Cell c4;
  Cell c5;
  Cell c6;
  
  // examples of list of cells
  ArrayList<Cell> cList;
  
  // examples of intermediate boards
  ArrayList<ArrayList<Cell>> grid;
  ArrayList<ArrayList<Cell>> grid2;
  ArrayList<ArrayList<Cell>> grid3;
  
  // examples of World 
  Minesweeper world;
  Minesweeper world2;
  Minesweeper world3;
  
  // example of seeded random object
  Random random;
  int randomNum;
  
  Random random2;
  int randomNum2;
  
  void reset() {
    
    // examples of list of cells
    cList = new ArrayList<Cell>();
    cList.add(c1);
    cList.add(c2);
    
    // examples of cells
    c1 = new Cell();
    c2 = new Cell();
    c3 = new Cell(cList, true);
    c4 = new Cell(cList, false);
    c5 = new Cell(cList, true);
    c6 = new Cell(cList, true);
    
    // examples of intermediate boards
    grid = new ArrayList<ArrayList<Cell>>();
    for (int i = 0; i < 10; i++) {
      grid.add(new ArrayList<Cell>());
      for (int j = 0; j < 10; j++) {
        Cell cell = new Cell();
        grid.get(i).add(cell);
      }
    }
    // example of World
    world = new Minesweeper(grid, 10, 10, 0);
    
    // examples of intermediate boards
    grid2 = new ArrayList<ArrayList<Cell>>();
    for (int i = 0; i < 8; i++) {
      grid2.add(new ArrayList<Cell>());
      for (int j = 0; j < 8; j++) {
        Cell cell = new Cell();
        grid2.get(i).add(cell);
      }
    }
    // example of World
    world2 = new Minesweeper(grid2, 8, 8, 0);
    
    // examples of intermediate boards
    grid3 = new ArrayList<ArrayList<Cell>>();
    for (int i = 0; i < 16; i++) {
      grid3.add(new ArrayList<Cell>());
      for (int j = 0; j < 30; j++) {
        Cell cell = new Cell();
        grid3.get(i).add(cell);
      }
    }
    // example of World
    world3 = new Minesweeper(grid3, 16, 30, 0);
    
    // examples of seeded random objects
    random = new Random(1);
    randomNum = random.nextInt(50);
    
    random2 = new Random(2);
    randomNum2 = random2.nextInt(50);
  }
  
  void testGenerateRandomMineForTesting(Tester t) {
    this.reset();
    Minesweeper ms = new Minesweeper(40, 40, 1);
    
    t.checkExpect(randomNum, 35);
    t.checkExpect(randomNum2, 8);
    
    ms.generateRandomMineForTesting(randomNum, randomNum2);
    t.checkExpect(ms.board.get(randomNum).get(randomNum2).isMine, true);
    t.checkExpect(ms.board.get(38).get(10).isMine, false);
  }
  
  // tests the makeScene method in Minesweeper
  void testMakeScene(Tester t) {
    this.reset();
    
    WorldScene scene = new WorldScene(200, 200);
    scene.placeImageXY(new RectangleImage(200, 200, OutlineMode.SOLID, Color.lightGray), 100, 100);
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        scene.placeImageXY(new RectangleImage(20, 20, OutlineMode.OUTLINE, Color.black), 
            j * 20 + 10, i * 20 + 10);
      }
    }
    
    scene.placeImageXY(new TextImage("Score:", 20, FontStyle.BOLD, Color.blue),
        200 - 37, 200 - (200 - 12));
    scene.placeImageXY(new TextImage("0", 20, 
        FontStyle.BOLD, Color.blue), 200 - 37, 200 - (200 - 35));
    
    // represents test of blank board 
    t.checkExpect(world.makeScene(), scene); 
  }
  
  // tests the onMouseClicked method in Minesweeper 
  void testOnMouseClicked(Tester t) {
    this.reset();
    ArrayList<ArrayList<Cell>> brd = new ArrayList<ArrayList<Cell>>();
    
    for (int i = 0; i < 5; i++) {
      brd.add(new ArrayList<Cell>());
      for (int j = 0; j < 5; j++) {
        Cell cell = new Cell();
        brd.get(i).add(cell);
      }
    }
    
    Minesweeper ms = new Minesweeper(brd, 5, 5, 5);
    t.checkExpect(brd.get(0).get(0).isBlankVisible, false);
    
    ms.onMouseClicked(new Posn(2, 5), "LeftButton");
    t.checkExpect(brd.get(0).get(0).isBlankVisible, true);
    
    ms.onMouseClicked(new Posn(2, 5), "LeftButton");
    t.checkExpect(brd.get(0).get(0).isBlankVisible, true);
    
    t.checkExpect(brd.get(0).get(1).hasFlag, false);
    
    ms.onMouseClicked(new Posn(30, 10), "RightButton");
    t.checkExpect(brd.get(0).get(1).hasFlag, true);   
  }
  
  // tests the lastScene method in Minesweeper 
  void testLastScene(Tester t) {
    this.reset();
    Minesweeper ms = new Minesweeper(10, 10, 5);
    WorldScene scene = new WorldScene(200, 200);
    WorldScene scene2 = new WorldScene(200, 200);
    
    scene.placeImageXY(new TextImage("You clicked a mine!", 200 / 20, FontStyle.BOLD, Color.RED),
        200 / 2, 200 / 2 - 30);
    scene.placeImageXY(new TextImage("FINAL SCORE: 0", 
        200 / 20, FontStyle.BOLD, Color.RED), 200 / 2, 200 / 2 + 10);
    
    t.checkExpect(ms.lastScene("You clicked a mine!"), scene);
    
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j ++) {
        if (!ms.board.get(i).get(i).isMine) {
          ms.board.get(i).get(i).isBlankVisible = true;
        }
      }
    }
    
    scene2.placeImageXY(new TextImage("Congrats, you won!", 200 / 20, FontStyle.BOLD, Color.RED),
        200 / 2, 200 / 2 - 30);
    scene2.placeImageXY(new TextImage("FINAL SCORE: " + Integer.toString(ms.trackScore()), 
        200 / 20, FontStyle.BOLD, Color.RED), 200 / 2, 200 / 2 + 10);
    
    t.checkExpect(ms.lastScene("Congrats, you won!"), scene2);
  }
  
  // tests the trackScore method in Minesweeper
  void testTrackScore(Tester t) {
    this.reset();
    ArrayList<ArrayList<Cell>> brd = new ArrayList<ArrayList<Cell>>();
    
    for (int i = 0; i < 5; i++) {
      brd.add(new ArrayList<Cell>());
      for (int j = 0; j < 5; j++) {
        Cell cell = new Cell();
        brd.get(i).add(cell);
      }
    }
    
    Minesweeper ms = new Minesweeper(brd, 5, 5, 5);
    t.checkExpect(ms.trackScore(), 0);
    
    brd.get(0).get(0).isBlankVisible = true;
    Minesweeper ms2 = new Minesweeper(brd, 5, 5, 5);
    t.checkExpect(ms2.trackScore(), 50);
    
    brd.get(4).get(4).isNumVisible = true;
    brd.get(3).get(2).isNumVisible = true;
    Minesweeper ms3 = new Minesweeper(brd, 5, 5, 5);
    t.checkExpect(ms3.trackScore(), 150);
    
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        if (!brd.get(i).get(j).isMine) {
          brd.get(i).get(j).isNumVisible = true;
        }
      }
    }
    
    Minesweeper ms4 = new Minesweeper(brd, 5, 5, 5);
    t.checkExpect(ms4.trackScore(), 1250);
  }
  
  // tests the gameWon method in Minesweeper
  void testGameWon(Tester t) {
    this.reset();
    ArrayList<ArrayList<Cell>> brd = new ArrayList<ArrayList<Cell>>();
    
    for (int i = 0; i < 5; i++) {
      brd.add(new ArrayList<Cell>());
      for (int j = 0; j < 5; j++) {
        Cell cell = new Cell();
        brd.get(i).add(cell);
      }
    }
    
    Minesweeper ms = new Minesweeper(brd, 5, 5, 5);
    t.checkExpect(ms.gameWon(), false);
    
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        if (!brd.get(i).get(j).isMine) {
          brd.get(i).get(j).isBlankVisible = true;
        }
      }
    }
    
    Minesweeper ms2 = new Minesweeper(brd, 5, 5, 5);
    t.checkExpect(ms2.gameWon(), true);
  }
  
  // tests the linkCells method in Cell
  void testlinkCells(Tester t) {
    this.reset();
    ArrayList<Cell> cL = new ArrayList<Cell>();
    
    t.checkExpect(c1.cells, cL);
    
    cL.add(c2);
    c1.linkCells(c2);
    t.checkExpect(c1.cells, cL);
    
    cL.add(c3);
    c1.linkCells(c3);
    t.checkExpect(c1.cells, cL);
    
    cL = new ArrayList<Cell>();
    t.checkExpect(c2.cells, cL);
  }
  
  // tests the countMines method in Cell
  void testCountMines(Tester t) {
    this.reset();
    
    t.checkExpect(c1.countMines(), 0);
    c2.cells.add(c4);
    c2.cells.add(c1);
    t.checkExpect(c2.countMines(), 0);
    c1.cells.add(c3);
    t.checkExpect(c1.countMines(), 1);
    c1.cells.add(c5);
    c1.cells.add(c2);
    c1.cells.add(c6);
    c1.cells.add(c1);
    t.checkExpect(c1.countMines(), 3);
  }
  
  // tests the hasNeighboringMines method in Cell
  void testHasNeighboringMines(Tester t) {
    this.reset();
    Cell result = new Cell();
    Cell mineCell = new Cell();
    Cell emptyCell = new Cell();
    mineCell.isMine = true;
    
    result.cells.add(mineCell);
    t.checkExpect(c1.hasNeighboringMines(), false);
    t.checkExpect(result.hasNeighboringMines(), true);
    
    result.cells.remove(0);
    result.cells.add(emptyCell);
    t.checkExpect(result.hasNeighboringMines(), false);
    
    result.cells.add(c5);
    t.checkExpect(result.hasNeighboringMines(), true);
  }
  
  // tests the alterColor method in Cell
  void testAlterColor(Tester t) {
    this.reset();
    Cell c7 = new Cell(new ArrayList<Cell>(), true);
    Cell c8 = new Cell(new ArrayList<Cell>(), true);
    
    t.checkExpect(c1.alterColor(), Color.black);
    c1.cells.add(c3);
    t.checkExpect(c1.alterColor(), Color.blue);
    c1.cells.add(c5);
    t.checkExpect(c1.alterColor(), Color.green);
    c1.cells.add(c6);
    t.checkExpect(c1.alterColor(), Color.red);
    c1.cells.add(c7);
    t.checkExpect(c1.alterColor(), Color.magenta);
    c1.cells.add(c8);
    t.checkExpect(c1.alterColor(), Color.black);
  }
  
  // tests the floodEffect method in Cell
  void testFloodEffect(Tester t) {
    this.reset();
    ArrayList<ArrayList<Cell>> brd = new ArrayList<ArrayList<Cell>>();
    
    for (int i = 0; i < 5; i++) {
      brd.add(new ArrayList<Cell>());
      for (int j = 0; j < 5; j++) {
        Cell cell = new Cell();
        brd.get(i).add(cell);
      }
    }
    
    Minesweeper ms = new Minesweeper(brd, 5, 5, 0);
    
    t.checkExpect(brd.get(0).get(0).isBlankVisible, false);
    t.checkExpect(brd.get(1).get(0).isNumVisible, false);
    t.checkExpect(brd.get(0).get(1).isNumVisible, false);
    t.checkExpect(brd.get(1).get(1).isNumVisible, false);
    
    brd.get(2).get(0).isMine = true;
    brd.get(2).get(1).isMine = true;
    brd.get(2).get(2).isMine = true;
    brd.get(1).get(2).isMine = true;
    brd.get(0).get(2).isMine = true;
    
    brd.get(0).get(0).floodEffect();
    t.checkExpect(brd.get(0).get(0).isBlankVisible, true);
  }
  
  // test the exceptions in Minesweeper 
  void testConstructorException(Tester t) {
    this.reset();
    
    t.checkConstructorException(new IllegalArgumentException("Number for rows must"
        + " be between 5 and 50 (inclusive)."), "Minesweeper", 2, 10, 7);
    t.checkConstructorException(new IllegalArgumentException("Number for rows must"
        + " be between 5 and 50 (inclusive)."), "Minesweeper", 99, 23, 2);
    t.checkConstructorException(new IllegalArgumentException("Number for columns must "
        + "be between 5 and 50 (inclusive)."), "Minesweeper", 33, 4, 22);
    t.checkConstructorException(new IllegalArgumentException("Number for columns must "
        + "be between 5 and 50 (inclusive)."), "Minesweeper", 18, 67, 4);
    t.checkConstructorException(new IllegalArgumentException("Number of mines must "
        + "be less than number of cells"), "Minesweeper", 50, 10, 572);
  }
  
  
  // tests bigBang
  void testBigBang(Tester t) {
    this.reset();
    
    int cellSize = 20;
    int rows = 16;
    int columns = 30;
    Minesweeper ws = new Minesweeper(rows, columns, 99);
    ws.bigBang(cellSize * columns, cellSize * rows, 0.1);
  }
}
