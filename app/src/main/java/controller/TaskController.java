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
import model.Tasks;
import util.ConnectionFactory;

/**
 *
 * @author WINDOWS 10
 */
public class TaskController {
    
    public void save(Tasks task) {
        
        String sql = " INSERT INTO tasks (idProject,"
                + "name,"
                + "description,"
                + "completed,"
                + "notes,"
                + "deadLine,"
                + "createdAt,"
                + "updatedAt)"
                + " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try{
            //Estabelecendo conexão com o bando de dados
            connection = ConnectionFactory.getConnection();
            
            //Preparando a Query
            statement = connection.prepareStatement(sql);
            
            //Setando valores ao statement
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            
            //Executando a Query
            statement.execute();
        
        }catch(Exception ex) {
            throw new RuntimeException("Erro ao salvar a tarefa "
                    + ex.getMessage(), ex);
        
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
            
    }
    
    public void update(Tasks task) {
        
        String sql = "UPDATE tasks SET "
                + "idProject = ?, "
                + "name = ?,"
                + "description = ?,"
                + "notes = ?,"
                + "completed = ?,"
                + "deadline = ?,"
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
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());   
            statement.setString(4, task.getNotes());
            statement.setBoolean(5, task.isIsCompleted()); 
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(9, task.getId());
            
            //Executando a Query
            statement.execute();
        
        }catch(SQLException ex) {
            throw new RuntimeException("Erro ao atualizar a tarefa "
                    + ex.getMessage(), ex);
        
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
                     
    }
    
    public void removeById(int taskId) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try{
            //Estabelecendo a conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //Preparando a Query
            statement = connection.prepareStatement(sql);
            
            //Setando o valor
            statement.setInt(1, taskId);
            
            //Executando a Query
            statement.execute();
        
        }catch(Exception ex) {            
            throw new RuntimeException("Erro ao deletar tarefa" + ex.getMessage(), ex);
            
        }finally {           
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public List<Tasks> getAll(int idProject) {
        
        String sql = "SELECT * FROM tasks WHERE idProject = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        //Lista e tarefas a ser devolvida quando a chamada do método ocorrer
        List<Tasks> tasks = new ArrayList<Tasks>();
        
        try{
            //Criando conexão
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            //Setando valor correspondente ao filtro de busca
            statement.setInt(1, idProject);
            
            //Valor retornado pela execução da Query
            resultSet = statement.executeQuery();
            
            //Enquanto houver valores a serem percorridos no resultSet
            while(resultSet.next()) {
                
                Tasks task = new Tasks();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setNotes(resultSet.getString("notes"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));
                
                tasks.add(task);
            }
        }catch(SQLException ex) {
            throw new RuntimeException("Erro ao inserir tarefa " + ex.getMessage(), ex);
        }finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        
        //Lista de tarefas criada e carregada do banco de dados
        return tasks;
    }
    
}
