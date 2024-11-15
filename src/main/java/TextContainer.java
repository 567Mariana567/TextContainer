import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Target({ElementType.TYPE_USE, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@interface SaveTo {
    String path();
}

class TextContainer {
    String text = "Hello";

    public void save() throws IOException {

        FileWriter writer = new FileWriter(annotation.path());
        writer.write(text);
        writer.close();
    }

    private SaveTo annotation;

    public TextContainer() {

        for (Field field : getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(SaveTo.class)) {
                annotation = field.getAnnotation(SaveTo.class);
                break;
            }
        }
    }
}

class Saver {
    public static void save(Object obj) throws Exception {

        for (Method method : obj.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(SaveTo.class)) {
                method.invoke(obj);
                break;
            }
        }
    }
}

