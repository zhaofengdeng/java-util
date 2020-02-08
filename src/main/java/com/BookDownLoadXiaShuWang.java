package com;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import com.util.http.HttpPostUtil;

public class BookDownLoadXiaShuWang {
	private static String BR_VALUE = "\r\n";

	public static void download(String menu, String fileName, String fileDir) throws IOException {
		File writename = new File(fileDir);
		if (!writename.exists()) {
			writename.mkdirs();
		}
		writename = new File(fileDir + "\\" + fileName + ".txt");// 相对路径，如果没有则要建立一个新的output。txt文件
		writename.createNewFile(); // 创建新文件
		BufferedWriter out = new BufferedWriter(new FileWriter(writename));
		out.write("来源地址：https://www.xiashu.cc/" + menu + "/");
		out.write(BR_VALUE);
		out.write(BR_VALUE);
		boolean end = false;
		int i = 1;
		while (!end) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String html = HttpPostUtil.post("https://www.xiashu.cc/" + menu + "/read_" + i + ".html", null);
			String title = getTitle(html);
			String content = getContent(html);
			out.write(title);
			out.write(BR_VALUE);
			out.write(BR_VALUE);
			out.write(content);
			out.write(BR_VALUE);
			out.write(BR_VALUE);
			out.write(BR_VALUE);
			end = isEnd(content);
			i++;
		}
		out.flush(); // 把缓存区内容压入文件
		out.close(); // 最后记得关闭文件
		System.out.println("书籍下载完成======================================");
	}

	private static String getTitle(String html) {
		String str = "";
		try {
			str = html;
			str = str.substring(str.indexOf("<title>") + 7, str.indexOf("</title>"));
			str = str.substring(0, str.indexOf("_"));
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("获取目录失败：" + str);
			return "获取目录失败：" + BR_VALUE;
		}

	}

	private static boolean isEnd(String html) {
		if (html.indexOf("readend") > 0) {
			return true;
		}
		return false;
	}

	private static String getContent(String html) {
		try {
			String str = html;
			str = str.substring(str.lastIndexOf("listtopad"), str.indexOf("bottominfo") - 15);
			str = str.substring(str.indexOf("div") + 20, str.lastIndexOf("div") - 2);
			str = htmlToString(str);
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			return "下载失败： " + BR_VALUE;
		}

	}

	private static String htmlToString(String str) {
		String strNew = str;
		strNew = strNew.replaceAll("<br />", BR_VALUE);
		strNew = strNew.replaceAll("<br/>", BR_VALUE);
		strNew = strNew.replaceAll("<br>", BR_VALUE);
		return strNew;
	}

}
