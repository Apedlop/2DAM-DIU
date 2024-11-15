package com.example.agenda_v2.modelo.repository.impl;

import com.example.agenda_v2.modelo.ExceptionPersona;
import com.example.agenda_v2.modelo.PersonVO;
import com.example.agenda_v2.modelo.repository.PersonRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class PersonRepositoryImpl implements PersonRepository {

    private final ConexionJDBC conexion = new ConexionJDBC();
    private Statement statement;
    private String sentencia;
    private ArrayList<PersonVO> personas;
    private PersonVO persona;

    public PersonRepositoryImpl() {}

    public ArrayList<PersonVO> ObtenerListaPersonas() throws ExceptionPersona, SQLException {

            Connection conn = this.conexion.conectarBD();
            this.personas = new ArrayList<>();
            this.statement = conn.createStatement();
            this.sentencia = "SELECT * FROM persona";
            ResultSet rs = this.statement.executeQuery(this.sentencia);
            System.out.println("QUE TAL");
            while (rs.next()) {
                Integer codigo = rs.getInt("codigo");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String calle = rs.getString("calle");
                String ciudad = rs.getString("ciudad");
                int codPostal = rs.getInt("cod_postal");
                LocalDate fechaNacimiento = rs.getDate("fech_nac").toLocalDate();
                this.persona = new PersonVO(codigo, nombre, apellido, calle, ciudad, codPostal, fechaNacimiento);
                this.persona.setCodigo(codigo);
                this.personas.add(this.persona);
                System.out.println("hecho");
            }
            System.out.println("hola");
            this.conexion.desconectarBD(conn);
            return this.personas;

    }

    @Override
    public void addPerson(PersonVO p) throws ExceptionPersona {
        try {
            Connection conn = this.conexion.conectarBD();
            this.statement = conn.createStatement();
            this.sentencia = "INSERT INTO persona (nombre, apellido, calle, ciudad, cod_postal, fech_nac) VALUES ('" + p.getNombre() + "','" + p.getApellido() + "','" + p.getCalle() + "','" + p.getCiudad() + "','" + p.getCodPostal() + "','" + p.getFechaNacimiento() + "')";
            this.statement.executeUpdate(this.sentencia);
            this.statement.close();
            this.conexion.desconectarBD(conn);
        } catch (SQLException e) {
            throw new ExceptionPersona("No se ha podido guardar la persona");
        }
    }

    @Override
    public void deletePerson(Integer idPersona) throws ExceptionPersona {
        try {
            Connection conn = this.conexion.conectarBD();
            this.statement = conn.createStatement();
            Statement comando = conn.createStatement();
            String sql = String.format("DELETE FROM persona WHERE codigo = %d", idPersona);
            comando.executeUpdate(sql);
            this.statement.close();
        } catch (SQLException e) {
            throw new ExceptionPersona("No se ha podido eliminar a la persona");
        }
    }

    @Override
    public void editPerson(PersonVO p) throws ExceptionPersona {
        try {
            Connection conn = this.conexion.conectarBD();
            this.statement = conn.createStatement();
            String sql = String.format("UPDATE Persona SET nombre = '%s', apellido = '%s', calle = '%s',  ciudad = '%s', cod_postal = '%s', fech_nac = '%s' WHERE codigo = %d", p.getNombre() + "','" + p.getApellido() + "','" + p.getCalle() + "','" + p.getCiudad() + "','" + p.getCodPostal() + "','" + p.getFechaNacimiento());
            this.statement.executeUpdate(sql);
            this.statement.close();
        } catch (Exception var4) {
            throw new ExceptionPersona("No se ha podido relaizar la edición");
        }
    }


    @Override
    public int codigoPersona() {
        int idPersona = 0;

        try {
            Connection conn = this.conexion.conectarBD();
            Statement comando = conn.createStatement();

            for (ResultSet registro = comando.executeQuery("SELECT codigo FROM persona ORDER BY codigo DESC LIMIT 1");
                 registro.next(); idPersona = registro.getInt("codigo")) {
            }

            return idPersona;
        } catch (SQLException e) {
            throw new ExceptionPersona("No se ha podido cargar la persona");
        }
    }
}
