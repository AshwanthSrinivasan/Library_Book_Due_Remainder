//package com.example.librarybookdueremainder;
//
//import com.google.api.core.ApiFuture;
//import com.google.cloud.firestore.DocumentSnapshot;
//import com.google.cloud.firestore.Firestore;
//import com.google.cloud.firestore.QuerySnapshot;
//import com.google.firebase.cloud.FirestoreClient;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Properties;
//
//import javax.mail.MessagingException;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//
//
//
//
//
//
//
//
//
//public class EmailSender {
//
//    // SMTP configuration
//    private static final String SMTP_HOST = "smtp.gmail.com";
//    private static final String SMTP_USERNAME = "appleashwanth2004@gmail.com";
//    private static final char [] SMTP_PASSWORD = "Lakshmivasan74@" .toCharArray();
//    private static final int SMTP_PORT = 465;
//
//    public static void sendEmailNotification(String studentRollNumber, String bookName, String returnDate) {
//        // Construct student's email address
//        String studentEmail = studentRollNumber + "@rajalakshmi.edu.in";
//
//        // Email configuration
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", SMTP_HOST);
//        props.put("mail.smtp.port", SMTP_PORT);
//
//        // Create a session with authentication
//        javax.mail.Session session = javax.mail.Session.getInstance(props);
//
//        try {
//            // Construct the email message
//            MimeMessage  message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(SMTP_USERNAME));
//            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(studentEmail));
//            message.setSubject("Reminder: Return Borrowed Book");
//            message.setText("Dear Student,\n\nThis is a reminder to return the book \"" + bookName + "\" by " + returnDate + ".\n\nRegards,\nLibrary Management System");
//
//            // Send the email
//            Transport.send(message);
//            System.out.println("Email sent successfully to: " + studentEmail);
//        } catch (MessagingException e) {
//            System.err.println("Error sending email: " + e.getMessage());
//        }
//    }
//
//    public static void main(String[] args) {
//        try {
//            // Get current date
//            Calendar cal = Calendar.getInstance();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            String currentDate = dateFormat.format(cal.getTime());
//
//            // Access Firestore instance
//            Firestore db = FirestoreClient.getFirestore();
//
//            // Query borrowed books with return date equal to current date
//            ApiFuture<QuerySnapshot> future = db.collectionGroup("BorrowedBooks")
//                    .limit(10)
//                    .get();
//
//            QuerySnapshot documents = future.get();
//
//            // Iterate through documents and send email notifications
//            for (DocumentSnapshot doc : documents) {
//                String studentRollNumber = doc.getReference().getParent().getParent().getId();
//                String bookName = doc.getString("bookName");
//                String returnDate = doc.getString("returnDate");
//
//                // Convert return date string to Date object
//                Date formattedReturnDate = dateFormat.parse(returnDate);
//
//                // Format return date back to string to compare with current date
//                String formattedReturnDateString = dateFormat.format(formattedReturnDate);
//
//                // Check if return date is equal to current date
//                if (formattedReturnDateString.equals(currentDate)) {
//                    sendEmailNotification(studentRollNumber, bookName, returnDate);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
