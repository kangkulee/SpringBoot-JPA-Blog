package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;

//스프링 Ioc 메모리에 띄워줌
@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void 글쓰기(Board board, User user) {
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}

	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("글 상세보기 실패 : 아이디 : " + id);
		});
	}

	@Transactional
	public void 글삭제(int id, PrincipalDetail principal) {
		Board board = boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("글 찾기 실패 : 해당 글이 존재하지 않습니다.");
		});
		if (board.getUser().getId() != principal.getUser().getId()) {
			throw new IllegalStateException("글 삭제 실패 : 해당 글을 삭제할 권한이 없습니다.");
		}
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정(int id, Board requestBoard) {
		Board board = boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("글 찾기 실패 : 해당 글이 존재하지 않습니다.");
		});
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
	}

	@Transactional
	public void 댓글등록(ReplySaveRequestDto replySaveRequestDto) {
		replyRepository.replySave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
	}
	
	@Transactional
	public void 댓글삭제(int id, PrincipalDetail principal) {
		
		Reply reply = replyRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("댓글 삭제 실패 : 권한이 없습니다.");
		});
		
		if (reply.getUser().getId() != principal.getUser().getId()) {
			throw new IllegalStateException("글 삭제 실패 : 해당 글을 삭제할 권한이 없습니다.");
		}
		replyRepository.deleteById(id);
	}

	
}
