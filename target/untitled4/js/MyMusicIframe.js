var mySongList_div_click;//填充歌单信息方法全局变量
document.write("<script type='text/javascript' src='js/ServerUrl.js'></script>");
document.write("<script type='text/javascript' src='js/RefreshUserCreateSongList.js'></script>");
document.write("<script type='text/javascript' src='js/ShowSongListComment.js'></script>");//填充评论内容
document.write("<script type='text/javascript' src='js/UpSongListenCount.js'></script>");//增加播放次数和增加用户播放记录
//根据用户编号查询用户信息
document.write("<script type='text/javascript' src='js/GetUserInfoOfUserId.js'></script>");
$(function(){
	var userId = sessionStorage.getItem("userId");
	var userName = sessionStorage.getItem("userName");
	function setIframeHeight(){
		$(window.parent.document).find("#main").css("height","auto");
		var parentHeight = $(window.parent.document).height();
		$("div[class='mymusic_content']").css("height", (parentHeight - 105) + "px");
		$(window.parent.document).find("#main").ready(function(){
			var main = $(window.parent.document).find("#main");
			var thisHeight = $(document).height();
			main.height(thisHeight);
		})
	}
	$(window).ready = setIframeHeight();
	var timer = null;
	$(window).resize(function(){
		clearTimeout(timer);
		timer = setTimeout(function(){
			$(window.parent.document).find("#main").css("height","auto");
			var parentHeight = $(window.parent.document).height();//窗口高度
			$("div[class='mymusic_content']").css("height", (parentHeight - 105) + "px");
			$(window.parent.document).find("#main").css("height", (parentHeight - 105) + "px")//设置iframe高度
//			alert((parentHeight - 80) + ";;;;" + $(window.parent.document).find("#main").height());
		},60)
	})
	
	//点击收起列表，展开列表
	$("div[name='list_title']").click(function(){
		$(this).next().toggleClass("songListdiv_none");
	})
	//根据用户编号获取当前用户信息
	if(userId != null && userId != null){
		var userinfoOfUserId = getUserInfoOfUserId(userId);
		var headSculptureUrl = serverURL + userinfoOfUserId.headSculptureUrl;
	}
	//填充评论输入框前当前用户头像
	$("#user_header_photo").attr("src", headSculptureUrl);
	
	
	songListDivShow(userId);//该方法是刷新和显示歌单列表使用，在RefreshUserCreateSongList.js中
	
	
	//根据歌单获取歌单中歌曲列表，并显示到右侧
	mySongList_div_click = function (songListId,createTheSongListOfUserId){
		$(".updateSongListImg_span").remove();
		if(userId == createTheSongListOfUserId){
			var updateSongListImg_span = $("<span>",{
				class:'updateSongListImg_span',
				songListId:songListId,
				click:function(){
					var updateSongListImgOfId = $(this).attr("songListId");
					sessionStorage.setItem("updateSongListImgOfId",updateSongListImgOfId);
					window.location.href = "updateSongListImg.html";
				}
			}).appendTo(".songListImgDiv").html("修改封面");
		}
//		var createTheSongListOfUserId = 0;
		$("tr[class='table_title']").nextAll().remove();//清除表格中原来显示的歌曲
//		if(userId != null){
			$.ajax({
				"url":serverURL + "/SelectSongInfo",
				"type":"POST",
				"data":{"songListId":songListId},
				"async" : false,
				"dataType":"text",
				"success":function(data){
					sessionStorage.setItem("songJSON",data);
					var songJSONArr = eval("(" + data + ")");
					var songListName = songJSONArr.songListInfo.songListName;
					$("#songList_Name").html(songListName);
					
					//歌单封面图像
					var songListImg = songJSONArr.songListInfo.songListImg;
					if(songListImg != "" && songListImg != null){
						$("#songList_photo").attr("src", serverURL + songListImg);
					}else{
						$("#songList_photo").attr("src","images/songListImg.jpg");
					}
					var createTheSongListUserName = songJSONArr.createUserInfo.userName;
					$("#userName").html(createTheSongListUserName);
					//用户头像
					var createTheSongListUserHeadSculptureUrl = serverURL + songJSONArr.createUserInfo.headSculptureUrl;
					$("#createTheSongList_user_header_photo").attr("src",createTheSongListUserHeadSculptureUrl);
					var userSongListCommArr = songJSONArr.songListComm;
					showSongListComment(userSongListCommArr);//填充评论信息
					
					if(songJSONArr.song.length == 0){
						$("div[class='songList_song']").hide();//如果歌单为空，隐藏页面元素
						$("div[class='write_songlist_comment']").hide();
						
					}else{
						//填充右侧歌曲信息
						$("div[class='songList_song']").show();//如果歌单不为空，显示页面元素
						$("div[class='write_songlist_comment']").show();
						for(var i = 0; i < songJSONArr.song.length; i++){
							var songId = songJSONArr.song[i].songId;//歌曲编号
							var songName = songJSONArr.song[i].songName;//歌曲名
							var songTime = songJSONArr.song[i].songTime;//歌曲时长
							var CDName = songJSONArr.song[i].CDName;//专辑名
							var singerName = songJSONArr.song[i].singerName;//歌手名
							var right_tr = $('<tr>',{
								mouseover:function(){
									$(this).find("span[id='addSongList']").css("display","inline-block");//把歌曲添加到歌单按钮显示
									$(this).find("span[id='deleteSongFromSongList']").css("display","inline-block");//把歌曲从当前歌单移除按钮显示
								},
								mouseout:function(){
									$(this).find("span[id='addSongList']").css("display","none");//把歌曲添加到歌单按钮隐藏
									$(this).find("span[id='deleteSongFromSongList']").css("display","none");//把歌曲从当前歌单移除按钮隐藏
								}
							}).appendTo($("#song_list_table"));
							var td1 = $("<td>",{}).appendTo(right_tr);
							var playSongBtn = $("<span>",{
								class:"playSongBtn",
								id:"playSongBtn",
								title:'播放',
								songId:songId,
								click:function(){
									var songId = $(this).attr("songId");
									//点击这里，播放音乐
									upSongListenCount(userId, songId);
								}
							}).appendTo(td1);
							var tdSongName = $("<td>",{
								
							}).appendTo(right_tr).html(songName);
							$(tdSongName).css("width","230px");
							
							//点击添加音乐到歌单
							var addSongList = $("<span>",{
								class:'addSongList',
								id:'addSongList',
								title:'添加到歌单',
								songId:songId,
								click:function(){
									if(userId == null || userId == ""){
										alert("请先登录");
									}else{
										$("div[class='addSongList_showDiv']").css("display","block");
										$("div[class='addSongList_songList']").empty();
										$("div[class='addSongList_songList']").attr("songid",$(this).attr("songId"));
										songListDivShow(userId);
									}
								}
							}).appendTo(tdSongName);
							
							//点击删除歌曲（从歌单中删除歌曲）
							if(userId == createTheSongListOfUserId){
								//登录用户与该歌单创建者一致，那么，用户就可以删除该歌曲
								var deleteSongFromSongList = $("<span>",{
									class:'deleteSongFromSongList',
									id:'deleteSongFromSongList',
									title:'移除',
									songId:songId,
									songListId:songListId,
									click:function(){
										//点击删除，向后端传递数据，歌单编号和歌曲编号
										var songId = $(this).attr("songId");
										var songListId = $(this).attr("songListId");
										$.ajax({
											"url":serverURL + "/DeleteSongFromSongListWithSong",
											"type":"POST",
											"data":{"songListId":songListId, "songId":songId},
											"async" : true,
											"dataType":"text",
											success:function(data){
												if(data == "true"){
													mySongList_div_click(songListId,createTheSongListOfUserId);
												}else{
													alert("删除失败")
												}
											},
											error:function(){
												alert("出现异常，删除失败");
											}
										});
									}
								}).appendTo(tdSongName);
							}else{
								
							}
							var tdSongTime = $("<td>",{}).appendTo(right_tr).html(songTime);
							var tdSingerName = $("<td>",{}).appendTo(right_tr).html(singerName);
							var tdCDName = $("<td>",{}).appendTo(right_tr).html(CDName);
	//						$(right_p).html(songName + "&nbsp;&nbsp;&nbsp;&nbsp;" + singerName + "&nbsp;&nbsp;&nbsp;&nbsp;" + CDName);
						}
					}
					
				},
				"error":function(err){
					console.log("获取歌曲信息出现异常");
				}
			})
//		}
	}
	//用户刚进来我的歌单界面是，先填充右侧歌曲信息，为第一个歌单里面的歌曲
//	var firstSongListId = sessionStorage.getItem("firstSongListId");
//	mySongList_div_click(firstSongListId);
	$("div[class='mySongList_div']:nth-child(1)").css("background","#E6E6E6");
	//设置table奇数行背景颜色
	$("#song_list_table tr:even").css("background","#F7F7F7");
		//点击x号修改歌单弹窗消失
	$("#none_updateDiv").click(function(){
		$("div[class='updateSongList_div']").css("width","0px");
	})
	//点击取消窗口消失
	$("#notsave_update_button").click(function(){
		$("div[class='updateSongList_div']").css("width","0px");
	})
	//用户添加歌曲到歌单，点击X号关闭窗口
	$("#none_addSongList_showDiv").click(function(){
		$("div[class='addSongList_songList']").attr("songid","");
		$("div[class='addSongList_showDiv']").css("display","none");
	})
	
	//点击评论，发表评论
	//点击发表评论
	$("#comment_submit").click(function(){
		if(userId == null || userId == ""){
			alert("请先登录！");
		}else{
			var commContentText = $("#songlist_comment_textarea").val().trim().replace(/\s/g,"");
			if (commContentText == null || commContentText == "") {
				alert("评论不能为空！");
			} else{
				var songListId = $("#comment_submit").attr("songListId");
				$.ajax({
					type:"get",
					url:serverURL + "/AddSongListComment",
					async:false,//同步
					data:{"userId":userId, "songListId":songListId, "songListCommText":commContentText},
					dataType:'text',//返回数据类型
					success:function(data){
						if (data == "300") {
							alert("添加失败");
						} else{
							$("#songlist_comment_textarea").val("");
							var userSongListCommArr = eval("(" + data + ")");
							showSongListComment(userSongListCommArr);
						}
					},//响应成功后执行代码
					error:function(){
						console.log("发表评论失败");
					}
				});
			}
		}
		
	})
	
	
	
	//点击X号关闭标签选择页
	$("#close_tags_choose").click(function(){
		$("div[class='tags_choose_input_div']").slideToggle();
	})
	
	//修改歌单信息，点击保存
	$("#save_update_button").click(function(){
		var songListId = $("#choose_songListId").val();//要修改的歌单的名称
		var songListNameChoose = $("#update_songListName").val();//修改后歌单名称
		var tagsChoose = "";//修改后歌单标签
		var tagsChooseLen = $("span[class='tags_span_one']").length;
		$("span[class='tags_span_one']").each(function(index, element){
			if(index == tagsChooseLen - 1){
				tagsChoose += $(element).text();
			}else{
				tagsChoose += $(element).text() + " ";
			}
		})
		var introduceChoose = $("#introduce").val();//修改后歌单简介
		
		$.ajax({
			"url":serverURL + "/UpdateSongListInfo",
			"type":"POST",
			"data":{"songListId":songListId, "songListName":songListNameChoose, "tags":tagsChoose, "introduce":introduceChoose, "userId":userId},
			"async" : false,
			"dataType":"text",
			success:function(data){
				if(data == "true"){
					getSongListInfo(userId);
					songListDivShow(userId);
					$("div[class='updateSongList_div']").css("width","0px");
				}else{
					alert("修改失败");
				}
			},
			error:function(){
				alert("发生错误")
			}
		})
	})
})