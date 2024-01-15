package com.sh.mvc.board.model.service;

import com.sh.mvc.board.model.entity.Board;
import com.sh.mvc.board.model.vo.BoardVo;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.sh.mvc.common.SqlSessionTemplate.getSqlSession;
import static org.assertj.core.api.Assertions.assertThat;

public class BoardServiceTest {
    static final int limit = 10; // 페이지당 게시글수
    BoardService boardService;
    @BeforeEach
    void setUp() {
        // Fixture생성코드
        this.boardService = new BoardService();
    }
    @DisplayName("게시글 전체 조회")
    @Test
    void test1(){
        // given
        // when
        List<Board> boards = boardService.findAll();
        // then
        // allSatisfy : 리스트의 각 요소가 모든 단정문을 충족하는지 확인
        assertThat(boards)
                .isNotNull()
                .allSatisfy((board -> {
                    // pk, 필수값 확인
                    assertThat(board.getId()).isNotZero();
                    assertThat(board.getTitle()).isNotNull();
                    assertThat(board.getContent()).isNotNull();
                    assertThat(board.getRegDate()).isNotNull();
                }));
    }
    @DisplayName("존재하는 게시글 한건 조회")
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    void test2_1(long id) {
        // given
        // when
        Board board = boardService.findById(id);

        // then
        // satisfies : 요소가 모든 단정문을 충족하는지 확인
        assertThat(board)
                .isNotNull()
                .satisfies((_board) -> {
                    // pk, 필수값 확인
                    assertThat(_board.getId()).isNotZero();
                    assertThat(_board.getTitle()).isNotNull();
                    assertThat(_board.getContent()).isNotNull();
                    assertThat(_board.getRegDate()).isNotNull();
                });
    }
    @DisplayName("존재하지 않는 게시글 한건 조회")
    @ParameterizedTest
    @ValueSource(longs = {100000000L, 9999999L})
    void test2_2(long id) {
        // given
        // when
        Board board = boardService.findById(id);
        // then
        assertThat(board) .isNull();
    }
    @DisplayName("게시글 등록")
    @Test
    void test3() {
        // given
        // when
        // pk : seq_board_id를 통해 채번
        // read_count : 기본값 처리
        // reg_date :  기본값 처리
        BoardVo board = new BoardVo();
        board.setTitle("제목");
        board.setContent("내용");

        int result = boardService.insertBoard(board);
        // then
        assertThat(result).isGreaterThan(0);
    }
    @DisplayName("게시글 수정")
    @ParameterizedTest
    @MethodSource("boardIdProvider") // boardIdProvider메소드가 반환하는 stream객체의 요소별로 테스트실행
    void test4(long id) {
        // given
        BoardVo board = boardService.findById(id);
        assertThat(board).isNotNull();
        // when
        String newTitle = "새 제목";
        String newContent = "새 내용";
        board.setTitle(newTitle);
        board.setContent(newContent);
        int result = boardService.updateBoard(board);
        // then
        assertThat(result).isGreaterThan(0);
        Board boardUpdated = boardService.findById(id);
        assertThat(boardUpdated).satisfies((b) -> {
            assertThat(b.getTitle()).isEqualTo(newTitle);
            assertThat(b.getContent()).isEqualTo(newContent);
        });
    }
    @DisplayName("게시글 삭제")
    @ParameterizedTest
    @MethodSource("boardIdProvider")
    void test5(long id) {
        // given
        Board board = boardService.findById(id);
        assertThat(board).isNotNull();
        // when
        int result = boardService.deleteBoard(id);
        // then
        assertThat(result).isGreaterThan(0);
        Board boardDeleted = boardService.findById(id);
        assertThat(boardDeleted).isNull();
    }
    @DisplayName("전체 게시글수 조회")
    @Test
    void test6() {
        // given
        // when
        int totalCount = boardService.getTotalCount();
        // then
        assertThat(totalCount).isNotNegative(); // 음수가 아니어야 한다. 0이상
    }
    @DisplayName("게시글 페이징 조회")
    @ParameterizedTest
    @MethodSource("pageNoProvider")
    void test7(int page) {
        Map<String, Object> param = Map.of("page", page, "limit", limit);
        List<BoardVo> boards = boardService.findAll(param);
        assertThat(boards)
                .isNotNull()
                .isNotEmpty()
                .size().isLessThanOrEqualTo(limit);
    }



    public static Stream<Integer> pageNoProvider() {
        BoardService boardService = new BoardService();
        SqlSession session = getSqlSession();
        int totalCount = boardService.getTotalCount();
        int totalPage = (int) Math.ceil((double) totalCount / limit);
        return IntStream.range(1, totalPage).boxed(); // 1 부터 total페이지까지를 요소로 하는 Stream생성
    }
    public static Stream<Arguments> boardIdProvider() {
        BoardService boardService = new BoardService(); // non-static fixture를 사용할 수 없다.
        List<Board> boards = boardService.findAll();
        return Stream.of(Arguments.arguments(boards.get(0).getId()));
    }



}