package ru.track.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Аннотация для преименования полей при сериализации
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface SerializedTo {
    String value();
}
class PSerializedTo{

    @SerializedTo(value = "SerializedTo Annotation")
    public static void example() throws NoSuchFieldException {
        PSerializedTo ob = new PSerializedTo();

        try {
            Class c = ob.getClass();

            // get the method example
            Method m = c.getMethod("example");
            Field f = c.getDeclaredField("fexample");
            // get the annotation for class 
            SerializedTo annotation = m.getAnnotation(SerializedTo.class);
            SerializedTo annotationf = f.getAnnotation(SerializedTo.class);
            // print the annotation
            System.out.println(annotation.value());
            System.out.println(annotationf.value());
        } catch (NoSuchMethodException exc) {
            exc.printStackTrace();
        }
    }
    public static void main(String args[]) throws NoSuchFieldException {
        example();

    }


}