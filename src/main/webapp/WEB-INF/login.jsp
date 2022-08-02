<%--
  Created by IntelliJ IDEA.
  User: 86183
  Date: 2022/7/15
  Time: 0:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="../css/login.css"/>
</head>
<body>
<div class="login-box">
    <h2>网易云音乐</h2>
    <form action="" method="post">
        <div class="input-box">
            <input type="text" name="username" required="" placeholder="用户名">
        </div>
        <div class="input-box">
            <input type="password" name="password" required="" placeholder="密码">
        </div>
        <input class="input-button" type="submit" name="" value="登录">
        <div class="sign-up">
            还没有账户？<a href="http://localhost:63342/untitled3/main/webapp/html/register.html?_ijt=3jj1ikpbmctj908gpanqmm6ggd">立即注册</a>
        </div>
    </form>
</div>
</body>
</html>