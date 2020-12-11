package juego;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import javax.swing.JPanel;

public class Juego extends JPanel {
	private static final long serialVersionUID = 1L;

	URL direccionSonidoSalto, direccionSonidoChoque;
	AudioClip sonidoChoque, sonidoSalto;

	Personaje personaje = new Personaje(this);
	Enemigo enemigo = new Enemigo(this);
	Fondo fondo = new Fondo(this);

	public static boolean juegoFinalizado = false;
	public static boolean pierdeIntentoVida = false;
	public static int intentosVidas = 3;
	public static int nivel = 1;
	public static int puntos = 0;

	public Juego() {
		direccionSonidoChoque = this.getClass().getResource("/sonidos/colision.wav");
		sonidoChoque = Applet.newAudioClip(direccionSonidoChoque);

		direccionSonidoSalto = this.getClass().getResource("/sonidos/saltopika.wav");
		sonidoSalto = Applet.newAudioClip(direccionSonidoSalto);

		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					sonidoSalto.play();
					personaje.keyPressed(e);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
		setFocusable(true);
	}

	public void mover() {
		enemigo.mover();
		personaje.mover();
		fondo.mover();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		dibujar(g2D);
		dibujarPuntaje(g2D);
	}

	public void dibujar(Graphics2D g) {
		fondo.paint(g);
		personaje.paint(g);
		enemigo.paint(g);
		mover();
	}

	public void dibujarPuntaje(Graphics2D g) {
		Graphics2D g1 = g, g2D = g;
		Font score = new Font("Tahoma", Font.BOLD, 30);
		g.setFont(score);
		g.setColor(Color.WHITE);
		g1.drawString("Puntos: " + puntos, 1100, 30);
		g1.drawString("Intentos: " + intentosVidas, 20, 30);
		g1.drawString("Nivel: " + nivel, 570, 30);

		if (juegoFinalizado) {
			g2D.setColor(Color.BLACK);
			g2D.drawString("Gracias D10S !", ((float) getBounds().getCenterX() / 2) + 210, 70);
		}
	}

	public void finJuego() {
		juegoFinalizado = true;
		sonidoChoque.play();
	}

	public void pierdeIntentoVida() {
		sonidoChoque.play();
		pierdeIntentoVida = true;
	}

}
