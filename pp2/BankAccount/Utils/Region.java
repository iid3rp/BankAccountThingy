package BankAccountThingy.pp2.BankAccount.Utils;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD}) // This annotation can be applied to methods
public @interface Region 
{
    String value(); // Define a value attribute for the annotation
}
