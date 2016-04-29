package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class Main
{

    public Main()
    {
        
    }
    
    static PrintWriter writer;
    
    public static void main(String[] args) throws XMLStreamException, IOException
    {
        Main m = new Main();
        
        File[] files = new File(".").listFiles();
        
        writer = new PrintWriter(new BufferedWriter(new FileWriter("output.txt")));
        
        for (File file : files)
        {
            if (file.isFile() && file.getName().contains(".xml")) 
            {
                try
                {
                    m.readXMLFile(file);
                } catch (XMLStreamException e){
                    System.out.println("XML Stream Exception: file \"" + file.getName() + "\" skipped");
                    System.out.println("===========");
                    continue;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
            }
            else
            {
                if (file.getName().contains(".jar"))
                {
                    continue;
                }
                else
                {
                    System.out.println("File: \"" + file.getName() + "\" is not an XML file.");
                }
            }
        }
        writer.close();
    }

    public void readXMLFile(File file) throws XMLStreamException, IOException
    {
        XMLInputFactory inFact = XMLInputFactory.newInstance();
        InputStream in = new FileInputStream(file.toString());
        XMLEventReader eventRead = inFact.createXMLEventReader(in);
        if (eventRead.peek() == null)
        {
            return;
        }
        System.out.println("Filename: " + file.getName());
        writer.println("Filename: " + file.getName());
        
        while (eventRead.hasNext())
        {
            XMLEvent event = eventRead.nextEvent();
            if (event.isEndElement())
            {
                if (event.asEndElement().getName().getLocalPart().equals("closing"))
                {
                    event = eventRead.nextEvent();
                    System.out.println("\n===========\n");
                    writer.println();
                    writer.println("===========");
                    writer.println();
                    continue;
                }
            }
            
            if (event.isStartElement())
            {
                if (event.asStartElement().getName().getLocalPart().equals("data1"))
                {
                    event = eventRead.nextEvent();
                    System.out.println("data1: " + event.asCharacters().getData());
                    writer.println("data1: " + event.asCharacters().getData());
                    continue;
                }
                
                // REPEAT ABOVE FOR EACH ELEMENT TO GET
            }
        }
    }

}