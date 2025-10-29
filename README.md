# 🎬 Movie Guess Game 🎭  

A **Movie Guessing Game Backend** built with **Spring Boot**, where players guess the movie name based on a randomly provided dialogue. Players get **5 chances** to guess, and if they get stuck, **3 hints** are available. Points increase based on correct guesses.

## 🛠️ Features  

✅ **Random Movie Dialogue:** A random dialogue is shown, and the user has to guess the movie.  
✅ **5 Chances to Guess:** The user has up to **5 attempts** to guess the correct movie.  
✅ **Hint System:** If the user is stuck, they can request up to **3 hints**:  
   1. **Movie Industry** – Hollywood or Bollywood  
   2. **Release Year** – The year the movie was released  
   3. **Lead Actor** – A dialogue from the lead actor  
✅ **Scoring System:**  
   - The user **earns 1 point** for each correct guess.   
✅ **Role-Based Access:**  
   - **User:** Can play the game (guess movies, request hints).  
   - **Admin:** Can play the game and also add new movies & dialogues.  
✅ **Secure API:** JWT-based authentication and authorization.  
✅ **Database Storage:** MySQL stores movie names and dialogues.  

## 🏗️ Tech Stack  

- **Spring Boot** – Backend framework  
- **Spring Security & JWT** – Authentication & authorization  
- **Lombok** – Reduces boilerplate code  
- **MySQL** – Stores movies & dialogues  
- **JPA (Hibernate)** – ORM for database interactions  

## 🚀 How to Play?  

1. A **random movie dialogue** is displayed.  
2. The user enters their guess.  
3. The system validates the answer:  
   - **Correct guess** → The user earns **1 point**.  
   - **Incorrect guess** → Remaining attempts decrease.  
4. If stuck, the user can request **hints** (max 3).  
5. The game ends when:  
   - The user **guesses correctly**.  
   - The user **exhausts all 5 chances**.  

<!--
## ⚙️ Installation & Setup  


1. Clone the repository:  
   ```bash
   git clone https://github.com/your-username/movie-guess-game.git
   cd movie-guess-game
   ```
2. Configure **MySQL Database** in `application.properties`:  
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/movie_guess_game
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   ```
3. Run the application:  
   ```bash
   mvn spring-boot:run
   ```
   -->

## 📌 Future Enhancements  

- **Leaderboard & Ranking System**  
- **More Hint Categories**  
- **Multiplayer Mode**  

🎉 **Contributions are welcome!** Feel free to fork, raise issues, or submit pull requests. 🚀

