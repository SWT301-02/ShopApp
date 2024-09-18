import Helper from '@codeceptjs/helper';
const { GroqClient } = require('groq-sdk');

class GroqAI extends Helper {
    async generateTest(testType: string, description: string) {
        const client = new GroqClient(process.env.GROQ_API_KEY);
        const prompt = `Generate a ${testType} test for the following scenario: ${description}`;

        const chatCompletion = await client.chat.completions.create({
            messages: [{ role: "user", content: prompt }],
            model: "mixtral-8x7b-32768",
        });

        return chatCompletion.choices[0]?.message?.content || '';
    }
}

module.exports = GroqAI;