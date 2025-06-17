import java.util.Arrays;
import java.util.List;

public class ReductionPat {
    public static void main(String[] args) {
        List<Integer> numeros = Arrays.asList(6, 25, 31, 14, 5); // Lista de valores

        // Reduce en paralelo con suma
        int suma = numeros.parallelStream()
                          .reduce(0, Integer::sum); // Eficienta la memoria

        System.out.println("Suma total (Reduccion): " + suma);
    }
}