package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();

		while (!chessMatch.getCheckMate()) {
			try {
				UI.clearScreen();
				UI.printMatch(chessMatch, captured);
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc);

				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc);

				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
			
				if (capturedPiece != null) {
					captured.add(capturedPiece);
				}
				
				if(chessMatch.getPromoted()!=null) {
					System.out.println("Seu peao sera promovido!");
					System.out.print("Entre com a peca para promocao (B Bispo/C Cavalo/T Torre/R Rainha): ");
					String type = sc.nextLine().toUpperCase();
					while(!type.equals("B") && !type.equals("C") &&!type.equals("R") &&!type.equals("T")) {
						System.out.print("Entrada Invalida!\nEntre com a peca para promocao (B Bispo/C Cavalo/T Torre/R Rainha): ");
						type = sc.nextLine().toUpperCase();
					}
					chessMatch.replacePromotedPiece(type);
				}
			} 
			catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} 
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		
		UI.clearScreen();
		UI.printMatch(chessMatch, captured);
	}
}