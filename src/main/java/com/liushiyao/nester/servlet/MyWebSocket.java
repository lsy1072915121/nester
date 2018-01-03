package com.liushiyao.nester.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 *                 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */

@ServerEndpoint("/websocket")
public class MyWebSocket
{
	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;

	// concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
	// 若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
	/*
	 * private static Map<String,MyWebSocket> webSocketMap = new
	 * HashMap<String, MyWebSocket>();
	 */// concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
	private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();

	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;

	/**
	 * 新增：连接名字
	 */
	private String name;
	// 禁止同一ID登录机制的表标志位
	private boolean login_flag = false;

	/**
	 * 连接建立成功调用的方法
	 * 
	 * @param session
	 *            可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 * @throws IOException
	 */
	@OnOpen
	public void onOpen(Session session) throws IOException
	{

		MyWebSocket webSocket3;

		this.session = session;
		// 将附加内容开头作为连接名
		this.name = session.getQueryString();
		if ((webSocket3 = getMyWebSocket(this.name)) == null) {
			addOnlineCount(); // 在线数加1
			webSocketSet.add(this);
			System.out.println("有新连接加入！当前在线人数为" + getOnlineCount()
					+ "--------->" + new Date());
			// System.out.println("分配的sessionID(与在线人数无关):" + session.getId());
			login_flag = true;
			System.out.println("正常连接");
			// System.out.println(webSocket3 = getWebSocketDemo(this.name));
			System.out.println("连接名为：" + this.name);
			// 返回OK

			if (this.name.equals("xx2")) {
				this.session.getBasicRemote().sendText("OK");
				System.out.println("xx2连入");
					/**
					 * 设备接入提醒机制
					 */
					System.out.println("xx2连接");
//					xx2_connect_flag = true;
					MyWebSocket webSocket;
					if ((webSocket = getMyWebSocket("app")) != null) {
						try {
							new Thread().sleep(200);
						} catch (InterruptedException e) {
							
							e.printStackTrace();
						}
						webSocket.session.getBasicRemote().sendText("xx2 已连入");
						this.session.getBasicRemote().sendText("{connected}");
						System.out.println("xx2连入，且app已经在线");
					
				}

			}

			/**
			 * 必须加附加信息。否则断开连接
			 */
			// 获取连接列表

			/*
			 * if (this.name == null) { System.out.println("连接时设备名为空null"); }
			 */
			// else {
			if (this.name.equals("check")) {
				System.out.println("发送连接列表");
				for (Iterator<MyWebSocket> iterator = webSocketSet.iterator(); iterator
						.hasNext();) {
					MyWebSocket type = (MyWebSocket) iterator.next();
					// 发送出 “check”以外的在线user
					if (!type.name.equals("check")) {
						session.getBasicRemote().sendText(type.name);
						System.out.println("Device List  " + type.name);
					}
				}
			}
			// 当有新连接接入时，更新在线设备
			else {
				MyWebSocket webSocket;
				if ((webSocket = getMyWebSocket("check")) != null) {
					System.out.println("更新连接列表");
					webSocket.session.getBasicRemote().sendText(this.name);
					System.out.println("Device List  " + this.name);
				}
			}
			/*
			 * 发送连接信息给watch
			 */
			if (this.name.equals("app")) {
				MyWebSocket webSocket2;
				if ((webSocket2 = getMyWebSocket("xx2")) != null) {
					sendMessageToUserByName("xx2", "{connected}");
				} else {
					System.out.println("xx2未在线，无法发送app连接响应");
				}
			}
		} else {
			// sendMessageToUserByName(this.name,"禁止使用同一ID登录"+this.name );
			System.out.println("禁止使用同一ID登录" + this.name + "-------->"
					+ new Date());
			// login_flag = false;
			this.session.close();
		}

	}

	/**
	 * 连接关闭调用的方法
	 * 
	 * @throws IOException
	 */
	@OnClose
	public void onClose() throws IOException
	{
		/*
		 * 发送断开信息给watch
		 */
		if (login_flag) {
			// app断开连接
			if (this.name.equals("app")) {
				MyWebSocket webSocket2;
				if ((webSocket2 = getMyWebSocket("xx2")) != null) {
					sendMessageToUserByName("xx2", "{unconnected}");
				} else {
					System.out.println("xx2未在线，无法发送app断开响应");
				}

			}
			// xx2断开连接
			/**
			 * 设备断开提醒机制
			 */
			else if (this.name.equals("xx2")) {
				MyWebSocket webSocket2;
				if ((webSocket2 = getMyWebSocket("app")) != null) {
					sendMessageToUserByName("app", "警告，手表已断开连接，无法获取数据");
				} else {
					System.out.println("xx2未在线，无法发送app断开响应");
				}
//				 xx2_connect_flag = false;
				System.out.println("xx2 已断开   ");

			}
			
			/* webSocketMap.remove(this); //从set中删除 */
			webSocketSet.remove(this);
			subOnlineCount(); // 在线数减1
			System.out.println("有一连接关闭！当前在线人数为 " + getOnlineCount()
					+ "===================================>" + new Date());
			// 使用一次性发送List的方式
			MyWebSocket webSocket2;
			if ((webSocket2 = getMyWebSocket("check")) != null) {
				System.out.println("更新连接列表");
				webSocket2.session.getBasicRemote().sendText(
						"@delete" + this.name);
				System.out.println("Device List  " + this.name);
			}
		}

	}

	/**
	 * 收到客户端消息后调用的方法
	 * 
	 * @param message
	 *            客户端发送过来的消息
	 * @param session
	 *            可选的参数
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	/*
	 * 实施“@”发送制度 每次发送消息要求带“@”指示符
	 * 
	 * @app ： 发送给app
	 * 
	 * @watch ： 发送给手表
	 */
	@OnMessage
	public void onMessage(String message, Session session) throws IOException
	{
		System.out.println("来自客户端" + session.getId() + "的消息:" + message);
		// 群发消息


		}

		/*
		 * if(message.contains("@0")) { Iterable<Map.Entry<String,
		 * MyWebSocket>>iterable = webSocketMap.entrySet(); for (Entry<String,
		 * MyWebSocket> entry : iterable) { try { if
		 * (entry.getKey().equals("0")) {
		 * 
		 * entry.getValue().sendMessage("User"+session.getId()+message); } }
		 * catch (IOException e) {
		 * 
		 * e.printStackTrace(); } } } else if(message.contains("@1")) {
		 * Iterable<Map.Entry<String, MyWebSocket>>iterable =
		 * webSocketMap.entrySet(); for (Entry<String, MyWebSocket> entry :
		 * iterable) { try { if (entry.getKey().equals("1")) {
		 * 
		 * entry.getValue().sendMessage("User"+session.getId()+message); } }
		 * catch (IOException e) {
		 * 
		 * e.printStackTrace(); } } } else if(message.contains("@2")) {
		 * Iterable<Map.Entry<String, MyWebSocket>>iterable =
		 * webSocketMap.entrySet(); for (Entry<String, MyWebSocket> entry :
		 * iterable) { try { if (entry.getKey().equals("2")) {
		 * 
		 * entry.getValue().sendMessage("User"+session.getId()+message); } }
		 * catch (IOException e) {
		 * 
		 * e.printStackTrace(); } } } else {
		 * 
		 * Iterable<Map.Entry<String, MyWebSocket>>iterable =
		 * webSocketMap.entrySet(); for (Entry<String, MyWebSocket> entry :
		 * iterable) { try {
		 * entry.getValue().sendMessage("User"+session.getId()+message); } catch
		 * (IOException e) {
		 * 
		 * e.printStackTrace(); } } }
		 */
		// sendMessageToUser("1", message);


	/**
	 * 发生错误时调用
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error)
	{
		System.out.println("发生错误");
		error.printStackTrace();
	}

	/**
	 * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
	 * 
	 * @param message
	 * @throws IOException
	 */
	private void sendMessage(String message) throws IOException
	{
		this.session.getBasicRemote().sendText(message);

		// this.session.getAsyncRemote().sendText(message);
	}

	private void sendMessageToUserById(String id, String message)
			throws IOException
	{
		MyWebSocket websocket = null;
		for (Iterator<MyWebSocket> iterator = webSocketSet.iterator(); iterator
				.hasNext();) {
			MyWebSocket type = (MyWebSocket) iterator.next();

			if (type.session.getId().equals(id)) {
				websocket = type;
			}
		}
		websocket.session.getBasicRemote().sendText(message);

	}

	/**
	 * 通过 name 进行消息发送
	 * 
	 * @param name
	 * @param message
	 * @throws IOException
	 */
	private void sendMessageToUserByName(String name, String message)
			throws IOException
	{
		MyWebSocket websocket = null;
		for (Iterator<MyWebSocket> iterator = webSocketSet.iterator(); iterator
				.hasNext();) {
			MyWebSocket type = (MyWebSocket) iterator.next();

			if (type.name.equals(name)) {
				websocket = type;
			}
		}
		websocket.session.getBasicRemote().sendText(message);

	}

	public MyWebSocket getMyWebSocket(String name)
	{
		MyWebSocket websocket = null;
		for (Iterator<MyWebSocket> iterator = webSocketSet.iterator(); iterator
				.hasNext();) {
			MyWebSocket type = (MyWebSocket) iterator.next();
			if (type != null) {

				if (type.name.equals(name)) {
					websocket = type;
					return websocket;
				}
			}

		}
		return null;
	}

	public static synchronized int getOnlineCount()
	{
		return onlineCount;
	}

	public static synchronized void addOnlineCount()
	{
		MyWebSocket.onlineCount++;
	}

	public static synchronized void subOnlineCount()
	{
		MyWebSocket.onlineCount--;
	}

	private String changeNum(int num)
	{
		if (num > 9) {
			return num + "";
		} else {
			return "0" + num;
		}

	}
}
