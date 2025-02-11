FROM openjdk:23
LABEL authors="apedlop"

# Copia el archivo JAR de la aplicación Agenda al contenedor
COPY target/Agenda-0.0.1-SNAPSHOT.jar /agendaapp.jar

# Comando para ejecutar la aplicación Agenda dentro del contenedor
CMD ["java", "-jar", "/agendaapp.jar"]
