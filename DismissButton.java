import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;  //you shouldn't need any more than this!
/***************class DismissButton******************/

// This class implements a Button which, when pressed,
// will find its parent window and close that window.
// FIRST, this is NOT necessary: Swing provides a parameter on
// the window itself to automatically dispose on a close
// (see the constructor of the client window!)
// So why is this here? to illustrate what we have to do when
// things are not in a hierarchical relationship - here we follow
// up through the various panels, however deep, until we get to the
// top window, then close THAT.  While it's not necessary here,
// this kind of thing does happen, and you need to know how to do it!

public class DismissButton extends JButton implements ActionListener {
    DismissButton(String word){
        setText(word);
        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
      }
