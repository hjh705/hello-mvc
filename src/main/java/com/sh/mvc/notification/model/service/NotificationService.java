package com.sh.mvc.notification.model.service;

import com.sh.mvc.board.model.entity.Board;
import com.sh.mvc.board.model.entity.BoardComment;
import com.sh.mvc.board.model.service.BoardService;
import com.sh.mvc.notification.model.dao.NotificationDao;
import com.sh.mvc.notification.model.entity.Notification;
import com.sh.mvc.notification.model.entity.Type;
import com.sh.mvc.ws.endpoint.EchoWebSocket;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

import static com.sh.mvc.common.HelloMvcUtils.getBoardCommentNotification;
import static com.sh.mvc.common.SqlSessionTemplate.getSqlSession;


public class NotificationService {

    final String TEMPLATE_OF_NEW_BOARD_COMMENT_NOTIFICATION = "%s님이 %s 게시글에 댓글을 작성했습니다.";
    private NotificationDao notificationDao = new NotificationDao();
    private BoardService boardService = new BoardService();


    public int insertNotification(Notification noti) {
        int result = 0;
        SqlSession session = getSqlSession();
        try {
            result = notificationDao.insertNotification(session, noti);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            throw e;
        } finally {
            session.close();
        }
        return result;
    }

    public List<Notification> findByMemberId(String memberId) {
        SqlSession session = getSqlSession();
        List<Notification> notifications = notificationDao.findByMemberId(session, memberId);
        session.close();
        return notifications;
    }

    /**
     * 새 댓글 알림 Notification 객체 생성
     * @param comment
     * @return
     */
    public int notify(BoardComment comment) {
        Long boardId = comment.getBoardId();
        Board board = boardService.findById(boardId);

        // 댓글이 달린 게시글의 작성자용 알림 생성
        Notification noti = new Notification();
        noti.setMemberId(board.getMemberId());
        String content = TEMPLATE_OF_NEW_BOARD_COMMENT_NOTIFICATION.formatted(
                getBoardCommentNotification(comment.getMemberId(), "/member/memberView?id=" + comment.getMemberId()),
                getBoardCommentNotification(board.getTitle(), "/board/boardDetail?id=" + board.getId())
        );
        noti.setContent(content);
        noti.setType(Type.NEW_BOARD_COMMENT);

        // 1. 실시간 알림
        EchoWebSocket.sendNotification(noti);
        // 2. 알림 테이블 등록
        return insertNotification(noti);
    }
}
