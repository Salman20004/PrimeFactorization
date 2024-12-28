const express = require('express');
const { exec } = require('child_process');
const path = require('path');

const app = express();
const port = 3000;


app.use(express.static(__dirname)); 




app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'index.html'));
});

app.get('/prime-factorization', (req, res) => {
    const number = req.query.number;

    console.log(`Received number: ${number}`);


    if (!number || isNaN(number) || number < 2) {
        console.log('Invalid number received.');
        return res.status(400).json({ error: 'Please enter a valid number greater than 1.' });
    }

    console.log('Calling Java program...');


    exec(`java PrimeFactorization ${number}`, (error, stdout, stderr) => {
        if (error) {
            console.error(`exec error: ${error}`);
            return res.status(500).json({ error: 'Error running the Java program.' });
        }

        if (stderr) {
            console.error(`stderr: ${stderr}`);
            return res.status(500).json({ error: `Java program error: ${stderr}` });
        }

        console.log(`Java output: ${stdout}`);

        const trimmedOutput = stdout.trim();
        if (trimmedOutput.includes("is a prime number")) {
            res.json({ factors: [number] });
        } else {
            const outputLines = trimmedOutput.split("\n");
            let factors = outputLines.find(line => line.includes("Prime Factorization"));
            if (factors) {
                factors = factors.split(":")[1].trim();
                res.json({ factors: factors.split(" x ") });
            } else {
                console.error('Failed to extract factors from Java output.');
                res.status(500).json({ error: 'Failed to extract factors from Java output.' });
            }
        }
    });
});

app.listen(port, () => {
    console.log(`Server running at http://localhost:${port}`);
});
