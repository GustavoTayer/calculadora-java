package br.com.group.calc.modelo;

@FunctionalInterface
public interface MemoriaObserver {
	public void valorAlterado(String novoValor);
}
