package entity;
import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    void testConstructorAndGetters(){
        User user = new User("Username", "Password");
        assertEquals("Username", user.getName());
        assertEquals("Password", user.getPassword());
        assertEquals(0f, user.getCoords()[0]);
        assertEquals(0f, user.getCoords()[1]);
    }

    @Test
    void testSetCoords(){
        User user = new User("Username", "Password");
        user.setCoords(1.5f, 2.0f);
        assertEquals(1.5f, user.getCoords()[0]);
        assertEquals(2.0f, user.getCoords()[1]);
    }

    @Test
    void testAddToReviewed(){
        User user = new User("Username", "Password");
        user.addToReviewed("254");
        assertTrue(user.inReviewed("254"));
    }

    @Test
    void testNotInReviewed(){
        User user = new User("Username", "Password");
        assertFalse(user.inReviewed("254"));
    }
}
