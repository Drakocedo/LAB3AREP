/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arep.app;

/**
 *
 * @author david
 */
import java.net.*;
import java.io.*;

public class HttpServer {
  public static void main(String[] args) throws IOException {
   ServerSocket serverSocket = null;
   int puerto = getPort();
   String path="";

   while (true){
        try { 
           serverSocket = new ServerSocket(puerto);
        } catch (IOException e) {
           System.err.println("Could not listen on port: "+puerto);
           System.exit(1);
        }
        Socket clientSocket = null;
        try {
            System.out.println("Listo para recibir ...");
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        PrintWriter out = new PrintWriter(
                              clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                              new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;
        while ((inputLine = in.readLine()) != null) {
           System.out.println("Recibí: " + inputLine);
           if (!in.ready()) {break; }
           if (inputLine.contains("GET")){
               //System.out.println("Vamos bien: " + inputLine);
               //System.out.println(path);
               
            
            path = inputLine.substring(inputLine.indexOf("/"),inputLine.lastIndexOf(" "));
            
        
           }
        }
        
        if (path.contains("PNG") || path.contains("png")){
            
        }    
                                  
        
        outputLine = "HTTP/1.1 200 OK\r\n"
             + "Content-Type: text/html\r\n"
              + "\r\n"
              + "<!DOCTYPE html>\n"
              + "<html>\n"
              + "<head>\n"
              + "<meta charset=\"UTF-8\">\n"
              + "<title>Title of the document</title>\n"
              + "</head>\n"
              + "<body>\n"
              + "<h1>Hola samuel</h1>\n"
              + "</body>\n"
              + "</html>\n" + inputLine;
         out.println(outputLine);
         out.close(); 
         in.close(); 
         clientSocket.close(); 
         serverSocket.close();
   }     
  }
  

   static String getPath(String archivo){
        String path = "src/main/resources/";
        return path+archivo;
   }
   
   
   
   
  
   static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 36000;
    }
  
}