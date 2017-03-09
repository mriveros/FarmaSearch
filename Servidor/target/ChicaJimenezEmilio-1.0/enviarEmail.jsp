<%@ page contentType="text/html" language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,javax.mail.*"%>
<%@ page import="javax.mail.internet.*,javax.activation.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>

<%!String email;%> 
<%email=request.getParameter("email");%> 
<%
   String result;
   // Recipient's email ID needs to be mentioned.
   String to = email;

   // Sender's email ID needs to be mentioned
   String from = "RU EMAIL";

   // Assuming you are sending email from localhost
   String host = "smtp.gmail.com";

   // Get system properties object
   Properties properties = System.getProperties();

   // Setup mail server
   properties.setProperty("mail.smtp.host", host);
   
   properties.setProperty("mail.smtp.user", from);
   
    properties.setProperty("mail.smtp.port", "465");
    properties.setProperty("mail.smtps.auth", "true");
    properties.setProperty("mail.smtp.starttls.enable", "true");
    properties.setProperty("mail.smtp.socketFactory.port", "465"); 
    properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
    properties.setProperty("mail.smtp.socketFactory.fallback", "false"); 
   
   properties.setProperty("mail.smtp.password", "TU CONTRASEÑA");

   // Get the default Session object.
   Session mailSession = Session.getDefaultInstance(properties);

   try{
      // Create a default MimeMessage object.
      MimeMessage message = new MimeMessage(mailSession);
      // Set From: header field of the header.
      message.setFrom(new InternetAddress(from));
      // Set To: header field of the header.
      message.addRecipient(Message.RecipientType.TO,
                               new InternetAddress(to));
      // Set Subject: header field
      message.setSubject("Recordatorio de contraseña!");
      // Now set the actual message
      message.setText("Pinche en el siguiente enlace para reestablecer su contraseña: <a>enlace</a>");
      // Send message
      Transport.send(message);
      result = "Mensaje enviado....";
   }catch (MessagingException mex) {
      mex.printStackTrace();
      result = "Error: unable to send message....";
   }
%>