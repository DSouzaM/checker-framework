import java.lang.annotation.*;

import checkers.quals.*;

/**
 * Units of frequency, such as hertz (@{@link Hz}).
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@TypeQualifier
@SubtypeOf( { Unqualified.class } )
public @interface Frequency {}
