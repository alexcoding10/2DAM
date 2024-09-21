// src/index.ts
import express, { Request, Response } from "express";
import dotenv from "dotenv";
import router_item from "./routes/items";

dotenv.config();

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware to parse JSON requests
app.use(express.json());

// Basic route
app.get("/", (req: Request, res: Response) => {
  res.send("Hello, TypeScript API!");
});
// use router items

app.use("/items", router_item);


// Start the server
app.listen(PORT, () => {
  console.log(`Server is running on http://localhost:${PORT}`);
});
