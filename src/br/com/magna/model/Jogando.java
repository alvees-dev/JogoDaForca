package br.com.magna.model;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Jogando {

	String palavra = "DESASNADO";

	Scanner scan = new Scanner(System.in);
	Jogador jogador = new Jogador("", 5);
	Forca forca = new Forca(jogador.jogadores, "DESASNADO");

	public void exibeBoasVindas() {
		System.out.println("""
				Jogo da Forca do Alves
				-------------------------------

				Para jogar, são necessários pelo menos 2 jogadores
				""");

		int opcao1 = 0;

		do {

			System.out.println("""
					   \nMenu Principal
					   
					1. Cadastrar Jogador
					2. Jogar
					3. Sair
						""");

			try {
				opcao1 = scan.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Por favor, digite uma opção válida (número).");
				
				// Limpa o buffer do scanner
				scan.next(); 
				continue;
			}

			if (opcao1 == 1) {
				System.out.println("\nDigite seu nome: ");
				jogador.adicionaJogadores();

			} else if (opcao1 == 2) {
				if ((jogador.jogadores.isEmpty() || jogador.jogadores.size() < 2)) {
					System.out.println("Não há jogadores suficientes para iniciar o jogo");
					
				} else {
					break;
				}
			} else if (opcao1 == 3) {
				System.out.println("Obrigado pelo acesso!");
				System.exit(0);
			} else {
				System.out.println("Por favor, digite uma opção válida");
			}

			jogador.exibeJogadores();

		} while (opcao1 != 3);
	}

	public void play() {
		exibeBoasVindas();
		forca.jogar();

	}
}
