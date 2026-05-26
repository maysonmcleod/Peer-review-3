import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
/***************class LabelledField******************/

/* This class implements a TextField with an attached
   Label within a small Panel. The panel uses a
   left-justified FlowLayout so that the label and the
   text field remain close together at all times. */

public class LabelledField extends JPanel {

	// instance variables
	private JLabel theLabel;
	private JTextField theField;

	//the constructor, which requires the label, and the
	//number of characters expected in the text field.
	//the text field is initially empty.
	public LabelledField(String labelText,int fieldSize) {
		setLayout(new FlowLayout(FlowLayout.LEFT,0,0)); // 0,0 = No extra space in the panel
		add(theLabel = new JLabel(labelText,JLabel.RIGHT));
		add(theField = new JTextField(fieldSize));
	} //end LabelledField constructor

	//as above but with the option of having the field be editable or not
	public LabelledField(String labelText,int fieldSize, boolean editable) {
			setLayout(new FlowLayout(FlowLayout.LEFT,0,0)); // 0,0 = No extra space in the panel
			add(theLabel = new JLabel(labelText,JLabel.RIGHT));
			add(theField = new JTextField(fieldSize));
			theField.setEditable(editable);
	} //end LabelledField constructor

	//the following methods simply call the TextField method of the same name
	public void setText(String s) {theField.setText(s);}
	public String getText() {return theField.getText();}

} //end class LabelledField