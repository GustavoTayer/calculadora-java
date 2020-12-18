package br.com.group.calc.visao;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import br.com.group.calc.modelo.Memoria;

@SuppressWarnings("serial")
public class Teclado extends JPanel implements ActionListener {
	GridBagLayout layout = new GridBagLayout();
	GridBagConstraints c = new GridBagConstraints();
	
	private final Color COR_CINZA_ESCURO = new Color(68, 68, 68);
	private final Color COR_CINZA_CLARO = new Color(99, 99, 99);
	private final Color COR_LARANJA = new Color(242, 163, 60);
	public Teclado() {
		setLayout(layout);
		c.fill  = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 3;
		adicionarBotoes("AC", COR_CINZA_ESCURO, c, 0, 0);
		c.gridwidth =1;
		adicionarBotoes("/", COR_CINZA_ESCURO, c, 3, 0);

		adicionarBotoes("7", COR_CINZA_CLARO, c, 0, 1);
		adicionarBotoes("8", COR_CINZA_CLARO, c, 1, 1);
		adicionarBotoes("9", COR_CINZA_CLARO, c, 2, 1);
		adicionarBotoes("*", COR_LARANJA, c, 3, 1);

		adicionarBotoes("4", COR_CINZA_CLARO, c, 0, 2);
		adicionarBotoes("5", COR_CINZA_CLARO, c, 1, 2);
		adicionarBotoes("6", COR_CINZA_CLARO, c, 2, 2);
		adicionarBotoes("-", COR_LARANJA, c, 3, 2);

		adicionarBotoes("1", COR_CINZA_CLARO, c, 0, 3);
		adicionarBotoes("2", COR_CINZA_CLARO, c, 1, 3);
		adicionarBotoes("3", COR_CINZA_CLARO, c, 2, 3);
		adicionarBotoes("+", COR_LARANJA, c, 3, 3);

		c.gridwidth = 2;
		adicionarBotoes("0", COR_CINZA_CLARO, c, 0, 4);
		c.gridwidth = 1;
		adicionarBotoes(",", COR_CINZA_CLARO, c, 2, 4);
		adicionarBotoes("=", COR_LARANJA, c, 3, 4);
		
	}
	
	private void adicionarBotoes(String texto, Color cor, GridBagConstraints c, int x, int y) {
		c.gridx = x;
		c.gridy = y;
		Botao botao = new Botao(texto, cor);
		botao.addActionListener(this);
		add(botao, c);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {			
			JButton botao = (JButton) e.getSource();
			Memoria.getInstancia().processarComando(botao.getText());
		}
		
	}
}
