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
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
import javax.imageio.ImageIO;

public class HttpServer {
  public static void main(String[] args) throws IOException {
   ServerSocket serverSocket = null;
   int puerto = getPort();
   

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
                //System.out.println("Recibí: " + inputLine);

                if (inputLine.contains("GET")){
                    //System.out.println("Vamos bien: " + inputLine);
                    //System.out.println(path);

                 String path="";
                 path = inputLine.substring(inputLine.indexOf("/"),inputLine.lastIndexOf(" "));
                 path = getPath(path);

                 File archivo = new File(path);
               
                if (path.contains("PNG") || path.contains("png")){
                     getImagen(path, clientSocket.getOutputStream(),out);            
                }    
                else if (path.contains(".html") || path.contains(".js")){
                     getArchivo(path, clientSocket.getOutputStream()); 
                }
                else {
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
                   + "<h1>No existe el archivo seleccionado</h1>\n"
                   + "</body>\n"
                   + "</html>\n" + inputLine;
                  out.println(outputLine); 
                }
             }       
             if (!in.ready()) {break; }                      
            } 
              out.close(); 
              in.close(); 
              clientSocket.close(); 
              serverSocket.close();
            }
        
     
  }

   private static String getPath(String archivo){
        String path = "/src/main/resources";
        return path+archivo;
   }
   

   
   public static void getImagen(String type, OutputStream clienteOutput, PrintWriter out) throws IOException {
        try {
            BufferedImage image = ImageIO.read(new File(System.getProperty("user.dir") + type));
            ByteArrayOutputStream ArrBytes = new ByteArrayOutputStream();
            DataOutputStream writeImg = new DataOutputStream(clienteOutput);
            String img = "HTTP /1.1 404 NOT FOUND \r\n"
                    + "Content-Type: text/html; charset=\"UTF-8\" \r\n"
                    + "\r\n";
            ImageIO.write(image, "PNG", ArrBytes);
            writeImg.writeBytes("HTTP/1.1 200 OK \r\n");
            writeImg.writeBytes("Content-Type: image/png \r\n");
            writeImg.writeBytes("\r\n");
            writeImg.write(ArrBytes.toByteArray());
            System.out.println(System.getProperty("user.dir") + type);
        } catch (IOException e) {
            out.println("r" + e.getMessage());
        }
    }

    
    private static void getArchivo(String route, OutputStream outputStream) throws IOException {
        
        try {
            String text = "";
            String temp;
            BufferedReader t = new BufferedReader(new FileReader(System.getProperty("user.dir") + route));
            while ((temp = t.readLine()) != null) {
             
                text = text + temp;
            }
            if (route.contains(".js")){
                outputStream.write(("HTTP/1.1 201 FOUND  \r\n"
                        + "Content-Type: application/json; charset=\"UTF-8\" \r\n"
                        + "\r\n"
                        + text).getBytes());
                }
            else if (route.contains(".html")){
                outputStream.write(("HTTP/1.1 201 Found  \r\n"
                    + "Content-Type: text/html; charset=\"utf-8\" \r\n"
                    + "\r\n"
                    + text).getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


   
  
   static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 57000;
    }
  
}
