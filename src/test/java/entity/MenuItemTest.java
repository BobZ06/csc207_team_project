package entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MenuItem entity.
 * Tests all constructors, getters, and setters to achieve 100% coverage.
 */
public class MenuItemTest {

    @Test
    public void testConstructor() {
        MenuItem item = new MenuItem("Burger", 12.99f, "Delicious burger");

        assertEquals("Burger", item.getName());
        assertEquals(12.99f, item.getPrice(), 0.001);
        assertEquals("Delicious burger", item.getDescription());
    }

    @Test
    public void testConstructorWithZeroPrice() {
        MenuItem item = new MenuItem("Free Water", 0.0f, "Complimentary");

        assertEquals("Free Water", item.getName());
        assertEquals(0.0f, item.getPrice(), 0.001);
        assertEquals("Complimentary", item.getDescription());
    }

    @Test
    public void testGetName() {
        MenuItem item = new MenuItem("Pizza", 15.50f, "Cheese pizza");
        assertEquals("Pizza", item.getName());
    }

    @Test
    public void testGetPrice() {
        MenuItem item = new MenuItem("Salad", 8.99f, "Fresh salad");
        assertEquals(8.99f, item.getPrice(), 0.001);
    }

    @Test
    public void testGetDescription() {
        MenuItem item = new MenuItem("Pasta", 13.50f, "Creamy alfredo");
        assertEquals("Creamy alfredo", item.getDescription());
    }

    @Test
    public void testSetName() {
        MenuItem item = new MenuItem("Original", 10.0f, "Test");
        item.setName("Updated");

        assertEquals("Updated", item.getName());
    }

    @Test
    public void testSetPrice() {
        MenuItem item = new MenuItem("Item", 10.0f, "Test");
        item.setPrice(15.99f);

        assertEquals(15.99f, item.getPrice(), 0.001);
    }

    @Test
    public void testSetDescription() {
        MenuItem item = new MenuItem("Item", 10.0f, "Original description");
        item.setDescription("New description");

        assertEquals("New description", item.getDescription());
    }

    @Test
    public void testMultipleUpdates() {
        MenuItem item = new MenuItem("Test", 5.0f, "Initial");

        item.setName("First Update");
        item.setPrice(10.0f);
        item.setDescription("First Description");

        assertEquals("First Update", item.getName());
        assertEquals(10.0f, item.getPrice(), 0.001);
        assertEquals("First Description", item.getDescription());

        item.setName("Second Update");
        item.setPrice(20.0f);
        item.setDescription("Second Description");

        assertEquals("Second Update", item.getName());
        assertEquals(20.0f, item.getPrice(), 0.001);
        assertEquals("Second Description", item.getDescription());
    }

    @Test
    public void testNegativePrice() {
        MenuItem item = new MenuItem("Item", -5.0f, "Test");
        assertEquals(-5.0f, item.getPrice(), 0.001);
    }

    @Test
    public void testEmptyStrings() {
        MenuItem item = new MenuItem("", 0.0f, "");

        assertEquals("", item.getName());
        assertEquals("", item.getDescription());
    }

    @Test
    public void testLargePrice() {
        MenuItem item = new MenuItem("Expensive", 999999.99f, "Very costly");
        assertEquals(999999.99f, item.getPrice(), 0.001);
    }
}