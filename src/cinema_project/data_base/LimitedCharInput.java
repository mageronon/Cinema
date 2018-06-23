package cinema_project.data_base;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class LimitedCharInput extends PlainDocument {
    private int limit;
    public LimitedCharInput(int lim){
        this.limit = lim;
    }

    public void insertString(int offset, String str, AttributeSet set) throws BadLocationException {
        if(str == null){
            return;
        }else if((getLength() + str.length()) <= limit){
            //str = str.toUpperCase();
            super.insertString(offset, str, set);
        }
    }
}
