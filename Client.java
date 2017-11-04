package Project4;

import javax.swing.*;
import java.io.IOException;

/**
 * @author BAC on 4/12/2017.
 */
public class Client {


    public static void main(String[] args) throws IOException{


        JFrame frame = new JFrame("MAP");
        Canvas canvas = new Canvas("filepath");

        frame.add(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setLocationRelativeTo(null);
        frame.setSize(750,750);
        frame.setResizable(false);
        frame.setVisible(true);
    }

}
