package pp2.BankAccount.Utils;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) // This annotation can be applied to class types :3

// Intention annotation - developed by Francis (iid3rp) Madanlo
/*
    This was intentionally made (pun intended) to put certain fields in the codes to create a reason
    why it is coded like that, and creates an annotation that some things are intentional and cannot
    be modified for 'that/those' certain reason/s and should be asked for permission to modify when collaboration, 
    code conflict, and code cleaning....
*/

public @interface Intention 
{
    boolean isPublic() default true; // stating the publicity of the feild
    String design() default ""; // whats the design of that field, the functionality
    String reason() default ""; // the reason why is it like that...
    
}
