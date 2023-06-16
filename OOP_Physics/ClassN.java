package agh.cs.lab2;

public class N {
    public static void main(String[] args) {
        Vector2d position1 = new Vector2d(-1,3);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2, 2);
        System.out.println(position2);
        System.out.println(position1.add(position2));
    }
}
