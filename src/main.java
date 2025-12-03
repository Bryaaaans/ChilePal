import controller.ChilePalController;
import view.ConsolaView;

public class main {
    public static void main (String[]arg) {
        ChilePalController controller = new ChilePalController();
        ConsolaView view = new ConsolaView(controller);
        view.iniciar();
    }
}