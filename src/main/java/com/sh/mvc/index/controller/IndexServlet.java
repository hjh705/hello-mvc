package com.sh.mvc.index.controller;

import com.sh.mvc.member.model.entity.Member;
import com.sh.mvc.notification.model.entity.Notification;
import com.sh.mvc.notification.model.service.NotificationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("")
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("index...");

        req.getRequestDispatcher("/index.jsp").forward(req,resp);
    }
}