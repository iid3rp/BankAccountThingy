package BankAccountThingy.pp2.BankAccount.Utils;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD})
public @interface Intention 
{
    /** Intention annotation - developed by Francis (iid3rp) Madanlo
     *
     *   <p>This was intentionally made (pun intended) to put certain fields/methods/constructors in the codes to create a reason
     *   why it is coded like that, and creates an annotation that some things are intentional and cannot
     *   be modified for 'that/those' certain reason/s and should be asked for permission to modify when collaboration,
     *   code conflict, and code cleaning....</p>
     */
    boolean isPublic() default true; // stating the publicity of the field / method
    String design() default ""; // what's the design of that field, the functionality
    String reason() default ""; // the reason why is it like that..
    RiskRate risk() default RiskRate.LOW;
}