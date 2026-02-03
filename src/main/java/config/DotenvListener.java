package config;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
/**
 *
 * @author cpere
 */
@WebListener
public class DotenvListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USER", dotenv.get("DB_USER"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

        System.out.println("✔ Variables .env cargadas correctamente");
    }
}