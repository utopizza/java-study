package competition;

import java.util.Scanner;

public class MazeFactory {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        try {
            // get row and col
            String firstLine = in.nextLine();
            String[] size = firstLine.split(" ");
            if (size.length != 2) throw new IncorrectCommandException();
            int row = Integer.valueOf(size[0]);
            int col = Integer.valueOf(size[1]);
            if (row <= 0 || col <= 0) throw new NumberOutOfRangeException();

            // create maze
            String[][] maze = new String[row * 2 + 1][col * 2 + 1];
            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[0].length; j++) {
                    if (i % 2 == 1 && j % 2 == 1) maze[i][j] = "[R]";
                    else maze[i][j] = "[W]";
                }
            }

            // get connections
            String[] str = in.nextLine().split(";");
            if (str.length == 0) throw new IncorrectCommandException();
            for (int i = 0; i < str.length; i++) {
                String[] points = str[i].split(" ");
                if (points.length != 2) throw new IncorrectCommandException();

                String[] point1 = points[0].split(",");
                if (point1.length != 2) throw new IncorrectCommandException();

                String[] point2 = points[1].split(",");
                if (point2.length != 2) throw new IncorrectCommandException();

                int x1 = Integer.valueOf(point1[0]);
                int y1 = Integer.valueOf(point1[1]);
                int x2 = Integer.valueOf(point2[0]);
                int y2 = Integer.valueOf(point2[1]);
                if (x1 < 0 || x1 >= row || y1 < 0 || y1 >= col || x2 < 0 || x2 >= row || y2 < 0 || y2 >= col)
                    throw new NumberOutOfRangeException();
                if (x1 != x2 && y1 != y2 || x1 == x2 && Math.abs(y1 - y2) > 1 || y1 == y2 && Math.abs(x1 - x2) > 1)
                    throw new MazeFormatException();

                //update maze by connections
                int updateX = 0, updateY = 0;
                if (x1 == x2) updateX = x1 * 2 + 1;
                else updateX = Math.min(x1, x2) * 2 + 2;
                if (y1 == y2) updateY = y1 * 2 + 1;
                else updateY = Math.min(y1, y2) * 2 + 2;
                maze[updateX][updateY] = "[R]";
            }

            // print maze
            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[0].length; j++) {
                    System.out.print(maze[i][j] + " ");
                }
                System.out.print('\n');
            }

        } catch (NumberOutOfRangeException e) {
            System.out.println("Number out of range.");
        } catch (MazeFormatException e) {
            System.out.println("Maze format error.");
        } catch (IncorrectCommandException e) {
            System.out.println("Incorrect Command format.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (Exception e) {
            System.out.println("Unexpected Error.");
        }
    }
}

class NumberOutOfRangeException extends Exception {
}

class MazeFormatException extends Exception {
}

class IncorrectCommandException extends Exception {
}