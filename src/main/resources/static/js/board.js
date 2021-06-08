function writeBoard() {
	let data = {
		title: $("#title").val(),
		content: $("#content").val()
	};

	$.ajax({
		// 수행 요청
		type: "POST",
		url: "/api/board",
		data: JSON.stringify(data),
		contentType: "application/json; charset=utf-8",
		dataType: "JSON"
	}).done(function(resp) {
		// 요청이 정상
		if (resp.success == 200) {
			alert("글쓰기가 완료되었습니다.");
		} else {
			alert("글쓰기가 실패하였습니다.");
		};
		location.href = "/";
	}).fail(function(err) {
		// 요청에 실패
		alert(JSON.stringify(err));
	});
};

function updateBoard(id) {
	let data = {
		title: $("#title").val(),
		content: $("#content").val()
	};

	$.ajax({
		// 수행 요청
		type: "PUT",
		url: "/api/board/" + id,
		data: JSON.stringify(data),
		contentType: "application/json; charset=utf-8",
		dataType: "JSON"
	}).done(function(resp) {
		// 요청이 정상
		if (resp.success == 200) {
			alert("글수정이 완료되었습니다.");
		} else {
			alert("글수정이 실패하였습니다.");
		};
		location.href = "/";
	}).fail(function(err) {
		// 요청에 실패
		alert(JSON.stringify(err));
	});
};

function deleteBoard(id) {
	if (confirm("정말 삭제하시겠습니까??") == true) { //확인
		$.ajax({
			// 수행 요청
			type: "DELETE",
			url: "/api/board/" + id
		}).done(function(resp) {
			// 요청이 정상
			if (resp.success == 200) {
				alert("글 삭제가 완료되었습니다.")
			} else {
				alert(resp.exception.message);
			}
			location.href = "/";
		}).fail(function(err) {
			// 요청에 실패
			alert(JSON.stringify(err));
		});
	} else { //취소
		return false;
	};
};

function replySave(boardId, userId) {
	if ($("#reply").val() == "") {
		alert("댓글 내용을 입력 하세요.");
		$("#reply").focus();
	};

	let data = {
		userId: userId,
		boardId: boardId,
		content: $("#reply").val()
	};
	$.ajax({
		type: "POST",
		url: "/api/board/" + boardId + "/reply",
		data: JSON.stringify(data),
		contentType: "application/json; charset=utf-8",
		dataType: "JSON"
	}).done(function(resp) {
		// 요청이 정상
		if (resp.success == 200) {
			location.href = "/board/" + boardId;
		} else {
			alert(resp.exception);
		}
	}).fail(function(err) {
		// 요청에 실패
		alert(JSON.stringify(err));
	});
}

function deleteReply(replyId) {
	if (confirm("정말 삭제하시겠습니까??") == true) {
		$.ajax({
			type: "DELETE",
			url: "/api/board/" + replyId + "/reply",
		}).done(function(resp) {
			if (resp.success == 200) {
				window.location = window.location.pathname;
			} else {
				alert(resp.exception);
			}
		}).fail(function(err) {
			alert(JSON.stringify(err));
		});
	} else {
		return false;
	}
};