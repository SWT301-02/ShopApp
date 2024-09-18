import { setHeadlessWhen, setCommonPlugins } from '@codeceptjs/configure';
require('dotenv').config();

// turn on headless mode when running with HEADLESS=true environment variable
// export HEADLESS=true && npx codeceptjs run
setHeadlessWhen(process.env.HEADLESS);

// enable all common plugins https://github.com/codeceptjs/configure#setcommonplugins
setCommonPlugins();

export const config: CodeceptJS.MainConfig = {
  tests: './*_test.ts',
  output: './output',
  helpers: {
    Playwright: {
      browser: 'chromium',
      url: 'http://localhost:8085/SWT301_Test_Project', // Update this to your application's URL
      show: true,
      waitForAction: 3000 // Wait for 3 seconds (3000 ms) before each action
    },
    GroqAI: {
      require: './helpers/groq_helper.ts'
    }
  },
  include: {
    I: './steps_file'
  },
  name: 'SWT301_Test_Project'
}