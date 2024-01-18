import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Animal {
    private String name;
    private String type;
    private List<String> commands;
    private  Date birthdate;

    public Animal(String name, String type) {
        this.name = name;
        this.type = type;
        this.commands = new ArrayList<>();
    }

    public void addCommand(String command) {
        commands.add(command);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public List<String> getCommands() {
        return commands;
    }
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Date getBirthdate() {
        return birthdate;
    }

}

class Pet extends Animal {
    public Pet(String name, String type) {
        super(name, type);
    }
}

class LivestockAnimal extends Animal {
    public LivestockAnimal(String name, String type) {
        super(name, type);
    }
}

class Cat extends Pet {
    public Cat(String name) {
        super(name, "кошка");
    }
}

class Dog extends Pet {
    public Dog(String name) {
        super(name, "собака");
    }
}

class Hamster extends Pet {
    public Hamster(String name) {
        super(name, "хомяк");
    }
}

class Horse extends LivestockAnimal {
    public Horse(String name) {
        super(name, "лошадь");
    }
}

class Donkey extends LivestockAnimal {
    public Donkey(String name) {
        super(name, "осел");
    }
}

class AnimalRegistry {
    private Map<String, Animal> animalMap;

    public AnimalRegistry() {
        this.animalMap = new HashMap<>();
    }

    public void addAnimal(String name, String type) {
        Animal animal;

        switch (type) {
            case "кошка":
                animal = new Cat(name);
                break;
            case "собака":
                animal = new Dog(name);
                break;
            case "хомяк":
                animal = new Hamster(name);
                break;
            case "лошадь":
                animal = new Horse(name);
                break;
            case "осел":
                animal = new Donkey(name);
                break;
            default:
                System.out.println("Некорректный тип животного!");
                return;
        }

        animalMap.put(name, animal);
        System.out.println("Животное успешно добавлено в реестр!");
    }

    public void printAnimalsByBirthdate() {
        List<Animal> sortedAnimals = new ArrayList<>(animalMap.values());

        Collections.sort(sortedAnimals, new Comparator<Animal>() {
            @Override
            public int compare(Animal animal1, Animal animal2) {
                if (animal1.getBirthdate() == null || animal2.getBirthdate() == null)
                    return 0;

                return animal1.getBirthdate().compareTo(animal2.getBirthdate());
            }
        });

        for (Animal animal : sortedAnimals) {
            System.out.println("Имя: " + animal.getName() + ", Тип: " + animal.getType() + ", Дата рождения: " + animal.getBirthdate());
        }
    }

    public List<String> getAllAnimals() {
        return new ArrayList<>(animalMap.keySet());

    }

    public void updateAnimal(String name, String type) {
        if (animalMap.containsKey(name)) {
            Animal animal = animalMap.get(name);
            animalMap.remove(name);

            switch (type) {
                case "кошка":
                    animal = new Cat(name);
                    break;
                case "собака":
                    animal = new Dog(name);
                    break;
                case "хомяк":
                    animal = new Hamster(name);
                    break;
                case "лошадь":
                    animal = new Horse(name);
                    break;
                case "осел":
                    animal = new Donkey(name);
                    break;
                default:
                    System.out.println("Некорректный тип животного!");
                    return;
            }

            animalMap.put(name, animal);
            System.out.println("Данные о животном успешно обновлены!");
        } else {
            System.out.println("Животного с таким именем не существует!");
        }
    }

    public void trainAnimal(String name, String command) {
        if (animalMap.containsKey(name)) {
            Animal animal = animalMap.get(name);
            animal.addCommand(command);
            System.out.println("Животное успешно обучено новой команде!");
        } else {
            System.out.println("Животного с таким именем не существует!");
        }
    }
    public Animal getAnimal(String name) {
        return animalMap.get(name);
    }

    public void deleteAnimal(String name) {
        if (animalMap.containsKey(name)) {
            animalMap.remove(name);
            System.out.println("Животное успешно удалено из реестра!");
        } else {
            System.out.println("Животного с таким именем не существует!");
        }
    }
    public void addBirthdate(String name, Date birthdate) {
        if (animalMap.containsKey(name)) {
            Animal animal = animalMap.get(name);
            animal.setBirthdate(birthdate);
        } else {
            System.out.println("Животное с именем " + name + " не найдено!");
        }
    }

    public void saveToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Animal animal : animalMap.values()) {
                writer.write("Имя: " + animal.getName() + "\n");
                writer.write("Тип: " + animal.getType() + "\n");
                writer.write("Дата рождения: " + animal.getBirthdate() + "\n");
                writer.write("Команды: " + String.join(", ", animal.getCommands()) + "\n");
                writer.write("\n");
            }
            System.out.println("Данные сохранены в файл: " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении данных в файл: " + e.getMessage());
        }
    }
}

class AnimalRegistryMenu {
    private AnimalRegistry animalRegistry;
    private Scanner scanner;

    public AnimalRegistryMenu() {
        this.animalRegistry = new AnimalRegistry();
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() throws ParseException {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Меню реестра животных ===");
            System.out.println("1. Список всех животных");
            System.out.println("2. Завести новое животное");
            System.out.println("3. Изменить данные о животном");
            System.out.println("4. Что умеет животное");
            System.out.println("5. Дрессировка животного");
            System.out.println("6. Удалить запись о животном");
            System.out.println("7. Сохранить в файл");
            System.out.println("8. Выход");
            System.out.println("9. Добавить дату рождения животному");
            System.out.println("10. Вывести животных по дате рождения");

            System.out.print("Введите номер пункта меню: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayAllAnimals();
                    break;
                case 2:
                    addNewAnimal();
                    break;
                case 3:
                    updateAnimal();
                    break;
                case 4:
                    displayAnimalCommands();
                    break;
                case 5:
                    trainAnimal();
                    break;
                case 6:
                    deleteAnimal();
                    break;
                case 7:
                    saveToFile();
                    break;
                case 8:
                    exit = true;
                    break;
                case 9:
                    addBirthdate();
                    break;
                case 10:
                    animalRegistry.printAnimalsByBirthdate();
                    break;
                default:
                    System.out.println("Некорректный номер пункта меню!");
            }
        }
    }

    private void displayAllAnimals() {
        List<String> animals = animalRegistry.getAllAnimals();
        System.out.println("\n=== Список всех животных ===");
        for (String animal : animals) {
            System.out.println(animal);
        }
    }

    private void addNewAnimal() {
        System.out.println("\n=== Завести новое животное ===");
        System.out.print("Введите имя животного: ");
        String name = scanner.next();
        System.out.print("Введите тип животного (кошка, собака, хомяк, лошадь, осел): ");
        String type = scanner.next();

        animalRegistry.addAnimal(name, type);
    }

    private void updateAnimal() {
        System.out.println("\n=== Изменить данные о животном ===");
        System.out.print("Введите имя животного: ");
        String name = scanner.next();
        System.out.print("Введите новый тип животного (кошка, собака, хомяк, лошадь, осел): ");
        String type = scanner.next();

        animalRegistry.updateAnimal(name, type);
    }

    private void displayAnimalCommands() {
        System.out.println("\n=== Что умеет животное ===");
        System.out.print("Введите имя животного: ");
        String name = scanner.next();  // Use 'next()' instead of 'nextLine()' to correctly read the input.

        Animal animal = animalRegistry.getAnimal(name);  // This method should be added in AnimalRegistry to retrieve a specific animal.
        if (animal != null) {
            List<String> commands = animal.getCommands();

            System.out.println("\n=== Команды животного " + name + " ===");
            for (String command : commands) {
                System.out.println(command);
            }
        } else {
            System.out.println("Животного с таким именем не существует!");
        }
    }

    private void trainAnimal() {
        System.out.println("\n=== Дрессировка животного ===");
        System.out.print("Введите имя животного: ");
        String name = scanner.next();
        System.out.print("Введите новую команду для животного: ");
        String command = scanner.next();

        animalRegistry.trainAnimal(name, command);
    }

    private void deleteAnimal() {
        System.out.println("\n=== Удалить запись о животном ===");
        System.out.print("Введите имя животного: ");
        String name = scanner.next();

        animalRegistry.deleteAnimal(name);
    }

    private void addBirthdate() throws ParseException {
        System.out.println("\n=== Добавить дату рождения животному ===");
        System.out.print("Введите имя животного: ");
        String name = scanner.next();
        System.out.print("Введите дату рождения животного в формате (dd.MM.yyyy): ");
        String dateInput = scanner.next();
        System.out.print("Дата рождения животному добавлена: ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = dateFormat.parse(dateInput);
        animalRegistry.addBirthdate(name, date);
    }
    private void saveToFile() {
        System.out.print("Введите имя файла для сохранения данных: ");
        String fileName = scanner.next();

        animalRegistry.saveToFile(fileName);
    }
}

public class AnimalRegistryApp {
    public static void main(String[] args) throws ParseException {
        AnimalRegistryMenu menu = new AnimalRegistryMenu();
        menu.displayMenu();
    }
}