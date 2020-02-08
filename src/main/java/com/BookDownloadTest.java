package com;

import java.io.IOException;

public class BookDownloadTest {
	public static void main(String[] args) {
		try {
			BookDownLoadXiaShuWang.download("246465", "从天下第一开始2", "C:\\Users\\Administrator\\Desktop\\a");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
