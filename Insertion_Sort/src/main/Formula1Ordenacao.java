package main;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class Volta {
    String data_hora;
    String piloto;
    String equipe;
    double tempo;  

    Volta(String dataHora, String piloto, String equipe, String tempoStr) {
        this.data_hora = dataHora;
        this.piloto = piloto;
        this.equipe = equipe;
        this.tempo = parseTempo(tempoStr);
    }

    private double parseTempo(String tempoStr) {
        String[] partes = tempoStr.split(":");
        int minutos = Integer.parseInt(partes[0]);
        double segundos = Double.parseDouble(partes[1]);
        return minutos * 60 + segundos;
    }

    public String toString() {
        return data_hora + " - " + piloto + " - " + equipe + " - " + formatarTempo();
    }

    private String formatarTempo() {
        int minutos = (int) (tempo / 60);
        double segundos = tempo % 60;
        return String.format("%02d:%06.3f", minutos, segundos);
    }
}

public class Formula1Ordenacao {
    public static void main(String[] args) {
        Volta[] voltas = new Volta[10000];
        String caminhoArquivo = "C:\\\\Users\\\\victt\\\\Downloads\\\\Formula1_InsertionSort.txt";
        int contador = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo));
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(" - ");
                String dataHora = partes[0];
                String piloto = partes[1];
                String equipe = partes[2];
                String tempo = partes[3];
                voltas[contador] = new Volta(dataHora, piloto, equipe, tempo);
                contador++;
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        for (int i = 1; i < contador; i++) {
            Volta chave = voltas[i];
            int j = i - 1;
            while (j >= 0 && (voltas[j].tempo > chave.tempo || 
                (voltas[j].tempo == chave.tempo && voltas[j].piloto.compareTo(chave.piloto) > 0))) {
                voltas[j + 1] = voltas[j];
                j = j - 1;
            }
            voltas[j + 1] = chave;
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("Formula1_Ordenado.txt"));
            for (int i = 0; i < contador; i++) {
                bw.write(voltas[i].toString());
                bw.newLine();
            }
            bw.close();
            System.out.println("Arquivo ordenado salvo com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }
}
