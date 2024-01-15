package com.sh.mvc.member.controller;

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

@WebServlet("/member/loginSuccess")
public class MemberLoginSuccessServlet extends HttpServlet {
    private NotificationService notificationService = new NotificationService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("로그인 성공 후처리...");
        // 로그인후 필요한 데이터를 로드해서 session 속성으로 전달
        Member loginMember = (Member) req.getSession().getAttribute("loginMember");
        if(loginMember != null) {
            List<Notification> notifications = notificationService.findByMemberId(loginMember.getId());
            System.out.println(notifications);
            req.getSession().setAttribute("notifications", notifications);
        }

        String location = req.getContextPath() + "/";
        String next = (String) req.getSession().getAttribute("next");
        if(next != null) {
            location = next;
            req.getSession().removeAttribute("next");
        }
        resp.sendRedirect(location);
    }
}