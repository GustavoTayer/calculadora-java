package br.com.group.calc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {
	private static final Memoria instancia = new Memoria();
	private TipoComando ultimaOp = null;
	private boolean substituir = false;
	private String textoAtual = "";
	private String textoBuffer = "";
	private final List<MemoriaObserver> observers = new ArrayList<>();

	private enum TipoComando {
		ZERAR, NUMERO, DIV, MULT, SUB, SOMA, IGUAL, VIRGULA;
	}

	private Memoria() {

	}

	public static Memoria getInstancia() {
		return instancia;
	}

	public String getTextoAtual() {
		return textoAtual.isEmpty() ? "0" : textoAtual;
	}

	public void setTextoAtual(String textoAtual) {
		this.textoAtual = textoAtual;
	}

	public void adicionarObservador(MemoriaObserver o) {
		observers.add(o);
	}

	public void processarComando(String valor) {
		TipoComando tipoComando = detectarTipoComando(valor);
		if (tipoComando == null) {
			return;
		}
		switch (tipoComando) {
		case ZERAR:
			textoAtual = "";
			textoBuffer = "";
			substituir = false;
			ultimaOp = null;
			break;
		case NUMERO:
		case VIRGULA:
			textoAtual = substituir ? valor : textoAtual + valor;
			substituir = false;
			break;
		default:
			substituir = true;
			textoAtual = obterResultadoOperacao();
			textoBuffer = textoAtual;
			ultimaOp = tipoComando;
			break;
		}
		observers.forEach(o -> o.valorAlterado(getTextoAtual()));
	}

	private String obterResultadoOperacao() {
		if (ultimaOp == null || ultimaOp == TipoComando.IGUAL) {
			return textoAtual;
		}
		double numeroBuffer = Double.parseDouble(textoBuffer.replace(",", "."));
		double numeroAtual = Double.parseDouble(textoAtual.replace(",", "."));

		double resultado = 0;

		if (ultimaOp == TipoComando.SOMA) {
			resultado = numeroBuffer + numeroAtual;
		} else if (ultimaOp == TipoComando.DIV) {
			resultado = numeroBuffer / numeroAtual;
		} else if (ultimaOp == TipoComando.SUB) {
			resultado = numeroBuffer - numeroAtual;
		} else if (ultimaOp == TipoComando.MULT) {
			resultado = numeroBuffer * numeroAtual;
		}

		String resultadoString = Double.toString(resultado).replace(".", ",");
		boolean inteiro = resultadoString.endsWith(",0");
		return inteiro ? resultadoString.replace(",0", "") : resultadoString;
	}

	private TipoComando detectarTipoComando(String valor) {
		if (textoAtual.isEmpty() && valor == "0") {
			return null;
		}
		try {
			Integer.parseInt(valor);
			return TipoComando.NUMERO;
		} catch (NumberFormatException e) {
			if ("AC".equals(valor)) {
				return TipoComando.ZERAR;
			} else if ("/".equals(valor)) {
				return TipoComando.DIV;
			} else if ("*".equals(valor)) {
				return TipoComando.MULT;
			} else if ("-".equals(valor)) {
				return TipoComando.SUB;
			} else if ("+".equals(valor)) {
				return TipoComando.SOMA;
			} else if ("=".equals(valor)) {
				return TipoComando.IGUAL;
			} else if (",".equals(valor) && !textoAtual.contains(",")) {
				return TipoComando.VIRGULA;
			}
		}
		return null;
	}
}
