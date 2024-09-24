package sample.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    private Email email;

    @BeforeEach
    void setUp() {
        email = new Email();
    }

    @Test
    void testIsValidEmail() {
        assertTrue(email.isValidEmail("test@example.com"));
        assertTrue(email.isValidEmail("user.name+tag@example.co.uk"));
        assertFalse(email.isValidEmail("invalid.email"));
        assertFalse(email.isValidEmail("@example.com"));
        assertFalse(email.isValidEmail("user@.com"));
    }

    @Test
    void testIsValidEmailWithSpecialCharacters() {
        assertTrue(email.isValidEmail("test+123@example.com"));
        assertTrue(email.isValidEmail("test.name@example.co.uk"));
        assertFalse(email.isValidEmail("test@example@com"));
    }

    @Test
    void testSubjectContact() {
        String name = "John Doe";
        String expected = "ClothesShop - Chào John Doe cảm ơn bạn vì đã liên hệ với chúng tôi";
        assertEquals(expected, email.subjectContact(name));
    }

    @Test
    void testSubjectNewOrder() {
        assertEquals("ClothesShop - Đặt hàng thành công", email.subjectNewOrder());
    }

    @Test
    void testSubjectSubsribe() {
        assertEquals("ClothesShop - bạn có thông báo mới", email.subjectSubsribe());
    }

    @Test
    void testSubjectForgotPass() {
        assertEquals("ClothesShop - mã code xác nhận", email.subjectForgotPass());
    }

    @Test
    void testMessageContact() {
        String name = "John Doe";
        String message = email.messageContact(name);
        assertTrue(message.contains("Chào John Doe,"));
        assertTrue(message.contains("CẢM ƠN VÌ BẠN ĐÃ LIÊN HỆ CLOTHES SHOP"));
    }

    @Test
    void testMessageContactContainsImportantElements() {
        String name = "Jane Doe";
        String message = email.messageContact(name);
        assertTrue(message.contains("<html>"));
        assertTrue(message.contains("<body"));
        assertTrue(message.contains("1900 9090"));
        assertTrue(message.contains("clothesshop@gmail.com"));
    }

    @Test
    void testMessageNewOrder() {
        String name = "John Doe";
        int quantity = 2;
        double total = 100.00;
        String paymentMethod = "Credit Card";
        String message = email.messageNewOrder(name, quantity, total, paymentMethod);
        assertTrue(message.contains("Khách hàng John Doe,"));
        assertTrue(message.contains("Số lượng sản phẩm: <span style=\"color:#e67e22;font-weight: bold;\"> 2 </span>"));
        assertTrue(message
                .contains("Số tiền sẽ thanh toán: <span style=\"color:#e67e22;font-weight: bold;\">100.0</span>"));
        assertTrue(message
                .contains("Hình thức thanh toán: <span style=\"color:#e67e22;font-weight: bold;\">Credit Card</span>"));
    }

    @Test
    void testMessageNewOrderWithZeroQuantityAndTotal() {
        String name = "Zero Order";
        int quantity = 0;
        double total = 0.00;
        String paymentMethod = "Cash";
        String message = email.messageNewOrder(name, quantity, total, paymentMethod);
        assertTrue(message.contains("Số lượng sản phẩm: <span style=\"color:#e67e22;font-weight: bold;\"> 0 </span>"));
        assertTrue(
                message.contains("Số tiền sẽ thanh toán: <span style=\"color:#e67e22;font-weight: bold;\">0.0</span>"));
    }

    @Test
    void testMessageFogot() {
        String name = "John Doe";
        int code = 123456;
        String message = email.messageFogot(name, code);
        assertTrue(message.contains("Chào John Doe,"));
        assertTrue(message.contains(
                "Mã khôi phục mật khẩu của bạn là:<span style=\"color:#e67e22;font-weight: bold;\">123456</span>"));
    }

    @Test
    void testMessageFogotWithLargeCode() {
        String name = "Large Code";
        int code = 999999999;
        String message = email.messageFogot(name, code);
        assertTrue(message.contains(
                "Mã khôi phục mật khẩu của bạn là:<span style=\"color:#e67e22;font-weight: bold;\">999999999</span>"));
    }

    @Test
    void testMessageSubscribe() {
        String message = email.messageSubscribe();
        assertTrue(message.contains("CHÀO MỪNG BẠN ĐÃ ĐẾN VỚI CLOTHES SHOP"));
        assertTrue(message.contains(
                "Bạn sẽ là một trong những người được nhận thông báo <span style=\"color:#e67e22;\">SALE, NEW PRODUCT</span> sớm nhất."));
    }

    @Test
    void testAllMessagesContainCommonElements() {
        String[] messages = {
                email.messageContact("Test"),
                email.messageNewOrder("Test", 1, 10.0, "Cash"),
                email.messageFogot("Test", 123456),
                email.messageSubscribe()
        };

        for (String message : messages) {
            assertTrue(message.contains("<html>"));
            assertTrue(message.contains("<body"));
            assertTrue(message.contains("ClothesShop"));
            assertTrue(message.contains("Ho Chi Minh - 0123 456 789 - clothesshop@gmail.com"));
        }
    }

    @Test
    void testIsValidEmailWithLongDomains() {
        assertTrue(email.isValidEmail("test@subdomain.example.com"));
        assertTrue(email.isValidEmail("test@example.co.uk"));
        assertFalse(email.isValidEmail("test@.com"));
    }

    @Test
    void testMessageNewOrderWithLargeValues() {
        String name = "Big Order";
        int quantity = 1000000;
        double total = 9999999.99;
        String paymentMethod = "Bank Transfer";
        String message = email.messageNewOrder(name, quantity, total, paymentMethod);
        assertTrue(message
                .contains("Số lượng sản phẩm: <span style=\"color:#e67e22;font-weight: bold;\"> 1000000 </span>"));
        assertTrue(message
                .contains("Số tiền sẽ thanh toán: <span style=\"color:#e67e22;font-weight: bold;\">9999999.99</span>"));
    }

    @Disabled("This test sends an actual email and should be run manually")
    @Test
    void testSendEmail() {
        String subject = "Test Subject";
        String message = "<h1>Test Message</h1>";
        String to = "test@example.com";

        // This will actually attempt to send an email
        // Be cautious when running this test
        assertDoesNotThrow(() -> email.sendEmail(subject, message, to));
    }
}
