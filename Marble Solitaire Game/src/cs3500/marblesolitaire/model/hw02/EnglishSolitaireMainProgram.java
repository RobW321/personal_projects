package cs3500.marblesolitaire.model.hw02;
import java.io.InputStreamReader;

import cs3500.marblesolitaire.controller.MarbleSolitaireController;
import cs3500.marblesolitaire.controller.MarbleSolitaireControllerImpl;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModel;
import cs3500.marblesolitaire.view.EuropeanSolitaireTextView;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;
import cs3500.marblesolitaire.view.MarbleSolitaireView;

/**
 * The main program
 */
public class EnglishSolitaireMainProgram {
  public static void main(String[] args) {
    MarbleSolitaireModel model = new EnglishSolitaireModel();
    MarbleSolitaireView view = new MarbleSolitaireTextView(model);
    EuropeanSolitaireModel eModel = new EuropeanSolitaireModel();
    EuropeanSolitaireTextView eView = new EuropeanSolitaireTextView(eModel);
    Readable readable = new InputStreamReader(System.in);
    MarbleSolitaireController msc = new MarbleSolitaireControllerImpl(eModel, eView, readable);
    msc.playGame();
  }
}
