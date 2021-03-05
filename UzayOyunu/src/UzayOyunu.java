import java.awt.Color;
import java.awt.HeadlessException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

//Main Class
public class UzayOyunu extends JFrame {
	public UzayOyunu(String title) throws HeadlessException {// Title Yaratmak icin
		super(title);
		ImageIcon logo = new ImageIcon(UzayOyunu.class.getResource("/img/icon.png"));
		setIconImage(logo.getImage());// icon resmini degistirir
	}

	public static void main(String[] args) {
		UzayOyunu ekran = new UzayOyunu("Uzay Oyunu");
		ekran.setResizable(false);
		ekran.setFocusable(false);// frame degil jpanele focuslanacak
		ekran.setSize(800, 600);
		ekran.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		OyunPanel oyun = new OyunPanel();

		// Bunlarin sirasi onemli
		oyun.requestFocus();// Jpanelin bizim klavye islemlerini anlamasi icin fokusu ona veriyoruz
		oyun.addKeyListener(oyun);
		oyun.setFocusable(true);// odagi jframeden cekmistik artik odagi jpanele verdik
		oyun.setFocusTraversalKeysEnabled(false);// klavye islemlerinin jpanelinin anlmasi icin gerekli
		ekran.add(oyun);
		ekran.setVisible(true);
	}
}
