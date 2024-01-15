package com.sh.mvc.board.controller;

import com.sh.mvc.board.model.entity.Board;
import com.sh.mvc.board.model.service.BoardService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/boardCommentDelete")
public class BoardCommentDeleteServlet extends HttpServlet {
    private BoardService boardService = new BoardService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 사용자입력값 처리
        long id = Long.parseLong(req.getParameter("id"));
        long boardId = Long.parseLong(req.getParameter("boardId"));
        // 2. 업무로직
        int result = boardService.deleteBoardComment(id);
        req.getSession().setAttribute("msg", "댓글을 삭제했습니다. 🤗");
        // 3. 리다이렉트
        resp.sendRedirect(req.getContextPath() + "/board/boardDetail?id=" + boardId);
    }
}