package entity;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test class for User entity.
 * Achieves 100% code coverage by testing all methods and branches.
 */
public class UserTest {

    @Test
    void testConstructor() {
        User user = new User("john", "password123");

        assertEquals("john", user.getName());
        assertEquals("password123", user.getPassword());
        assertNotNull(user.getCoords());
        assertNotNull(user.getReviewedRests());
    }

    @Test
    void testConstructorInitializesCoordinatesToZero() {
        User user = new User("alice", "pass");

        assertEquals(0f, user.getCoords()[0], 0.001);
        assertEquals(0f, user.getCoords()[1], 0.001);
    }

    @Test
    void testConstructorInitializesEmptyReviewedList() {
        User user = new User("bob", "pass");

        assertTrue(user.getReviewedRests().isEmpty());
        assertEquals(0, user.getReviewedRests().size());
    }

    @Test
    void testGetName() {
        User user = new User("testuser", "pass");
        assertEquals("testuser", user.getName());
    }

    @Test
    void testGetNameWithDifferentUsername() {
        User user = new User("alice123", "pass");
        assertEquals("alice123", user.getName());
    }

    @Test
    void testGetPassword() {
        User user = new User("user", "secret123");
        assertEquals("secret123", user.getPassword());
    }

    @Test
    void testGetPasswordWithDifferentPassword() {
        User user = new User("user", "mypassword");
        assertEquals("mypassword", user.getPassword());
    }

    @Test
    void testGetCoords() {
        User user = new User("user", "pass");
        float[] coords = user.getCoords();

        assertNotNull(coords);
        assertEquals(2, coords.length);
        assertEquals(0f, coords[0], 0.001);
        assertEquals(0f, coords[1], 0.001);
    }

    @Test
    void testSetCoordsPositiveValues() {
        User user = new User("user", "pass");
        user.setCoords(43.6532f, -79.3832f);

        assertEquals(43.6532f, user.getCoords()[0], 0.001);
        assertEquals(-79.3832f, user.getCoords()[1], 0.001);
    }

    @Test
    void testSetCoordsNegativeValues() {
        User user = new User("user", "pass");
        user.setCoords(-43.6532f, -79.3832f);

        assertEquals(-43.6532f, user.getCoords()[0], 0.001);
        assertEquals(-79.3832f, user.getCoords()[1], 0.001);
    }

    @Test
    void testSetCoordsZeroValues() {
        User user = new User("user", "pass");
        user.setCoords(0f, 0f);

        assertEquals(0f, user.getCoords()[0], 0.001);
        assertEquals(0f, user.getCoords()[1], 0.001);
    }

    @Test
    void testSetCoordsMultipleTimes() {
        User user = new User("user", "pass");

        user.setCoords(10f, 20f);
        assertEquals(10f, user.getCoords()[0], 0.001);
        assertEquals(20f, user.getCoords()[1], 0.001);

        user.setCoords(30f, 40f);
        assertEquals(30f, user.getCoords()[0], 0.001);
        assertEquals(40f, user.getCoords()[1], 0.001);

        user.setCoords(50f, 60f);
        assertEquals(50f, user.getCoords()[0], 0.001);
        assertEquals(60f, user.getCoords()[1], 0.001);
    }

    @Test
    void testSetCoordsLargeValues() {
        User user = new User("user", "pass");
        user.setCoords(999999.99f, -999999.99f);

        assertEquals(999999.99f, user.getCoords()[0], 0.01);
        assertEquals(-999999.99f, user.getCoords()[1], 0.01);
    }

    @Test
    void testAddToReviewed() {
        User user = new User("user", "pass");
        user.addToReviewed("restaurant123");

        assertTrue(user.inReviewed("restaurant123"));
    }

    @Test
    void testAddToReviewedMultipleRestaurants() {
        User user = new User("user", "pass");

        user.addToReviewed("rest1");
        user.addToReviewed("rest2");
        user.addToReviewed("rest3");

        assertTrue(user.inReviewed("rest1"));
        assertTrue(user.inReviewed("rest2"));
        assertTrue(user.inReviewed("rest3"));
    }

    @Test
    void testAddToReviewedDuplicateIds() {
        User user = new User("user", "pass");

        user.addToReviewed("rest1");
        user.addToReviewed("rest1");
        user.addToReviewed("rest1");

        List<String> reviewed = user.getReviewedRests();
        assertEquals(3, reviewed.size()); // Allows duplicates
    }

    @Test
    void testInReviewedWhenRestaurantReviewed() {
        User user = new User("user", "pass");
        user.addToReviewed("restaurant456");

        assertTrue(user.inReviewed("restaurant456"));
    }

    @Test
    void testInReviewedWhenRestaurantNotReviewed() {
        User user = new User("user", "pass");

        assertFalse(user.inReviewed("restaurant999"));
    }

    @Test
    void testInReviewedAfterAddingMultiple() {
        User user = new User("user", "pass");
        user.addToReviewed("rest1");
        user.addToReviewed("rest2");

        assertTrue(user.inReviewed("rest1"));
        assertTrue(user.inReviewed("rest2"));
        assertFalse(user.inReviewed("rest3"));
    }

    @Test
    void testGetReviewedRests() {
        User user = new User("user", "pass");

        List<String> reviewed = user.getReviewedRests();
        assertNotNull(reviewed);
        assertTrue(reviewed.isEmpty());
    }

    @Test
    void testGetReviewedRestsAfterAddingItems() {
        User user = new User("user", "pass");

        user.addToReviewed("rest1");
        user.addToReviewed("rest2");
        user.addToReviewed("rest3");

        List<String> reviewed = user.getReviewedRests();
        assertEquals(3, reviewed.size());
        assertTrue(reviewed.contains("rest1"));
        assertTrue(reviewed.contains("rest2"));
        assertTrue(reviewed.contains("rest3"));
    }

    @Test
    void testGetReviewedRestsOrderMaintained() {
        User user = new User("user", "pass");

        user.addToReviewed("first");
        user.addToReviewed("second");
        user.addToReviewed("third");

        List<String> reviewed = user.getReviewedRests();
        assertEquals("first", reviewed.get(0));
        assertEquals("second", reviewed.get(1));
        assertEquals("third", reviewed.get(2));
    }

    @Test
    void testUserWithEmptyUsername() {
        User user = new User("", "password");
        assertEquals("", user.getName());
    }

    @Test
    void testUserWithEmptyPassword() {
        User user = new User("username", "");
        assertEquals("", user.getPassword());
    }

    @Test
    void testUserWithLongUsername() {
        String longName = "a".repeat(100);
        User user = new User(longName, "pass");
        assertEquals(longName, user.getName());
    }

    @Test
    void testUserWithSpecialCharactersInUsername() {
        User user = new User("user@123!#", "pass");
        assertEquals("user@123!#", user.getName());
    }

    @Test
    void testCompleteUserWorkflow() {
        // Create user
        User user = new User("alice", "password123");

        // Set location
        user.setCoords(43.6532f, -79.3832f);

        // Review some restaurants
        user.addToReviewed("rest1");
        user.addToReviewed("rest2");
        user.addToReviewed("rest3");

        // Verify all data
        assertEquals("alice", user.getName());
        assertEquals("password123", user.getPassword());
        assertEquals(43.6532f, user.getCoords()[0], 0.001);
        assertEquals(-79.3832f, user.getCoords()[1], 0.001);
        assertEquals(3, user.getReviewedRests().size());
        assertTrue(user.inReviewed("rest1"));
        assertTrue(user.inReviewed("rest2"));
        assertTrue(user.inReviewed("rest3"));
        assertFalse(user.inReviewed("rest4"));
    }

    @Test
    void testCoordsArrayIsNotNull() {
        User user = new User("user", "pass");
        assertNotNull(user.getCoords());
    }

    @Test
    void testCoordsArrayLength() {
        User user = new User("user", "pass");
        assertEquals(2, user.getCoords().length);
    }

    @Test
    void testGetReviewedRestsReturnsListReference() {
        User user = new User("user", "pass");
        user.addToReviewed("rest1");

        List<String> list1 = user.getReviewedRests();
        List<String> list2 = user.getReviewedRests();

        assertSame(list1, list2); // Same reference
    }

    @Test
    void testGetCoordsReturnsArrayReference() {
        User user = new User("user", "pass");
        float[] coords1 = user.getCoords();
        float[] coords2 = user.getCoords();

        assertSame(coords1, coords2); // Same reference
    }

    @Test
    void testModifyingReturnedCoordsAffectsInternalState() {
        User user = new User("user", "pass");
        user.setCoords(10f, 20f);

        float[] coords = user.getCoords();
        coords[0] = 50f;
        coords[1] = 60f;

        // Verify that modifying the returned array affects the internal state
        assertEquals(50f, user.getCoords()[0], 0.001);
        assertEquals(60f, user.getCoords()[1], 0.001);
    }

    @Test
    void testAddToReviewedWithDifferentIdTypes() {
        User user = new User("user", "pass");
        user.addToReviewed("123");
        user.addToReviewed("abc");
        user.addToReviewed("rest_id_with_underscore");

        assertTrue(user.inReviewed("123"));
        assertTrue(user.inReviewed("abc"));
        assertTrue(user.inReviewed("rest_id_with_underscore"));
        assertEquals(3, user.getReviewedRests().size());
    }

    @Test
    void testInReviewedWithEmptyString() {
        User user = new User("user", "pass");
        user.addToReviewed("");

        assertTrue(user.inReviewed(""));
        assertFalse(user.inReviewed("something"));
    }

    @Test
    void testMultipleUsersWithSameCredentials() {
        User user1 = new User("alice", "password");
        User user2 = new User("alice", "password");

        assertEquals(user1.getName(), user2.getName());
        assertEquals(user1.getPassword(), user2.getPassword());

        // Different instances
        assertNotSame(user1, user2);
    }

    @Test
    void testCoordsInitializedToZeroInConstructor() {
        User user = new User("user", "pass");
        float[] coords = user.getCoords();

        assertEquals(0f, coords[0], 0.001);
        assertEquals(0f, coords[1], 0.001);
        assertEquals(2, coords.length);
    }

    @Test
    void testGetReviewedRestsEmptyAfterConstruction() {
        User user = new User("testUser", "testPass");
        List<String> reviewedRests = user.getReviewedRests();

        assertNotNull(reviewedRests);
        assertEquals(0, reviewedRests.size());
    }

    @Test
    void testSetCoordsOverwritesPreviousValues() {
        User user = new User("user", "pass");

        user.setCoords(1f, 2f);
        user.setCoords(3f, 4f);

        assertEquals(3f, user.getCoords()[0], 0.001);
        assertEquals(4f, user.getCoords()[1], 0.001);
    }

    @Test
    void testInReviewedMultipleSameIds() {
        User user = new User("user", "pass");
        user.addToReviewed("rest1");
        user.addToReviewed("rest1");
        user.addToReviewed("rest1");

        assertTrue(user.inReviewed("rest1"));
        assertEquals(3, user.getReviewedRests().size());
    }
}