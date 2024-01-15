package com.sh.mvc.notification.model.entity;

public enum Type {
    NEW_BOARD_COMMENT, // 내 게시글에 댓글이 달린 경우
    NEW_BOARD_COMMENT_REPLY, // 내 댓글에 답글이 달린 경우
    DM, // DM이 온 경우
    LIKE_FOR_BOARD, // 게시글에 좋아요를 누른 경우
    LIKE_FOR_BOARD_COMMENT, // 게시글 댓글에 좋아요를 누른 경우
    NEW_FOLLOWER, // 새 팔로워가 생겼을때
    NEW_BOARD_OF_FOLLOWING; // 팔로잉하는 회원의 새게시글 등록시
}
