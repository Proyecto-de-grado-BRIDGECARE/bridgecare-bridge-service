# BridgeCare Bridge Service

## Getting Started

### Prerequisites
- Java **17+**
- Maven **3.8+**
- Docker & Docker Compose
- PostgreSQL Database

### Installation

#### Clone the repository
```bash
git clone https://github.com/Proyecto-de-grado-BRIDGECARE/bridgecare-bridge-service.git
cd bridgecare-bridge-service
```

#### Setup environment variables
Copy `.env.example` to `.env` and set the required environment variables:
`cp .env.example .env`

#### Build and Run

To build and run the service, ensure you have a .env file in the project root with the following:
```
GITHUB_USERNAME=bridgecare-bot
GITHUB_TOKEN=<bridgecare-bot-personal-access-token>
```
Copy .env.example to .env and replace `<bridgecare-bot-personal-access-token>` with a valid GitHub PAT with read:packages scope.

##### Local Build and Run
Use the Maven Wrapper to build and run the application locally (works on Linux, macOS, and Windows):

1. **Build the JAR:**
   ```bash
   # Windows:
   .\mvnw.cmd clean package -U

   # Linux:
   ./mvnw clean package -U
   ```
   - The `-U` flag ensures the latest dependencies are fetched (needed only once or after updates).

2. **Run the JAR:**
   ```bash
   java -jar target/auth-0.0.1-SNAPSHOT.jar
   ```

3. **Or Run with Maven Wrapper:**
   ```bash
   # Windows:
   .\mvnw.cmd spring-boot:run

   # Linux:
   ./mvnw spring-boot:run
   ```

##### Docker Build and Run
To build and run the service in a Docker container:

```bash
docker-compose up --build
```
- Ensure Docker and Docker Compose are installed.
- This builds the image and starts the service on port 8080.
- On Windows, run this in Command Prompt or PowerShell with Docker Desktop running.

For a fresh build (e.g., after dependency updates):
```bash
docker-compose build --no-cache
docker-compose up
```

## License
This project is licensed under the MIT License.
