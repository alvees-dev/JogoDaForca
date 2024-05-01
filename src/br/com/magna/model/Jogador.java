package br.com.magna.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jogador {

	private String nome;
	private int tentativas;
	protected List<Jogador> jogadores = new ArrayList<>();

	Scanner scan = new Scanner(System.in);

	public Jogador(String nome, int tentativas) {
		this.nome = nome;
		this.tentativas = tentativas;
	}

	public String getNome() {
		return nome;
	}

	public int getTentativas() {
		return tentativas;
	}

	public void diminuiTentativas() {
		tentativas--;
	}

	public List<Jogador> adicionaJogadores() {

		this.nome = scan.nextLine().toUpperCase();
		jogadores.add(new Jogador(nome, tentativas));

		return jogadores;
	}

	public void exibeJogadores() {

		System.out.println("Jogadores Cadastrados:");
		System.out.println("======================");
		for (Jogador jogador : jogadores) {
			System.out.println(jogador);
		}
	}

	@Override
	public String toString() {
		return this.nome;
	}
}