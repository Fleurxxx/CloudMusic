$(function(){
	var userId = sessionStorage.getItem("userId");
	$("#btn").click(function(){
		let src = $("#view").attr("src");
		console.log(src);
		console.log($('#view')[0].src);
		let s = "http://localhost:8080/";
		// if (src == "" || src == null) {
		// 	alert("请先截取图片");
		// } else{
			axios({
				type:"POST",
				url:"http://localhost:8080/userServlet?method=updatePhoto",
				data:{
					userphoto:s,
					username:src
				}
			}).then(function (response) {
				console.log(response);
				alert(response.data.message);
				if(response.data.code===200)
				{
					console.log("下载完成")
				}
			})

			// $.ajax({
			// 	type:"POST",
			// 	url:"http://localhost:8080/userServlet?method=updatePhoto",
			// 	data:{
			// 		userphoto:"hhh",
			// 		username:src
			// 	},
			// 	dataType: 'json',
			// 	success:function(data){
			// 		if (data == "200") {
			// 			alert("修改成功");
			// 			parent.location.reload();
			// 		} else{
			// 			alert("修改失败");
			// 		}
			// 	},//响应成功后执行代码
			// 	error:function(){
			// 		console.log("信息获取失败");
			// 	}
			// });
		// }
	})
})
