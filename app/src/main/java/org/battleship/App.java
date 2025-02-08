/*
 * This source file was generated by the Gradle 'init' task
 */
package org.battleship;

import java.io.*;

public class App {
      final Board<Character> theBoard;
      final BoardTextView view;
      final BufferedReader inputReader;
      final PrintStream out;

      public App(Board<Character> theBoard, Reader inputSource, PrintStream out) {
            this.theBoard = theBoard;
            this.view = new BoardTextView(theBoard);
            this.inputReader = new BufferedReader(inputSource);
            this.out = out;
      }

      public Placement readPlacement(String prompt) throws IOException {
            out.println(prompt);
            // Note that we are not doing error checking YET (later, in task 17).  Also note
            // that we don't print to System.out, we print to out.  Why?  We might want to print
            // somewhere else, especially for testing.   When we want System.out, we'll pass it into
            // the constructor.
            String s = inputReader.readLine();
            return new Placement(s);
      }

      /**
       *   - read a Placement (prompt: "Where would you like to put your ship?")
       *   - Create a basic ship based on the location in that Placement
       *     (orientation doesn't matter yet)
       *   - Add that ship to the board
       *   - Print out the board (to out, not to System.out)
       * @throws IOException
       */
      public void doOnePlacement() throws IOException {
            Placement currentPlacement = readPlacement("Where would you like to put your ship?");
            BasicShip bs = new BasicShip(currentPlacement.getWhere());
            theBoard.tryAddShip(bs);
            out.println(view.displayMyOwnBoard());
      }

      public static void main(String[] args) throws IOException {
            Board<Character> board = new BattleShipBoard<>(10, 20);
            App app = new App(board, new BufferedReader(new InputStreamReader(System.in)), System.out);
            // (*) Readers provide characters and Streams provide bytes.  Generally when
            //    working with text, you want a Reader.  You can always wrap an InputStream
            //    in an InputStreamReader to do the conversion.
            app.doOnePlacement();
      }


}
