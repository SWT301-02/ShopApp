Feature('PRJ301_3W_1_JSLT Tests');

// Unit Tests
Scenario('Unit Test Example', ({ I }) => {
    // Add your unit test logic here
    I.assertEqual(1 + 1, 2, 'Basic addition should work');
});

// Integration Tests
Scenario('Integration Test Example', ({ I }) => {
    // Add your integration test logic here
    I.amOnPage('/');
    I.see('Welcome to PRJ301_3W_1_JSLT');
});

// Additional Unit Tests
Scenario('UserDAO - checkLogin', async ({ I }) => {
    const userDAO = new UserDAO();
    const result = await userDAO.checkLogin('testUser', 'testPassword');
    I.assertNotEqual(result, null, 'Login should return a user object');
    I.assertEqual(result.getUserID(), 'testUser', 'User ID should match');
});

Scenario('UserDAO - insertUser', async ({ I }) => {
    const userDAO = new UserDAO();
    const newUser = new UserDTO('newUser', 'fullName', 'password', 'US');
    const result = await userDAO.insertUser(newUser);
    I.assertTrue(result, 'User insertion should be successful');
});

Scenario('ProductDAO - getProduct', async ({ I }) => {
    const productDAO = new ProductDAO();
    const product = await productDAO.getProduct('testProductID');
    I.assertNotEqual(product, null, 'Product should be retrieved');
    I.assertEqual(product.getProductID(), 'testProductID', 'Product ID should match');
});

Scenario('ProductDAO - updateQuantity', async ({ I }) => {
    const productDAO = new ProductDAO();
    const result = await productDAO.updateQuantity('testProductID', 10);
    I.assertTrue(result, 'Quantity update should be successful');
});

Scenario('LoginController - processRequest', async ({ I }) => {
    const loginController = new LoginController();
    const request = { getParameter: (param) => param === 'userID' ? 'testUser' : 'testPassword' };
    const response = { sendRedirect: (url) => url };
    const result = await loginController.processRequest(request, response);
    I.assertEqual(result, 'user.jsp', 'Should redirect to user page on successful login');
});

// Integration Tests
Scenario('Login and Session Management', async ({ I }) => {
    I.amOnPage('/login.jsp');
    I.fillField('userID', 'testUser');
    I.fillField('password', 'testPassword');
    I.click('Login');
    I.seeInCurrentUrl('/user.jsp');
    I.see('Welcome, testUser');
});

// Additional Integration Tests
Scenario('Add to Cart and Checkout', async ({ I }) => {
    I.amOnPage('/shop.jsp');
    I.click('Add to Cart', { css: '.product-item' });
    I.amOnPage('/cart.jsp');
    I.see('1 item in your cart');
    I.click('Proceed to Checkout');
    I.seeInCurrentUrl('/checkout.jsp');
    I.click('Confirm Order');
    I.see('Thank you for your order');
});

// New Integration Test
Scenario('Admin User Management', async ({ I }) => {
    I.loginAsAdmin();
    I.amOnPage('/admin.jsp');
    I.click('Manage Users');
    I.click('Add New User');
    I.fillField('userID', 'newUser');
    I.fillField('fullName', 'New User');
    I.fillField('password', 'password123');
    I.selectOption('roleID', 'US');
    I.click('Add User');
    I.see('User added successfully');
});

// API Tests
Scenario('API - Get Product Details', async ({ I }) => {
    const response = await I.sendGetRequest('/api/product/testProductID');
    I.assertEqual(response.status, 200);
    I.assertEqual(response.data.productID, 'testProductID');
});

Scenario('API - Create Order', async ({ I }) => {
    const orderData = {
        userID: 'testUser',
        products: [{ productID: 'testProductID', quantity: 2 }]
    };
    const response = await I.sendPostRequest('/api/order', orderData);
    I.assertEqual(response.status, 201);
    I.assertNotEqual(response.data.orderID, null);
});