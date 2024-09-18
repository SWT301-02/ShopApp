import 'dotenv/config';
import GroqClient from 'groq-sdk';
import assert from 'assert';

Feature('Groq AI Integration');

Scenario('Test Groq AI response', async ({ I }) => {
    const apiKey = process.env.GROQ_API_KEY;
    if (!apiKey) {
        throw new Error('GROQ_API_KEY is not set');
    }

    const client = new GroqClient({ apiKey });

    const chatCompletion = await client.chat.completions.create({
        messages: [{ role: "user", content: "Hello, Groq!" }],
        model: "mixtral-8x7b-32768",
    });

    const response = chatCompletion.choices[0]?.message?.content;

    assert(response, 'Groq AI should return a response');
    I.say('Groq AI Response: ' + response);
});