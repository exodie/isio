import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CustomTerminal {
    private static String currentDirectory = System.getProperty("user.dir");

    private static final Map<String, CommandHandler> commandHandlers = new HashMap<>();

    static {
        commandHandlers.put("find", CustomTerminal::findHandler);
        commandHandlers.put("kill", CustomTerminal::killHandler);
        commandHandlers.put("ps", CustomTerminal::psHandler);
        commandHandlers.put("ls", CustomTerminal::lsHandler);
        commandHandlers.put("mkdir", CustomTerminal::mkdirHandler);
        commandHandlers.put("cd", CustomTerminal::cdHandler);
        commandHandlers.put("pwd", CustomTerminal::pwdHandler);
        commandHandlers.put("echo", CustomTerminal::echoHandler);
        commandHandlers.put("clear", CustomTerminal::clearHandler);
        commandHandlers.put("exit", CustomTerminal::exitHandler);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(currentDirectory + "> ");
            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            String commandName = parts[0];

            CommandHandler handler = commandHandlers.get(commandName);
            if (handler != null) {
                handler.handle(parts);
            } else {
                System.out.println("Unknown command: " + commandName);
            }
        }
    }

    private interface CommandHandler {
        void handle(String[] args);
    }

    private static void findHandler(String[] args) {
        System.out.println("Searching files and directories...");
        // Здесь можно добавить логику для поиска файлов и папок в файловой системе
    }

    private static void killHandler(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: kill <process_id>");
            return;
        }
        int processId = Integer.parseInt(args[1]);
        System.out.println("Killing process with ID " + processId + "...");
        // Здесь можно добавить логику для завершения процесса с указанным идентификатором
    }

    private static void psHandler(String[] args) {
        System.out.println("Listing processes...");
        // Здесь можно добавить логику для вывода списка активных процессов
    }

    private static void lsHandler(String[] args) {
        String directoryPath = args.length > 1 ? args[1] : currentDirectory;
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            System.out.println("Listing files and directories in " + directoryPath + ":");
            File[] files = directory.listFiles();
            for (File file : files) {
                System.out.println(file.getName());
            }
        } else {
            System.out.println("Directory " + directoryPath + " not found.");
        }
    }

    private static void mkdirHandler(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: mkdir <directory_name>");
            return;
        }
        String newDirectoryName = args[1];
        File newDirectory = new File(newDirectoryName);
        if (!newDirectory.exists()) {
            boolean created = newDirectory.mkdir();
            if (created) {
                System.out.println("Directory " + newDirectoryName + " created successfully.");
            } else {
                System.out.println("Failed to create directory " + newDirectoryName + ".");
            }
        } else {
            System.out.println("Directory " + newDirectoryName + " already exists.");
        }
    }

    private static void cdHandler(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: cd <directory_path>");
            return;
        }
        String targetDirectory = args[1];
        File newDir = new File(targetDirectory);
        if (newDir.exists() && newDir.isDirectory()) {
            currentDirectory = newDir.getAbsolutePath();
            System.out.println("Changed directory to " + currentDirectory);
        } else {
            System.out.println("Directory " + targetDirectory + " not found.");
        }
    }

    private static void pwdHandler(String[] args) {
        System.out.println("Current directory: " + currentDirectory);
    }

    private static void echoHandler(String[] args) {
        String message = String.join(" ", args, 1, args.length);
        System.out.println(message);
    }

    private static void clearHandler(String[] args) {
        clearScreen();
    }

    private static void exitHandler(String[] args) {
        System.exit(0);
    }

    private static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
