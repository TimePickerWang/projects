package snake;

import javax.swing.*;

public class SnackMain extends JFrame {
	public SnackMain() {
		super("贪吃蛇");
		SnackWin win = new SnackWin();
		add(win);
		setSize(435, 380);
		setLocation(450, 150);// 窗口出现位置
		setVisible(true);
		setResizable(false);// 不能调整窗口大小
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new SnackMain();
	}

}
