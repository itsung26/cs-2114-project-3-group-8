public class Main {
    public static void main(String[] args) {
        Controller application = new Controller(new Restaurant(), new View());
        application.run();
    }
}
