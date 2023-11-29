package com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Entity.Orders;
import com.acciojobsshopping.backend.Accio.Shopping.Website.Backend.Entity.Users;

@Service
public class MailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;
    public void sendEmail(){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        String to = "tiwarisomendra22@gmail.com";
        String sub = "Hii You got your first mail";
        try{
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(sub);
            Context context = new Context();
            String htmlContent = templateEngine.process("email-template", context);
            mimeMessageHelper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
        }catch(Exception e){

            System.out.println(e.getMessage());

        }


    }


    public void sendOrderCreatedMail(Users user, Orders order){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        String to = user.getEmail();
        String sub = "Congratulations !! Your Order got Successfully Placed.";
        Context context = new Context();
        context.setVariable("userName", user.getUserName());
        context.setVariable("orderID", order.getId());
        context.setVariable("estimatedDate", order.getEstimatedDelivery());
        try{
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(sub);
            String htmlContent = templateEngine.process("email-template", context);
            htmlContent = htmlContent.replace("${userName}", user.getUserName());
            htmlContent = htmlContent.replace("${orderID}", order.getId() + "");
            htmlContent = htmlContent.replace("${estimatedDate}", order.getEstimatedDelivery() + "");
            mimeMessageHelper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
        }catch(Exception e){

        }
    }
}
