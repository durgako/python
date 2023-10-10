package com.pack1;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
public class LoginDemo extends JFrame implements ActionListener {
    JPanel panel;
    JLabel user_label, password_label,url_label,clientid_label,clientsecret_label, message;
    JTextField userName_text;
    JPasswordField password_text;
    JTextField url_text;
    JTextField client_text;
    JTextField clientsecret_text;

    JButton submit, cancel;
    LoginDemo() {
        // Username Label
        user_label = new JLabel();
        user_label.setText("User Name :");
        userName_text = new JTextField();
        // Password Label
        password_label = new JLabel();
        password_label.setText("Password :");
        password_text = new JPasswordField();
        //url label
        url_label=new JLabel();
        url_label.setText("url");
        url_text=new JTextField();
        //clientid label
        clientid_label=new JLabel();
        clientid_label.setText("clientid");
        client_text=new JTextField();
       //clientsecret label
        clientsecret_label=new JLabel();
        clientsecret_label.setText("clientsecret");
        clientsecret_text=new JTextField();
        // Submit
        submit = new JButton("SUBMIT");
        panel = new JPanel(new GridLayout(3, 1));
        panel.add(user_label);
        panel.add(userName_text);
        panel.add(password_label);
        panel.add(password_text);
        panel.add(url_label);
        panel.add(url_text);
        panel.add(clientid_label);
        panel.add(client_text);
        panel.add(clientsecret_label);
        panel.add(clientsecret_text);
        message = new JLabel();
        panel.add(message);
        panel.add(submit);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Adding the listeners to components..
        submit.addActionListener(this);
        add(panel, BorderLayout.CENTER);
        setTitle("Please Login Here !");
        setSize(450,350);
        setVisible(true);
    }
    public static void main(String[] args) {
        new LoginDemo();
    }

    public void actionPerformed(ActionEvent ae) {
        String userName = userName_text.getText();
        String password = password_text.getText();
        String url=url_text.getText();
        String clientid=client_text.getText();
        String clientsecret=clientsecret_text.getText();
        String loginURL = url +

                "/services/oauth2/token?grant_type=password"  +

                "&client_id=" + clientid +

                "&client_secret=" + clientsecret +

                "&username=" + userName +

                "&password=" +password;

        System.out.println(loginURL);
        HttpClient client=HttpClientBuilder.create().build();
    	HttpResponse resp=null;
        HttpPost httpPost = new HttpPost(loginURL);
        
        try {
       	 resp=client.execute(httpPost);
       
        String getResult = null;
        
           
        		getResult = EntityUtils.toString(resp.getEntity());
        		System.out.println(getResult+"getting result");
        	
        
        ObjectMapper mapper=new ObjectMapper();
        JSONObject jsonObject=null;
      
        	jsonObject = mapper.readValue(getResult, JSONObject.class);
        
         
           Object accesstoken = jsonObject.get("access_token");
          Object instanceurl=jsonObject.get("instance_url");
         System.out.println("accesstoken"+accesstoken);
         System.out.println("instanceurl"+instanceurl);
        }
        catch (Exception e) {
			e.printStackTrace();
		}
    }
}


    
        
    


