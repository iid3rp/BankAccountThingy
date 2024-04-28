package BankAccountThingy.pp2.BankAccount.Utils;
import java.lang.annotation.*;
/**
 *
 *   <p>This was intentionally made (pun intended) to put certain fields/methods/constructors in the code to create
 *   a crucial argument, through the design of the stated code, the reason of the action, and how risky this action will be.</p>
 *
 *   <p>This creates an annotation that some things are intentional and cannot
 *   be modified for 'the' certain reason/s and should be asked for permission to modify when collaboration,
 *   code conflict, and code cleaning....</p>
 *
 * @author Francis (iid3rp) Madanlo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.LOCAL_VARIABLE, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD})
public @interface Intention 
{
    boolean isPublic() default true; // stating the publicity of the field / method
    String design() default ""; // what's the design of that field, the functionality
    String reason() default ""; // the reason why is it like that..
}