public class Main {

    private static File filee;
    private static File fileee;

    public static void main(String[] args) throws InterruptedException{

        filee = new File("bumba");
        Thread.sleep(100);
        fileee = new File("loes");
        Thread.sleep(100);

        filee.setName("bumbaNieuw");
        Thread.sleep(100);

        fileee.setName("loesNieuw");
        Thread.sleep(100);

        filee.setName("bumbaOud");

        System.out.println(filee.hasOverLappingUsePeriod(fileee));
        System.out.println(fileee.hasOverLappingUsePeriod(filee));

    }
}