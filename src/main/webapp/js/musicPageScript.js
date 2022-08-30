window.onload = function(){
	// let file1 = document.querySelector("audio").src;
	var sname;//歌名
	var sgname;//作者名
	var songsrc;//歌曲路径
	var gctxt;//歌词
	var simg;//歌曲封面
	var lrcArr;
	// var FileName = file1.replace(/.*(\/|\\)/, ""); //带后缀
	// var strFileName = file1.replace(/(.*\/)*([^.]+).*/ig,"$2");//不带后缀
	// var FileExt = file1.replace(/.+\./, ""); //获取后缀
	initialize();
	/*初始化获取歌曲信息*/
	function initialize() {
		let musicid = localStorage.getItem("ssid");
		console.log(musicid);
		axios({
			method: 'POST',
			url: 'http://localhost:8080/songServlet?method=getSongMsg',
			data:{
				songid:musicid
			}
		}).then(function (response) {
			console.log(response);
			if(response.data.code===200) {//验证成功显示歌曲
				let data = response['data'];
				sname = data['data']['song_name'];
				sgname = data['data']['song_singername'];
				songsrc = data['data']['song_path'];
				gctxt = data['data']['lyric_path'];
				if (data['data']['photo_path'] == null || data['data']['photo_path'] == "") {
					simg = "../img/cover/封面1.png";
				} else {
					simg = data['data']['photo_path'];
				}
				$(".songName")[0].innerHTML = sname;
				$(".singer")[0].innerHTML = sgname;
				$(".songName")[1].innerHTML = sname;
				$(".singer")[1].innerHTML = sgname;
				$("#myMusic").src = songsrc
				$("#musicImg").src = simg;
				$("img")[0].src = simg;
				var strFileName = simg.replace(/.*(\/|\\)/, ""); //带后缀
				loadCssCode(`.Opage::before{ position: absolute; top: 0; left: 0; right: 0; bottom: 0; margin: -30px; z-index: -1; content: ''; background: url(../img/cover/${strFileName}) no-repeat; background-size:100% 100%; filter: blur(20px); }`);
				lrcArr = gctxt.split("[");
				console.log(lrcArr.length);

				Initialize();
			}
		})
	}

	loveInit();
	//初始化播放器的喜欢按钮
	function loveInit(){
		let musicid = localStorage.getItem("ssid");
		axios({
			method:'POST',
			url:'http://localhost:8080/songServlet?method=lovestate',
			data:{
				songid:musicid
			}
		}).then(function (response) {
			console.log(response);
			if(response.data.code===200)
			{
				if(response.data.data===1){
					oLove.className = "iconfont love icon-xihuan2";
					console.log("喜欢过啦！");
				}else{
					oLove.className = "iconfont love icon-xihuan1";
					console.log("还没喜欢过哦！");
				}
			}
		})
	}



	/* 个位数转两位数 */
	function doubleNum(n){
		return (n <10) ? ("0" + n) : (n);
	}
	/* 储存信息 */
	var songName =["水星记", "当我唱起这首歌"]
	var singers =["郭顶", "小贱"]
	/* 动态插入css代码片段 */
	var head = $('head')[0];
	function loadCssCode(code){
		var style = document.createElement('style');
		style.type = 'text/css';
		style.rel = 'stylesheet';
		style.id = 'style';
		try{
			//for Chrome Firefox Opera Safari
			style .appendChild(document.createTextNode(code));
		}catch(ex){
			//for IE
			style.styleSheet.cssText = code;
		}
		head.appendChild(style);
	}
	/* 定时器 */
	function oTimer(){
		real = setInterval( function(){
			realTime = parseInt(myMusic.currentTime);//获取时长
			realMinute = doubleNum(parseInt(realTime/60));//分
			realSecond = doubleNum(realTime%60);//秒
			oRealTime.innerHTML = realMinute + ":" + realSecond;
			left = (realTime*600)/totalTime;
			oSpot.style.left = (left-10) + "px";
			oProgress.style.width = left + "px";
			if(myMusic.ended){
				Play = false;
				oPaly.className = "play iconfont Iconfont icon-zanting";
				oPaly.title = "播放";
			}
		},1000)
	}

	/* 更新信息 */
	function update(){
		initialize();
		lrcArr = gctxt.split("[");
		Initialize(lrcArr);
	}
	/* 上一首 */
	var I = 0; var arr = null; var Txt = null; var TXT = null;
	var lrc = txt0.value;
	var oLast = $(".last")[0];//上一首
	oLast.onclick = function(){
		--I; (I < 0) ? I = 1 : I = I;
		if($("#style") != null){
			head.removeChild(style);
			loadCssCode(`.Opage::before{ position: absolute; top: 0; left: 0; right: 0; bottom: 0; margin: -30px; z-index: -1; content: ''; background: url(./img/${I}.jpg) no-repeat; background-size:100% 100%; filter: blur(20px); }`);
		}else{
			loadCssCode(`.Opage::before{ position: absolute; top: 0; left: 0; right: 0; bottom: 0; margin: -30px; z-index: -1; content: ''; background: url(./img/${I}.jpg) no-repeat; background-size:100% 100%; filter: blur(20px); }`);
		}
		$("img")[0].src = `./img/${I}.jpg`;
		$("img")[1].src = `./img/${I}.jpg`;
		myMusic.pause();
		myMusic.src = `./music/ogg/${I}.ogg`;
		Play = true;
		oPaly.className = "play iconfont Iconfont icon-bofang";
		oPaly.title = "暂停";
		/* 延时获取totalTime */
		setTimeout( function(){
			if(myMusic.readyState == 4){
				totalTime = parseInt(myMusic.duration);
			}else{
				location.reload();
				alert("信息获取错误，自动刷新页面")
			}
			realTime = parseInt(myMusic.currentTime);
			totalMinute = doubleNum(parseInt(totalTime/60));
			totalSecond = doubleNum(totalTime%60);
			oTotalTime.innerHTML = totalMinute + ":" + totalSecond;
			left = 0;
			myMusic.play();
			oLyric.scrollTop = 0;
			clearInterval(real);
			oTimer();
			progress();
		},200);
		/* 更新信息 */
		update();
	}
	/* 下一首 */
	var oNext = $(".next")[0];
	oNext.onclick = function(){
		++I; (I > 1) ? I = 0 : I = I;
		if($("#style") != null){
			head.removeChild(style);
			loadCssCode(`.Opage::before{ position: absolute; top: 0; left: 0; right: 0; bottom: 0; margin: -30px; z-index: -1; content: ''; background: url(./img/${I}.jpg) no-repeat; background-size:100% 100%; filter: blur(20px); }`);
		}else{
			loadCssCode(`.Opage::before{ position: absolute; top: 0; left: 0; right: 0; bottom: 0; margin: -30px; z-index: -1; content: ''; background: url(./img/${I}.jpg) no-repeat; background-size:100% 100%; filter: blur(20px); }`);
		}	
		$("img")[0].src = `./img/${I}.jpg`;
		$("img")[1].src = `./img/${I}.jpg`;
		myMusic.pause();
		myMusic.src = `./music/ogg/${I}.ogg`;
		Play = true;
		oPaly.className = "play iconfont Iconfont icon-bofang";
		oPaly.title = "暂停";
		/* 延时获取totalTime */
		setTimeout( function(){
			if(myMusic.readyState == 4){
				totalTime = parseInt(myMusic.duration);
			}else{
				location.reload();
				alert("信息获取错误，自动刷新页面")
			}
			realTime = parseInt(myMusic.currentTime);
			totalMinute = doubleNum(parseInt(totalTime/60));
			totalSecond = doubleNum(totalTime%60);
			oTotalTime.innerHTML = totalMinute + ":" + totalSecond;
			left = 0;
			myMusic.play();
			oLyric.scrollTop = 0;
			clearInterval(real);
			oTimer();
			progress();
		},200);
		/* 更新信息 */
		update();
	}
	/* 播放/暂停 */
	var oPaly = $(".play")[0];
	var Play = false;
	oPaly.onclick = function(){
		if(Play){
			myMusic.pause();
			Play = false;
			oPaly.className = "play iconfont Iconfont icon-zanting";
			oPaly.title = "播放";
			clearInterval(real);
		}else{
			myMusic.play();
			Play = true;
			oPaly.className = "play iconfont Iconfont icon-bofang";
			oPaly.title = "暂停";
			/* 当前时长 */
			oTimer();
		}
	}
	/* 歌曲进度条 */
	var oProgress = $(".progress")[0];
	var oSpot = $(".spot")[0];
	progress();
	function progress(){
		oSpot.onmousedown = function(event){
			event = event || window.event; //兼容
			//           获取水平坐标 - 进度条到页面最左侧的距离 - 进度条的总长度/2
			var offsetX = event.clientX - oSpot.offsetLeft - oSpot.offsetWidth/2;
			document.onmousemove = function(event){//拖动时改变进度条和歌曲音频进度同步
				myMusic.muted = true; //被静音
				var left = event.clientX - offsetX; //歌曲当前进度长度
				if(left < 0){
					left = 0;
				}else if(left > 600){
					left =600;
				}
				oSpot.style.left = (left-10) + "px";
				oProgress.style.width = left + "px";
				realTime = parseInt((left*totalTime)/600);
				realMinute = doubleNum(parseInt(realTime/60));
				realSecond = doubleNum(realTime%60);
				oRealTime = $(".realTime")[0];
				myMusic.currentTime = realTime;
				oRealTime.innerHTML = realMinute + ":" + realSecond; //当前播放时间显示
			}
		}
	}
	/* 音频时间 */
	//总时长
	var real;
	var myMusic = $("#myMusic");
	var totalTime;
	var totalMinute;
	var totalSecond;
	var oTotalTime;
	/* 确保获取成功 */
	setTimeout( function(){
		if(myMusic.readyState == 4){
			totalTime = parseInt(myMusic.duration);
		}else{
			// location.reload();
		}
		totalMinute = doubleNum(parseInt(totalTime/60));
		totalSecond = doubleNum(totalTime%60);
		oTotalTime = $(".totalTime")[0];
		oTotalTime.innerHTML = totalMinute + ":" + totalSecond;
	},200);
	/* 当前时长 */
	var realTime = parseInt(myMusic.currentTime);
	var realMinute = doubleNum(parseInt(realTime/60));
	var realSecond = doubleNum(realTime%60);
	var oRealTime = $(".realTime")[0];
	/* 音量 */
	var oShengyin = $(".icon-shengyin")[0];
	var oVolumeBox = $(".volumeBox")[0];
	var display = false;
	oShengyin.onclick = function(){
		if(display){
			oVolumeBox.style.display = "none";
			display = false;
		}else{
			oVolumeBox.style.display = "";
			display = true;
		}
	}
	/* 音量进度条 */
	var oVolume = $(".volume")[0];
	var ovolumeSpot = $(".volumeSpot")[0];
	ovolumeSpot.onmousedown = function(event){
		event = event || window.event;
		var offsetY = event.clientY - ovolumeSpot.offsetTop + ovolumeSpot.offsetHeight/2;
		document.onmousemove = function(event){
			var bottom = (80- (event.clientY - offsetY));
			if(bottom < 0){
				bottom = 0;
			}else if(bottom > 80){
				bottom =80;
			}
			ovolumeSpot.style.bottom = (bottom + 5) + "px";
			oVolume.style.height = bottom + "px";
			myMusic.volume = bottom/80;
		}
	}
	document.onmouseup  = function(){
		myMusic.muted = false; //静音取消
		document.onmousemove = null;
		oVolumeBox.style.display = "none";
		display = false;
	}
	/* 单曲/列表/随机循环图标 */
	var oLoop = $(".loop")[0];
	var Num = 1;
	var Company = 1;
	oLoop.onclick = function(){
		switch(Num){
			case 1:
				oLoop.className = "iconfont loop icon-suiji";
				Num = 2;
				myMusic.loop = false;
				break;
			case 2:
				oLoop.className = "iconfont loop icon-danquxunhuan";
				Num = 3;
				myMusic.loop = true;
				break;
			case 3:
				oLoop.className = "iconfont loop icon-liebiaoxunhuan";
				Num = 1;
				myMusic.loop = false;
				break;
		}
	}
	if( Num ==1 ){
		/* 循环播放 */
		myMusic.onended = function(){
			oNext.click();
		}
	}else if( Num ==3 ){
		/*随机播放*/
		myMusic.onended = function(){
			I = Math.floor(Math.random() * 2);
			(I > 1) ? I = 0 : I = I;
			loadCssCode(`.Opage::before{ position: absolute; top: 0; left: 0; right: 0; bottom: 0; margin: -30px; z-index: -1; content: ''; background: url(./img/${I}.jpg) no-repeat; background-size:100% 100%; filter: blur(20px); }`);
			$("img")[0].src = `./img/${I}.jpg`;
			$("img")[1].src = `./img/${I}.jpg`;
			myMusic.pause();
			myMusic.src = `./music/ogg/${I}.ogg`;
			Play = true;
			oPaly.className = "play iconfont Iconfont icon-bofang";
			oPaly.title = "暂停";
			/* 延时获取totalTime */
			setTimeout( function(){
				if(myMusic.readyState == 4){
					totalTime = parseInt(myMusic.duration);
				}else{
					location.reload();
					alert("信息获取错误，自动刷新页面")
				}
				realTime = parseInt(myMusic.currentTime);
				totalMinute = doubleNum(parseInt(totalTime/60));
				totalSecond = doubleNum(totalTime%60);
				oTotalTime.innerHTML = totalMinute + ":" + totalSecond;
				left = 0;
				myMusic.play();
				oLyric.scrollTop = 0;
				clearInterval(real);
				oTimer();
				progress();
			},200);
			/* 更新信息 */
			update();
		}
	}
	/* 喜爱 */
	var oLove = $(".love")[0];
	var state = false;
	oLove.onclick = function(){
		let musicid = localStorage.getItem("musicid");
		if(state){//已经喜欢，点击后取消喜欢
			oLove.className = "iconfont love icon-xihuan1";
			state = false;
			axios({
				method:'POST',
				dataType:"json",
				url:'http://localhost:8080/songServlet?method=cancelLoveSong',
				data: {
					song_id: musicid
				}
			}).then(function (response) {
				console.log(response);
				// alert(response.data.message);
				if(response.data.code===200)
				{
					console.log("取消喜欢成功");
					window.main.location.href = "indexIframe.html";//子网页跳转
					// location.reload(true);
				}else{
					console.log("操作失败，仍喜欢");
				}
			});
		}else{//为喜欢，点击后喜欢
			oLove.className = "iconfont love icon-xihuan2";
			state = true;
			axios({
				method:'POST',
				dataType:"json",
				url:'http://localhost:8080/songServlet?method=loveSong',
				data: {
					song_id: musicid
				}
			}).then(function (response) {
				console.log(response);
				// alert(response.data.message);
				if(response.data.code===200)
				{
					console.log("喜欢成功");
				}else{
					console.log("加入喜欢的列表失败或者已经存在该音乐");
				}
			});
		}
	}
	/* 播放列表 */
	var oLiebiao = $(".liebiao")[0];
	var oList = $(".list")[0];
	var oListTriangle = $(".listTriangle")[0];
	var Open1 = false;
	oLiebiao.onclick = function(){
		if(Open1){
			oList.style.display = "none";
			oListTriangle.style.display = "none";
			Open1 = false;
		}else{
			oList.style.display = "";
			oListTriangle.style.display = "";
			Open1 = true;
		}
	}
	/* 更多 */
	var oGengduo = $(".gengduo")[0];
	var oGengduoBox = $(".gengduoBox")[0];
	var oBoxTriangle = $(".boxTriangle")[0];
	var Open2 = false;
	oGengduo.onclick = function(){
		if(Open2){
			oGengduoBox.style.display = "none";
			oBoxTriangle.style.display = "none";
			Open2 = false;
		}else{
			oGengduoBox.style.display = "";
			oBoxTriangle.style.display = "";
			Open2 = true;
		}
	}
	/* 初始化页面 */
	var str = "";
	/* 歌曲,歌手名称，歌单 */
	for(let i=0; i < 2; i++){
		$(".songName")[i].innerHTML = sname;
		$(".singer")[i].innerHTML = sgname;
		str += `<p><span class="l">${songName[i]}</span><span >--</span><span class="r">${singers[i]}</span></p>`;
		$(".Song")[0].innerHTML = str;
	}
	/* 解析lrc */
	// var lrcArr = lrc.split("[");
	var html = "";
	var oLyric = $(".Lyric")[0];
	Initialize();
	function Initialize(){
		if(lrcArr){
			for(let i=0; i < lrcArr.length; i++){
				lrcArr = gctxt.split("[");
				arr = lrcArr[i].split("]");
				var time = arr[0].split(".");
				var timer = time[0].split(":");
				var ms = timer[0] * 60 + timer[1] * 1;
				var text = arr[1];
				if(text){
					html += "<p id=" + ms +">" + text + "</p>";
				}
		}
			oLyric.innerHTML = html;
			arr[0] = null; arr[1] = null;
		}
		html = "";
	}
	var oP = oLyric.getElementsByTagName("p");
	/*歌词滚动*/
	myMusic.addEventListener("timeupdate",function(){
	    if($("#"+realTime)){
			/*清除样式*/
	        for(let i=0; i < oP.length ; i++){
	            oP[i].style.cssText = "font-size: 16px;";
	        }
			/* 歌词滚动 */
	        $("#"+realTime).style.cssText = "background: linear-gradient(-3deg,rgba(219, 43, 43, 0.94) 0%,rgba(219, 43, 43, 0.8) 60%);-webkit-background-clip: text;color: transparent;font-size: 20px;";
			//获得滚动窗口距离浏览器的距离
			var Distance1 = oLyric.offsetTop;
			//获得当前歌词距离浏览器顶部的距离
			var Distance2 = $("#"+realTime).offsetTop;
			//获得当前歌词距离滚动窗口的距离
			var Distance3 = Distance2-Distance1;
			//获得滚动窗口的滚动距离
			var Rolling = Distance2-Distance1-153;
			//比较当前歌词距离滚动窗口的距离，大于153，就让窗口滚动
			if(Distance3>153){
				oLyric.scrollTop = Rolling;
			}
	    }
		/* 播放完毕归位 */
		if(realTime >= (totalTime-1)){
			oLyric.scrollTop = 0;
		}
	})

	// /*下载歌曲*/
	$(".download")[0].onclick=function () {
		let audio = document.querySelector("audio");
		let src = audio.src;
		let url = '/fileDownServlet'
		function getFileName(path){
			var pos1 = path.lastIndexOf('/');
			var pos2 = path.lastIndexOf('\\');
			var pos  = Math.max(pos1, pos2)
			if( pos<0 )
				return path;
			else
				return path.substring(pos+1);
		}
		var fname = getFileName(src);   //文件名
		console.log(fname);
		download(url,fname);
		function download(url, fileName){
			// 创建表单
			const formEle = document.createElement('form')
			formEle.action = url
			formEle.method = 'post'
			formEle.style.display = 'none'
			// 创建 input，用于传参
			const formItem = document.createElement('input')
			formItem.value = fileName
			formItem.name = 'fileName'
			// 插入到页面中
			formEle.appendChild(formItem)
			document.body.appendChild(formEle)
			formEle.submit()
			document.body.removeChild(formEle)
		}

	}
	
}