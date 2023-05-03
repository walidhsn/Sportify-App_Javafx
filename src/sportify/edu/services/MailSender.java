/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sportify.edu.services;

import sportify.edu.entities.User;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Properties;
import java.time.LocalDate;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 *
 * @author moata
 */
public class MailSender {
    
    
    public static void sendMail(String recipient,String verifCode) throws MessagingException{
        String mailContent = "<!DOCTYPE html>\n" +
"<html>\n" +
"<head>\n" +
"\n" +
"    <meta charset=\"utf-8\">\n" +
"    <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n" +
"    <title>Email Confirmation</title>\n" +
"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
"    <style type=\"text/css\">\n" +
"        /**\n" +
"         * Google webfonts. Recommended to include the .woff version for cross-client compatibility.\n" +
"         */\n" +
"        @media screen {\n" +
"            @font-face {\n" +
"                font-family: 'Source Sans Pro';\n" +
"                font-style: normal;\n" +
"                font-weight: 400;\n" +
"                src: local('Source Sans Pro Regular'), local('SourceSansPro-Regular'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/ODelI1aHBYDBqgeIAH2zlBM0YzuT7MdOe03otPbuUS0.woff) format('woff');\n" +
"            }\n" +
"\n" +
"            @font-face {\n" +
"                font-family: 'Source Sans Pro';\n" +
"                font-style: normal;\n" +
"                font-weight: 700;\n" +
"                src: local('Source Sans Pro Bold'), local('SourceSansPro-Bold'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/toadOcfmlt9b38dHJxOBGFkQc6VGVFSmCnC_l7QZG60.woff) format('woff');\n" +
"            }\n" +
"        }\n" +
"\n" +
"        /**\n" +
"         * Avoid browser level font resizing.\n" +
"         * 1. Windows Mobile\n" +
"         * 2. iOS / OSX\n" +
"         */\n" +
"        body,\n" +
"        table,\n" +
"        td,\n" +
"        a {\n" +
"            -ms-text-size-adjust: 100%; /* 1 */\n" +
"            -webkit-text-size-adjust: 100%; /* 2 */\n" +
"        }\n" +
"\n" +
"        /**\n" +
"         * Remove extra space added to tables and cells in Outlook.\n" +
"         */\n" +
"        table,\n" +
"        td {\n" +
"            mso-table-rspace: 0pt;\n" +
"            mso-table-lspace: 0pt;\n" +
"        }\n" +
"\n" +
"        /**\n" +
"         * Better fluid images in Internet Explorer.\n" +
"         */\n" +
"        img {\n" +
"            -ms-interpolation-mode: bicubic;\n" +
"        }\n" +
"\n" +
"        /**\n" +
"         * Remove blue links for iOS devices.\n" +
"         */\n" +
"        a[x-apple-data-detectors] {\n" +
"            font-family: inherit !important;\n" +
"            font-size: inherit !important;\n" +
"            font-weight: inherit !important;\n" +
"            line-height: inherit !important;\n" +
"            color: inherit !important;\n" +
"            text-decoration: none !important;\n" +
"        }\n" +
"\n" +
"        /**\n" +
"         * Fix centering issues in Android 4.4.\n" +
"         */\n" +
"        div[style*=\"margin: 16px 0;\"] {\n" +
"            margin: 0 !important;\n" +
"        }\n" +
"\n" +
"        body {\n" +
"            width: 100% !important;\n" +
"            height: 100% !important;\n" +
"            padding: 0 !important;\n" +
"            margin: 0 !important;\n" +
"        }\n" +
"\n" +
"        /**\n" +
"         * Collapse table borders to avoid space between cells.\n" +
"         */\n" +
"        table {\n" +
"            border-collapse: collapse !important;\n" +
"        }\n" +
"\n" +
"        a {\n" +
"            color: #1a82e2;\n" +
"        }\n" +
"\n" +
"        img {\n" +
"            height: auto;\n" +
"            line-height: 100%;\n" +
"            text-decoration: none;\n" +
"            border: 0;\n" +
"            outline: none;\n" +
"        }\n" +
"    </style>\n" +
"\n" +
"</head>\n" +
"<body style=\"background-color: #e9ecef;\">\n" +
"\n" +
"<!-- start preheader -->\n" +
"<div class=\"preheader\" style=\"display: none; max-width: 0; max-height: 0; overflow: hidden; font-size: 1px; line-height: 1px; color: #fff; opacity: 0;\">\n" +
"    A preheader is the short summary text that follows the subject line when an email is viewed in the inbox.\n" +
"</div>\n" +
"<!-- end preheader -->\n" +
"\n" +
"<!-- start body -->\n" +
"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
"\n" +
"    <!-- start logo -->\n" +
"    <tr>\n" +
"        <td align=\"center\" bgcolor=\"#e9ecef\">\n" +
"            <!--[if (gte mso 9)|(IE)]>\n" +
"            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
"                <tr>\n" +
"                    <td align=\"center\" valign=\"top\" width=\"600\">\n" +
"            <![endif]-->\n" +
"            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
"                <tr>\n" +
"                    <td align=\"center\" valign=\"top\" style=\"padding: 36px 24px;\">\n" +
"                        <a data-flickr-embed=\"true\" href=\"https://www.flickr.com/photos/197860043@N04/52735520617/\" title=\"logo\"><img src=\"https://live.staticflickr.com/65535/52735520617_51e09f14de_k.jpg\" width=\"320\" height=\"240\" alt=\"logo\"/></a><script async src=\"//embedr.flickr.com/assets/client-code.js\" charset=\"utf-8\"></script>                    </td>\n" +
"                </tr>\n" +
"            </table>\n" +
"            <!--[if (gte mso 9)|(IE)]>\n" +
"            </td>\n" +
"            </tr>\n" +
"            </table>\n" +
"            <![endif]-->\n" +
"        </td>\n" +
"    </tr>\n" +
"    <!-- end logo -->\n" +
"\n" +
"    <!-- start hero -->\n" +
"    <tr>\n" +
"        <td align=\"center\" bgcolor=\"#e9ecef\">\n" +
"            <!--[if (gte mso 9)|(IE)]>\n" +
"            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
"                <tr>\n" +
"                    <td align=\"center\" valign=\"top\" width=\"600\">\n" +
"            <![endif]-->\n" +
"            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
"                <tr>\n" +
"                    <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 36px 24px 0; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; border-top: 3px solid #d4dadf;\">\n" +
"                        <h1 style=\"margin: 0; font-size: 32px; font-weight: 700; letter-spacing: -1px; line-height: 48px;\">Confirm Your Email Address</h1>\n" +
"                    </td>\n" +
"                </tr>\n" +
"            </table>\n" +
"            <!--[if (gte mso 9)|(IE)]>\n" +
"            </td>\n" +
"            </tr>\n" +
"            </table>\n" +
"            <![endif]-->\n" +
"        </td>\n" +
"    </tr>\n" +
"    <!-- end hero -->\n" +
"\n" +
"    <!-- start copy block -->\n" +
"    <tr>\n" +
"        <td align=\"center\" bgcolor=\"#e9ecef\">\n" +
"            <!--[if (gte mso 9)|(IE)]>\n" +
"            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
"                <tr>\n" +
"                    <td align=\"center\" valign=\"top\" width=\"600\">\n" +
"            <![endif]-->\n" +
"            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
"\n" +
"                <!-- start copy -->\n" +
"                <tr>\n" +
"                    <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">\n" +
"                        <p style=\"margin: 0;\">Your verification code is :</p>\n" +
"                    </td>\n" +
"                </tr>\n" +
"                <!-- end copy -->\n" +
"\n" +
"                <!-- start button -->\n" +
"                <tr>\n" +
"                    <td align=\"left\" bgcolor=\"#ffffff\">\n" +
"                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
"                            <tr>\n" +
"                                <th align=\"center\" bgcolor=\"#ffffff\" style=\"padding: 12px;\">\n" +
"                                    "+verifCode+
"                                </th>\n" +
"                            </tr>\n" +
"                        </table>\n" +
"                    </td>\n" +
"                </tr>\n" +
"                <!-- end button -->\n" +
"\n" +
"                <!-- start copy -->\n" +
"                <!-- end copy -->\n" +
"\n" +
"                <!-- start copy -->\n" +
"                <!-- end copy -->\n" +
"\n" +
"            </table>\n" +
"            <!--[if (gte mso 9)|(IE)]>\n" +
"            </td>\n" +
"            </tr>\n" +
"            </table>\n" +
"            <![endif]-->\n" +
"        </td>\n" +
"    </tr>\n" +
"    <!-- end copy block -->\n" +
"\n" +
"    <!-- start footer -->\n" +
"    <tr>\n" +
"        <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 24px;\">\n" +
"            <!--[if (gte mso 9)|(IE)]>\n" +
"            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
"                <tr>\n" +
"                    <td align=\"center\" valign=\"top\" width=\"600\">\n" +
"            <![endif]-->\n" +
"            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
"\n" +
"                <!-- start permission -->\n" +
"                <tr>\n" +
"                    <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 12px 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 20px; color: #666;\">\n" +
"                        <p style=\"margin: 0;\">You received this email because we received a request for [type_of_action] for your account. If you didn't request [type_of_action] you can safely delete this email.</p>\n" +
"                    </td>\n" +
"                </tr>\n" +
"                <!-- end permission -->\n" +
"\n" +
"                <!-- start unsubscribe -->\n" +
"                <tr>\n" +
"                    <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 12px 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 20px; color: #666;\">\n" +
"                        <p style=\"margin: 0;\">To stop receiving these emails, you can <a href=\"https://sendgrid.com\" target=\"_blank\">unsubscribe</a> at any time.</p>\n" +
"                        <p style=\"margin: 0;\">Paste 1234 S. Broadway St. City, State 12345</p>\n" +
"                    </td>\n" +
"                </tr>\n" +
"                <!-- end unsubscribe -->\n" +
"\n" +
"            </table>\n" +
"            <!--[if (gte mso 9)|(IE)]>\n" +
"            </td>\n" +
"            </tr>\n" +
"            </table>\n" +
"            <![endif]-->\n" +
"        </td>\n" +
"    </tr>\n" +
"    <!-- end footer -->\n" +
"\n" +
"</table>\n" +
"<!-- end body -->\n" +
"\n" +
"</body>\n" +
"</html>";
    String myAccountEmail = "prexzcod@gmail.com";
        String password = "agxdusiorgzjsptx";
        System.out.println("Preparing to send email");
        Properties p = new Properties();

        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.ssl.enable", "true");
        p.put("mail.smtp.host", "smtp.gmail.com");
        p.put("mail.smtp.port", "465");

        Session session = Session.getInstance(p, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        Message message = prepareMessage(session, myAccountEmail, recipient, mailContent);

        Transport.send(message);
        System.out.println("Message sent successfully");

    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recipient , String mailContent) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Vérification d'email pour sportify");
            message.setContent(mailContent, "text/html");
            return message;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
}
