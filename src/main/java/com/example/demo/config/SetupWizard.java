package com.example.demo.config;


import com.example.demo.Main;
import com.example.demo.entities.*;
import com.example.demo.entities.enums.ProjectStatus;
import com.example.demo.entities.enums.TaskStatus;
import com.example.demo.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
@Slf4j
public class SetupWizard implements CommandLineRunner, ApplicationContextAware {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final AccountRepository accountRepository;
    private final DepartmentRepository departmentRepository;
    private final PersonaRepository personaRepository;
    private final ProjectRepository projectRepository;
    private final TeamRepository teamRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;
    private final Environment environment;


    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();

    private ConfigurableApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = (ConfigurableApplicationContext) applicationContext;
    }

    @Override
    public void run(String... args) throws Exception {
        // Controlla se è il primo avvio
        boolean isFirstRun = isFirstRun();

        // Se vengono passati argomenti direttamente via CLI, gestiscili subito
        if (args.length > 0) {
            handleCommandLineArgs(args);
            return;
        }

        // Se è il primo avvio, avvia il setup wizard
        if (isFirstRun) {
            log.info("🚀 Benvenuto! Sembra essere il primo avvio dell'applicazione.");
            runSetupWizard();
        }
        log.info("✅ Applicazione avviata correttamente. Dati già presenti.");
        while (true) {
            System.out.println("\nDigita un comando CLI (ad es. --help, --generate-data 10, --reset, --restart, --exit),");
            System.out.println("oppure premi Invio per non fare nulla e rivedere nuovamente questo prompt:");
            System.out.print("> ");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                // L’utente ha premuto solo Invio: torna a chiedere di nuovo
                continue;
            }
            // Se l’utente ha digitato qualcosa, scompongo in token e gestisco
            String[] userArgs = line.split("\\s+");
            String cmd = userArgs[0].toLowerCase();

            // Se è exit, esco subito dal ciclo (e chiudo l’app)
            if (cmd.equals("--exit") || cmd.equals("-e") || cmd.equals("exit")) {
                System.out.println("Uscita dall’applicazione...");
                context.close();
                System.exit(0);
                return;
            }
            handleCommandLineArgs(userArgs);
            // Dopo aver eseguito il comando, ritorno al prompt
            // (per esempio, se era --generate-data, l’ho già eseguito, adesso rido il prompt)
        }
    }

    private boolean isFirstRun() {
        return personaRepository.count() == 0 || accountRepository.count() == 0;
    }

    private void handleCommandLineArgs(String[] args) {
        String command = args[0].toLowerCase();

        switch (command) {
            case "--setup":
            case "-s":
                runSetupWizard();
                break;
            case "--generate-data":
            case "-g":
                int records = args.length > 1 ? Integer.parseInt(args[1]) : 10;
                generateSampleData(records);
                break;
            case "--reset":
                resetDatabase();
                break;
            case "--help":
            case "-h":
                printHelp();
                break;
            case "--exit":
            case "-e":
                System.out.println("Uscita dall’applicazione...");
                context.close();
                System.exit(0);
                break;
            case "--restart": // solo per contesto developer, da implementare script esterno in futuro per evitare problemi di riavvio e loop
            case "-r":
                System.out.println("Riavvio dell’applicazione in corso...");
                // Avviamo un nuovo thread che chiude l’attuale context e ne crea uno nuovo
                new Thread(() -> {
                    try {
                        // Piccolo ritardo per far terminare correttamente eventuali log in corso
                        Thread.sleep(500);
                    } catch (InterruptedException ignored) {
                    }
                    // Chiudi il context corrente
                    context.close();
                    // Riavvia una nuova istanza dell’applicazione
                    SpringApplication app = new SpringApplication(Main.class);
                    app.run();
                }).start();
                System.exit(0);
                break;
            default:
                log.warn("Comando non riconosciuto: {}", command);
                printHelp();
        }
    }

    private void runSetupWizard() {
        try {
            printWelcome();

            // Step 1: Configurazione base
            log.info("\n=== STEP 1: Configurazione di Base ===");
            initializePermissions();
            initializeRoles();

            // Step 2: Account amministratore
            log.info("\n=== STEP 2: Creazione Account Amministratore ===");
            createAdminAccount();

            // Step 3: Configurazione azienda
            log.info("\n=== STEP 3: Configurazione Azienda ===");
            int departmentCount = askForNumber("Quanti dipartimenti vuoi creare?", 1, 20, 4);
            createDepartments(departmentCount);

            // Step 4: Dati di esempio
            log.info("\n=== STEP 4: Dati di Esempio ===");
            boolean generateSample = askYesNo("Vuoi generare dati di esempio? (utenti, progetti, task, etc..)");

            if (generateSample) {
                int userCount = askForNumber("Quanti utenti di esempio?", 1, 100, 10);
                int projectCount = askForNumber("Quanti progetti di esempio?", 1, 50, 5);
                int taskCount = askForNumber("Quanti task di esempio?", 1, 200, 20);

                generateUsers(userCount);
                generateProjects(projectCount);
                generateTasks(taskCount);
            }

            printSetupComplete();

        } catch (Exception e) {
            log.error("❌ Errore durante il setup", e);
            System.out.println("Setup interrotto a causa di un errore. Controlla i log per i dettagli.");
        }
    }

    private void printWelcome() {
        System.out.println("\n" +
                "╔══════════════════════════════════════════════════════════════╗\n" +
                "║               🏢 AZIENDA MANAGEMENT SYSTEM                   ║\n" +
                "║                         Setup Wizard                         ║\n" +
                "╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("Questo wizard ti guiderà nella configurazione iniziale del sistema.\n");
    }

    @Transactional
    private void createAdminAccount() {
        System.out.println("📝 Configurazione account amministratore:");

        System.out.print("Username amministratore [admin]: ");
        String username = scanner.nextLine().trim();
        if (username.isEmpty()) username = "admin";

        System.out.print("Password amministratore [admin123]: ");
        String password = scanner.nextLine().trim();
        if (password.isEmpty()) password = "admin123";

        System.out.print("Email amministratore [admin@azienda.com]: ");
        String email = scanner.nextLine().trim();
        if (email.isEmpty()) email = "admin@azienda.com";

        Department defaultDept;
        if (departmentRepository.count() == 0) {
            defaultDept = Department.builder()
                    .name("Admin Department")
                    .location("Milano")
                    .createdAt(Instant.now())
                    .build();
            defaultDept = departmentRepository.save(defaultDept);
            log.info("✅ Dipartimento predefinito creato per l'admin: {}", defaultDept.getName());
        } else {
            defaultDept = departmentRepository.findAll().iterator().next();
        }
        //Creazione della Persona associata all'admin
        Persona adminPersona = Persona.builder()
                .firstName("Admin")
                .lastName("System")
                .email(email)
                .department(defaultDept)
                .hireDate(LocalDate.now())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        adminPersona = personaRepository.save(adminPersona);
        //Creazione dell'account admin
        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new IllegalStateException("Ruolo ADMIN non trovato"));
        Account adminAccount = Account.builder()
                .username(username)
                .passwordHash(passwordEncoder.encode(password))
                .emailVerified(true)
                .enabled(true)
                .failedAttempts(0)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .persona(adminPersona)
                .roles(Collections.singleton(adminRole))
                .build();
        accountRepository.save(adminAccount);
        log.info("✅ Account amministratore creato: {}", username);
    }

    @Transactional
    private void createDepartments(int count) {
        String[] defaultDepts = {"IT Development", "Marketing", "Human Resources", "Finance", "Operations", "Sales"};
        System.out.println("📋 Creazione dipartimenti:");
        for (int i = 0; i < count; i++) {
            String defaultName = i < defaultDepts.length ? defaultDepts[i] : "Dipartimento " + (i + 1);
            System.out.printf("Nome dipartimento %d [%s]: ", i + 1, defaultName);
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) name = defaultName;
            System.out.printf("Sede dipartimento %d [Milano]: ", i + 1);
            String location = scanner.nextLine().trim();
            if (location.isEmpty()) location = "Milano";
            Department dept = Department.builder()
                    .name(name)
                    .location(location)
                    .createdAt(Instant.now())
                    .build();
            departmentRepository.save(dept);
            log.info("✅ Dipartimento creato: {} - {}", name, location);
        }
    }

    private int askForNumber(String question, int min, int max, int defaultValue) {
        while (true) {
            System.out.printf("%s [%d]: ", question, defaultValue);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                return defaultValue;
            }
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("⚠️  Il valore deve essere tra %d e %d\n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("⚠️  Inserisci un numero valido");
            }
        }
    }

    private boolean askYesNo(String question) {
        while (true) {
            System.out.printf("%s [S/n]: ", question);
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.isEmpty() || input.equals("s") || input.equals("si") || input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            }

            System.out.println("⚠️  Rispondi con S (sì) o N (no)");
        }
    }

    // Metodi per generazione dati di esempio
    private void generateUsers(int count) {
        log.info("👥 Generazione {} utenti...", count);
        //Recupero tutti i dipartimenti e il ruolo USER
        List<Department> departments = departmentRepository.findAll();
        if (departments.isEmpty()) {
            log.warn("❌ Nessun dipartimento trovato. Creane almeno uno prima di generare utenti.");
            return;
        }
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("Ruolo USER non trovato"));
        for (int i = 1; i <= count; i++) {
            String firstName = "User" + i;
            String lastName = "Test" + i;
            String email = "user" + i + "@example.it";

            Department assignedDept = departments.get((i - 1) % departments.size());

            Persona persona = Persona.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .department(assignedDept)
                    .hireDate(LocalDate.now().minusDays(random.nextInt(365)))
                    .createdAt(Instant.now())
                    .updatedAt(Instant.now())
                    .build();
            persona = personaRepository.save(persona);

            String username = "user" + i;
            String rawPassword = "password" + i;
            Account account = Account.builder()
                    .username(username)
                    .passwordHash(passwordEncoder.encode(rawPassword))
                    .emailVerified(false)
                    .enabled(true)
                    .failedAttempts(0)
                    .createdAt(Instant.now())
                    .updatedAt(Instant.now())
                    .persona(persona)
                    .roles(Collections.singleton(userRole))
                    .build();
            accountRepository.save(account);

            log.info("✅ Utente creato: {} (persona ID: {})", username, persona.getId());
        }
    }

    private void generateProjects(int count) {
        log.info("📁 Generazione {} progetti...", count);

        for (int i = 1; i <= count; i++) {
            String code = "PRJ" + String.format("%03d", i);
            String name = "Project " + i;
            String description = "Descrizione generica per " + name;
            LocalDate startDate = LocalDate.now().minusDays(random.nextInt(30));
            LocalDate endDate = startDate.plusDays(30 + random.nextInt(120));

            Project project = Project.builder()
                    .code(code)
                    .name(name)
                    .description(description)
                    .startDate(startDate)
                    .endDate(endDate)
                    .status(ProjectStatus.PLANNED)
                    .createdAt(Instant.now())
                    .build();
            projectRepository.save(project);

            log.info("✅ Progetto creato: {} - {}", code, name);
        }
    }

    private void generateTasks(int count) {
        log.info("📋 Generazione {} task...", count);

        List<Persona> personas = personaRepository.findAll();
        List<Project> projects = projectRepository.findAll();

        if (personas.isEmpty() || projects.isEmpty()) {
            log.warn("❌ Impossibile generare task: mancano persone o progetti.");
            return;
        }

        for (int i = 1; i <= count; i++) {
            Project project = projects.get((i - 1) % projects.size());
            Persona assignee = personas.get(random.nextInt(personas.size()));

            String title = "Task " + i + " per " + project.getCode();
            String description = "Descrizione per " + title;
            LocalDate dueDate = project.getStartDate().plusDays(1 + random.nextInt(60));

            Task task = Task.builder()
                    .title(title)
                    .description(description)
                    .project(project)
                    .assignedTo(assignee)
                    .dueDate(dueDate)
                    .status(TaskStatus.TODO)
                    .build();
            taskRepository.save(task);

            log.info("✅ Task creato: {} assegnato a {} (Progetto: {})", title, assignee.getId(), project.getCode());
        }
    }

    private void generateSampleData(int records) {
        log.info("🔄 Generazione {} record di esempio...", records);

        if (!isFirstRun()) {
            initializePermissions();
            initializeRoles();
        }

        generateUsers(records);
        generateProjects(records / 2);
        generateTasks(records * 2);

        log.info("✅ Generazione dati completata!");
    }

    private void resetDatabase() {
        log.warn("🗑️  Reset del database...");

        // Elimina tutti i dati in ordine inverso alle dipendenze
        taskRepository.deleteAll();
        projectRepository.deleteAll();
        personaRepository.deleteAll();
        teamRepository.deleteAll();
        departmentRepository.deleteAll();
        accountRepository.deleteAll();
        roleRepository.deleteAll();
        permissionRepository.deleteAll();

        log.info("✅ Database resettato!");
    }

    private void printHelp() {
        System.out.println("\n📖 Comandi disponibili:");
        System.out.println("  --setup, -s              : Avvia il setup wizard");
        System.out.println("  --generate-data [n], -g  : Genera n record di esempio");
        System.out.println("  --reset                  : Reset completo del database");
        System.out.println("  --help, -h               : Mostra questo messaggio");
        System.out.println("  --exit, -e               : Chiude immediatamente l’applicazione");
        System.out.println("  --restart, -r            : Riavvia l’applicazione Spring Boot");
        System.out.println("\nEsempi:");
        System.out.println("  java -jar app.jar --setup");
        System.out.println("  java -jar app.jar --generate-data 50");
        System.out.println("  java -jar app.jar --reset");
        System.out.println("  java -jar app.jar --exit");
        System.out.println("  java -jar app.jar --restart");
    }

    private void printSetupComplete() {
        System.out.println("\n" +
                "╔══════════════════════════════════════════════════════════════╗\n" +
                "║                    ✅ SETUP COMPLETATO!                      ║\n" +
                "╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("🎉 Il sistema è stato configurato correttamente!");
        System.out.println("📱 Puoi ora accedere a:");
        System.out.println("   • API REST: http://localhost:8080/api");
        System.out.println("   • Web Interface: http://localhost:8080");
        System.out.println("   • Swagger UI: http://localhost:8080/swagger-ui.html");
        System.out.println("\n🔐 Credenziali amministratore salvate nel database.");
    }

    // Implementa i metodi di inizializzazione base...
    private void initializePermissions() {
        String[] permissions = {
                "USER_READ", "USER_WRITE", "USER_DELETE",
                "PROJECT_READ", "PROJECT_WRITE", "PROJECT_DELETE",
                "TASK_READ", "TASK_WRITE", "TASK_DELETE",
                "ADMIN_ACCESS", "REPORT_VIEW"
        };

        for (String permName : permissions) {
            if (!permissionRepository.findByName(permName).isPresent()) {
                Permission permission = Permission.builder()
                        .name(permName)
                        .description("Permesso per " + permName.toLowerCase().replace("_", " "))
                        .build();
                permissionRepository.save(permission);
            }
        }
    }

    private void initializeRoles() {
        // Crea ruoli base se non esistono
        createRoleIfNotExists("ADMIN", "Amministratore del sistema");
        createRoleIfNotExists("MANAGER", "Manager di progetto");
        createRoleIfNotExists("USER", "Utente standard");
    }

    private void createRoleIfNotExists(String name, String description) {
        if (!roleRepository.findByName(name).isPresent()) {
            Role role = Role.builder()
                    .name(name)
                    .description(description)
                    .build();
            roleRepository.save(role);
        }
    }
}