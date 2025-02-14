/*
 * This source file was generated by the Gradle 'init' task
 */
package org.battleship;

import java.io.*;

public class App {
      private TextPlayer player1, player2;

      public App(TextPlayer player1, TextPlayer player2) {
            this.player1 = player1;
            this.player2 = player2;
      }

      public static void main(String[] args) throws IOException {
            Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
            Board<Character> b2 = new BattleShipBoard<Character>(10, 20, 'X');
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            // (*) Readers provide characters and Streams provide bytes.  Generally when
            //    working with text, you want a Reader.  You can always wrap an InputStream
            //    in an InputStreamReader to do the conversion.
            V1ShipFactory factory = new V1ShipFactory();

            // 2 players share BufferedReader and System.out
            // 2 players have their own board and view
            TextPlayer p1 = new TextPlayer("A", b1, input, System.out, factory);
            TextPlayer p2 = new TextPlayer("B", b2, input, System.out, factory);

            App app = new App(p1, p2);
            app.doPlacementPhase();
            app.firePhase();
      }

      public void doPlacementPhase() throws IOException {
            player1.doPlacementPhase();
            player2.doPlacementPhase();
      }

      public void firePhase() throws IOException {
            while (true) {
                  player1.firePhase(player2, "My Ocean", "Player" + player2.getName() + "'s Ocean");
                  if (player2.isLost()) {
                        System.out.println("Player" + player1.getName() + " wins!");
                        break;
                  }
                  player2.firePhase(player1, "My Ocean", "Player" + player1.getName() + "'s Ocean");
                  if (player1.isLost()) {
                        System.out.println("Player" + player2.getName() + " wins!");
                        break;
                  }
            }
      }
}
