import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class MazeRunner {

    public MazeRunner(String filename) {
        try {
            var maze = LoadMaze(filename);
            // printMaze(maze);
            var path = MakePaths(maze, false);
            printSolution(maze, path);
        } catch (Exception e) {
            System.out.println("EXCEPTION encountered: " + e);
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    private ArrayList<ArrayList<Integer>> LoadMaze(String path) throws Exception {

        ArrayList<ArrayList<Integer>> maze = new ArrayList<ArrayList<Integer>>();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line = null;
        while ((line = reader.readLine()) != null) {
            ArrayList<Integer> temp = new ArrayList<Integer>();
            for (char a : line.toCharArray()) {
                if (a == 'S') {
                    temp.add(1);
                } else if (a == 'F') {
                    temp.add(2);
                } else if (a == 'C') {
                    temp.add(0);
                } else if (a == 'W') {
                    temp.add(-1);
                } else if (a != '\t') {
                    throw new Exception("Unrecognized character");
                }
            }
            maze.add(temp);
        }
        reader.close();
        return maze;
    }

    private void NumberAdjacent(ArrayList<ArrayList<Integer>> maze, ArrayList<ArrayList<Integer>> paths, int x_init,
            int y_init, int iter, boolean verbose) {
        if (verbose) System.out.println(String.format("\n >> moving to [%d, %d]:", y_init, x_init));
        
        int[][] x_y = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (var mod : x_y){
            int x = x_init + mod[0];
            int y = y_init + mod[1];
            if (verbose) System.out.print(String.format("[%d]\t", iter));
            if (verbose) System.out.print(String.format(" >> Checking [%d, %d]", y, x));
            try {
                int value = maze.get(y).get(x);
                if (value == 0) {
                    // System.out.print(String.format("[%d]\t", iter));
                    if (verbose) System.out.print(" >> (path)");
                    if (paths.get(y).get(x) == 0) {
                        if (verbose) System.out.print(" >> (empty)");
                        paths.get(y).set(x, iter);
                        if (verbose) System.out.print(String.format(" >> taking [%d, %d] at depth ", y, x, iter));
                        // System.out.println(paths.get(y));
                        NumberAdjacent(maze, paths, x, y, iter + 1, verbose);
                    } else {
                        if (verbose) System.out.print(String.format(" >> (taken) [depth %d]", paths.get(y).get(x)));
                    }
                } else {
                    if (value == 1) {
                        if (verbose) System.out.print(String.format(" >> (START) [depth %d]", paths.get(y).get(x)));
                    } else if (value == 2) {
                        if (verbose) System.out.print(String.format(" >> (FINISH) [depth %d]", paths.get(y).get(x)));
                        paths.get(y).set(x, iter);
                    } else {
                        if (verbose) System.out.print(String.format(" >> (wall)", x, y));
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                if (verbose) System.out.print(String.format(" >> (bounds)", x, y));
            }
            if (verbose) System.out.println();
        }
        if (verbose) System.out.println(">> FINISHED DEPTH: " + iter);
    }

    private ArrayList<ArrayList<Integer>> MakePaths(ArrayList<ArrayList<Integer>> maze, boolean verbose) {
        ArrayList<ArrayList<Integer>> paths = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < maze.size(); i++){
            ArrayList<Integer> temp = new ArrayList<>();
            for (int z = 0; z < maze.get(0).size(); z++){
                temp.add(0);
            }
            paths.add(temp);
        }
        int start_x = -1;
        int start_y = -1;
        int end_x = -1;
        int end_y = -1;
        for (int row = 0; row < maze.size(); row++) {
            for (int col = 0; col < maze.get(0).size(); col++) {
                int value = maze.get(row).get(col);
                if (value == 1) {
                    start_x = col;
                    start_y = row;
                }else if (value == 2){
                    end_x = col;
                    end_y = row;
                }
            }
            if (start_x != -1 && start_y != -1 && end_x != -1 && end_y != -1) {
                break;
            }
        }
        
        paths.get(start_y).set(start_x, 1);
        System.out.println(String.format("Start at [%d, %d], Finish at [%d, %d]", start_x, start_y, end_x, end_y));
        NumberAdjacent(maze, paths, start_x, start_y, 2, verbose);
        // System.out.println("Paths:");
        // printMaze(paths);
        var way = findPath(paths, start_x, start_y, end_x, end_y, verbose);

        return way;

    }

    private void Backtrack() {

    }

    private ArrayList<ArrayList<Integer>> findPath(ArrayList<ArrayList<Integer>> path, int x_start, int y_start, int x_finish, int y_finish, boolean verbose) {
        ArrayList<ArrayList<Integer>> backPath = new ArrayList<>();
        for (int i = 0; i < path.size(); i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int z = 0; z < path.get(0).size(); z++){
                temp.add(0);
            }
            backPath.add(temp);
        }

        backPath.get(y_start).set(x_start, -1);

        boolean finished = false;
        int temp_x = x_finish;
        int temp_y = y_finish;
        
        int[][] x_y = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        
        if (verbose) System.out.println(String.format("[%d, %d] << %d", temp_x, temp_y, path.get(temp_y).get(temp_x)));
        while(!finished) {
            int value = path.get(temp_y).get(temp_x);
            for (var mod : x_y){
                int x = temp_x + mod[0];
                int y = temp_y + mod[1];
                try {
                    int next = path.get(y).get(x);
                    if (verbose) System.out.print(String.format("[%d, %d] %d --> [%d, %d] %d \n", x, y, value, temp_x, temp_y, next));
                    if (next == 1){
                        finished = true;
                        break;
                    }
                    if (next == value - 1) {
                        backPath.get(y).set(x, -1);
                        temp_x = x;
                        temp_y = y;
                        break;
                    }
                } catch (IndexOutOfBoundsException e) {
                    // Do nothing, ignore out of bounds coordinates
                }
            }
        }
        return backPath;
    }

    private void printMaze(ArrayList<ArrayList<Integer>> maze) {
        for (var row : maze) {
            for (var col : row) {
                System.out.print(col + "\t");
            }
            System.out.println();
        }
    }

    private void printSolution(ArrayList<ArrayList<Integer>> maze, ArrayList<ArrayList<Integer>> path) {
        System.out.println("Maze Solution:");
        for (int i = 0; i < maze.size(); i++) {
            for (int j = 0; j < maze.get(i).size(); j++) {
                int value = path.get(i).get(j);
                if (value == -1) {
                    System.out.print("X\t");
                } else {
                    System.out.print(value + "\t");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        String filename = "maze.txt";
        MazeRunner runner = new MazeRunner(filename);
    }
}
