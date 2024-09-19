Feature('Groq AI Generated Tests');

Scenario('Test Groq AI response', async ({ I }) => {
    I.amOnPage('http://localhost:8085/SWT301_Test_Project');
    I.see('Luxury Fashion Collection', 'h1');

    // Here you would typically parse and execute the generated test
    // For demonstration, we'll just assert that a test was generated

});

Scenario('Test Login Functionality with Admin role', async ({ I }) => {
    I.amOnPage('http://localhost:8085/SWT301_Test_Project');
    I.see('Luxury Fashion Collection', 'h1');
    I.click('Login');
    I.see('Elegant Simplicity', 'h1');
    I.fillField('userID', 'admin');
    I.fillField('Password', '123456');
    I.click('Login');
    I.see('Welcome: Tao la ad', 'h1');

    // Here you would typically parse and execute the generated test
    // For demonstration, we'll just assert that a test was generated
});

Scenario('Test Logout Functionality', async ({ I }) => {
    I.amOnPage('http://localhost:8085/SWT301_Test_Project');
    I.see('Luxury Fashion Collection', 'h1');
    I.click('Logout');
    I.see('Luxury Fashion Collection', 'h1');

    // Here you would typically parse and execute the generated test
    // For demonstration, we'll just assert that a test was generated
});

Scenario('Test Search Functionality', async ({ I }) => {
    I.amOnPage('http://localhost:8085/SWT301_Test_Project');
    I.see('Luxury Fashion Collection', 'h1');
    I.fillField('search', 'shirt');
    I.click('Search');
    I.see('Search Results', 'h1');

    // Here you would typically parse and execute the generated test
    // For demonstration, we'll just assert that a test was generated
});