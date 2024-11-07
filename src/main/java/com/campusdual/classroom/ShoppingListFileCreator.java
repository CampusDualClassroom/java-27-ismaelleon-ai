package com.campusdual.classroom;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class JSONFileCreator {

    public static void createShoppingListJSON() {
        JsonObject shoppingList = new JsonObject();
        JsonArray itemsArray = new JsonArray();

        // Agregar elementos de la lista de la compra en formato JSON
        itemsArray.add(createJSONItem(2, "Manzana"));
        itemsArray.add(createJSONItem(1, "Leche"));
        itemsArray.add(createJSONItem(3, "Pan"));
        itemsArray.add(createJSONItem(2, "Huevo"));
        itemsArray.add(createJSONItem(1, "Queso"));
        itemsArray.add(createJSONItem(1, "Cereal"));
        itemsArray.add(createJSONItem(4, "Agua"));
        itemsArray.add(createJSONItem(6, "Yogur"));
        itemsArray.add(createJSONItem(2, "Arroz"));

        shoppingList.add("items", itemsArray);

        try (FileWriter fw = new FileWriter("src/main/resources/shoppingList.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(shoppingList);
            fw.write(json);
            fw.flush();
            System.out.println("Archivo JSON de la lista de la compra creado en src/main/resources/shoppingList.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JsonObject createJSONItem(int quantity, String itemDesc) {
        JsonObject item = new JsonObject();
        item.addProperty("quantity", quantity);
        item.addProperty("text", itemDesc);
        return item;
    }

    public static void createShoppingListXML() throws ParserConfigurationException, TransformerException {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();


            Element root = document.createElement("shoppinglist");
            document.appendChild(root);


            Element items = document.createElement("items");
            root.appendChild(items);


            items.appendChild(createXMLItem(document, "item", "quantity", "2", "Manzana"));
            items.appendChild(createXMLItem(document, "item", "quantity", "1", "Leche"));
            items.appendChild(createXMLItem(document, "item", "quantity", "3", "Pan"));
            items.appendChild(createXMLItem(document, "item", "quantity", "2", "Huevo"));
            items.appendChild(createXMLItem(document, "item", "quantity", "1", "Queso"));
            items.appendChild(createXMLItem(document, "item", "quantity", "1", "Cereal"));
            items.appendChild(createXMLItem(document, "item", "quantity", "4", "Agua"));
            items.appendChild(createXMLItem(document, "item", "quantity", "6", "Yogur"));
            items.appendChild(createXMLItem(document, "item", "quantity", "2", "Arroz"));

            writeToFile(document, "src/main/resources/shoppingList.xml");
        }

    private static void writeToFile(Document document, String pathFile) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "3");

        File file = new File(pathFile);
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(file);
        transformer.transform(source, result);

        System.out.println("Archivo XML de la lista de la compra creado en src/main/resources/shoppingList.xml");
    }

    private static Element createXMLItem(Document document, String tagName, String attribute, String attrValue, String textComponent) {
        Element item = document.createElement(tagName);
        item.setAttribute(attribute, attrValue);
        item.setTextContent(textComponent);
        return item;
    }

    public static void main(String[] args) {
        try {
            createShoppingListJSON();
            createShoppingListXML();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }
}


