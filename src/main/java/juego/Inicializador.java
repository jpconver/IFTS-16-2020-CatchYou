package juego;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Inicializador {
	public static int reiniciaJuego = -1;

	public static void main(String[] args) {
		String nombresonido = "/dontLetThemCatchYou-main/src/main/resources/sonidos/backgroundMusic.wav";
		ReproducirSonido(nombresonido);
		JOptionPane.showMessageDialog(null, "¿Preparados?");
		System.setProperty("sun.java2d.opengl", "true");
		JFrame ventana = new JFrame("Don't let them catch you");
		Juego miJuego = new Juego();
		ventana.add(miJuego);
		ventana.setSize(1300, 400);
		ventana.setVisible(true);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		while (true) {
			if (Juego.juegoFinalizado) {	
				reiniciaJuego = JOptionPane.showConfirmDialog(null, "Te atraparon Mauricio! Volver a jugar?", "¡ Game Over !", JOptionPane.YES_NO_OPTION);
				if (reiniciaJuego == 0) {	
					reiniciaValores();
				} else if (reiniciaJuego == 1) {
					System.exit(0);
				}
			} else {
				miJuego.repaint();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (Juego.pierdeIntentoVida == true) {
					JOptionPane.showMessageDialog(null, "Cuidado!! Te quedan: " + Juego.intentosVidas + " intentos");
					Juego.pierdeIntentoVida = false;
					Juego.intentosVidas--;
					Personaje.inicialY = 270;
					Personaje.saltando = false;
					Enemigo.inicialX = 1300;
				}
			}
		}
	}

	public static void reiniciaValores() {
		Juego.juegoFinalizado = false;
		Enemigo.auxiliarX = -4;
		Juego.puntos = 0;
		Juego.intentosVidas = 3;
		Juego.nivel = 1;
		reiniciaJuego = -1;
		Enemigo.inicialX = 1300;
	}

	public static void ReproducirSonido(String nombreSonido){
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(nombreSonido).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.loop(reiniciaJuego);
		} catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("Error al reproducir el sonido.");
		}
	}

}
