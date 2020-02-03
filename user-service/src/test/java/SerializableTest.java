import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.*;

/**
 * create by wangfei on 2020-02-24
 */
@Slf4j
public class SerializableTest {

    @Test
    public void serialization() throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("F:\\111"));
        Person person = new Person();
        person.setAge(20);
        person.setName("wangwu");
        outputStream.writeObject(person);
    }

    @Test
    public void deserialization() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("F:\\111"));
        Person person = (Person) objectInputStream.readObject();
        log.info(person.toString());
    }

}

@Data
class Person implements Serializable{
//    private static final long serialVersionUID = -1;
    private String name;
    private Integer age;
//    private String ID;
}
