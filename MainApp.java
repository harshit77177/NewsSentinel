public class MainApp {

    public static void main(String[] args) {

        DatabaseManager.connect();

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new UI();
            }
        });
    }
}