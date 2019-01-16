package tetris;
import javax.swing.*;

public class GameMain extends JFrame {
	public GameMain(){
		super("俄罗斯方块");
		GameWin g = new GameWin();
		add(g);
		setSize(380,410);
		setLocation(450,150);
		setVisible(true);
		setResizable(false);// 不能调整窗口大小
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		new GameMain();
	}
}
