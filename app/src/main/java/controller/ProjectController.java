/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;

/**
 *
 * @author WINDOWS 10
 */
public class ProjectController {
    
    public void save(Project project) {
        
        String sql = " INSERT INTO projects (name,"
                + "description,"
                + "createdAt,"
                + "updatedAt) VALUES ( ?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try{
            //Estabelecendo conexão com o bando de dados
            connection = ConnectionFactory.getConnection();
            
            //Preparando a Query
            statement = connection.prepareStatement(sql);
            
            //Setando valores ao statement
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            
            //Executando a Query
            statement.execute();
        
        }catch(SQLException ex) {
            throw new RuntimeException("Erro ao salvar o projeto ", ex);
        
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
            
    }
    
    public void update(Project project) {
        
        String sql = "UPDATE projects SET "
                + "name = ?,"
                + "description = ?,"
                + "createdAt = ?,"
                + "updatedAt = ?"
                + "WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try{
            //Estabelecendo a conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //Preparando a Query
            statement = connection.prepareStatement(sql);
            
            //Setando os valores do Statement
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());    
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());
            
            //Executando a Query
            statement.execute();
        
        }catch(SQLException ex) {
            throw new RuntimeException("Erro ao atualizar projeto ", ex);
        
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
                     
    }
    
    public void removeById(int projectId){
        String sql = "DELETE FROM projects WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try{
            //Estabelecendo a conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //Preparando a Query
            statement = connection.prepareStatement(sql);
            
            //Setando o valor
            statement.setInt(1, projectId);
            
            //Executando a Query
            statement.execute();
        
        }catch(SQLException ex) {            
            throw new RuntimeException("Erro ao deletar Projeto ", ex);
            
        }finally {           
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public List<Project> getAll() {
        
        String sql = "SELECT * FROM projects";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        //Classe que vai recuperar os dados do banco de dados
        ResultSet resultSet = null;
        
        //Lista e tarefas a ser devolvida quando a chamada do método ocorrer
        List<Project> projects = new ArrayList<Project>();
        
        try{
            //Criando conexão
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            //Valor retornado pela execução da Query
            resultSet = statement.executeQuery();
            
            //Enquanto houver valores a serem percorridos no resultSet
            while(resultSet.next()) {
                
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("createdAt"));
                project.setUpdatedAt(resultSet.getDate("updatedAt"));
                
                projects.add(project);
            }
        }catch(SQLException ex) {
            throw new RuntimeException("Erro ao inserir projeto " + ex.getMessage(), ex);
        }finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        
        //Lista de tarefas criada e carregada do banco de dados
        return projects;
    }
}
