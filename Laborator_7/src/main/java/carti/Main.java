package carti;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
public class Main {
    public static void main(String[] args) throws IOException {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Map<String, Object>>>(){}.getType();


        Map<Integer, Carte> cartiMap = new HashMap<>();
        try (FileReader reader = new FileReader("carti.json")) {
            List<Map<String, Object>> books = gson.fromJson(reader, listType);
            for (Map<String, Object> book : books) {
                Integer id = ((Double) book.get("id")).intValue();
                String titlu = (String) book.get("titlu");
                String autor = (String) book.get("autor");
                Integer anulAparitiei = ((Double) book.get("anulAparitiei")).intValue();
                cartiMap.put(id, new Carte(titlu, autor, anulAparitiei));
            }
        }

        // 1. Afisare colectie
        System.out.println("Colectia initiala:");
        cartiMap.forEach((id, carte) -> System.out.println("ID: " + id + ", Carte: " + carte));

        // 2. Stergerea unei carti
        cartiMap.remove(1); // stergerea unei carti care are id 1
        System.out.println("\nDupa stergere:");
        cartiMap.forEach((id, carte) -> System.out.println("ID: " + id + ", Carte: " + carte));

        // 3. Adaugarea unei carti
        cartiMap.putIfAbsent(3, new Carte("21 de lectii", "Andrei", 2014));
        System.out.println("\nDupa adaugarea unei carti noi:");
        cartiMap.forEach((id, carte) -> System.out.println("ID: " + id + ", Carte: " + carte));

        // 4. Salvare modificari
        try (FileWriter writer = new FileWriter("carti_modified.json")) {
            gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(cartiMap, writer);
        }

        // 5. Crearea unui set de carti
        Set<Carte> Mihai_carti = cartiMap.values().stream()
                .filter(carte -> "Mihai".equals(carte.autor()))
                .collect(Collectors.toSet());
        System.out.println("\nCarti de Mihai:");
        Mihai_carti.forEach(System.out::println);

        // 6. Sortare dupa titlu
        System.out.println("\nCarti sortate dupa titlu:");
        Mihai_carti.stream()
                .sorted(Comparator.comparing(Carte::titlu))
                .forEach(System.out::println);

        // 7. Afisare carti vechi
        Optional<Carte> carti_vechi = Mihai_carti.stream()
                .min(Comparator.comparingInt(Carte::anulAparitiei));
        carti_vechi.ifPresent(carte -> System.out.println("\nCarte veche: " + carte));
    }
}
