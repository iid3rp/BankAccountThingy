package pp2.BankAccount.Utils;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) // This annotation can be applied to class types :3

// Intention annotation - developed by Francis (iid3rp) Madanlo
/*
    This was intentionally made (pun intended) to put certain fields in the codes to create a reason
    why it is coded like that, and creates an annotation that some things are intentional and cannot
    be modified for certain reasons and should be asked for permission to modify when collaboration
    and code cleaning....
*/

public @interface Intention 
{
    boolean isPublic() default true;
    String reason() default "";
}
