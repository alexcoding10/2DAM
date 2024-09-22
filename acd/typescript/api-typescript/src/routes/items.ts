import { Request, Response, Router } from "express";

const router_item = Router();

// In-memory data store for items
const items: string[] = ["Item 1", "Item 2", "Item 3"];

//Get all items
router_item.get("/",(req:Request,res:Response) => {
    res.json(items)
})

// Add a new item
router_item.post("/",(req:Request,res:Response) => {
    const { item } = req.body;
    // Validate the item
    if (!item) {
        return res.status(400).json({ error: "Item is required" });
      }
      // Add the item to the data store
    items.push(item);
    res.status(201).json({ message: "Item added successfully" });
})

export default router_item;