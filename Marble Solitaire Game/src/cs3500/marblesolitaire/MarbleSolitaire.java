package cs3500.marblesolitaire;
import java.io.InputStreamReader;
import cs3500.marblesolitaire.controller.MarbleSolitaireController;
import cs3500.marblesolitaire.controller.MarbleSolitaireControllerImpl;
import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModel;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModel;
import cs3500.marblesolitaire.view.EuropeanSolitaireTextView;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;
import cs3500.marblesolitaire.view.TriangleSolitaireTextView;

/**
 * Represents main method of MarbleSolitaire program
 */
public final class MarbleSolitaire {
  public static void main(String[] args) {
    switch (args[0]) {
      case "english":
        EnglishSolitaireModel model;
        if (args.length == 1) {
           model = new EnglishSolitaireModel();
        }
        else if (args.length == 3) {
           model = new EnglishSolitaireModel(Integer.parseInt(args[2]));
        }
        else {
          model = new EnglishSolitaireModel(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        }
        MarbleSolitaireTextView view = new MarbleSolitaireTextView(model);
        Readable readable = new InputStreamReader(System.in);
        MarbleSolitaireController controller = new MarbleSolitaireControllerImpl(model, view, readable);
        controller.playGame();
        break;
      case "european":
        EuropeanSolitaireModel eModel;
        if (args.length == 1) {
          eModel = new EuropeanSolitaireModel();
        }
        else if (args.length == 3) {
          eModel = new EuropeanSolitaireModel(Integer.parseInt(args[2]));
        }
        else {
          eModel = new EuropeanSolitaireModel(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        }
        EuropeanSolitaireTextView eView = new EuropeanSolitaireTextView(eModel);
        Readable eReadable = new InputStreamReader(System.in);
        MarbleSolitaireController eController = new MarbleSolitaireControllerImpl(eModel, eView, eReadable);
        eController.playGame();
        break;
      case "triangle":
        TriangleSolitaireModel tModel;
        if (args.length == 1) {
          tModel = new TriangleSolitaireModel();
        }
        else if (args.length == 3) {
          tModel = new TriangleSolitaireModel(Integer.parseInt(args[2]));
        }
        else {
          tModel = new TriangleSolitaireModel(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        }
        TriangleSolitaireTextView tView = new TriangleSolitaireTextView(tModel);
        Readable tReadable = new InputStreamReader(System.in);
        MarbleSolitaireController tController = new MarbleSolitaireControllerImpl(tModel, tView, tReadable);
        tController.playGame();
        break;
    }
  }
}
