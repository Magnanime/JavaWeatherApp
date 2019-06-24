import javax.swing.*;

public class Main {



    public static void main(String[] args) {
        System.out.println("Hello World!");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        MainWindow mainGUI = new MainWindow();
        mainGUI.setVisible(true);
    }
}
