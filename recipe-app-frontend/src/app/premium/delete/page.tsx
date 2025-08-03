import { DeleteRecipe } from "app/components/premium/DeleteRecipe"
import { Metadata } from "next"

export const runtime = "edge"

export const metadata: Metadata = {
  title: "✨ BiaChef | Delete Recipe",
  description: "Welcome to the best recipe app, learn how to cook delicious food",
  keywords: ["recipes", "health", "diet","food"],
}


export default function CreateRecipePage() {
  return <DeleteRecipe />
}