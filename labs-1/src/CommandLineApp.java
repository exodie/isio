import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class CommandLineApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProcessManager processManager = new ProcessManager();

        while (true) {
            System.out.print(">> ");
            String input = scanner.nextLine().trim(); // Читаем ввод пользователя и удаляем лишние пробелы

            // Разбиваем ввод на команду и аргументы
            String[] tokens = input.split("\\s+");
            String command = tokens[0];
            String[] arguments = new String[tokens.length - 1];
            System.arraycopy(tokens, 1, arguments, 0, tokens.length - 1);

            // Обработка команд
            switch (command) {
                case "help":
                    System.out.println("Список команд:");
                    System.out.println("ps - Показать запущенные процессы");
                    System.out.println("threads - Показать запущенные потоки");
                    System.out.println("kill <PID> - Завершить процесс по его идентификатору");
                    System.out.println("find <filename> - Поиск файла в текущей директории и её поддиректориях");
                    System.out.println("top - Вывести информацию о загрузке процессора");
                    System.out.println("start command - Запустить новый процесс с указанной командой");
                    System.out.println("threads-count PID - Вывести количество потоков для указанного процесса");
                    System.out.println("vmstat - Отобразить нагрузу ОЗУ и ЦПУ.");
                    System.out.println("thread-info PID - Вывести информацию о потоках для указанного процесса");
                    System.out.println("exit - Завершить приложение");
                    break;
                case "ps":
                    processManager.showProcesses();
                    break;
                case "threads":
                    processManager.showThreads();
                    break;
                case "kill":
                    if (arguments.length > 0) {
                        int pid = Integer.parseInt(arguments[0]);
                        processManager.killProcess(pid);
                    } else {
                        System.out.println("Неверное количество аргументов для команды kill");
                    }
                    break;
                case "find":
                    if (arguments.length > 0) {
                        processManager.findFile(arguments[0]);
                    } else {
                        System.out.println("Неверное количество аргументов для команды find");
                    }
                    break;
                case "top":
                    processManager.showCPUUsage();
                    break;
                case "start":
                    if (arguments.length > 0) {
                        processManager.startNewProcess(String.join(" ", arguments));
                    } else {
                        System.out.println("Неверное количество аргументов для команды start");
                    }
                    break;
                case "threads-count":
                    if (arguments.length > 0) {
                        int pid = Integer.parseInt(arguments[0]);
                        processManager.showThreadCount(pid);
                    } else {
                        System.out.println("Неверное количество аргументов для команды threads-count");
                    }
                    break;
                case "vmstat":
                    processManager.showVmStat();
                    break;
                case "thread-info":
                    if (arguments.length > 0) {
                        int pid = Integer.parseInt(arguments[0]);
                        processManager.showThreadInfo(pid);
                    } else {
                        System.out.println("Неверное количество аргументов для команды thread-info");
                    }
                    break;
                case "exit":
                    scanner.close();
                    System.out.println("Выход из приложения.");
                    return;
                default:
                    System.out.println("Команда не распознана. Введите 'help' для просмотра списка команд.");
            }
        }
    }
}

class ProcessManager {
    public void showProcesses() {
        try {
            Process process = Runtime.getRuntime().exec("ps -e");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showThreads() {
        try {
            Process process = Runtime.getRuntime().exec("ps -eLf");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void killProcess(int pid) {
        try {
            Runtime.getRuntime().exec("kill " + pid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findFile(String filename) {
        try {
            Process process = Runtime.getRuntime().exec("find . -name " + filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCPUUsage() {
        try {
            Process process = Runtime.getRuntime().exec("top -b -n 1");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startNewProcess(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showThreadCount(int pid) {
        try {
            Process process = Runtime.getRuntime().exec("ps -T -p " + pid);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showVmStat() {
        try {
            Process process = Runtime.getRuntime().exec("vmstat");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showThreadInfo(int pid) {
        try {
            Process process = Runtime.getRuntime().exec("ps -L -p " + pid);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}