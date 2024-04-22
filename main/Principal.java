package main;

import com.google.gson.Gson;
import main.Conversion;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner intInput = new Scanner(System.in);
        Scanner valorConvertir = new Scanner(System.in);
        Scanner intentarInput = new Scanner(System.in);

        String name = "Francisco";
        int bucle = 1;

        while (bucle == 1) {
            System.out.println("**************************************");
            System.out.println(name + " Bienvenido al conversor de monedas");
            System.out.println("");
            System.out.println("1) Dólar ==> Peso Argentino ");
            System.out.println("2) Peso Argentino ==> Dólar ");
            System.out.println("3) Dólar ==> Peso Colombiano ");
            System.out.println("4) Colombiano ==> Dólar ");
            System.out.println("5) Dólar ==> Peso Chileno ");
            System.out.println("6) Peso Chileno ==> Dólar ");
            System.out.println("7) Salir. ");
            System.out.println("Ingresa el numero a seleccionar:");
            System.out.println("***************************************");
            int opcion = intInput.nextInt();

            double valorInput = 0;
            try {
                String codigo_1 = "";
                String codigo_2 = "";
                double conversionRate = 0;

                switch (opcion) {
                    case 1:
                        codigo_1 = "USD";
                        codigo_2 = "ARS";
                        break;
                    case 2:
                        codigo_1 = "ARS";
                        codigo_2 = "USD";
                        break;
                    case 3:
                        codigo_1 = "USD";
                        codigo_2 = "COP";
                        break;
                    case 4:
                        codigo_1 = "COP";
                        codigo_2 = "USD";
                        break;
                    case 5:
                        codigo_1 = "USD";
                        codigo_2 = "CLP";
                        break;
                    case 6:
                        codigo_1 = "CLP";
                        codigo_2 = "USD";
                        break;
                    case 7:
                        System.out.println("Hasta luego, gracias por usar nuestro programa...");
                        bucle = 0;
                        break;
                    default:
                        System.out.println("El dato ingresado es incorrecto, vuelva a intentarlo..");
                }

                if (opcion >= 1 && opcion <= 6) {
                    System.out.println("Ingrese el valor a convertir:");
                    valorInput = valorConvertir.nextDouble();

                    HttpClient client = HttpClient.newHttpClient();
                    String apiUrl = "https://v6.exchangerate-api.com/v6/3926484fdfc70227e859f54c/pair/" + codigo_1 + "/" + codigo_2 + "/" + valorInput;
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(apiUrl))
                            .build();

                    HttpResponse<String> response = client
                            .send(request, HttpResponse.BodyHandlers.ofString());

                    String json = response.body();

                    Gson gson = new Gson();
                    Conversion conversion = gson.fromJson(json, Conversion.class);

                    conversionRate = conversion.conversion_rate();
                    double conversionResult = conversion.conversion_result();

                    System.out.println("Resultado de la conversión: " + conversionResult + " " + codigo_2);
                }

                System.out.println("**************************************");
                System.out.println();

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println("Desea realizar otra conversión? (1: Sí / 0: No)");

            bucle = intentarInput.nextInt();
            if(bucle == 0){
                System.out.println("Gracias por utilizar nuestro programa. ");
            }

        }
    }
}
