package br.com.magna.model;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Forca {

	private Scanner scan = new Scanner(System.in);
	Set<Character> letrasRepetidas = new HashSet<>();
	private List<Jogador> jogadores;
	private String palavra;

	public Forca(List<Jogador> jogadores, String palavra) {
		this.jogadores = jogadores;
		this.palavra = palavra;
	}

	public static final void espacos() {
		for (int i = 0; i < 40; i++) {
			System.out.println();
		}
	}

	// inicia rodada 1 do jogo
	public void jogar() {

		// Recebe palavra e converte em char percorrendo por caracter
		char[] palavraSecreta = new char[palavra.length()];
		for (int i = 0; i < palavra.length(); i++) {

			palavraSecreta[i] = '_';
		}

		// Percorrendo lista de jogadores
		for (Jogador jogador : jogadores) {
			
			espacos();

			// verifica qual jogador tem vida maior que 0
			if (jogador.getTentativas() > 0) {

				// impreme linhas em branco no console

				// mostra de quem é a vez, tentativas restantes através do desenho
				// e converve o arraychar[] para String para mostrar o progresso da palavra
				System.out.println("-------------------");
				System.out.println("\nVez do jogador: " + jogador.getNome() + "");
				System.out.println("Tentativas restantes: " + jogador.getTentativas());
				desenharForca(jogador.getTentativas());
				System.out.println("\nLetras já escritas: " + letrasRepetidas);
				System.out.print("\nPalavra: ");
				for (int i = 0; i < palavraSecreta.length; i++) {
					System.out.print(palavraSecreta[i] + " ");
				}

				System.out.print("\nDigite uma letra: ");
				char letra = scan.nextLine().toUpperCase().charAt(0);

				// Verifica se a letra é repetida e, se sim, perde tentativa
				if (letrasRepetidas.contains(letra)) {
					System.out.println("\nErro! Letra já foi tentada.");
					jogador.diminuiTentativas();
					continue;
				}

				letrasRepetidas.add(letra);

				boolean letraAdivinhada = atualizarPalavra(letra, palavra, palavraSecreta);

				// Diminui número de tentativas caso erre a letra
				if (!letraAdivinhada) {
					System.out.println("Letra errada.");
					jogador.diminuiTentativas();
				}

				// Convertendo o Array de char[] para String e verificando se é igual a palavra
				if (String.valueOf(palavraSecreta).equals(palavra)) {
					System.out.println("Parabéns, você adivinhou a palavra!");
					return;
				}

				// Se a tentativa chegar a zero, está fora do jogo
				if (jogador.getTentativas() == 0) {
					desenharForca(jogador.getTentativas());
					System.out.println("Você perdeu todas as suas tentativas! Fim de jogo para você.");
				}

			}
		}
		rodada2(palavraSecreta);
	}

	public void rodada2(char[] palavraAdivinhada) {

		boolean jogoContinua = true;
		int tentativaPalavra = 0;

		// loop criado repetindo o jogo até que acabe
		while (jogoContinua) {

			jogoContinua = false;

			for (Jogador jogador : jogadores) {

				espacos();

				if (jogador.getTentativas() > 0) {

					jogoContinua = true;


					System.out.println("\n-----------------");
					System.out.println("\nVez do jogador: " + jogador.getNome());
					System.out.println("Tentativas restantes: " + jogador.getTentativas());
					desenharForca(jogador.getTentativas());
					System.out.println("Letras já escritas: " + letrasRepetidas);
					System.out.print("\nPalavra: ");
					for (int i = 0; i < palavraAdivinhada.length; i++) {
						System.out.print(palavraAdivinhada[i] + " ");
					}
					System.out.println("""
							\nDeseja tentar acertar a palavra?
							--------------------------------
							1. Sim
							2. Não
								""");

					// tratando erro caso seja passada alguma letra
					try {
						tentativaPalavra = scan.nextInt();
					} catch (InputMismatchException e) {
						// limpando buffer do Scanner
						scan.nextLine();
						break;
					}

					scan.nextLine();

					if (tentativaPalavra == 1) {
						adivinhaPalavra(jogador, palavraAdivinhada);
						continue;
					} else if (tentativaPalavra == 2) {
						System.out.print("\nDigite uma letra: ");
						char letra = scan.nextLine().toUpperCase().charAt(0);

						if (letrasRepetidas.contains(letra)) {
							System.out.println("\nErro! Letra já foi tentada.");
							jogador.diminuiTentativas();
							continue;
						}

						// Adiciona a letra ao conjunto de letras tentadas
						letrasRepetidas.add(letra);

						// Caso a letra não seja repetida e seja valida, atualiza o progresso da palavra
						boolean letraAdivinhada = atualizarPalavra(letra, palavra, palavraAdivinhada);

						if (!letraAdivinhada) {
							System.out.println("Letra errada!");
							jogador.diminuiTentativas();
						} else if (String.valueOf(palavraAdivinhada).equals(palavra)) {

							System.out.println("Parabéns, o jogador " + jogador.getNome() + " adivinhou a palavra!");
							System.out.println("\nPalavra: " + palavra);
							System.out.println("\nO jogo será encerrado!");
							System.out.println("Obrigado por jogar. :)");

							System.exit(0);
							return;
						} else if (jogador.getTentativas() == 0) {
							desenharForca(jogador.getTentativas());
							System.out.println("Você perdeu todas as suas tentativas! Fim de jogo para você.");
						}
					} else {
						System.out.println("Digito errado");
					}

				}
			}
		}

		System.out.println("\n\nTodos os jogadores esgotaram suas tentativas. Jogo encerrado!");
	}

	private boolean atualizarPalavra(char letra, String palavra, char[] palavraAdivinhada) {

		boolean letraAdivinhada = false;

		// recebe a letra digitada, percorre a palavra e verifica se a letra
		// existe na palavra. Se sim, revela a letra
		for (int i = 0; i < palavra.length(); i++) {
			if (palavra.charAt(i) == letra) {
				palavraAdivinhada[i] = letra;
				letraAdivinhada = true;
			}
		}
		return letraAdivinhada;
	}

	private void adivinhaPalavra(Jogador jogador, char[] palavraAdivinhada) {

		while (true) {

			System.out.print("Digite a palavra: ");
			String tentativa = scan.nextLine().toUpperCase();

			// verificando se a palavra recebida é igual a palavra secreta
			if (tentativa.equalsIgnoreCase(palavra)) {

				System.out.println("\nParabéns, o jogador " + jogador.getNome() + " adivinhou a palavra!");
				System.out.println("\nPalavra: " + palavra);
				System.out.println("\nO jogo será encerrado!");
				System.out.println("Obrigado por jogar. :)");
				System.exit(0);

			} else {
				System.out.println("Palavra incorreta. Continue tentando!");
				jogador.diminuiTentativas();
				break;
			}

		}
	}

	// Desenhando a forca
	// passei como parametro as tentativas pois assim estara
	// conectado com o numero de chances que cada jogador ainda tem

	private void desenharForca(int tentativas) {
		switch (tentativas) {
		case 5:
			System.out.println("  ____");
			System.out.println(" |    |");
			System.out.println(" |");
			System.out.println(" |");
			System.out.println(" |");
			System.out.println(" |");
			System.out.println("_|__________");
			break;
		case 4:
			System.out.println("  ____");
			System.out.println(" |    |");
			System.out.println(" |    O");
			System.out.println(" |");
			System.out.println(" |");
			System.out.println(" |");
			System.out.println("_|__________");
			break;
		case 3:
			System.out.println("  ____");
			System.out.println(" |    |");
			System.out.println(" |    O");
			System.out.println(" |    |");
			System.out.println(" |");
			System.out.println(" |");
			System.out.println("_|__________");
			break;
		case 2:
			System.out.println("  ____");
			System.out.println(" |    |");
			System.out.println(" |    O");
			System.out.println(" |   /|\\");
			System.out.println(" |");
			System.out.println(" |");
			System.out.println("_|__________");
			break;
		case 1:
			System.out.println("  ____");
			System.out.println(" |    |");
			System.out.println(" |    O");
			System.out.println(" |   /|\\");
			System.out.println(" |   / \\");
			System.out.println(" |");
			System.out.println("_|__________");
			break;
		case 0:
			System.out.println("  ____");
			System.out.println(" |    |");
			System.out.println(" |    O");
			System.out.println(" |");
			System.out.println(" |   /|\\");
			System.out.println(" |   / \\");
			System.out.println("_|__________");
			break;
		}
	}
}
