package demo;

public class Java8 {

    public static void main(String[] args) {
        String s = new StringBuilder("jjjj").append("hhh").toString();
        System.out.println(s.intern() == s);
        String s2 = new StringBuilder("ja").append("va").toString();
        System.out.println(s2.intern() == s2);
    }

}
