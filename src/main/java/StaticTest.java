public class StaticTest {
    public static void main(String[] args) {
        staticFunction();
    }

    static StaticTest st = new StaticTest();

    static {
        System.out.println("1");
    }

    {
        System.out.println("2");
    }

    StaticTest() {
        System.out.println("3");
        System.out.println("a = " + a + " , b = " + b);

    }

    public static void staticFunction() {
        System.out.println("4");
    }

    int a = 100;
    static int b = 200;
}

// 2 3 a=null b =0 1 4