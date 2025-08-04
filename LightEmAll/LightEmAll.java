import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

/*
 * Instructions:
 * 
 * The game starts off with a grid of randomly configured wires which 
 * can be clicked to rotate them. The number of rows and columns of the grid 
 * can be adjusted through the arguments. Starting with the power station, wires can
 * be powered up by connecting them to adjacent pieces which have an opening.
 * Furthermore, the power station can be moved using the arrow keys but only
 * across connecting wires that are also powered. On the top right of the screen,
 * there is a click count which counts how many clicks it takes a player to 
 * fully connect and power all the wires. Once all wires are connected and powered,
 * a win message is displayed on the screen.
 */


interface IWorld {
  
  int gpSize = 50;
}

// represents the game LightEmAll
class LightEmAll extends World implements IWorld {
  // a list of columns of GamePieces,
  // i.e., represents the board in column-major order
  ArrayList<ArrayList<GamePiece>> board;
  // a list of all nodes
  ArrayList<GamePiece> nodes;
  // a list of edges of the minimum spanning tree
  ArrayList<Edge> mst;
  // a list of every edge on the board
  ArrayList<Edge> workList;
  int width;
  int height;
  int powerRow;
  int powerCol;
  Random rand;
  int clicks;
  
  // convenience constructor that takes in a board and nodes for testing
  LightEmAll(ArrayList<ArrayList<GamePiece>> board, ArrayList<GamePiece> nodes, 
      int height, int width) {
    this.board = board;
    this.nodes = nodes;
    this.height = height;
    this.width = width;
    this.mst = new ArrayList<Edge>();
    this.workList = new ArrayList<Edge>();
    this.rand = new Random();
  }
  
  // constructor for LightEmAll
  LightEmAll(int height, int width) {
    this.height = height;
    this.width = width;
    this.board = new ArrayList<ArrayList<GamePiece>>();
    this.nodes = new ArrayList<GamePiece>();
    this.powerRow = 0;
    this.powerCol = 0;
    this.rand = new Random();
    this.mst = new ArrayList<Edge>();
    this.workList = new ArrayList<Edge>();
    this.clicks = 0;
    
    // fills the board with GamePieces based on the inputed dimensions
    this.generateBoard();
    
    // connects all adjacent GamePieces with edges and assigns them random weights
    this.generateEdges();
    
    // sorts the given list of edges by their assigned weights 
    this.sortByWeight(workList);
   
    HashMap<GamePiece, GamePiece> representatives = new HashMap<GamePiece, GamePiece>();
    
    // uses HashMaps to map every GamePiece to itself
    for (int i = 0; i < nodes.size(); i++) {
      representatives.put(nodes.get(i), nodes.get(i));
    }
    
    // finds the representatives of connecting GamePieces and unites them
    // if these representatives are different
    this.findAndUnite(representatives);
    
    // renders wires on the GamePieces based on the edges that are
    // in the minimum spanning tree
    this.renderWires();
      
    // scrambles board by rotating each GamePiece a random number of times
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int randomInt = this.rand.nextInt(3);
        for (int k = 0; k < randomInt; k++) {
          board.get(i).get(j).rotate();
        }
      }
    }
  }
  
  //fills the board with GamePieces based on the inputed dimensions
  void generateBoard() {
    for (int i = 0; i < height; i++) {
      board.add(new ArrayList<GamePiece>());
      for (int j = 0; j < width; j++) {
        GamePiece gp = new GamePiece();
        board.get(i).add(gp);
        nodes.add(gp);     
        gp.row = i;
        gp.col = j;
        
        if (i == 0 && j == 0) {
          board.get(i).get(j).powerStation = true;
          board.get(i).get(j).powered = true;
        }
      }
    }
  }
  
  // connects all adjacent GamePieces with edges and assigns them random weights
  void generateEdges() {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Edge edge = new Edge();
        if (j < width - 1) {
          edge.updateHorizontalEdge(board, i, j, rand);
          workList.add(edge);
        }
        
        Edge edge2 = new Edge();
        if (i < height - 1) {
          edge2.updateVerticalEdge(board, i, j, rand);
          workList.add(edge2);
        }
      }
    }
  }
  
  // sorts the given list of edges by their assigned weights
  void sortByWeight(ArrayList<Edge> workList) {
    Collections.sort(workList, new CompWeight());
  }
  
  // finds and returns the representative of the given GamePiece
  GamePiece findRep(HashMap<GamePiece, GamePiece> rep, GamePiece gp) {
    if (rep.get(gp).equals(gp)) {
      return gp;
    }
    else {
      return findRep(rep, rep.get(gp));
    }
  }
  
  // combines two GamePieces by making the first GamePiece the 
  // representative of the second GamePiece
  void union(HashMap<GamePiece, GamePiece> rep, GamePiece gp1, GamePiece gp2) {
    rep.put(gp1, gp2);
  }
  
  // finds the representatives of connecting GamePieces and unites them
  // if these representatives are different
  void findAndUnite(HashMap<GamePiece, GamePiece> representatives) {
    while (mst.size() < nodes.size() - 1) {
      if (findRep(representatives, workList.get(0).fromNode)
          .equals(findRep(representatives, workList.get(0).toNode))) {
        workList.remove(0);
      }
      else {
        mst.add(workList.get(0));
        union(representatives, 
            findRep(representatives, workList.get(0).fromNode), 
            findRep(representatives, workList.get(0).toNode));
      }
    }
  }
  
  // renders wires on the GamePieces based on the edges that are
  // in the minimum spanning tree
  void renderWires() {
    for (int i = 0; i < mst.size(); i++) {
      if (mst.get(i).fromNode.col < mst.get(i).toNode.col) {
        mst.get(i).fromNode.right = true;
        mst.get(i).toNode.left = true;
      }
   
      if (mst.get(i).fromNode.row < mst.get(i).toNode.row) {
        mst.get(i).fromNode.bottom = true;
        mst.get(i).toNode.top = true;
      }
    }
  }
  
  // method for testing random rotation of each GamePiece
  void scrambleBoardForTesting(int rand) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        for (int k = 0; k < rand; k++) {
          board.get(i).get(j).rotate();
        }
      }
    }
  }
  
  // draws the world scene of the game
  public WorldScene makeScene() { 
    int width = gpSize * this.width;
    int height = gpSize * this.height;
    
    WorldScene scene = new WorldScene(width, height);
    scene.placeImageXY(new RectangleImage(width, height, OutlineMode.SOLID, Color.darkGray),
        width / 2, height / 2);
    
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        board.get(i).get(j).generateTile(scene, 50, i, j);
        this.connectPower();
      }
    }
    
    scene.placeImageXY(new TextImage("Clicks:", 20, 
        FontStyle.BOLD, Color.GREEN), width - 50, height - (height - 20));
    scene.placeImageXY(new TextImage(String.valueOf(this.clicks), 20, 
        FontStyle.BOLD, Color.GREEN), width - 50, height - (height - 45));
    
    // displays a win message once all the GamePieces are powered
    if (this.allPowered()) {
      scene.placeImageXY(new TextImage("YOU WIN!", width / 7, Color.GREEN), width / 2, height / 2);
      return scene;
    }
    else { 
      return scene;
    }
  }
  
  // rotates the wire configuration of the GamePiece that is clicked
  public void onMouseClicked(Posn pos, String buttonName) {
    if (buttonName.equals("LeftButton") && !allPowered()) {
      boolean top = board.get(Math.floorDiv(pos.y, 50)).get(Math.floorDiv(pos.x, 50)).top;
      boolean right = board.get(Math.floorDiv(pos.y, 50)).get(Math.floorDiv(pos.x, 50)).right;
      boolean bottom = board.get(Math.floorDiv(pos.y, 50)).get(Math.floorDiv(pos.x, 50)).bottom;
      boolean left = board.get(Math.floorDiv(pos.y, 50)).get(Math.floorDiv(pos.x, 50)).left;
      
      board.get(Math.floorDiv(pos.y, 50)).get(Math.floorDiv(pos.x, 50)).top = left;
      board.get(Math.floorDiv(pos.y, 50)).get(Math.floorDiv(pos.x, 50)).right = top;
      board.get(Math.floorDiv(pos.y, 50)).get(Math.floorDiv(pos.x, 50)).bottom = right;
      board.get(Math.floorDiv(pos.y, 50)).get(Math.floorDiv(pos.x, 50)).left = bottom;
      
      this.clicks++;
    }
  }
  
  // returns true if all nodes are powered
  boolean allPowered() {
    for (int i = 0; i < nodes.size(); i++) {
      if (!nodes.get(i).powered) {
        return false;
      }
    }
    return true;
  }
  
  // moves the powerStation along powered and connected wires in the direction
  // of the key that is pressed
  public void onKeyEvent(String key) {
    if (key.equals("up") && !this.allPowered()) {
      boolean hasMoved = false;
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          if (board.get(i).get(j).powerStation && i > 0 
              && board.get(i - 1).get(j).powered 
              && board.get(i).get(j).top
              && board.get(i - 1).get(j).bottom
              && !hasMoved) {
            board.get(i).get(j).powerStation = false;
            board.get(i - 1).get(j).powerStation = true;
            hasMoved = true;
          }
        }
      }
    }
    if (key.equals("down") && !this.allPowered()) {
      boolean hasMoved = false;
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          if (board.get(i).get(j).powerStation 
              && i < height - 1 
              && board.get(i + 1).get(j).powered 
              && board.get(i).get(j).bottom
              && board.get(i + 1).get(j).top
              && !hasMoved) {
            board.get(i).get(j).powerStation = false;
            board.get(i + 1).get(j).powerStation = true;
            hasMoved = true;
          }
        }
      }
    }
    if (key.equals("left") && !this.allPowered()) {
      boolean hasMoved = false;
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          if (board.get(i).get(j).powerStation 
              && j > 0 
              && board.get(i).get(j - 1).powered 
              && board.get(i).get(j).left 
              && board.get(i).get(j - 1).right 
              && !hasMoved) {
            board.get(i).get(j).powerStation = false;
            board.get(i).get(j - 1).powerStation = true;
            hasMoved = true;
          }
        }
      }
    }
    if (key.equals("right") && !this.allPowered()) {
      boolean hasMoved = false;
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          if (board.get(i).get(j).powerStation 
              && j < width - 1 
              && board.get(i).get(j + 1).powered 
              && board.get(i).get(j).right
              && board.get(i).get(j + 1).left 
              && !hasMoved) {
            board.get(i).get(j).powerStation = false;
            board.get(i).get(j + 1).powerStation = true;
            hasMoved = true;
          }
        }
      }
    }
  }
  
  
  // distributes power to neighboring GamePieces if wires are aligned
  void connectPower() {
    ArrayList<GamePiece> workList = new ArrayList<GamePiece>();
    
    for (int i = 0; i < nodes.size(); i++) {
      if (nodes.get(i).powerStation) {
        workList.add(nodes.get(i));
      }
      else {
        nodes.get(i).powered = false;
      }
    }
     
    while (!workList.isEmpty()) {
      if (workList.get(0).top 
          && workList.get(0).row > 0
          && board.get(workList.get(0).row - 1).get(workList.get(0).col).bottom 
          && !board.get(workList.get(0).row - 1).get(workList.get(0).col).powered) {
        board.get(workList.get(0).row - 1).get(workList.get(0).col).powered = true;
        workList.add(board.get(workList.get(0).row - 1).get(workList.get(0).col));     
      }
      if (workList.get(0).bottom 
          && workList.get(0).row < height - 1
          && board.get(workList.get(0).row + 1).get(workList.get(0).col).top 
          && !board.get(workList.get(0).row + 1).get(workList.get(0).col).powered) {
        board.get(workList.get(0).row + 1).get(workList.get(0).col).powered = true;
        workList.add(board.get(workList.get(0).row + 1).get(workList.get(0).col));
      }
      if (workList.get(0).left 
          && workList.get(0).col > 0
          && board.get(workList.get(0).row).get(workList.get(0).col - 1).right 
          && !board.get(workList.get(0).row).get(workList.get(0).col - 1).powered) {
        board.get(workList.get(0).row).get(workList.get(0).col - 1).powered = true;
        workList.add(board.get(workList.get(0).row).get(workList.get(0).col - 1));
      }
      if (workList.get(0).right 
          && workList.get(0).col < width - 1
          && board.get(workList.get(0).row).get(workList.get(0).col + 1).left 
          && !board.get(workList.get(0).row).get(workList.get(0).col + 1).powered) {
        board.get(workList.get(0).row).get(workList.get(0).col + 1).powered = true;
        workList.add(board.get(workList.get(0).row).get(workList.get(0).col + 1));
      }
      workList.remove(0);
    }
  }
}

// function object for comparing weights 
class CompWeight implements Comparator<Edge> {

  // compares the weights of the given edges
  public int compare(Edge o1, Edge o2) {
    return o1.weight - o2.weight;
  }
}

// represents a GamePiece in LightEmAll
class GamePiece {
  int row;
  int col;
  boolean left;
  boolean right;
  boolean top;
  boolean bottom;
  boolean powerStation;
  boolean powered;
  
  // constructor for GamePiece 
  GamePiece(int row, int col, boolean left, boolean right, boolean top, boolean bottom, 
      boolean powerStation, boolean powered) {
    this.row = row;
    this.col = col;
    this.left = left;
    this.right = right;
    this.top = top;
    this.bottom = bottom;
    this.powerStation = powerStation;
    this.powered = powered;
  }
  
  // convenience constructor for GamePiece
  GamePiece() {
    this(0, 0, false, false, false, false, false, false);
  }
  
  // convenience constructor for 4-way powerStation
  GamePiece(int row, int col) {
    this(row, col, true, true, true, true, true, true);
  }
  
  // rotates this GamePiece
  public void rotate() {
    boolean top = this.top;
    boolean right = this.right;
    boolean bottom = this.bottom;
    boolean left = this.left;
    
    this.top = left;
    this.right = top;
    this.bottom = right;
    this.left = bottom;
  }
  
  // returns yellow if this GamePiece is powered and gray if not
  Color checkPowered() {
    if (this.powered) {
      return Color.yellow;
    }
    return Color.gray;
  }
  
  // generates a tile with a given size on a given WorldScene 
  // with the implemented configuration of wires
  WorldScene generateTile(WorldScene scene, int gpSize, int i, int j) {
    if (this.left) {
      scene.placeImageXY(new RectangleImage(gpSize, gpSize, OutlineMode.OUTLINE, Color.black), 
          j * gpSize + gpSize / 2, i * gpSize + gpSize / 2);
      scene.placeImageXY(new OverlayOffsetImage(new RectangleImage(gpSize / 2, gpSize / 10, 
          OutlineMode.SOLID, this.checkPowered()), 35, 0, 
          new RectangleImage(0, 0, OutlineMode.OUTLINE, Color.black)), 
          j * gpSize + gpSize / 2, i * gpSize + gpSize / 2);
      scene.placeImageXY(new RectangleImage(gpSize / 10, gpSize / 10, OutlineMode.SOLID, 
          this.checkPowered()), j * gpSize + gpSize / 2, i * gpSize + gpSize / 2);
    }
    if (this.right) {
      scene.placeImageXY(new RectangleImage(gpSize, gpSize, OutlineMode.OUTLINE, Color.black), 
          j * gpSize + gpSize / 2, i * gpSize + gpSize / 2);
      scene.placeImageXY(new OverlayOffsetImage(new RectangleImage(gpSize / 2, gpSize / 10, 
          OutlineMode.SOLID, this.checkPowered()), -37, 0, 
          new RectangleImage(0, 0, OutlineMode.OUTLINE, Color.black)), 
          j * gpSize + gpSize / 2, i * gpSize + gpSize / 2);
      scene.placeImageXY(new RectangleImage(gpSize / 10, gpSize / 10, OutlineMode.SOLID, 
          this.checkPowered()), j * gpSize + gpSize / 2, i * gpSize + gpSize / 2);
    } 
    if (this.top) {
      scene.placeImageXY(new RectangleImage(gpSize, gpSize, OutlineMode.OUTLINE, Color.black),
          j * gpSize + gpSize / 2, i * gpSize + gpSize / 2);
      scene.placeImageXY(new OverlayOffsetImage(new RectangleImage(gpSize / 10, gpSize / 2,
          OutlineMode.SOLID, this.checkPowered()), 0, 37, 
          new RectangleImage(0, 0, OutlineMode.OUTLINE, Color.black)), 
          j * gpSize + gpSize / 2, i * gpSize + gpSize / 2);
      scene.placeImageXY(new RectangleImage(gpSize / 10, gpSize / 10, OutlineMode.SOLID, 
          this.checkPowered()), j * gpSize + gpSize / 2, i * gpSize + gpSize / 2);
    }
    if (this.bottom) {
      scene.placeImageXY(new RectangleImage(gpSize, gpSize, OutlineMode.OUTLINE, Color.black), 
          j * gpSize + gpSize / 2, i * gpSize + gpSize / 2);
      scene.placeImageXY(new OverlayOffsetImage(new RectangleImage(gpSize / 10, gpSize / 2, 
          OutlineMode.SOLID, this.checkPowered()), 0, -37, 
          new RectangleImage(0, 0, OutlineMode.OUTLINE, Color.black)), 
          j * gpSize + gpSize / 2, i * gpSize + gpSize / 2);
      scene.placeImageXY(new RectangleImage(gpSize / 10, gpSize / 10, OutlineMode.SOLID,
          this.checkPowered()), j * gpSize + gpSize / 2, i * gpSize + gpSize / 2);
    }
    if (this.powerStation) {
      scene.placeImageXY(new RectangleImage(gpSize, gpSize, OutlineMode.OUTLINE, Color.black),
          j * gpSize + gpSize / 2, i * gpSize + gpSize / 2);
      scene.placeImageXY(new StarImage(12, 7, 2, OutlineMode.SOLID, Color.cyan), 
          j * gpSize + gpSize / 2, i * gpSize + gpSize / 2);
      scene.placeImageXY(new StarImage(7, 7, 2, OutlineMode.SOLID, Color.orange), 
          j * gpSize + gpSize / 2, i * gpSize + gpSize / 2);
      scene.placeImageXY(new StarImage(4, 7, 2, OutlineMode.SOLID, Color.yellow),
          j * gpSize + gpSize / 2, i * gpSize + gpSize / 2);
    }
    else {
      scene.placeImageXY(new RectangleImage(gpSize, gpSize, OutlineMode.OUTLINE, Color.black),
          j * gpSize + gpSize / 2, i * gpSize + gpSize / 2);
    }
    return scene;
  }
}

// represents an Edge
class Edge {
  GamePiece fromNode;
  GamePiece toNode;
  int weight;
  
  // constructor for Edge
  Edge(GamePiece fromNode, GamePiece toNode, int weight) {
    this.fromNode = fromNode;
    this.toNode = toNode;
    this.weight = weight;
  }
  
  // convenience constructor for Edge
  Edge() {
    this(new GamePiece(), new GamePiece(), 0);
  }
  
  // updates the fromNode of this edge and the toNode of the 
  // neighboring horizontal edge
  void updateHorizontalEdge(ArrayList<ArrayList<GamePiece>> board, int row, int col, Random rand) {
    this.fromNode = board.get(row).get(col);
    this.toNode = board.get(row).get(col + 1);
    this.weight = rand.nextInt(100);
  }
  
  // updates the fromNode of this edge and the toNode of the 
  // neighboring vertical edge
  void updateVerticalEdge(ArrayList<ArrayList<GamePiece>> board, int row, int col, Random rand) {
    this.fromNode = board.get(row).get(col);
    this.toNode = board.get(row + 1).get(col);
    this.weight = rand.nextInt(100);
  }
}


class ExamplesLightEmAll {
  
  // example of world 
  LightEmAll world;
  LightEmAll world3;
  
  // example of world with given board
  LightEmAll world2;
  
  // examples of GamePieces
  GamePiece gp1;
  GamePiece gp2;
  GamePiece gp3;
  GamePiece gp4; 
  GamePiece gp5; 
  GamePiece gp6; 
  GamePiece gp7; 
  
  // examples of Edges
  Edge e1;
  Edge e2;
  Edge e3;
  Edge e4;
  Edge e5;
  Edge e6;
  Edge e7;
  
  // example of random object
  Random rand1;
  
  void reset() {
    
    // example of world 
    world = new LightEmAll(3, 3);
    
    // examples of GamePieces
    gp1 = new GamePiece(0, 0, true, true, false, false, false, false);
    gp2 = new GamePiece(0, 0, false, false, true, false, false, true);
    gp3 = new GamePiece(0, 0);
    gp4 = new GamePiece();
    gp5 = new GamePiece();
    gp6 = new GamePiece();
    gp7 = new GamePiece();
    
    // examples of edges
    e1 = new Edge(gp1, gp2, 20);
    e2 = new Edge(gp1, gp2, 22);
    e3 = new Edge(gp1, gp2, 1);
    e4 = new Edge(gp1, gp2, 98);
    e5 = new Edge(gp4, gp5, 19);
    e6 = new Edge(gp5, gp6, 25);
    e7 = new Edge(gp6, gp7, 30);
    
    ArrayList<ArrayList<GamePiece>> board = new ArrayList<ArrayList<GamePiece>>();
    ArrayList<GamePiece> nodes = new ArrayList<GamePiece>();
    
    for (int i = 0; i < 3; i++) {
      board.add(new ArrayList<GamePiece>());
      for (int j = 0; j < 3; j++) {
        GamePiece gp = new GamePiece();
        board.get(i).add(gp);
        nodes.add(gp);
      }
    }
    
    // example of GamePiece with board
    world2 = new LightEmAll(board, nodes, 3, 3);
    
    // example of world
    world3 = new LightEmAll(1, 1);
    
    // examples of random object
    rand1 = new Random(1);
  }
  
  // test the generateBoard method in LightEmAll
  void testGenerateBoard(Tester t) {
    this.reset();
    
    ArrayList<ArrayList<GamePiece>> resultBoard = new ArrayList<ArrayList<GamePiece>>();
    ArrayList<GamePiece> nodes = new ArrayList<GamePiece>();
    LightEmAll emptyWorld = new LightEmAll(resultBoard, nodes, 3, 3);
    
    t.checkExpect(emptyWorld.board, new ArrayList<ArrayList<GamePiece>>());
    
    for (int i = 0; i < 3; i++) {
      resultBoard.add(new ArrayList<GamePiece>());
      for (int j = 0; j < 3; j++) {
        GamePiece newGP = new GamePiece();
        resultBoard.get(i).add(newGP);
      }
    }
    
    emptyWorld.generateBoard();
    
    t.checkExpect(emptyWorld.board, resultBoard);
  }
  
  // tests the generateEdges method in LightEmAll 
  void testGenerateEdges(Tester t) {
    this.reset();
    
    t.checkExpect(world2.workList.size(), 0);
    
    world2.generateEdges();
    
    t.checkExpect(world2.workList.size(), 12);
    t.checkExpect(world2.workList.get(0).fromNode, world2.board.get(0).get(0));
    t.checkExpect(world2.workList.get(0).toNode, world2.board.get(0).get(1));
  }
  
  // tests the sortByWeight method in LightEmAll
  void testSortByWeight(Tester t) {
    this.reset();
    ArrayList<Edge> list = new ArrayList<Edge>(Arrays.asList(e1, e2, e3, e4));
    
    t.checkExpect(list, new ArrayList<Edge>(Arrays.asList(e1, e2, e3, e4)));
    
    world.sortByWeight(list);
    t.checkExpect(list, new ArrayList<Edge>(Arrays.asList(e3, e1, e2, e4)));
    
    Edge e0 = new Edge(gp1, gp2, 20);
    list.add(e0);
    world.sortByWeight(list);
    t.checkExpect(list, new ArrayList<Edge>(Arrays.asList(e3, e1, e0, e2, e4)));
  }
  
  // tests the findRep method in LightEmAll 
  void testFindRep(Tester t) {
    this.reset();
    HashMap<GamePiece, GamePiece> hm = new HashMap<>();
    hm.put(gp1, gp1);
    hm.put(gp2, gp3);
    hm.put(gp3, gp1);
    
    t.checkExpect(world.findRep(hm, gp1), gp1);
    t.checkExpect(world.findRep(hm, gp2), gp1);
    t.checkExpect(world.findRep(hm, gp3), gp1);
  }
  
  // tests the union method in LightEmAll
  void testUnion(Tester t) {
    this.reset();
    HashMap<GamePiece, GamePiece> hm = new HashMap<>();
    hm.put(gp1, gp1);
    hm.put(gp2, gp2);
    hm.put(gp3, gp1);
    
    
    t.checkExpect(world.findRep(hm, gp2), gp2);
    t.checkExpect(world.findRep(hm, gp3), gp1);
    
    world.union(hm, gp2, gp1);
    t.checkExpect(world.findRep(hm, gp2), gp1);
    t.checkExpect(world.findRep(hm, gp3), gp1);
  }
  
  // tests the findAndUnite method in LightEmAll
  void testFindAndUnite(Tester t) {
    this.reset();
    HashMap<GamePiece, GamePiece> hm = new HashMap<>();
    world2.generateEdges();
    
    for (int i = 0; i < world2.nodes.size(); i++) {
      hm.put(world2.nodes.get(i), world2.nodes.get(i));
      if (i % 2 == 0) {
        world2.nodes.get(i).top = true;
        world2.nodes.get(i).bottom = true;
      }
      else {
        world2.nodes.get(i).left = true;
        world2.nodes.get(i).right = true;
      }
    }
    
    // size of workList before kruskals
    t.checkExpect(world2.workList.size(), 12);
    // size of minimum spanning tree before kruskals
    t.checkExpect(world2.mst.size(), 0);  
    // showing how GamePiece is its own representative before kruskals
    t.checkExpect(world2.findRep(hm, world2.nodes.get(0)), world2.nodes.get(0));
    t.checkExpect(world2.findRep(hm, world2.nodes.get(1)), world2.nodes.get(1));
    
    world2.findAndUnite(hm);
    
    // size of workList after kruskals
    t.checkExpect(world2.workList.size(), 3);
    // size of minimum spanning tree after kruskals
    t.checkExpect(world2.mst.size(), 8);
    t.checkExpect(world2.findRep(hm, world2.nodes.get(0)), world2.nodes.get(0));
    // showing how GamePiece becomes mapped to another representative after kruskals
    t.checkExpect(world2.findRep(hm, world2.nodes.get(1)), world2.nodes.get(2));
  }
  
  // tests the renderWires method in LightEmAll
  void testRenderWires(Tester t) {
    this.reset();
    world2.generateEdges();
    world2.mst = new ArrayList<Edge>(Arrays.asList(e5, e6, e7));
    
    // represents horizontal rendering of wires
    e6.fromNode.col = 1;
    e6.toNode.col = 2;
    
    t.checkExpect(e6.fromNode.left, false);
    t.checkExpect(e6.fromNode.right, false);
    t.checkExpect(e6.toNode.left, false);
    t.checkExpect(e6.toNode.right, false);
    
    world2.renderWires();
    
    t.checkExpect(e6.fromNode.left, true);
    t.checkExpect(e6.fromNode.right, true);
    t.checkExpect(e6.toNode.left, true);
    t.checkExpect(e6.toNode.right, false);
       
    // represents vertical rendering of wires
    e7.fromNode.row = 0;
    e7.toNode.row = 1;
    
    t.checkExpect(e7.fromNode.top, false);
    t.checkExpect(e7.fromNode.bottom, false);
    t.checkExpect(e7.toNode.top, false);
    t.checkExpect(e7.toNode.bottom, false);
    
    world2.renderWires();
    
    t.checkExpect(e7.fromNode.top, false);
    t.checkExpect(e7.fromNode.bottom, true);
    t.checkExpect(e7.toNode.top, true);
    t.checkExpect(e7.toNode.bottom, false);
  }
  
  // tests the scrambleBoardForTesting method in LightEmAll 
  void testScrambleBoardForTesting(Tester t) {
    this.reset();
    
    ArrayList<ArrayList<GamePiece>> board = new ArrayList<ArrayList<GamePiece>>();
    ArrayList<GamePiece> nodes = new ArrayList<GamePiece>();
    LightEmAll emptyWorld = new LightEmAll(board, nodes, 3, 3); 
    emptyWorld.generateBoard();
    
    int randInt1 = rand1.nextInt(2);
    
    emptyWorld.board.get(0).get(0).top = true;
    emptyWorld.board.get(0).get(0).bottom = true;
    emptyWorld.board.get(0).get(0).left = false;
    emptyWorld.board.get(0).get(0).right = false;
    
    emptyWorld.scrambleBoardForTesting(randInt1); 
    
    t.checkExpect(emptyWorld.board.get(0).get(0).top, false);
    t.checkExpect(emptyWorld.board.get(0).get(0).bottom, false);
    t.checkExpect(emptyWorld.board.get(0).get(0).left, true);
    t.checkExpect(emptyWorld.board.get(0).get(0).right, true);  
  }
  
  // tests the makeScene method in LightEmAll
  void testMakeScene(Tester t) {
    this.reset();

    world3.board.get(0).get(0).top = true;
    world3.board.get(0).get(0).bottom = true; 
    world3.board.get(0).get(0).left = true;
    world3.board.get(0).get(0).right = true;
       
    WorldScene scene = new WorldScene(50, 50);
    scene.placeImageXY(new RectangleImage(50, 50, OutlineMode.SOLID, Color.darkGray), 25, 25);
    
    for (int i = 0; i < 1; i++) {
      for (int j = 0; j < 1; j++) {
        world3.board.get(i).get(j).generateTile(scene, 50, i, j);
      } 
    }
    
    scene.placeImageXY(new TextImage("Clicks:", 20, 
        FontStyle.BOLD, Color.GREEN), 50 - 50, 50 - (50 - 20));
    scene.placeImageXY(new TextImage(String.valueOf(0), 20, 
        FontStyle.BOLD, Color.GREEN), 50 - 50, 50 - (50 - 45));
    scene.placeImageXY(new TextImage("YOU WIN!", 50 / 7, Color.GREEN), 25, 25);
    
    t.checkExpect(world3.makeScene(), scene);
  }
  
  // tests the onMouseClicked method in LightEmAll 
  void testOnMouseClicked(Tester t) {
    this.reset();
    
    world.board.get(0).get(0).top = true;
    world.board.get(0).get(0).bottom = true;
    world.board.get(0).get(0).left = false;
    world.board.get(0).get(0).right = false;
    
    world.onMouseClicked(new Posn(30, 20), "LeftButton");
    
    t.checkExpect(world.board.get(0).get(0).top, false);
    t.checkExpect(world.board.get(0).get(0).bottom, false); 
    t.checkExpect(world.board.get(0).get(0).left, true);
    t.checkExpect(world.board.get(0).get(0).right, true); 
    
    world.board.get(1).get(0).top = true;
    world.board.get(1).get(0).bottom = true;
    world.board.get(1).get(0).left = false;
    world.board.get(1).get(0).right = true;
    
    world.onMouseClicked(new Posn(28, 72), "LeftButton");
    
    t.checkExpect(world.board.get(1).get(0).top, false);
    t.checkExpect(world.board.get(1).get(0).bottom, true); 
    t.checkExpect(world.board.get(1).get(0).left, true);
    t.checkExpect(world.board.get(1).get(0).right, true); 
    
    world.board.get(1).get(1).top = true;
    world.board.get(1).get(1).bottom = true;
    world.board.get(1).get(1).left = true;
    world.board.get(1).get(1).right = true;
    
    world.onMouseClicked(new Posn(84, 84), "LeftButton");
    
    t.checkExpect(world.board.get(1).get(1).top, true);
    t.checkExpect(world.board.get(1).get(1).bottom, true); 
    t.checkExpect(world.board.get(1).get(1).left, true);
    t.checkExpect(world.board.get(1).get(1).right, true); 
  }
  
  // tests the onKeyEvent method in LightEmAll
  void testOnKeyEvent(Tester t) {
    this.reset();
    
    for (int i = 0; i < world.nodes.size(); i++) {
      if (i == 0) {
        world.nodes.get(i).top = true;
        world.nodes.get(i).left = true;
        world.nodes.get(i).right = false;
        world.nodes.get(i).bottom = false;
        world.nodes.get(i).powered = false;
      }
      else {
        world.nodes.get(i).top = true;
        world.nodes.get(i).bottom = true;
        world.nodes.get(i).left = true;
        world.nodes.get(i).right = true;
        world.nodes.get(i).powered = true;
      }
    }
    
    world.board.get(1).get(1).powerStation = true;
    world.board.get(0).get(1).powered = false;
    
    // tests trying to move powerStation up without power
    world.onKeyEvent("up");
    t.checkExpect(world.board.get(0).get(1).powerStation, false);
    t.checkExpect(world.board.get(1).get(1).powerStation, true);
    
    world.board.get(0).get(1).powered = true;
    
    // tests moving powerStation up with power
    world.onKeyEvent("up");
    t.checkExpect(world.board.get(0).get(1).powerStation, true);
    t.checkExpect(world.board.get(1).get(1).powerStation, false);
    
    world.board.get(0).get(1).right = true;
    world.board.get(0).get(2).left = true;
    world.board.get(0).get(2).powered = true;
    
    // tests moving powerStation right with power
    world.onKeyEvent("right");
    t.checkExpect(world.board.get(0).get(2).powerStation, true);
    t.checkExpect(world.board.get(0).get(1).powerStation, false);
    
    world.board.get(1).get(2).powered = true;
    
    // tests moving powerStation down with power
    world.onKeyEvent("down");
    t.checkExpect(world.board.get(1).get(2).powerStation, true);
    t.checkExpect(world.board.get(0).get(2).powerStation, false);
    
    world.board.get(1).get(2).left = true;
    
    // tests moving powerStation left with power
    world.onKeyEvent("left");
    t.checkExpect(world.board.get(1).get(1).powerStation, true);
    t.checkExpect(world.board.get(1).get(2).powerStation, false);
  }
  
  // tests the allPowered method in LightEmAll
  void testAllPowered(Tester t) {
    this.reset();
    
    t.checkExpect(world2.allPowered(), false);
    world2.nodes.get(0).powered = true;
    t.checkExpect(world2.allPowered(), false);
    
    for (int i = 0; i < world2.nodes.size(); i++) {
      world2.nodes.get(i).powered = true;
    }
    
    t.checkExpect(world2.allPowered(), true);
  }
  
  // tests the connectPower method in LightEmAll
  void testConnectPower(Tester t) {
    this.reset();
    
    for (int i = 0; i < world.nodes.size(); i++) {
      if (i == 4) {
        world.nodes.get(i).top = true;
        world.nodes.get(i).bottom = true;
        world.nodes.get(i).left = true;
        world.nodes.get(i).right = true;
      }
      else if (i == 3) {
        world.nodes.get(i).top = true;
        world.nodes.get(i).bottom = true;
        world.nodes.get(i).right = true;
        world.nodes.get(i).left = false;
      }
      else if (i == 5) {
        world.nodes.get(i).top = true;
        world.nodes.get(i).bottom = true;
        world.nodes.get(i).left = true;
        world.nodes.get(i).right = false;
      }
      else {
        world.nodes.get(i).top = true;
        world.nodes.get(i).bottom = true;
        world.nodes.get(i).left = false;
        world.nodes.get(i).right = false;
      }
    }
    
    world.board.get(1).get(1).powered = true;
    world.board.get(0).get(0).powered = false;
    world.board.get(2).get(2).powered = false;
    world.board.get(1).get(2).powered = false;
   
    world.connectPower();
    
    t.checkExpect(world.board.get(0).get(0).powered, true);
    t.checkExpect(world.board.get(2).get(2).powered, true);
    t.checkExpect(world.board.get(1).get(2).powered, true);
  }
  
  // tests the compare method in CompWeight
  void testCompare(Tester t) {
    this.reset();
    CompWeight cw = new CompWeight();
    
    t.checkExpect(cw.compare(e1, e2), -2);
    t.checkExpect(cw.compare(e4, e7), 68);
    t.checkExpect(cw.compare(e3, e3), 0);
  }
  
  // tests the rotate method in GamePiece
  void testRotate(Tester t) {
    this.reset();
    
    gp4.top = true;
    gp4.bottom = true;
    gp4.left = false;
    gp4.right = false;
    
    t.checkExpect(gp4.top, true);
    t.checkExpect(gp4.bottom, true);
    t.checkExpect(gp4.left, false);
    t.checkExpect(gp4.right, false);
    
    gp4.rotate();
    
    t.checkExpect(gp4.top, false);
    t.checkExpect(gp4.bottom, false);
    t.checkExpect(gp4.left, true);
    t.checkExpect(gp4.right, true);
    
    gp4.top = true;
    gp4.rotate();
    
    t.checkExpect(gp4.top, true);
    t.checkExpect(gp4.bottom, true);
    t.checkExpect(gp4.left, false);
    t.checkExpect(gp4.right, true);
  }
  
  // tests the checkPowered method in GamePiece
  void testCheckPowered(Tester t) {
    this.reset();
    
    t.checkExpect(gp4.checkPowered(), Color.gray);
    gp4.powered = true;
    t.checkExpect(gp4.checkPowered(), Color.yellow);
  }
  
  // tests the generateTile method in GamePiece 
  void testGenerateTile(Tester t) {
    this.reset();
    WorldScene scene = new WorldScene(150, 150);
    
    scene.placeImageXY(new RectangleImage(50, 50, OutlineMode.OUTLINE, Color.black), 25, 25);
    scene.placeImageXY(new OverlayOffsetImage(new RectangleImage(25, 5, 
        OutlineMode.SOLID, Color.gray), 35, 0, 
        new RectangleImage(0, 0, OutlineMode.OUTLINE, Color.black)), 25, 25);
    scene.placeImageXY(new OverlayOffsetImage(new RectangleImage(25, 5,
        OutlineMode.SOLID, Color.gray), -37, 0, 
        new RectangleImage(0, 0, OutlineMode.OUTLINE, Color.black)), 25, 25);
    
    // tests the generation of left-right GamePiece
    t.checkExpect(gp1.generateTile(new WorldScene(150, 150), 50, 0, 0), scene);
  }
  
  // tests the updateHorizontalEdge method in Edge 
  void testUpdateHorizontalEdge(Tester t) {
    this.reset();
    
    t.checkExpect(e1.fromNode, gp1);
    t.checkExpect(e1.toNode, gp2);
    
    e1.updateHorizontalEdge(world2.board, 1, 1, rand1);
    
    t.checkExpect(e1.fromNode, world2.board.get(1).get(1));
    t.checkExpect(e1.toNode, world2.board.get(1).get(2));
  }
  
  // tests the updateVerticalEdge method in Edge 
  void updateVerticalEdge(Tester t) {
    this.reset();
    
    t.checkExpect(e2.fromNode, gp1);
    t.checkExpect(e2.toNode, gp2);
    
    e2.updateVerticalEdge(world2.board, 0, 1, rand1);
    
    t.checkExpect(e2.fromNode, world2.board.get(0).get(1));
    t.checkExpect(e2.toNode, world2.board.get(1).get(1));
  }
  
  // tests the bigBang method
  void testBigBang(Tester t) {
    int gpSize = 50;
    int rows = 8;
    int col = 8;
    LightEmAll lea = new LightEmAll(rows, col);
    lea.bigBang(gpSize * col, gpSize * rows, 0.1);
  }
}
