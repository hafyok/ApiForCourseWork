import Model.User;
//import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository{ //extends JpaRepository<User, Long> {
    // Дополнительные методы для работы с данными пользователя
    public User findByEmail(String email);
    public User findByUsername(String username);
    public int countUser();

}