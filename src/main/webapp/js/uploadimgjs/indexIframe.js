document.write("<script type='text/javascript' src='js/ServerUrl.js'></script>");
//热门歌单Ajaxjs
document.write("<script type='text/javascript' src='js/SongListOfAccessCountLimit.js'></script>");
//歌曲排行榜
document.write("<script type='text/javascript' src='js/GetSongRankingList.js'></script>");
//根据用户编号，查询用户创建的歌单
document.write("<script type='text/javascript' src='js/GetSongListOfUserId.js'></script>");
//填充添加歌曲到歌单的歌单界面
document.write("<script type='text/javascript' src='js/SearchSongAddSongToSongListDiv.js'></script>");
$(function(){
	//热门歌单标签JSON遍历
	$.getJSON("js/tuijian_tags.json", "", function(data){
		$.each(data, function(name, value) {
			for (var i = 0; i < value.length; i++) {
				var tags_a = $("<a>",{
					tagsName:value[i],
					class:'indexIframe_tags_a',
					click:function(){
						sessionStorage.setItem("tagsName",$(this).attr("tagsName"));
						selectSongListOfAccessCountLimit($(this).attr("tagsName"), 1);
					}
				}).appendTo($("#index_reco_p")).html(value[i]);
				var tags_span = $("<span>",{
					
				}).appendTo("#index_reco_p").html("|");
			}
		});
	})
	
	var userId = sessionStorage.getItem("userId");
	var songListToAccessCount = sessionStorage.getItem("songListToAccessCount");
	var songListToAccessCountArr = eval("(" + songListToAccessCount + ")");
	var eachNum = 0;//记录遍历几次
	$.each(songListToAccessCountArr, function(name, value) {
		var songListImg = value.songListInfo.songListImg;//歌单图片
		console.log("歌单编号：" + name)
		eachNum += 1;
		if(eachNum > 8){
			return false;
		}
		//填充页面
		var li = $("<li>", {}).appendTo($("#accessList_ul"));//填充li
				
		var songList_img_div = $("<div>", {
			class:'songList_img_div'
		}).appendTo(li);//填充图片div
				
		var songList_img = $("<img/>",{
			id:'songList_img',
			src:function(){
				if (songListImg != "" && songListImg != null) {
					return serverURL + songListImg;
				} else{
					return "images/songListImg.jpg";
				}
			},
			alt:'歌单图片',
		}).appendTo(songList_img_div);//填充图片img
			
		var songList_a = $("<a>",{
			id:'songList_a',
			title:value.songListInfo.songListName,
			songlistId:value.songListInfo.songListId,
			click:function(){
				sessionStorage.setItem("indexIframeSongListId", $(this).attr("songlistId"));
				window.location.href = "indexIframeSongList.html";
			}
		}).appendTo(songList_img_div);
				
		var accessCount_div = $("<div>",{
				id:'accessCount_div'
		}).appendTo(songList_img_div);//填充显示访问量的div
				
		accessCount_div.html("热度：" + value.songListInfo.accessCount);
				
		var songList_name = $("<p>",{
					class:'songList_name'
		}).appendTo(li);//填充歌单名字p
				
		var songList_name_a = $("<a>",{
			id:'songList_name_a',
			title:value.songListInfo.songListName,
			songlistId:value.songListInfo.songListId,
	//		href:'indexIframeSongList.html',
			click:function(){
			sessionStorage.setItem("indexIframeSongListId", $(this).attr("songlistId"));
			window.location.href = "indexIframeSongList.html";
			}
		}).appendTo(songList_name);
				
		songList_name_a.html(value.songListInfo.songListName);
	});
	
	
	
	function setIframeHeight(){
		$(window.parent.document).find("#main").css("height","auto");
		var parentHeight = $(window.parent.document).height();
		$("#indexIframe_body").css("height", (parentHeight - 105) + "px");
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
			$("#indexIframe_body").css("height", (parentHeight - 105) + "px");
			$(window.parent.document).find("#main").css("height", (parentHeight - 105) + "px")//设置iframe高度
//			alert((parentHeight - 80) + ";;;;" + $(window.parent.document).find("#main").height());
		},60)
	})
	
	
	
//	$(window.parent.document).find("#main").ready(function(){
//		var main = $(window.parent.document).find("#main");
//		var thisHeight = $(document).height();
//		main.height(thisHeight + 90);
//	})
	//获取推荐歌单json
	if(userId != null){
		//用户登录，显示个性化推荐模块
		$("#gexinghua").css("display","display");
		var commentSongList = sessionStorage.getItem("commentSongList");//获取到的是包括登录信息和推荐信息的数组字符串
		if(commentSongList != null){
			var commentSongListArr = eval("(" + commentSongList + ")");
			var commentJSON = commentSongListArr[0].commentSongList;
			function getJSONLength(){//获取JSON长度
				var jsonLength = 0;
				$.each(commentJSON, function(name, value){
					jsonLength++;
				})
				return jsonLength;
			}
			var eachNum = 0;//记录遍历了几次
			//填充个性化推荐歌单
			$.each(commentJSON, function(name, value){
				var songListImg = value.songList.songListImg;//封面图片
				eachNum += 1;
				if(eachNum > 4){
					return false;
				}
				//填充页面
				var li = $("<li>", {}).appendTo($("#list_ul"));//填充li
				
				var songList_img_div = $("<div>", {
					class:'songList_img_div'
				}).appendTo(li);//填充图片div
				
				var songList_img = $("<img/>",{
					id:'songList_img',
					src:function(){
						if (songListImg != "" && songListImg != null) {
							return serverURL + songListImg;
						} else{
							return "images/songListImg.jpg";
						}
					},
					alt:'歌单图片',
				}).appendTo(songList_img_div);//填充图片img
				
				var songList_a = $("<a>",{
					id:'songList_a',
					title:value.songList.songListName,
					songlistId:name,
					click:function(){
						sessionStorage.setItem("indexIframeSongListId", $(this).attr("songlistId"));
						window.location.href = "indexIframeSongList.html";
					}
				}).appendTo(songList_img_div);
				
				var accessCount_div = $("<div>",{
					id:'accessCount_div'
				}).appendTo(songList_img_div);//填充显示访问量的div
				
				accessCount_div.html("热度：" + value.songList.accessCount);
				
				var songList_name = $("<p>",{
					class:'songList_name'
				}).appendTo(li);//填充歌单名字p
				
				var songList_name_a = $("<a>",{
					id:'songList_name_a',
					title:value.songList.songListName,
					songlistId:name,
	//				href:'indexIframeSongList.html',
					click:function(){
						sessionStorage.setItem("indexIframeSongListId", $(this).attr("songlistId"));
						window.location.href = "indexIframeSongList.html";
					}
				}).appendTo(songList_name);
				
				songList_name_a.html(value.songList.songListName);
				
				
			})
			
		}
	}else{
		//如果用户没有登录，把个性化推荐歌单设置为不显示
		$("#gexinghua").css("display","none");
	}
	//填充热门榜单表单
	
	//点击更多，跳转界面
	$("#moreSongList").click(function(){
		var tagsName = sessionStorage.getItem("tagsName");
		selectSongListOfAccessCountLimit(tagsName, 1);
	})
	
	
	//获取播放排行榜JSON数据
	var songPlayCountListJSON = getSongPlayCountList();
	//填充播放排行榜
	var tableId = "#playCount_song_table";
	var songCountType = "songPlay";
	tianCSongList(songPlayCountListJSON, tableId, songCountType, 10);
	
	//获取下载次数排行榜JSON数据
	var songDownloadCountListJSON = getSongDownloadCountList();
	//填充下载排行榜
	var tableId = "#downloadCount_song_table";
	var songCountType = "songDownload";
	tianCSongList(songDownloadCountListJSON, tableId, songCountType, 10);
	
	
	//获取收藏次数排行榜JSON数据
	var songCollectionCountListJSON = getCollectionCountList();
	//填充播放排行榜
	var tableId = "#collectionCount_song_table";
	var songCountType = "songCollection";
	tianCSongList(songCollectionCountListJSON, tableId, songCountType, 10);
	
	$(".songList_table tr:even").css({"background":"#E8E8E8"});//tr偶数行背景颜色
	$(".songList_table tr:odd").css({"background":"#F4F4F4"});//tr奇数行背景颜色
	
})