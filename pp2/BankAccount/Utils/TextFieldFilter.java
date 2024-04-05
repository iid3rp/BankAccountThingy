package BankAccountThingy.pp2.BankAccount.Utils;
import javax.swing.text.AttributeSet;
import javax.swing.text.DocumentFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class TextFieldFilter extends DocumentFilter 
{
    // thank you Stack Overflow with this one!!!
    // citation: https://stackoverflow.com/questions/11093326/restricting-jtextfield-input-to-integers
    // user: https://stackoverflow.com/users/522444/hovercraft-full-of-eels :3 :3 :3
    // index modifiers will be stated here below xd
    //
    // modified by iid3rp :3
    public enum DataType
    {
        TYPE_STRING, TYPE_CHARACTERS_ONLY, TYPE_NUMERICAL, TYPE_CURRENCY
    }
    public String dataType; // the string equivalent of the enum
    
    public TextFieldFilter()
    {
        this.dataType = DataType.TYPE_STRING.toString();
    }   
    
    // constructor with @parameters
    public TextFieldFilter(DataType d)
    {
        this.dataType = d.toString();
    }
    
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException 
    {
        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.insert(offset, string);
        
        if (test(sb.toString())) 
        {
            super.insertString(fb, offset, string, attr);
        } 
        else 
        {
            // warn the user and don't allow the insert
        }
    }

    private boolean test(String text) 
    {
        if (text.isEmpty()) 
        {
            return true; // Allow blank document
        }
        try 
        {
            switch(dataType)
            {
                // default choice
                case "TYPE_STRING":
                {
                    break;
                }
                
                case "TYPE_NUMERICAL":
                {
                    Long.parseLong(text);
                    
                    break;
                }
                
                // currency based
                case "TYPE_CURRENCY":
                {
                    Double.parseDouble(text);
                    
                    String[] parts = text.split("\\.");
                    if (parts.length > 1) 
                    {
                        // Check if the fractional part has more than two digits
                        String fractionalPart = parts[1];
                        if (fractionalPart.length() > 2) 
                        {
                            return false; // More than two decimal places
                        }
                    }
                    if(text.toLowerCase().contains("d") || text.toLowerCase().contains("f"))
                    {
                        return false;
                    }
                    break;
                }
                case "TYPE_CHARACTERS_ONLY":
                {
                    if(!text.matches("[a-zA-Z]+")) 
                    {
                        return false;
                    }
                    break;
                }
                default:
                {
                    throw new IllegalArgumentException("Unknown data type: " + dataType);
                }
            }
        
            return true;
        } 
        catch (NumberFormatException e) 
        {
            return false;
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException 
    {
        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.replace(offset, offset + length, text);
    
        if(test(sb.toString())) 
        {
            super.replace(fb, offset, length, text, attrs);
        } 
        else 
        {
            // warn the user and don't allow the insert
        }
 
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException 
    {
        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.delete(offset, offset + length);
    
        if (test(sb.toString())) 
        {
           super.remove(fb, offset, length);
        } 
        else 
        {
           // warn the user and don't allow the insert
        }
    }
}