$(document).ready(function() {
	$('#username').change(function() {
		$("#btn-chkusername").text("username중복체크").attr("disabled", false);
		$("#btn-save").attr("disabled", true);
	});

});

function save() {
	if ($("#password").val() == "") {
		alert("password를 입력하세요.");
		$("#password").focus();
	} else {
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		}

		$.ajax({
			// 수행 요청
			type: "POST",
			url: "/auth/joinProc",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "JSON"
		}).done(function(resp) {
			// 요청이 정상
			if (resp.success == 200) {
				alert("회원가입이 완료되었습니다.")
				location.href = "/";
			} else {
				alert("회원가입이 실패하였습니다.");
			};
		}).fail(function(err) {
			// 요청에 실패
			alert(JSON.stringify(err));
		});
	}
};

function chkusername() {

	let username = $("#username").val();

	$.ajax({
		type: "GET",
		url: "/auth/user/chk/" + username,
		contentType: "application/json; charset=utf-8",
		dataType: "JSON"
	}).done(function(resp) {
		if (resp == true) {
			alert("중복된 username 입니다.");
		} else {
			alert("사용가능한 username 입니다.");
			$("#btn-chkusername").text("중복체크완료").attr("disabled", true);
			$("#btn-save").attr("disabled", false);
		}
	}).fail(function(err) {
		alert("username을 입력하세요.");
		$("#username").focus();
	})
}

function login() {
	let data = {
		username: $("#username").val(),
		password: $("#password").val(),
	}

	$.ajax({
		// 수행 요청
		type: "POST",
		url: "/api/user/login",
		data: JSON.stringify(data),
		contentType: "application/json; charset=utf-8",
		dataType: "JSON"
	}).done(function(resp) {
		// 요청이 정상
		alert("로그인이 완료되었습니다.")
		location.href = "/";
	}).fail(function(err) {
		// 요청에 실패
		alert(JSON.stringify(err));
	});
};

function updateUser(id) {
	if ($("#password").val() == "") {
		alert("password를 입력하세요.");
		$("#password").focus();
	} else {
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		$.ajax({
			// 수행 요청
			type: "PUT",
			url: "/api/user/" + id,
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "JSON"
		}).done(function(resp) {
			console.log();
			// 요청이 정상
			if (resp.success == 200) {
				alert("회원수정이 완료되었습니다.");
			} else {
				alert("회원수정이 실패하였습니다.");
				alert(resp.exception);
			};
			//location.href = "/";
		}).fail(function(err) {
			// 요청에 실패
			alert(JSON.stringify(err));
		});
	}
};