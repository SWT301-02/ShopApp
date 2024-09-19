// in this file you can append custom step methods to 'I' object

export = function () {
  return actor({

    // Define custom steps here, use 'this' to access default methods of I.
    // It is recommended to place a general 'login' function here.
    async login() {
      this.amOnPage('http://localhost:8085/SWT301_Test_Project');
      this.see('Luxury Fashion Collection', 'h1');
      this.click('Login');
      this.see('Elegant Simplicity', 'h1');
      this.fillField('userID', 'admin');
      this.fillField('Password', '123456');
      this.click('Login');
    },

    async logout() {
      this.click('Logout');
    }

  });
}
