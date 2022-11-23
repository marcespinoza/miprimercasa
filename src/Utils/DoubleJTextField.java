/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.awt.TextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javafx.scene.text.TextFlow;
import javax.swing.JTextField;

/**
 *
 * @author Marceloi7
 */
public class DoubleJTextField implements KeyListener{
    
    private JTextField textField;

    public DoubleJTextField(JTextField textField) {
        this.textField = textField;
    }   
    

    @Override
    public void keyTyped(KeyEvent e) {
         char ch = e.getKeyChar();
         if (!isNumber(ch) && !isValidSignal(ch) && !validatePoint(ch)  && ch != '\b') {
                e.consume();
         }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    private boolean isNumber(char ch){
        return ch >= '0' && ch <= '9';
    }

    private boolean isValidSignal(char ch){
        if( (textField.getText() == null || "".equals(textField.getText().trim()) ) && ch == '-'){
            return true;
        }

        return false;
    }

    private boolean validatePoint(char ch){
        if(ch != '.'){
            return false;
        }

        if(textField.getText() == null || "".equals(textField.getText().trim())){
            textField.setText("0.");
            return false;
        }

        return true;
    }
    
}
