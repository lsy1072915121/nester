<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>WebSocket调试工具</title>
</head>
<body>
    <div id="userInfo">调试工具</div><br/>
    <input id="text" type="text"/>
    <button onclick="send()">发送消息</button><hr/>
    <button onclick="closeWebSocket()">关闭WebSocket连接</button><hr/>
    <div id="message"></div>
</body>

<script type="text/javascript">
    var websocket = null;
    var id = randomNum();
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
    	//一定要写8080
    websocket = new WebSocket("ws://localhost:8082/websocket?test");
//    websocket = new WebSocket("ws://www.while-true.cn/nester/websocket?xx2");
    }
    else {
        alert('当前浏览器 Not support websocket')
    }

    //连接发生错误的回调方法
    websocket.onerror = function () {
        setMessageInnerHTML("WebSocket连接发生错误");
    };

    //连接成功建立的回调方法
    websocket.onopen = function () {
        setConnectInfo(id);
        setMessageInnerHTML("WebSocket连接成功");
    }

    //接收到消息的回调方法
    websocket.onmessage = function (event) {
        setMessageInnerHTML(event.data);
    }

    //连接关闭的回调方法
    websocket.onclose = function () {
        setMessageInnerHTML("WebSocket连接关闭");
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        closeWebSocket();
    }

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        document.getElementById('message').innerHTML += innerHTML + '<br/>';
    }
    //显示成功连接信息
    function setConnectInfo(id) {
      document.getElementById('userInfo').innerHTML += 'Id:'+id +"连接成功";
    }

    //关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
    }

    //发送消息
    function send() {
        var message = document.getElementById('text').value;
        websocket.send(message);
    }
    function randomNum() {
      var Min = 0,Max = 10000;
      var Range = Max - Min;
      var Rand = Math.random();
      if(Math.round(Rand * Range)==0){
        return Min + 1;
      }
      var num = Min + Math.round(Rand * Range);
      return num;
    }
</script>
</html>