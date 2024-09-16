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